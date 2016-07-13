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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.custom.TextViewProRegular;
import ru.klops.klops.gcm.QuickstartPreferences;
import ru.klops.klops.gcm.RegistrationIntentService;
import ru.klops.klops.models.feed.News;
import ru.klops.klops.models.feed.Page;
import ru.klops.klops.services.RetrofitServiceGenerator;

public class SplashActivity extends AppCompatActivity {
    final String LOG = "SplashActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    KlopsApplication myApp;
    @BindView(R.id.support)
    TextViewProRegular support;
    Unbinder unbinder;
    private BroadcastReceiver registrationReceiver;
    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        unbinder = ButterKnife.bind(this);
        myApp = KlopsApplication.getINSTANCE();
        Log.d(LOG, "onCreate");
        checkConnection();
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
        PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
        Call<Page> call = api.getAllNews();
        call.enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                if (response.isSuccessful()) {
                    myApp.setFirstPage(response.body());
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Log.d(LOG, "Ошибка доступа..." + t.getLocalizedMessage());
                Toast.makeText(SplashActivity.this, "Не удалось подключение к серверу...", Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder somethingWrong = new AlertDialog.Builder(SplashActivity.this);
                somethingWrong.setIcon(R.drawable.alert_icon).setTitle("Подключение невозможно")
                        .setMessage("В процессе загрузки произошел сбой. Перезагрузите приложение").setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
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
    public void onBackPressed() {
        Log.d(LOG, "onBackPressed");
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
        finish();
    }

    @Override
    protected void onStop() {
        Log.d(LOG, "onStop");
        finish();
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        unbinder.unbind();
        super.onDestroy();
    }

}
