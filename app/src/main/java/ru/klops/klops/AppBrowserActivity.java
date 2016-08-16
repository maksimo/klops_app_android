package ru.klops.klops;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.facebook.appevents.AppEventsLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_browser_activity);
        Log.d(LOG, "onCreate");
        unbinder = ButterKnife.bind(this);
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        url = getIntent().getStringExtra(Constants.URL);
        setUpWeb();
    }

    private void setUpWeb() {
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loader.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loader.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                return false;
            }

        });
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(false);
        browser.getSettings().setDisplayZoomControls(true);
        browser.getSettings().setSupportZoom(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        browser.loadUrl(url);
        browser.setWebChromeClient(new WebChromeClient());
    }

    @OnClick(R.id.backToFeed)
    public void backToFeed() {
        back.startAnimation(alpha);
        onBackPressed();
    }

    @Override
    public void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
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
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG, "onBackPressed");
        finish();
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG, "onDestroy");
        unbinder.unbind();
        finish();
        super.onDestroy();
    }
}
