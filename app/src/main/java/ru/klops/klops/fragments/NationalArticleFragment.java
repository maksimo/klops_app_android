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

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

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

public class NationalArticleFragment extends Fragment {
    final String LOG = "NationalArticle";
    View fragmentView;

    @BindView(R.id.nationalTitle)
    TextView title;
    @BindView(R.id.nationalDate)
    TextView date;
    @BindView(R.id.nationalAuthor)
    TextView author;
    @BindView(R.id.nationalCameraIcon)
    ImageView cameraIcon;
    @BindView(R.id.nationalDescription)
    TextView shortdescription;
    @BindView(R.id.nationalPhoto)
    ImageView photo;
    @BindView(R.id.nationalProgress)
    ProgressBar bar;
    Unbinder unbinder;
    Item item;
    KlopsApplication app;
    ArticleActivity activity;
    String fullAuthor;

    @Override
    public void onAttach(Context context) {
        Log.d(LOG, "onAttach");
        activity = (ArticleActivity) getActivity();
        app = KlopsApplication.getINSTANCE();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.national_article_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        Log.d(LOG, "onCreateView");
        item = getArguments().getParcelable(Constants.ARTICLE);
        setUpImages();
        setUpView();
        return fragmentView;
    }

    private void setUpImages() {
        Log.d(LOG, "setUpImages");
        if (!item.getOg_image().getUrl().equals("")) {
            Ion.with(getContext()).load(item.getOg_image().getUrl()).progressHandler(new ProgressCallback() {
                @Override
                public void onProgress(long downloaded, long total) {
                    bar.setVisibility(View.VISIBLE);
                }
            }).intoImageView(photo).setCallback(new FutureCallback<ImageView>() {
                @Override
                public void onCompleted(Exception e, ImageView result) {
                    bar.setVisibility(View.GONE);
                }
            });
        } else if (!item.getImage().equals("")) {
            Ion.with(getContext()).load(item.getImage()).progressHandler(new ProgressCallback() {
                @Override
                public void onProgress(long downloaded, long total) {
                    bar.setVisibility(View.VISIBLE);
                }
            }).intoImageView(photo).setCallback(new FutureCallback<ImageView>() {
                @Override
                public void onCompleted(Exception e, ImageView result) {
                    bar.setVisibility(View.GONE);
                }
            });
        } else {
            photo.setVisibility(View.GONE);
        }
    }

    private void setUpView() {
        Log.d(LOG, "setUpView");
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.BELOW, author.getId());
        relativeParams.setMargins(20, -10, 0, 10);
        fullAuthor = item.getSource() + " " + (item.getAuthor());
        if (fullAuthor.length() > 5) {
            author.setVisibility(View.VISIBLE);
        }
        if (fullAuthor.length() > 35) {
            date.setLayoutParams(relativeParams);
            author.setVisibility(View.VISIBLE);
        }
        title.setText(item.getTitle());
        title.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
        date.setText(item.getDate());
        date.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        author.setText(fullAuthor);
        author.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        shortdescription.setText(item.getShortdecription());
        shortdescription.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
    }


    public void formatIncrement() {
        title.setTextSize(30);
        date.setTextSize(12);
        author.setTextSize(12);
        shortdescription.setTextSize(18);
    }

    public void formatDecrement() {
        title.setTextSize(28);
        date.setTextSize(10);
        author.setTextSize(10);
        shortdescription.setTextSize(16);
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