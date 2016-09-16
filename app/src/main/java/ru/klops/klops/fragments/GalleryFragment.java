package ru.klops.klops.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.utils.Constants;

public class GalleryFragment extends Fragment{
    final String LOG = "GalleryFragment";
    View fragmentView;

    @BindView(R.id.galleryTitle)
    TextView title;
    @BindView(R.id.galleryAuthor)
    TextView author;
    @BindView(R.id.galleryDate)
    TextView date;
    @BindView(R.id.galleryCamera)
    ImageView camera;
    @BindView(R.id.galleryDescription)
    TextView shortdescription;
    Unbinder unbinder;
    Item item;
    KlopsApplication app;
    ArticleActivity activity;
    String fullAuthor;

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
        fragmentView = inflater.inflate(R.layout.gallery_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        item = getArguments().getParcelable(Constants.ARTICLE);
        setUpView();
        Log.d(LOG, "onCreateView");
        return fragmentView;
    }

    private void setUpView() {
        Log.d(LOG, "setUpView");
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.BELOW, author.getId());
        relativeParams.setMargins(20, -10, 0, 0);
        fullAuthor = item.getSource() + " " + (item.getAuthor());
        if (fullAuthor.length() > 5) {
            author.setVisibility(View.VISIBLE);
        }
        if (fullAuthor.length() > 35) {
            date.setLayoutParams(relativeParams);
            author.setVisibility(View.VISIBLE);
        }
        title.setText("     " + item.getTitle());
        title.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
        date.setText(item.getDate());
        date.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        author.setText(fullAuthor);
        author.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        shortdescription.setText(Html.fromHtml(item.getShortdecription()));
        shortdescription.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));

    }

    public void formatIncrement() {
        title.setTextSize(27);
        date.setTextSize(12);
        author.setTextSize(12);
        shortdescription.setTextSize(18);
    }

    public void formatDecrement() {
        title.setTextSize(25);
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
