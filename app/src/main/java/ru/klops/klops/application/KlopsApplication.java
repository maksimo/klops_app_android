package ru.klops.klops.application;

import android.app.Application;

//import com.crashlytics.android.Crashlytics;

//import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;

import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.feed.News;
import ru.klops.klops.models.feed.Page;

public class KlopsApplication extends Application {
    private static final String LOG_TAG = "KlopsAppliction: ";

    private static KlopsApplication INSTANCE;

    private Page firstPage;
    private String token;
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
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        INSTANCE = this;

    }


    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
