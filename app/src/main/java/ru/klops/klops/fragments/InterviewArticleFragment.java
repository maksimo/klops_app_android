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

public class InterviewArticleFragment extends Fragment {
    final String LOG = "InterviewArticle";
    View fragmentView;

    @BindView(R.id.interviewTitle)
    TextView title;
    @BindView(R.id.interviewStatusIcon)
    ImageView statusCircle;
    @BindView(R.id.interviewStatus)
    TextView status;
    @BindView(R.id.interviewAuthor)
    TextView author;
    @BindView(R.id.interviewDate)
    TextView date;
    @BindView(R.id.interviewCameraIcon)
    ImageView cameraIcon;
    @BindView(R.id.interviewImagePhoto)
    ImageView interviewImagePhoto;
    @BindView(R.id.interviewDescription)
    TextView shortdescription;
    @BindView(R.id.interviewProgress)
    ProgressBar bar;
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
        fragmentView = inflater.inflate(R.layout.interview_article_fragment, container, false);
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
            Picasso.with(getContext()).load(item.getImage()).into(interviewImagePhoto, new Callback() {
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
            interviewImagePhoto.setVisibility(View.GONE);
        }
        if (!item.getUpdate_status().equals("")) {
            statusCircle.setVisibility(View.VISIBLE);
        }
    }

    private void setUpView() {
        Log.d(LOG, "setUpView");
        title.setText(item.getTitle());
        title.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
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
        title.setTextSize(16);
        status.setTextSize(10);
        date.setTextSize(10);
        author.setTextSize(10);
        shortdescription.setTextSize(16);
    }

    public void formatIncrement() {
        title.setTextSize(17);
        status.setTextSize(11);
        date.setTextSize(11);
        author.setTextSize(11);
        shortdescription.setTextSize(17);
    }

    public void formatDecrement() {
        title.setTextSize(15);
        status.setTextSize(9);
        date.setTextSize(9);
        author.setTextSize(9);
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