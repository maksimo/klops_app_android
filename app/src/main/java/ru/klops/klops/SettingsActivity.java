package ru.klops.klops;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
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
    Toolbar toolbarSettings;
    int clickChecker = 0;
    Animation alpha;
    Unbinder unbinder;
    String tokenDevice;
    KlopsApplication app;
    AlertDialog.Builder confirmBuilder;
    AlertDialog confirmDialog;
    AlertDialog.Builder mailBuilder;
    AlertDialog mailDialog;
    View confirmLayout;
    View mailLayout;
    TextView confirmTitle;
    TextView confirmText;
    ProgressBar confirmProgress;
    String sharedSubscription;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor innerPrefs;
    SharedPreferences preferences;
    boolean appSubscription;
    TextView mailTitle;
    EditText mailText;
    Button mailPositiveBtn;
    Tracker mTracker;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.settings_activity);
        unbinder = ButterKnife.bind(this);
        confirmLayout = LayoutInflater.from(this).inflate(R.layout.confirm_dialog, null);
        mailLayout = LayoutInflater.from(this).inflate(R.layout.mail_dialog, null);
        alpha = AnimationUtils.loadAnimation(SettingsActivity.this, R.anim.alpha);
        app = KlopsApplication.getINSTANCE();
        mTracker = app.getDefaultTracker();
        switchNotifications.setOnCheckedChangeListener(this);
        sharedSubscription = loadStatement();
        appSubscription = loadInnerStatement();
        loadState();
        initFonts();
        initProgressDialog();
        Log.d(LOG, "onCreate");
    }


    private void initFonts() {
        setSupportActionBar(toolbarSettings);
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

        mailBuilder = new AlertDialog.Builder(this);
        mailBuilder.setView(mailLayout);
        mailBuilder.setCancelable(true);
        mailTitle = (TextView) mailLayout.findViewById(R.id.mailTitle);
        mailText = (EditText) mailLayout.findViewById(R.id.mailText);
        mailPositiveBtn = (Button) mailLayout.findViewById(R.id.mailPositiveBtn);
        mailTitle.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        mailText.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        mailDialog = mailBuilder.create();
        mailPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setType("text/plain");
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString().trim()});
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Klops.ru");
                mailIntent.putExtra(Intent.EXTRA_TEXT, mailText.getText().toString());
                startActivity(Intent.createChooser(mailIntent, "Отправить сообщение"));
                mailDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.confirm_button)
    public void confirm() {
        confirm.startAnimation(alpha);
        onBackPressed();
    }

    @OnClick(R.id.join)
    public void checkBaseUrl() {
        switch (clickChecker) {
            case 0:
                clickChecker++;
                join.startAnimation(alpha);
                Toast.makeText(this, "1й клик", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                clickChecker++;
                join.startAnimation(alpha);
                Toast.makeText(this, "2й клик", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                clickChecker++;
                join.startAnimation(alpha);
                Toast.makeText(this, "3й клик", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                clickChecker++;
                join.startAnimation(alpha);
                Toast.makeText(this, "4й клик", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                clickChecker = 0;
                join.startAnimation(alpha);
                Toast.makeText(this, "5й клик", Toast.LENGTH_SHORT).show();
                joinPhone.setVisibility(View.GONE);
                joinTestBaseUrl.setVisibility(View.VISIBLE);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                joinTestBaseUrl.requestFocus();
                inputMethodManager.showSoftInput(joinTestBaseUrl, 0);
                changeURLOk.setVisibility(View.VISIBLE);
                break;
        }

    }

    @OnClick(R.id.changeURLOk)
    public void submitNewUrl() {
        joinTestBaseUrl.setFocusable(false);
        changeURLOk.setVisibility(View.GONE);
        joinTestBaseUrl.setVisibility(View.GONE);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(joinTestBaseUrl.getWindowToken(), 0);
        saveNewUrl(joinTestBaseUrl.getText().toString());
        Toast.makeText(this, joinTestBaseUrl.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void saveNewUrl(String url) {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.PATH, MODE_PRIVATE).edit();
        editor.putString(Constants.NEW_URL, url);
        editor.commit();
    }

    @OnClick(R.id.logo)
    public void testPush() {
        logo.startAnimation(alpha);
        PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
        Observable<ResponseBody> call = api.giveMeTestPush(app.getToken());
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "response code: 200");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, "response code: 403");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.d(LOG, "response code: 200");
                    }
                });
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        tokenDevice = app.getToken();
        if (isChecked) {
            if (!sharedSubscription.equals(Constants.SUBSCRIBED) | appSubscription != true) {
                saveState(true);
                confirmTitle.setText("Подписка на уведомления...");
                confirmDialog.show();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Settings Activity action")
                        .setAction("Subscribe on push notifs")
                        .build());
                PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
                Observable<ResponseBody> call = api.subscribeNotification(tokenDevice, Constants.PLATFORM);
                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                Log.d(LOG, "response code: 200");
                                saveStatement(Constants.SUBSCRIBED);
                                saveInnerStatement(true);
                                appSubscription = true;
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
            } else {

            }
        } else {
            if (!sharedSubscription.equals(Constants.UNSUBSCRIBED) | appSubscription != false) {
                saveState(false);
                confirmTitle.setText("Отмена подписки...");
                confirmDialog.show();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Settings Activity action")
                        .setAction("UnSubscribe from push notifs")
                        .build());
                PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
                final Observable<ResponseBody> call = api.unSubscribeNotification(tokenDevice, Constants.PLATFORM);
                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                Log.d(LOG, "response code: 200");
                                saveStatement(Constants.UNSUBSCRIBED);
                                saveInnerStatement(false);
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
    }

    private void saveInnerStatement(boolean statement) {
        innerPrefs = getPreferences(MODE_PRIVATE).edit();
        innerPrefs.putBoolean(Constants.INNER_PREFS, statement);
        innerPrefs.apply();
    }

    private Boolean loadInnerStatement() {
        preferences = getPreferences(MODE_PRIVATE);
        appSubscription = preferences.getBoolean(Constants.INNER_PREFS, false);
        return appSubscription;
    }

    public void saveStatement(String subscription) {
        editor = getSharedPreferences(Constants.PATH, MODE_PRIVATE).edit();
        editor.putString(Constants.SUBSCRIPTION, subscription);
        editor.apply();
    }

    public String loadStatement() {
        sharedPreferences = getSharedPreferences(Constants.PATH, MODE_PRIVATE);
        sharedSubscription = sharedPreferences.getString(Constants.SUBSCRIPTION, Constants.UNSUBSCRIBED);
        return sharedSubscription;
    }


    public void saveState(boolean value) {
        editor = getSharedPreferences(Constants.PATH, MODE_PRIVATE).edit();
        editor.putBoolean(Constants.GCM_AVAILABLE, value);
        editor.apply();
    }

    public void loadState() {
        sharedPreferences = getSharedPreferences(Constants.PATH, MODE_PRIVATE);
        switchNotifications.setChecked(sharedPreferences.getBoolean(Constants.GCM_AVAILABLE, false));
    }

//    @OnClick(R.id.socialViber)
//    public void callViber() {
//        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + socialPhone.getText().toString().trim()));
//        call.setPackage("com.viber.voip");
//        startActivity(call);
//    }
//
//    @OnClick(R.id.socialWhats)
//    public void callWhatsApp() {
//        String DisplayName = "Klops.ru";
//        String MobileNumber = "+79097823333";
//        Bitmap bmImage = BitmapFactory.decodeResource(this.getResources(),
//                R.drawable.app_icon_main);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmImage.compress(Bitmap.CompressFormat.JPEG, 80, baos);
//        byte[] Photo = baos.toByteArray();
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//        ops.add(ContentProviderOperation.newInsert(
//                ContactsContract.RawContacts.CONTENT_URI)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
//                .build());
//        if (DisplayName != null) {
//            ops.add(ContentProviderOperation.newInsert(
//                    ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                    .withValue(ContactsContract.Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                    .withValue(
//                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
//                            DisplayName).build());
//        }
//
//        if (MobileNumber != null) {
//            ops.add(ContentProviderOperation.
//                    newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                    .withValue(ContactsContract.Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
//                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
//                    .build());
//        }
//        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                .withValue(ContactsContract.Data.MIMETYPE,
//                        ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.CommonDataKinds.Photo.DATA15,Photo)
//                .build());
//
//        try {
//            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        openApp(this, "com.whatsapp");
//
//    }

    @OnClick(R.id.relativeLayout5)
    public void socialCall(){
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + socialPhone.getText().toString().trim()));
        startActivity(call);
    }



    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            return false;
            //throw new PackageManager.NameNotFoundException();
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return true;
    }

    @OnClick(R.id.joinPhone)
    public void callJoinUs() {
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + joinPhone.getText().toString().trim()));
        startActivity(call);
    }

    @OnClick(R.id.advertisePhone)
    public void callAdvertise() {
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + advertisePhone.getText().toString().trim()));
        startActivity(call);
    }

    @OnClick(R.id.email)
    public void sendMail() {
        mailDialog.show();


    }

    @Override
    protected void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
        FlurryAgent.onStartSession(this, Constants.FLURRY_API_KEY);
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Settings Activity")
                .setAction("Settings Activity Start")
                .build());
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
