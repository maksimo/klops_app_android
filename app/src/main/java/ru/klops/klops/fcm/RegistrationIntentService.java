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
    String subscribeState;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        app = KlopsApplication.getINSTANCE();
        String token = FirebaseInstanceId.getInstance().getToken();
        String senderId = getResources().getString(R.string.gcm_defaultSenderId);
        app.setToken(token);
        Log.d(TAG, "FCM token: " + token);
        checkSubscription();
        firstTimeSubscribe();

    }

    private void checkSubscription() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PATH, MODE_PRIVATE);
        subscribeState = sharedPreferences.getString(Constants.FIRST_TIME_CHECK, Constants.UNSUBSCRIBED);
    }

    private void firstTimeSubscribe() {
        Log.d(TAG, "firstTimeSubscribe");
        if (subscribeState.equals(Constants.UNSUBSCRIBED)) {
            KlopsApi.NotificationApi api = RetrofitServiceGenerator.createService(KlopsApi.NotificationApi.class);
            Observable<ResponseBody> call = api.subscribeNotification(FirebaseInstanceId.getInstance().getToken(), Constants.PLATFORM);
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "firstTimeSubscribe - onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "firstTimeSubscribe - onError" + e.getLocalizedMessage());
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Log.d(TAG, "firstTimeSubscribe - onNext" + responseBody.toString());
                            SharedPreferences.Editor editor = getSharedPreferences(Constants.PATH, MODE_PRIVATE).edit();
                            editor.putString(Constants.FIRST_TIME_CHECK, Constants.SUBSCRIBED);
                            editor.apply();
                        }
                    });
        } else if (subscribeState.equals(Constants.SUBSCRIBED)) {
            Log.d(TAG, "App is already subscribed on push service");
        }
    }

    private void subscribeTopics() throws IOException {
        for (String topic : TOPICS) {
            FirebaseMessaging.getInstance().subscribeToTopic(topic);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

