package ru.klops.klops;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.cutomfonts.TextViewProRegular;
import ru.klops.klops.models.feed.Page;
import ru.klops.klops.services.RetrofitServiceGenerator;

public class SplashActivity extends AppCompatActivity {
    final String LOG = "SplashActivity";
    KlopsApplication myApp;
    @BindView(R.id.support)
    TextViewProRegular support;
    Unbinder unbinder;

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

    private void startDataLoad() {
        Log.d(LOG, "startDataLoad");
        PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
        Call<Page> call = api.getAllNews(1);
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
                Log.d(LOG, "Ошибка доступа...");
                Toast.makeText(SplashActivity.this, "Отсутствует подключение к интернету...", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder somethingWrong = new AlertDialog.Builder(SplashActivity.this);
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
        super.onPause();
        Log.d(LOG, "onPause");
    }

    @Override
    protected void onResume() {
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
