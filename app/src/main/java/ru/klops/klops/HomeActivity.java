package ru.klops.klops;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.fragments.BaseFragment;
import ru.klops.klops.services.ReceiverArticle;
import ru.klops.klops.utils.Constants;


public class HomeActivity extends AppCompatActivity {
    final String LOG = "HomeActivity";
    KlopsApplication app;
    ProgressDialog dialog;
    ReceiverArticle receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(LOG, "onCreate");
        app = KlopsApplication.getINSTANCE();
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Klops.Ru");
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setMessage("Загрузка данных");
        dialog.setIndeterminate(false);
        dialog.show();
        placeBaseFragment();
        dialog.dismiss();
    }


    public void placeBaseFragment() {
        Log.d(LOG, "placeBaseFragment");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.home_container, new BaseFragment())
                .addToBackStack(null).commit();
    }


    public void replaceFragmentFadeIn(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_container, fragment).setCustomAnimations(R.anim.fade_in, R.anim.fade_out).addToBackStack(null).commit();
    }

    public void popBackWithFadeOut(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_container, fragment).setCustomAnimations(R.anim.fade_out, R.anim.fade_in).addToBackStack(null).commit();
    }

    @Override
    protected void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.d(LOG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter(Constants.RECEIVE_ACTION);
        receiver = new ReceiverArticle();
        registerReceiver(receiver, filter);
        Log.d(LOG, "onResume");
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG, "onBackPressed");
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        Log.d(LOG, "onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        Log.d(LOG, "onDestroy");
        super.onDestroy();
    }

}


