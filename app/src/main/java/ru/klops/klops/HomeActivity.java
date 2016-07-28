package ru.klops.klops;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.fragments.BaseFragment;


public class HomeActivity extends AppCompatActivity {
    final String LOG = "HomeActivity";
    KlopsApplication app;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_home);
        Log.d(LOG, "onCreate");
        app = KlopsApplication.getINSTANCE();
        hideKeyboard();
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
                .addToBackStack(null)
                .add(R.id.home_container, new BaseFragment()).commit();
    }


    public void replaceFragmentFadeIn(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.home_container, fragment).setCustomAnimations(R.anim.fade_in, R.anim.fade_out).commit();
    }

    public void popBackWithFadeOut(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.home_container, fragment).setCustomAnimations(R.anim.fade_out, R.anim.fade_in).commit();
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
        Log.d(LOG, "onResume");
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG, "onBackPressed");
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void hideKeyboard(){
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        );
    }

    @Override
    protected void onStop() {
        Log.d(LOG, "onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
    }

}


