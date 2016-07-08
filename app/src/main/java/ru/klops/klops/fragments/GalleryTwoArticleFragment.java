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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.adapter.GalleryPagerAdapter;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.article.Content;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.article.Photos;
import ru.klops.klops.utils.Constants;

public class GalleryTwoArticleFragment extends Fragment {
    final String LOG = "GalleryTwoArticle";
    View fragmentView;

    @BindView(R.id.galleryTwoTitle)
    TextView title;
    @BindView(R.id.galleryTwoStatusIcon)
    ImageView statusCircle;
    @BindView(R.id.galleryTwoStatus)
    TextView status;
    @BindView(R.id.galleryTwoAuthor)
    TextView author;
    @BindView(R.id.galleryTwoDate)
    TextView date;
    @BindView(R.id.galleryCameraIconTwo)
    ImageView photoIcon;
    @BindView(R.id.galleryTwoDescription)
    TextView shortdescription;
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
        fragmentView = inflater.inflate(R.layout.gallery_two_article_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        Log.d(LOG, "onCreateView");
        item = getArguments().getParcelable(Constants.ARTICLE);
        setUpImages();
        setUpView();
        return fragmentView;
    }

    private void setUpImages() {
        Log.d(LOG, "setUpImages");
        if (!item.getUpdate_status().equals("")) {
            statusCircle.setVisibility(View.VISIBLE);
        }
    }

    private void setUpView() {
        Log.d(LOG, "setUpView");
        title.setText(String.format("%1$" + 5 + "s", item.getTitle()));
        title.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
        status.setText(item.getUpdate_status());
        status.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        date.setText(item.getDate());
        date.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        author.setText(item.getAuthor());
        author.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        shortdescription.setText(item.getShortdecription());
        shortdescription.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));

    }

    public void formatDefault() {
        title.setTextSize(28);
        status.setTextSize(8);
        date.setTextSize(8);
        author.setTextSize(8);
        shortdescription.setTextSize(16);
    }

    public void formatIncrement() {
        title.setTextSize(29);
        status.setTextSize(9);
        date.setTextSize(9);
        author.setTextSize(9);
        shortdescription.setTextSize(17);
      }

    public void formatDecrement() {
        title.setTextSize(27);
        status.setTextSize(7);
        date.setTextSize(7);
        author.setTextSize(7);
        shortdescription.setTextSize(15);
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