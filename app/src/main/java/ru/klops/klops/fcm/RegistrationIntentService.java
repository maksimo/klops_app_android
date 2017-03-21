package ru.klops.klops.fcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.ResponseBody;
import ru.klops.klops.R;
import ru.klops.klops.api.KlopsApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    KlopsApplication app;
    SharedPreferences sharedPreferences;
    String sharedSubscription;
    SharedPreferences.Editor editor;
    String subscribeState;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        app = KlopsApplication.getINSTANCE();
        FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();
        String senderId = getResources().getString(R.string.gcm_defaultSenderId);
        String token = instanceID.getToken();
        Log.d(TAG, "FCM Registration Token: " + token);
        subscribeState = firstTimeSubscribe();
        sendRegistrationToServer(token);
        try {
            subscribeTopics(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer(String token) {
        app.setToken(token);
        checkSubscription();
    }

    private void checkSubscription() {
        if (subscribeState.equals(Constants.UNSUBSCRIBED)) {
            KlopsApi.NotificationApi api = RetrofitServiceGenerator.createService(KlopsApi.NotificationApi.class);
            Observable<ResponseBody> call = api.subscribeNotification(app.getToken(), Constants.PLATFORM);
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "response code: 200");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "response code: 403" + e.getLocalizedMessage());
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Log.d(TAG, "response code: 200" + responseBody.toString());
                            editor = getSharedPreferences(Constants.PATH, MODE_PRIVATE).edit();
                            editor.putString(Constants.FIRST_TIME_CHECK, Constants.SUBSCRIBED);
                            editor.apply();
                        }
                    });
        } else if (subscribeState.equals(Constants.SUBSCRIBED)) {
            Log.d(TAG, "App is already subscribed on push service");
        }
    }

    private String firstTimeSubscribe() {
        sharedPreferences = getSharedPreferences(Constants.PATH, MODE_PRIVATE);
        sharedSubscription = sharedPreferences.getString(Constants.FIRST_TIME_CHECK, Constants.UNSUBSCRIBED);
        return sharedSubscription;
    }

    private void subscribeTopics(String token) throws IOException {
        for (String topic : TOPICS) {
            FirebaseMessaging.getInstance().subscribeToTopic(topic);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

