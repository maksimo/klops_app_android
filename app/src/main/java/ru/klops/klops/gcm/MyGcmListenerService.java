package ru.klops.klops.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.
GcmListenerService;

import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.api.KlopsApi;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListenerService";
    Integer id;
    PendingIntent pendingIntent;
    Intent intent;
    String message;
    String title;

    @Override
    public void onMessageReceived(String from, Bundle data) {
         message = data.getString("message");
        String dataString = data.toString();
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        if (dataString.contains("newsID=")){
            String full[] = dataString.split("newsID=");
            String idPart = full[1];
            String separateId[] = idPart.split(",");
             id = Integer.parseInt(separateId[0]);
            Log.d(TAG, String.valueOf(id));
        }
        sendNotification(message);

    }

    private void sendNotification(final String message) {
        KlopsApi.ArticleApi api = RetrofitServiceGenerator.createService(KlopsApi.ArticleApi.class);
        Observable<Article> call = api.getItemById(id, Constants.ARTICLE_TYPE);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MyGcmListenerService", "Push has been taken");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("MyGcmListenerService", "Failure" + e.getMessage());
                    }

                    @Override
                    public void onNext(Article article) {
                        openArticleIntent(article.getItem(), article.getItem().article_type);

                    }
                });

    }

    private void openArticleIntent(Item item, String article_type) {
        intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(Constants.ITEM, item);
        intent.putExtra(Constants.TYPE, article_type);
        intent.putExtra(Constants.CONTENT_TYPE, Constants.ARTICLE_TYPE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.app_icon_main_transparent_full);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.app_icon_main);
        }
        notificationBuilder.setContentTitle("Последние новости");
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
