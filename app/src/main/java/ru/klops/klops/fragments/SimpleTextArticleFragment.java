package ru.klops.klops.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.utils.Constants;

public class SimpleTextArticleFragment extends Fragment {
    final String LOG = "SimpleTextArticle";
    View fragmentView;
    @BindView(R.id.simpleTitle)
    TextView title;
    @BindView(R.id.simpleStatusIcon)
    ImageView statusCircle;
    @BindView(R.id.simpleStatus)
    TextView status;
    @BindView(R.id.simpleAuthor)
    TextView author;
    @BindView(R.id.simpleDate)
    TextView date;
    @BindView(R.id.simpleTextCameraIcon)
    ImageView cameraIcon;
    @BindView(R.id.simpleDescription)
    TextView shortdescription;
    @BindView(R.id.simpleTextField)
    WebView textField;
    @BindView(R.id.simpleMatch)
    TextView matchArticles;
    Unbinder unbinder;
    Item item;
    KlopsApplication app;
    ArticleActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(LOG, "onAttach");
        activity = (ArticleActivity) getActivity();
        app = KlopsApplication.getINSTANCE();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.simple_text_article_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        Log.d(LOG, "onCreateView");
        item = getArguments().getParcelable(Constants.ARTICLE);
        setUpImages();
        setUpWebViw();
        setUpView();
        return fragmentView;
    }

    private void setUpImages() {
        Log.d(LOG, "setUpImages");
        cameraIcon.setVisibility(View.VISIBLE);
        if (!item.getUpdate_status().equals("")) {
            statusCircle.setVisibility(View.VISIBLE);
        }
    }

    private void setUpWebViw() {
        textField.getSettings().setJavaScriptEnabled(true);
        textField.loadData(item.getText(), "text/html; charset=utf-8", "UTF-8");
        textField.getSettings().setDefaultFontSize(16);
        textField.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        textField.getSettings().setLoadsImagesAutomatically(true);
    }

    private void setUpView() {
        Log.d(LOG, "setUpView");
        title.setText(item.getTitle());
        title.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/akzidenzgroteskpro-md.ttf"));
        status.setText(item.getUpdate_status());
        status.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
        date.setText(item.getDate());
        date.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
        author.setText(item.getAuthor());
        author.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        shortdescription.setText(item.getShortdecription());
        shortdescription.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
        matchArticles.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-boldex.ttf"));
    }

    public void formatDefault() {
        title.setTextSize(16);
        status.setTextSize(8);
        date.setTextSize(8);
        author.setTextSize(8);
        shortdescription.setTextSize(16);
        matchArticles.setTextSize(34);
        textField.getSettings().setDefaultFontSize(16);
    }

    public void formatIncrement() {
        title.setTextSize(17);
        status.setTextSize(9);
        date.setTextSize(9);
        author.setTextSize(9);
        shortdescription.setTextSize(17);
        matchArticles.setTextSize(35);
        textField.getSettings().setDefaultFontSize(17);
    }

    public void formatDecrement() {
        title.setTextSize(15);
        status.setTextSize(7);
        date.setTextSize(7);
        author.setTextSize(7);
        shortdescription.setTextSize(15);
        matchArticles.setTextSize(33);
        textField.getSettings().setDefaultFontSize(15);
    }

    public void shareToSocial() {
        item.getOg_image(); // image for share
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
        super.onPause();
        Log.d(LOG, "onPause");
    }

    @Override
    public void onStop() {
        Log.d(LOG, "onStop");
        super.onStop();
    }

    @Override
    public void onDetach() {
        Log.d(LOG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        Log.d(LOG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
    }
}