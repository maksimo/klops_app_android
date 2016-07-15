package ru.klops.klops;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingsActivity extends AppCompatActivity {
    final String LOG = "SettingsActivity";
    @BindView(R.id.confirm_button)
    TextView confirm;
    @BindView(R.id.switch_notifications)
    ToggleButton switchNotifications;
    @BindView(R.id.notifications)
    TextView notifications;
    @BindView(R.id.quickly)
    TextView quickly;
    @BindView(R.id.contacts)
    TextView contacts;
    @BindView(R.id.mail)
    TextView mail;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.join)
    TextView join;
    @BindView(R.id.joinPhone)
    TextView joinPhone;
    @BindView(R.id.social)
    TextView social;
    @BindView(R.id.socialPhone)
    TextView socialPhone;
    @BindView(R.id.advertise)
    TextView advertise;
    @BindView(R.id.advertisePhone)
    TextView advertisePhone;
    Animation alpha;
    Unbinder unbinder;
    String tokenDevice;
    KlopsApplication app;
    AlertDialog.Builder confirmBuilder;
    AlertDialog confirmDialog;
    View confirmLayout;
    TextView confirmTitle;
    TextView confirmText;
    ProgressBar confirmProgress;
    SharedPreferences preferences;
    boolean btnState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        unbinder = ButterKnife.bind(this);
        confirmLayout = LayoutInflater.from(this).inflate(R.layout.confirm_dialog, null);
        alpha = AnimationUtils.loadAnimation(SettingsActivity.this, R.anim.alpha);
        app = KlopsApplication.getINSTANCE();
        preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        initFonts();
        initProgressDialog();
        Log.d(LOG, "onCreate");
    }


    private void initFonts() {
        confirm.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        notifications.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        quickly.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        contacts.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        mail.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        email.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        join.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        joinPhone.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        social.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        socialPhone.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        advertise.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        advertisePhone.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
    }

    public void initProgressDialog() {
        confirmBuilder = new AlertDialog.Builder(this);
        confirmBuilder.setView(confirmLayout);
        confirmBuilder.setCancelable(false);
        confirmTitle = (TextView) confirmLayout.findViewById(R.id.confirmTitle);
        confirmText = (TextView) confirmLayout.findViewById(R.id.confirmText);
        confirmProgress = (ProgressBar) confirmLayout.findViewById(R.id.confirmProgress);
        confirmTitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        confirmText.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        confirmDialog = confirmBuilder.create();
    }

    @OnClick(R.id.confirm_button)
    public void confirm() {
        confirm.startAnimation(alpha);
        onBackPressed();
    }

    @OnCheckedChanged(R.id.switch_notifications)
    public void registerGms() {
        btnState = loadState();
        tokenDevice = app.getToken();
        if (!btnState) {
            saveState(true);
            switchNotifications.setChecked(true);
            confirmTitle.setText("Подписка на уведомления...");
            confirmDialog.show();
            PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
            Observable<ResponseBody> call = api.subscribeNotification(tokenDevice, Constants.PLATFORM);
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            Log.d(LOG, "response code: 200");
                            Toast.makeText(SettingsActivity.this, "Подписка оформлена...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(LOG, "response code: 403" + e.getLocalizedMessage());
                            confirmDialog.dismiss();
                            Toast.makeText(SettingsActivity.this, "Сервис временно недоступен...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Log.d(LOG, "response code: 200" + responseBody.toString());
                            AndroidSchedulers.reset();
                            confirmDialog.dismiss();
                        }
                    });


        } else if (btnState) {
            saveState(false);
            switchNotifications.setChecked(false);
            confirmTitle.setText("Отмена подписки...");
            confirmDialog.show();
            PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
            final Observable<ResponseBody> call = api.unSubscribeNotification(tokenDevice, Constants.PLATFORM);
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            Log.d(LOG, "response code: 200");
                            Toast.makeText(SettingsActivity.this, "Подписка отменена...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(LOG, "response code: 403" + e.getLocalizedMessage());
                            confirmDialog.dismiss();
                            Toast.makeText(SettingsActivity.this, "Сервис временно недоступен...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Log.d(LOG, "response code: 200" + responseBody.toString());
                            AndroidSchedulers.reset();
                            confirmDialog.dismiss();
                        }
                    });
        }
    }

   public void saveState(boolean value){
       SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putBoolean(Constants.GCM_AVAILABLE, value);
       editor.commit();
   }

    public boolean loadState() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean state = sharedPreferences.getBoolean(Constants.GCM_AVAILABLE, true);
        return state;
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
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        Log.d(LOG, "onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        finish();
        super.onDestroy();
    }
}
