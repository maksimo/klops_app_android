package ru.klops.klops.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import ru.klops.klops.ArticleActivity;
import ru.klops.klops.HomeActivity;
import ru.klops.klops.R;
import ru.klops.klops.SettingsActivity;
import ru.klops.klops.api.PageApi;
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
        PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
        Observable<Article> call = api.getItemById(id);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d("SearchAdapter", "Loading search article complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("SearchAdapter", "Loading failed");
                    }

                    @Override
                    public void onNext(Article article) {
                        openArticleIntent(article.getItem(), article.getItem().article_type);

                    }
                });


//        Intent intent = new Intent(this, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);


    }

    private void openArticleIntent(Item item, String article_type) {
        intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(Constants.ITEM, item);
        intent.putExtra(Constants.TYPE, article_type);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.app_icon_transparent);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.app_icon_main);
        }
        notificationBuilder.setContentTitle("Важные новости");
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
