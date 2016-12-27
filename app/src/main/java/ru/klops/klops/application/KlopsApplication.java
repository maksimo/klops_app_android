package ru.klops.klops.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

import java.text.Collator;

import io.fabric.sdk.android.Fabric;
import ru.klops.klops.R;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.feed.Page;
import ru.klops.klops.models.popular.Popular;
import ru.klops.klops.utils.Constants;

public class KlopsApplication extends Application {
    private static final String LOG_TAG = "KlopsAppliction: ";
    private static KlopsApplication INSTANCE;
    private Page firstPage;
    private String token;
    private Item item;
    private Popular popularPage;
    private Tracker tracker;
    private int photoPosition;
    private int flag;
    private String state;
    private String intentData;


    public static KlopsApplication getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        INSTANCE = this;
        FlurryAgent.setLogEnabled(false);
        FlurryAgent.init(this, Constants.FLURRY_API_KEY);
        YandexMetricaConfig.Builder configBuilder = YandexMetricaConfig.newConfigBuilder(Constants.YANDEX_KEY);
        configBuilder.setLogEnabled();
        YandexMetricaConfig extendedConfig = configBuilder.build();
        YandexMetrica.activate(getApplicationContext(), extendedConfig);
        YandexMetrica.enableActivityAutoTracking(this);

    }

    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            tracker = analytics.newTracker(R.xml.analytics_tracker);
            tracker.enableAutoActivityTracking(true);
        }
        return tracker;
    }

    public String loadBaseURL() {
        SharedPreferences sharedPreferences = getINSTANCE().getSharedPreferences(Constants.PATH, getINSTANCE().MODE_PRIVATE);
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

    public void savePhotoPosition(int position) {
        this.photoPosition = position;
    }

    public int getPhotoPosition() {
        return photoPosition;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setIntentData(String intentData) {
        this.intentData = intentData;
    }

    public String getIntentData() {
        return intentData;
    }
}
