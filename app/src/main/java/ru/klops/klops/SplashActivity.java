package ru.klops.klops;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.klops.klops.api.KlopsApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.gcm.QuickstartPreferences;
import ru.klops.klops.gcm.RegistrationIntentService;
import ru.klops.klops.models.feed.Page;
import ru.klops.klops.models.popular.Popular;
import ru.klops.klops.services.RetrofitServiceGenerator;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    final String LOG = "SplashActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    KlopsApplication myApp;
    Unbinder unbinder;
    private BroadcastReceiver registrationReceiver;
    private boolean isReceiverRegistered;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.splash_activity);
        unbinder = ButterKnife.bind(this);
        myApp = KlopsApplication.getINSTANCE();
        Log.d(LOG, "onCreate");
        checkAPIbaseURL();
    }

    private void checkAPIbaseURL() {
        String baseUrl = myApp.loadBaseURL();
        if (!baseUrl.equals("https://klops.ru/api/")) {
            final AlertDialog.Builder apiChanged = new AlertDialog.Builder(SplashActivity.this);
            apiChanged.setIcon(R.drawable.alert_icon).setTitle("Ошибка приложения").setCancelable(false)
                    .setMessage("Настройки API приложения были изменены. Хотите продолжить работу?").setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    finish();
                    startActivity(intent);
                }
            }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        } else {
            checkConnection();
        }
    }

    private void checkConnection() {
        Log.d(LOG, "checkConnection");
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNwInfo = conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNwInfo = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        AlertDialog.Builder alertConnection = new AlertDialog.Builder(SplashActivity.this);
        if (mobileNwInfo.isConnected() || wifiNwInfo.isConnected()) {
            startGoogleServices();
            startDataLoad();
        } else if (!mobileNwInfo.isConnected() || !wifiNwInfo.isConnected()) {
            alertConnection.setIcon(R.drawable.alert_icon).setTitle("Подключение невозможно")
                    .setMessage("Пожалуйста проверьте интернет или Wi-Fi соединение вашего устройства")
                    .setPositiveButton("Настройки", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                        }
                    }).setNegativeButton("Выйти", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        }
    }

    private void startGoogleServices() {
        Log.d(LOG, "startGoogleServices");
        registrationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                data = intent.getDataString();
            }
        };
        registerReceiver();
        if (checkPlayService()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(registrationReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    private boolean checkPlayService() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(LOG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void startDataLoad() {
        Log.d(LOG, "startDataLoad");
        KlopsApi.FeedApi apiNew = RetrofitServiceGenerator.createService(KlopsApi.FeedApi.class);
        Observable<Page> callNew = apiNew.getAllNews();
        callNew.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Page>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "Loading data complete...");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, "Ошибка доступа..." + e.getLocalizedMessage());
                        Toast.makeText(SplashActivity.this, "Не удалось подключение к серверу...", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder somethingWrong = new AlertDialog.Builder(SplashActivity.this);
                        somethingWrong.setIcon(R.drawable.alert_icon).setTitle("Подключение невозможно")
                                .setMessage("Не удалось подключиться к серверу. Попробуйте позже").setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
                    }

                    @Override
                    public void onNext(Page page) {
                        myApp.setFirstPage(page);
                        KlopsApi.FeedApi apiPopular = RetrofitServiceGenerator.createService(KlopsApi.FeedApi.class);
                        Observable<Popular> callPopular = apiPopular.getPopularNews();
                        callPopular.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Popular>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.d(LOG, "Loading popular news complete...");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(LOG, "Ошибка доступа..." + e.getLocalizedMessage());
                                        Toast.makeText(SplashActivity.this, "Не удалось загрузить дополнительные данные...", Toast.LENGTH_SHORT).show();
                                        final AlertDialog.Builder somethingWrong = new AlertDialog.Builder(SplashActivity.this);
                                        somethingWrong.setIcon(R.drawable.alert_icon).setTitle("Ошибка данных")
                                                .setMessage("Желаете продолжить?").setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                                finish();
                                                startActivity(intent);
                                            }
                                        }).show();
                                    }

                                    @Override
                                    public void onNext(Popular popular) {
                                        myApp.setPopularPage(popular);
                                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG, "onStart");
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registrationReceiver);
        isReceiverRegistered = false;
        super.onPause();
        Log.d(LOG, "onPause");
    }

    @Override
    protected void onResume() {
        registerReceiver();
        super.onResume();
        Log.d(LOG, "onResume");
    }


    @Override
    protected void onStop() {
        Log.d(LOG, "onStop");
        finish();
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        Log.d(LOG, "onBackPressed");
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        unbinder.unbind();
        finish();
        super.onDestroy();
    }

}
