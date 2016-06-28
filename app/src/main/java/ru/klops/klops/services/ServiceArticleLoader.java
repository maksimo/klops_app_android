package ru.klops.klops.services;


import android.app.IntentService;
import android.content.Intent;

import java.io.IOException;

import retrofit2.Call;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.utils.Constants;

public class ServiceArticleLoader extends IntentService{


    public ServiceArticleLoader() {
        super(ServiceArticleLoader.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Integer articleId = intent.getIntExtra(Constants.ARTICLE_ID, 0);
        PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
        Call<Article> call = api.getItemById(articleId);
        Item article = null;
        try {
            article = call.execute().body().getItem();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent broadcasetIntent = new Intent(this, ReceiverArticle.class);
        broadcasetIntent.setAction(Constants.RECEIVE_ACTION);
        broadcasetIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcasetIntent.putExtra(Constants.ARTICLE, article);
        sendBroadcast(broadcasetIntent);
    }
}
