package ru.klops.klops.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.api.KlopsApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class KlopsMessagingService extends FirebaseMessagingService {
    private static final String TAG = "KlopsMessagingService";
    Integer id;
    PendingIntent pendingIntent;
    Intent intent;
    String message;
    KlopsApplication application = KlopsApplication.getINSTANCE();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        id = Integer.parseInt(remoteMessage.getData().get("newsID"));
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        createNotification(notification, id);
    }

    private void createNotification(final RemoteMessage.Notification notification, int pageId) {
        KlopsApi.ArticleApi api = RetrofitServiceGenerator.createService(KlopsApi.ArticleApi.class);
        Observable<Article> call = api.getItemById(pageId, Constants.ARTICLE_TYPE);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Push has been taken");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Failure" + e.getMessage());
                    }

                    @Override
                    public void onNext(Article article) {
                        openArticleIntent(article.getItem(), article.getItem().article_type, notification);

                    }
                });
    }


    private void openArticleIntent(Item item, String article_type, RemoteMessage.Notification notification) {
        Random random = new Random();
        int m = random.nextInt(9999 - 1000);
        intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(Constants.ITEM, item);
        intent.putExtra(Constants.TYPE, article_type);
        intent.putExtra(Constants.CONTENT_TYPE, Constants.ARTICLE_TYPE);
        application.setFlag(1);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, m, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.app_icon_main_transparent_full);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.app_icon_main);
        }

        notificationBuilder.setContentTitle(notification.getTitle());
        notificationBuilder.setContentText(notification.getBody());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notificationBuilder.build());
    }

}
