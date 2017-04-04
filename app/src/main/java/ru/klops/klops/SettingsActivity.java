package ru.klops.klops;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import ru.klops.klops.api.KlopsApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingsActivity extends AppCompatActivity {
    final String LOG = "SettingsActivity";
    BottomSheetBehavior bottomSheetBehavior;
    @BindView(R.id.settingsCoordinator)
    CoordinatorLayout mMainLayout;
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
    @BindView(R.id.socialViber)
    TextView socialViber;
    @BindView(R.id.socialWhats)
    TextView socialWhatsApp;
    @BindView(R.id.socialPhone)
    TextView socialPhone;
    @BindView(R.id.advertise)
    TextView advertise;
    @BindView(R.id.advertisePhone)
    TextView advertisePhone;
    @BindView(R.id.joinTestBaseUrl)
    EditText joinTestBaseUrl;
    @BindView(R.id.changeURLOk)
    Button changeURLOk;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.toolbarSettings)
    RelativeLayout toolbarSettings;
    @BindView(R.id.socialViberLayer)
    RelativeLayout socialViberLayer;
    @BindView(R.id.viberLogo)
    ImageView viber;
    @BindView(R.id.socialWhatsappLayer)
    RelativeLayout socialWhatsappLayer;
    @BindView(R.id.whatsappLogo)
    ImageView whatsapp;
    @BindView(R.id.socialCallLayer)
    RelativeLayout socialCallLayer;
    @BindView(R.id.callLogo)
    ImageView call;
    Animation alpha;
    Unbinder unbinder;
    KlopsApplication app;
    AlertDialog.Builder confirmBuilder;
    AlertDialog confirmDialog;
    View confirmLayout;
    TextView confirmTitle;
    TextView confirmText;
    ProgressBar confirmProgress;
    Tracker mTracker;
    View bottomSheet;
    String subScriptionStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.settings_activity);
        unbinder = ButterKnife.bind(this);
        bottomSheet = findViewById(R.id.socialBottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        confirmLayout = LayoutInflater.from(this).inflate(R.layout.confirm_dialog, null);
        alpha = AnimationUtils.loadAnimation(SettingsActivity.this, R.anim.alpha);
        app = KlopsApplication.getINSTANCE();
        mTracker = app.getDefaultTracker();
        initViews();
        getUserPreferences();
        Log.d(LOG, "onCreate");
    }

    private void getUserPreferences() {
        SharedPreferences preferences = getSharedPreferences(Constants.PATH, MODE_PRIVATE);
        subScriptionStatus = preferences.getString(Constants.FIRST_TIME_SUBSCRIBE, Constants.SUBSCRIBED);
        switch (subScriptionStatus){
            case Constants.SUBSCRIBED:
                switchNotifications.setChecked(true);
                break;
            case Constants.UNSUBSCRIBED:
                switchNotifications.setChecked(false);
                break;
        }
    }


    private void initViews() {
        confirm.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        notifications.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        quickly.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        contacts.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        mail.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        email.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        join.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        joinPhone.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        joinTestBaseUrl.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        socialViber.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        socialWhatsApp.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        socialPhone.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        advertise.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        advertisePhone.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));

        confirmBuilder = new AlertDialog.Builder(this);
        confirmBuilder.setView(confirmLayout);
        confirmBuilder.setCancelable(false);
        confirmTitle = (TextView) confirmLayout.findViewById(R.id.confirmTitle);
        confirmText = (TextView) confirmLayout.findViewById(R.id.confirmText);
        confirmProgress = (ProgressBar) confirmLayout.findViewById(R.id.confirmProgress);
        confirmTitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        confirmText.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        confirmDialog = confirmBuilder.create();

        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(LOG, "onCheckedChanged : " + String.valueOf(isChecked));
                if (isChecked) {
                    confirmTitle.setText("Подписка на уведомления...");
                    confirmDialog.show();
                    KlopsApi.NotificationApi api = RetrofitServiceGenerator.createService(KlopsApi.NotificationApi.class);
                    Observable<ResponseBody> call = api.subscribeNotification(app.getToken(), Constants.PLATFORM);
                    call.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseBody>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(LOG, "onCheckedChanged - onCompleted");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(LOG, "onCheckedChanged - onError " + e.getLocalizedMessage());
                                    confirmDialog.dismiss();
                                }

                                @Override
                                public void onNext(ResponseBody responseBody) {
                                    Log.d(LOG, "onCheckedChanged - onNext");
                                    confirmDialog.dismiss();
                                    SharedPreferences.Editor editor = getSharedPreferences(Constants.PATH, MODE_PRIVATE).edit();
                                    editor.putString(Constants.FIRST_TIME_SUBSCRIBE, Constants.SUBSCRIBED);
                                    editor.apply();
                                    showMessage("Вы подписались на уведомления");
                                }
                            });
                } else {
                    confirmTitle.setText("Отмена подписки...");
                    confirmDialog.show();
                    KlopsApi.NotificationApi api = RetrofitServiceGenerator.createService(KlopsApi.NotificationApi.class);
                    Observable<ResponseBody> call = api.unSubscribeNotification(app.getToken(), Constants.PLATFORM);
                    call.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseBody>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(LOG, "onCheckedChanged - onCompleted");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(LOG, "onCheckedChanged - onError " + e.getLocalizedMessage());
                                    confirmDialog.dismiss();
                                }

                                @Override
                                public void onNext(ResponseBody responseBody) {
                                    Log.d(LOG, "onCheckedChanged - onNext");
                                    confirmDialog.dismiss();
                                    SharedPreferences.Editor editor = getSharedPreferences(Constants.PATH, MODE_PRIVATE).edit();
                                    editor.putString(Constants.FIRST_TIME_SUBSCRIBE, Constants.UNSUBSCRIBED);
                                    editor.apply();
                                    showMessage("Вы отменили подписку на уведомления");
                                }
                            });
                }
            }
        });
    }

    public void showMessage(String message){
        Snackbar snackbar = Snackbar.make(mMainLayout,
                message,
                Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.blackText));
        textView.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        snackbar.show();
    }

    @OnClick(R.id.mainSettingsLayer)
    public void closeBottomSheer() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @OnClick(R.id.confirm_button)
    public void confirm() {
        confirm.startAnimation(alpha);
        onBackPressed();
    }

    @OnClick(R.id.relativeLayout5)
    public void socialCall() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @OnClick(R.id.socialViberLayer)
    public void openViber() {
        socialViberLayer.startAnimation(alpha);
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + socialPhone.getText().toString().trim()));
        call.setPackage("com.viber.voip");
        startActivity(call);
    }

    @OnClick(R.id.socialWhatsappLayer)
    public void openWhatsapp() {
        socialWhatsappLayer.startAnimation(alpha);
        String DisplayName = "Klops.ru";
        String MobileNumber = "+79097823333";
        Bitmap bmImage = BitmapFactory.decodeResource(SettingsActivity.this.getResources(),
                R.drawable.app_icon_main);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmImage.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] Photo = baos.toByteArray();
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        DisplayName).build());

        ops.add(ContentProviderOperation.
                newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Photo.DATA15, Photo)
                .build());

        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }

        openApp(SettingsActivity.this, "com.whatsapp");
    }

    @OnClick(R.id.socialCallLayer)
    public void openDialDialog() {
        socialCallLayer.startAnimation(alpha);
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + socialPhone.getText().toString().trim()));
        startActivity(call);
    }

    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            return false;
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return true;
    }

    @OnClick(R.id.relativeLayout6)
    public void callJoinUs() {
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + joinPhone.getText().toString().trim()));
        startActivity(call);
    }

    @OnClick(R.id.relativeLayout7)
    public void callAdvertise() {
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + advertisePhone.getText().toString().trim()));
        startActivity(call);
    }

    @OnClick(R.id.relativeLayout4)
    public void sendMail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String aEmailList[] = {"news@klops.ru"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, aEmailList);
        startActivity(emailIntent);
    }

    @Override
    protected void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
        FlurryAgent.onStartSession(this, Constants.FLURRY_API_KEY);
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
        FlurryAgent.onEndSession(this);
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        finish();
        super.onDestroy();
    }
}
