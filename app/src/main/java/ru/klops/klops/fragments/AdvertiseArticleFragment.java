package ru.klops.klops.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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

public class AdvertiseArticleFragment extends Fragment {
    final String LOG = "AdvertiseArticle";
    View fragmentView;
    ArticleActivity activity;
    @BindView(R.id.advertisePhoto)
    ImageView advertisePhoto;
    @BindView(R.id.advertiseTitle)
    TextView advertiseTitle;
    @BindView(R.id.advertiseStatusIcon)
    ImageView advertiseStatusIcon;
    @BindView(R.id.advertiseStatus)
    TextView advertiseStatus;
    @BindView(R.id.advertiseAuthor)
    TextView author;
    @BindView(R.id.advertiseDate)
    TextView advertiseDate;
    @BindView(R.id.advertiseCameraIcon)
    ImageView cameraIcon;
    @BindView(R.id.advertiseDescription)
    TextView advertiseDescription;
    @BindView(R.id.advertiseField)
    WebView advertiseField;
    @BindView(R.id.advertiseMegaphone)
    ImageView megaphoneIcon;
    @BindView(R.id.advertiseCommercial)
    TextView advertiseCommercial;
    @BindView(R.id.advertiseMatch)
    TextView advertiseMatch;
    Unbinder unbinder;
    Item item;
    KlopsApplication app;

    @Override
    public void onAttach(Context context) {
        Log.d(LOG, "onAttach");
        super.onAttach(context);
        activity = (ArticleActivity) getActivity();
        app = KlopsApplication.getINSTANCE();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.advertise_article_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        Log.d(LOG, "onCreateView");
        item = getArguments().getParcelable(Constants.ARTICLE);
        setUpImages();
        setUpView();
        return fragmentView;
    }

    private void setUpImages() {
        Log.d(LOG, "setUpImages");
        Picasso.with(getContext()).load(item.getImage()).into(advertisePhoto);
        megaphoneIcon.setVisibility(View.VISIBLE);
        if (!item.getUpdate_status().equals("")) {
            advertiseStatusIcon.setVisibility(View.VISIBLE);
        }
    }


    private void setUpView() {
        Log.d(LOG, "setUpView");
        advertiseTitle.setText(item.getTitle());
        advertiseTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
        advertiseStatus.setText(item.getUpdate_status());
        advertiseStatus.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        advertiseDate.setText(item.getDate());
        advertiseDate.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        author.setText(item.getAuthor());
        author.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        advertiseDescription.setText(item.getShortdecription());
        advertiseDescription.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
        advertiseMatch.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-boldex.ttf"));
        advertiseCommercial.setText("материал размещен на коммерческой основе");
        advertiseCommercial.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));

    }

    public void formatDefault() {
        advertiseTitle.setTextSize(28);
        advertiseStatus.setTextSize(8);
        advertiseDate.setTextSize(8);
        advertiseDescription.setTextSize(16);
        advertiseCommercial.setTextSize(8);
        advertiseMatch.setTextSize(34);
        author.setTextSize(8);

    }

    public void formatIncrement() {
        advertiseTitle.setTextSize(29);
        advertiseStatus.setTextSize(9);
        advertiseDate.setTextSize(9);
        advertiseDescription.setTextSize(17);
        advertiseCommercial.setTextSize(9);
        advertiseMatch.setTextSize(35);
        author.setTextSize(9);

    }

    public void formatDecrement() {
        advertiseTitle.setTextSize(27);
        advertiseStatus.setTextSize(7);
        advertiseDate.setTextSize(7);
        advertiseDescription.setTextSize(15);
        advertiseCommercial.setTextSize(7);
        advertiseMatch.setTextSize(33);
        author.setTextSize(7);

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