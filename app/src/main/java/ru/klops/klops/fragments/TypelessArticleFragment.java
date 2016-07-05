package ru.klops.klops.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.utils.Constants;

public class TypelessArticleFragment extends Fragment {
    final String LOG = "ImportantArticle";
    View fragmentView;

    @BindView(R.id.typelessPhoto)
    ImageView photo;
    @BindView(R.id.typelessTitle)
    TextView title;
    @BindView(R.id.typelessStatusIcon)
    ImageView statusCircle;
    @BindView(R.id.typelessStatus)
    TextView status;
    @BindView(R.id.typelessAuthor)
    TextView author;
    @BindView(R.id.typelessDate)
    TextView date;
    @BindView(R.id.typelessCameraIcon)
    ImageView cameraIcon;
    @BindView(R.id.typelessDescription)
    TextView shortdescription;
    @BindView(R.id.typelessField)
    WebView textField;
    @BindView(R.id.typelessMatch)
    TextView matchArticles;
    @BindView(R.id.typelessProgress)
    ProgressBar bar;
    @BindView(R.id.typelessImageLayer)
    RelativeLayout typelessImageLayer;
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
        fragmentView = inflater.inflate(R.layout.typeless_article_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        item = getArguments().getParcelable(Constants.ARTICLE);
        Log.d(LOG, "onCreateView");
        setUpImages();
        setUpWebViw();
        setUpView();
        return fragmentView;
    }

    private void setUpImages() {
        Log.d(LOG, "setUpImages");
        if (!item.getImage().equals("")) {
            Picasso.with(getContext()).load(item.getImage()).into(photo, new Callback() {
                @Override
                public void onSuccess() {
                    bar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    bar.setVisibility(View.VISIBLE);
                }
            });
        }else {
            typelessImageLayer.setVisibility(View.GONE);
        }
        if (!item.getUpdate_status().equals("")) {
            statusCircle.setVisibility(View.VISIBLE);
        }
    }

    private void setUpWebViw() {
        Log.d(LOG, "setUpWebView");
        textField.getSettings().setJavaScriptEnabled(true);
        textField.loadData(item.getText(), "text/html; charset=utf-8", "UTF-8");
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

