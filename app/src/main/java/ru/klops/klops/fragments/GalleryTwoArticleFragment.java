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
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.adapter.GalleryPagerAdapter;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.article.Item;
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
    @BindView(R.id.galleryTwoPhotos)
    ViewPager gallery;
    @BindView(R.id.gallerySwitchIcon)
    ImageView switchPhoto;
    @BindView(R.id.gallerySwitchCounter)
    TextView photoCounter;
    @BindView(R.id.galleryTwoField)
    RelativeLayout textField;
    @BindView(R.id.galleryTwoMatch)
    TextView matchArticles;
    Unbinder unbinder;
    Item item;
    KlopsApplication app;
    List<TextView> textTypes;
    List<TextView> linkTypes;
    List<TextView> titleTypes;
    GalleryPagerAdapter adapter;
    List<String> photos;
    ArticleActivity activity;
    String slash = "/";

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
        setUpTextField();
        setUpView();
        setUpGalleryPager();
        return fragmentView;
    }

    private void setUpImages() {
        Log.d(LOG, "setUpImages");
        photoIcon.setVisibility(View.VISIBLE);
        switchPhoto.setVisibility(View.VISIBLE);
        if (!item.getUpdate_status().equals("")) {
            statusCircle.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.gallerySwitchIcon)
    public void nextPhoto() {

        int position;
        for (int i = 0; i < photos.size(); i++) {
            if (gallery.getCurrentItem() == i) {
                gallery.setCurrentItem(i + 1);
                position = gallery.getCurrentItem() + 1;
                photoCounter.setText(position + slash + photos.size());
                i++;
            }else if (gallery.getCurrentItem() == photos.size()){
                gallery.setCurrentItem(photos.size() - 1);
                position = gallery.getCurrentItem() - 1;
                photoCounter.setText(position + slash + photos.size());
                i--;
            }
        }
    }

    private void setUpTextField() {
        Log.d(LOG, "setUpTextField");
        textTypes = new ArrayList<>();
        linkTypes = new ArrayList<>();
        titleTypes = new ArrayList<>();
        List<Text> texts = new ArrayList<>();
        texts.addAll(item.getText());
        int previousID = 0;
        for (int i = 0; i < texts.size(); i++) {
            if (texts.get(i).getType().equals(Constants.TEXT)) {
                TextView textView = new TextView(getContext());
                textView.setText(texts.get(i).getText());
                textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                textView.setTextSize(16);
                textView.setSingleLine(false);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.darkGreyText));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                textView.setPadding(0, 5, 0, 5);
                textView.setLineSpacing(2, 2);
                int currentID = previousID + 1;
                textView.setId(currentID);
                params.addRule(RelativeLayout.BELOW, previousID);
                textView.setLayoutParams(params);
                previousID = currentID;
                textField.addView(textView, params);
                textTypes.add(textView);
            } else if (texts.get(i).getType().equals(Constants.TITLE)) {
                TextView titleView = new TextView(getContext());
                titleView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                titleView.setText(texts.get(i).getText());
                titleView.setTextSize(16);
                titleView.setSingleLine(false);
                titleView.setTextColor(ContextCompat.getColor(getContext(), R.color.darkGreyText));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                titleView.setPadding(0, 10, 0, 10);
                titleView.setLineSpacing(2, 2);
                int currentID = previousID + 1;
                titleView.setId(currentID);
                params.addRule(RelativeLayout.BELOW, previousID);
                titleView.setLayoutParams(params);
                textField.addView(titleView, params);
                titleTypes.add(titleView);
            } else if (texts.get(i).getType().equals(Constants.LINK)) {
                final TextView linkView = new TextView(getContext());
                linkView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                linkView.setText(texts.get(i).getLink());
                linkView.setTextSize(16);
                linkView.setSingleLine(false);
                linkView.setPaintFlags(linkView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                linkView.setTextColor(ContextCompat.getColor(getContext(), R.color.linkColor));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                linkView.setPadding(0, 5, 0, 5);
                linkView.setLineSpacing(2, 2);
                int currentID = previousID + 1;
                linkView.setId(currentID);
                params.addRule(RelativeLayout.BELOW, previousID);
                linkView.setLayoutParams(params);
                textField.addView(linkView, params);
                linkView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(linkView.getText().toString())));
                    }
                });
                linkTypes.add(linkView);
            } else if (texts.get(i).getType().equals(Constants.IMAGE)) {
                ImageView imageView = new ImageView(getContext());
                final ProgressBar bar = new ProgressBar(getContext());
                bar.setPadding(0, 10, 0, 10);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setPadding(0, 10, 0, 10);
                int currentID = previousID + 1;
                imageView.setId(currentID);
                bar.setId(currentID);
                params.addRule(RelativeLayout.BELOW, previousID);
                imageView.setLayoutParams(params);
                textField.addView(imageView, params);
                textField.addView(bar, params);
                Picasso.with(getContext()).load(texts.get(i).getText()).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        bar.setVisibility(View.VISIBLE);
                    }
                });
            }

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
        matchArticles.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-boldex.ttf"));
        photoCounter.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));

    }

    private void setUpGalleryPager() {
        Log.d(LOG, "setUpGalleryPager");
        photos = new ArrayList<>();
        photos.addAll(item.getPhotos());
        adapter = new GalleryPagerAdapter(getContext(), photos);
        gallery.setAdapter(adapter);
        photoCounter.setText("1" + slash + photos.size());
    }

    public void formatDefault() {
        title.setTextSize(28);
        status.setTextSize(8);
        date.setTextSize(8);
        author.setTextSize(8);
        photoCounter.setTextSize(8);
        shortdescription.setTextSize(16);
        matchArticles.setTextSize(34);
        for (TextView texts : textTypes) {
            texts.setTextSize(16);
        }
        for (TextView titles : titleTypes) {
            titles.setTextColor(16);
        }
        for (TextView links : linkTypes) {
            links.setTextSize(16);
        }
    }

    public void formatIncrement() {
        title.setTextSize(29);
        status.setTextSize(9);
        date.setTextSize(9);
        author.setTextSize(9);
        photoCounter.setTextSize(9);
        shortdescription.setTextSize(17);
        matchArticles.setTextSize(35);
        for (TextView texts : textTypes) {
            texts.setTextSize(17);
        }
        for (TextView titles : titleTypes) {
            titles.setTextColor(17);
        }
        for (TextView links : linkTypes) {
            links.setTextSize(17);
        }
    }

    public void formatDecrement() {
        title.setTextSize(27);
        status.setTextSize(7);
        date.setTextSize(7);
        author.setTextSize(7);
        photoCounter.setTextSize(7);
        shortdescription.setTextSize(15);
        matchArticles.setTextSize(33);
        for (TextView texts : textTypes) {
            texts.setTextSize(15);
        }
        for (TextView titles : titleTypes) {
            titles.setTextColor(15);
        }
        for (TextView links : linkTypes) {
            links.setTextSize(15);
        }
    }


    public void shareToSocial(){
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