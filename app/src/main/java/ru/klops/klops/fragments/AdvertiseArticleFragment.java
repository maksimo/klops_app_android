package ru.klops.klops.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.adapter.GalleryPagerAdapter;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.article.Content;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.article.Photos;
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
    @BindView(R.id.advertiseMegaphone)
    ImageView megaphoneIcon;
    @BindView(R.id.advertiseCommercial)
    TextView advertiseCommercial;
    @BindView(R.id.advertiseProgress)
    ProgressBar bar;
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
        if (!item.getImage().equals("")) {
            Picasso.with(getContext()).load(item.getImage()).into(advertisePhoto, new Callback() {
                @Override
                public void onSuccess() {
                    bar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    bar.setVisibility(View.VISIBLE);
                }
            });
        } else {
            advertisePhoto.setVisibility(View.GONE);
        }
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
        advertiseCommercial.setText("материал размещен на коммерческой основе");
        advertiseCommercial.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
    }

    public void formatDefault() {
        advertiseTitle.setTextSize(28);
        advertiseStatus.setTextSize(8);
        advertiseDate.setTextSize(8);
        advertiseDescription.setTextSize(16);
        advertiseCommercial.setTextSize(8);
        author.setTextSize(8);
    }

    public void formatIncrement() {
        advertiseTitle.setTextSize(29);
        advertiseStatus.setTextSize(9);
        advertiseDate.setTextSize(9);
        advertiseDescription.setTextSize(17);
        advertiseCommercial.setTextSize(9);
        author.setTextSize(9);
    }

    public void formatDecrement() {
        advertiseTitle.setTextSize(27);
        advertiseStatus.setTextSize(7);
        advertiseDate.setTextSize(7);
        advertiseDescription.setTextSize(15);
        advertiseCommercial.setTextSize(7);
        author.setTextSize(7);
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