package ru.klops.klops.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.klops.klops.ArticleActivity;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.utils.Constants;


public class ReceiverArticle extends BroadcastReceiver {

    public ReceiverArticle() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Item response = intent.getParcelableExtra(Constants.ARTICLE);
        Intent intentActivity = new Intent(context, ArticleActivity.class);
        intentActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentActivity.putExtra(Constants.ITEM, response);
        context.startActivity(intentActivity);

    }
}