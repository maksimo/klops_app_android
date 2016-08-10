package ru.klops.klops.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.widget.ImageView;

//import com.crashlytics.android.Crashlytics;

//import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;
import java.util.List;

import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.feed.News;
import ru.klops.klops.models.feed.Page;
import ru.klops.klops.models.popular.Popular;
import ru.klops.klops.utils.Constants;

public class KlopsApplication extends Application {
    private static final String LOG_TAG = "KlopsAppliction: ";
    VKAccessTokenTracker accessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(@Nullable VKAccessToken oldToken, @Nullable VKAccessToken newToken) {
            if (newToken == null) {
                Log.d(LOG_TAG, "VK token is invalid");
            }
        }
    };
    private static KlopsApplication INSTANCE;

    private Page firstPage;
    private String token;
    private Item item;
    private Popular popularPage;


    public static KlopsApplication getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
        VKSdk.initialize(getApplicationContext());
        accessTokenTracker.startTracking();
        INSTANCE = this;

    }

    public String loadBaseURL() {
        SharedPreferences sharedPreferences =  getINSTANCE().getSharedPreferences(Constants.PATH, getINSTANCE().MODE_PRIVATE);
        String subscription = sharedPreferences.getString(Constants.NEW_URL, "https://klops.ru/api/");
        return subscription;
    }
    public Page getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Page firstPage) {
        this.firstPage = firstPage;
    }

    public Popular getPopularPage() {
        return popularPage;
    }

    public void setPopularPage(Popular popularPage) {
        this.popularPage = popularPage;
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
