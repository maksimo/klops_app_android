package ru.klops.klops.application;

import android.app.Application;

//import com.crashlytics.android.Crashlytics;

//import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;

import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.feed.News;
import ru.klops.klops.models.feed.Page;

public class KlopsApplication extends Application {
    private static final String LOG_TAG = "KlopsAppliction: ";

    private static KlopsApplication INSTANCE;

    private Page firstPage;
    private ArrayList<News> nextData;
    private String searchQuery;
    private ArrayList<News> searchData;
    private String token;
    private News requestedArticle;
    private Item item;

    public static KlopsApplication getINSTANCE() {
        return INSTANCE;
    }

    public Page getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Page firstPage) {
        this.firstPage = firstPage;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        INSTANCE = this;
    }

    public ArrayList<News> getNextData() {
        return nextData;
    }

    public void setNextData(ArrayList<News> nextData) {
        this.nextData = nextData;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public ArrayList<News> getSearchData() {
        return searchData;
    }

    public void setSearchData(ArrayList<News> searchData) {
        this.searchData = searchData;
    }

    public int checkSize() {
        return nextData.size();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setRequestedArticle(News requestedArticle) {
        this.requestedArticle = requestedArticle;
    }

    public News getRequestedArticle() {
        return requestedArticle;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
