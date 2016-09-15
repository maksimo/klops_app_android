package ru.klops.klops;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.klops.klops.R;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.utils.Constants;

public class AppBrowserActivity extends AppCompatActivity {
    final String LOG = "ArticleActivity";
    Unbinder unbinder;
    Animation alpha;
    String url;
    @BindView(R.id.backToFeed)
    ImageView back;
    @BindView(R.id.browserWindow)
    WebView browser;
    @BindView(R.id.browserProgress)
    ProgressBar loader;
    KlopsApplication mApp;
    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_browser_activity);
        Log.d(LOG, "onCreate");
        unbinder = ButterKnife.bind(this);
        mApp = KlopsApplication.getINSTANCE();
        mTracker = mApp.getDefaultTracker();
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        url = getIntent().getStringExtra(Constants.URL);
        setUpWeb();
    }

    private void setUpWeb() {
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.getSettings().setDisplayZoomControls(true);
        browser.getSettings().setSupportZoom(true);
        browser.loadUrl(url);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loader.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loader.setVisibility(View.GONE);
                WindowManager manager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics metrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(metrics);
                metrics.widthPixels /= metrics.density;
                view.loadUrl("javascript:var scale = " + metrics.widthPixels + " / document.body.scrollWidth; document.body.style.zoom = scale;");
            }
        });
        browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        browser.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        browser.setWebChromeClient(new WebChromeClient());
    }

    @OnClick(R.id.backToFeed)
    public void backToFeed() {
        back.startAnimation(alpha);
        finish();
    }

    @Override
    public void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
        FlurryAgent.onStartSession(this, Constants.FLURRY_API_KEY);
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Browser Activity")
                .setAction("Browser Activity Start")
                .build());
    }

    @Override
    public void onResume() {
        Log.d(LOG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(LOG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(LOG, "onStop");
        super.onStop();
        FlurryAgent.onEndSession(this);
        finish();
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG, "onBackPressed");
        finish();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG, "onDestroy");
        unbinder.unbind();
        super.onDestroy();
    }
}
