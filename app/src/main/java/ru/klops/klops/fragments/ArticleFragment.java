package ru.klops.klops.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.klops.klops.HomeActivity;
import ru.klops.klops.R;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.model.page.News;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;

public class ArticleFragment extends Fragment implements View.OnClickListener {
    final String LOG = "ArticleFragment";
    private View fragmentView;
    ImageView back;
    ImageView titleImage;
    TextView title;
    TextView date;
    TextView description;
    ImageView gallery;
    ImageView photoSwipe;
    TextView photoCounter;
    TextView articleText;
    RelativeLayout baseLayout;
    TextView whiteTitle;
    News news;
    News received;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.article_fragment, container, false);
        received = getArguments().getParcelable("ARTICLE");
        initViews();
        initListeners();
        Log.d(LOG, "onCreateView");
        return fragmentView;
    }

    private void initViews() {
        Log.d(LOG, "initialize layers");
        back = (ImageView) fragmentView.findViewById(R.id.back_button);
        titleImage = (ImageView) fragmentView.findViewById(R.id.article_image);
        title = (TextView) fragmentView.findViewById(R.id.article_title);
        date = (TextView) fragmentView.findViewById(R.id.date_article);
        description = (TextView) fragmentView.findViewById(R.id.article_description);
        gallery = (ImageView) fragmentView.findViewById(R.id.article_gallery);
        photoSwipe = (ImageView) fragmentView.findViewById(R.id.article_photos);
        photoCounter = (TextView) fragmentView.findViewById(R.id.photo_set_text);
        articleText = (TextView) fragmentView.findViewById(R.id.article_text);
        baseLayout = (RelativeLayout) fragmentView.findViewById(R.id.base_layout);
        whiteTitle = (TextView) fragmentView.findViewById(R.id.white_title);

    }

    private void initListeners() {
        Log.d(LOG, "initialize listeners");
        back.setOnClickListener(this);
        titleImage.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        gallery.setVisibility(View.INVISIBLE);
        photoSwipe.setVisibility(View.INVISIBLE);
        photoCounter.setVisibility(View.INVISIBLE);
        articleText.setVisibility(View.INVISIBLE);
        String articleType = received.getArticle_type();
        drawFragment(articleType);

    }

    private void drawFragment(String articleType) {
        switch (articleType) {
            case Constants.SIMPLE_IMAGE_TEXT:
                baseLayout.setVisibility(View.VISIBLE);
                titleImage.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                articleText.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(received.getImage()).into(titleImage);
                title.setText(received.getTitle());
                date.setText(received.getDate());
                description.setText(received.getShortdecription());
                articleText.setText(received.getText());
                break;
            case Constants.SIMPLE_TEXT:
                baseLayout.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                articleText.setVisibility(View.VISIBLE);
                title.setText(received.getTitle());
                date.setText(received.getDate());
                description.setText(received.getShortdecription());
                articleText.setText(received.getText());
                break;
            case Constants.LONG_TEXT:
                baseLayout.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                articleText.setVisibility(View.VISIBLE);
                title.setText(received.getTitle());
                date.setText(received.getDate());
                description.setText(received.getShortdecription());
                articleText.setText(received.getText());
                break;
            case Constants.INTERVIEW_TEXT:
                baseLayout.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                articleText.setVisibility(View.VISIBLE);
                titleImage.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(received.getImage()).into(titleImage);
                titleImage.setMinimumWidth(60);
                titleImage.setMaxWidth(60);
                titleImage.setMinimumHeight(60);
                titleImage.setMaxHeight(60);
                date.setText(received.getDate());
                title.setText(received.getTitle());
                description.setText(received.getShortdecription());
                articleText.setText(received.getText());
                break;
            case Constants.AUTHORS_TEXT:
                baseLayout.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                articleText.setVisibility(View.VISIBLE);
                titleImage.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(received.getImage()).into(titleImage);
                titleImage.setMinimumWidth(60);
                titleImage.setMaxWidth(60);
                titleImage.setMinimumHeight(60);
                titleImage.setMaxHeight(60);
                date.setText(received.getDate());
                title.setText(received.getTitle());
                description.setText(received.getShortdecription());
                articleText.setText(received.getText());
                break;
            case Constants.NATIONAL_TEXT:
                baseLayout.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                articleText.setVisibility(View.VISIBLE);
                date.setText(received.getDate());
                title.setText(received.getTitle());
                description.setText(received.getShortdecription());
                articleText.setText(received.getText());
                break;
            case Constants.IMPORTANT_TEXT:
                baseLayout.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                articleText.setVisibility(View.VISIBLE);
                titleImage.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(received.getImage()).into(titleImage);
                title.setText(received.getTitle());
                date.setText(received.getDate());
                description.setText(received.getShortdecription());
                articleText.setText(received.getText());
                break;
            case Constants.GALLERY_FIRST_TEXT:
                baseLayout.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                articleText.setVisibility(View.VISIBLE);
                whiteTitle.setVisibility(View.VISIBLE);
                gallery.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(received.getImage()).into(gallery);
                whiteTitle.setText(received.getTitle());
                whiteTitle.setBackgroundResource(R.color.galleryCard);
                date.setText(received.getDate());
                description.setText(received.getShortdecription());
                articleText.setText(received.getText());
                break;
            case Constants.GALLERY_SECOND_TEXT:
                baseLayout.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                articleText.setVisibility(View.VISIBLE);
                whiteTitle.setVisibility(View.VISIBLE);
                gallery.setVisibility(View.VISIBLE);
                photoCounter.setVisibility(View.VISIBLE);
                photoSwipe.setVisibility(View.VISIBLE);
                whiteTitle.setText(received.getTitle());
                whiteTitle.setBackgroundResource(R.color.galleryCard);
                whiteTitle.setPadding(0, 15, 0, 0);
                whiteTitle.setTextSize(22);
                date.setText(received.getDate());
                description.setText(received.getShortdecription());
                articleText.setText(received.getText());
                if (received.getPhotos().size() != 0) {
                    Picasso.with(getContext()).load(received.getPhotos().get(0)).into(gallery);
                    articleText.setText(received.getText());
                    photoCounter.setText("1/" + received.getPhotos().size());
                    photoSwipe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 1; i < received.getPhotos().size(); i++) {
                                int counter = 1 + i;
                                Picasso.with(getContext()).load(received.getPhotos().get(1)).into(gallery);
                                photoCounter.setText(counter + "/" + received.getPhotos().size());
                            }
                        }
                    });
                } else {
                    Picasso.with(getContext()).load(received.getImage()).into(gallery);
                }
                break;
            case Constants.ADS_TEXT:
                baseLayout.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                articleText.setVisibility(View.VISIBLE);
                titleImage.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(received.getImage()).into(titleImage);
                title.setText(received.getTitle());
                date.setText(received.getDate());
                description.setText(received.getShortdecription());
                articleText.setText(received.getText());
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
//                ((HomeActivity) getActivity()).replaceFragment(new NewsLineSlideFragment());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG, "onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(LOG, "onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG, "onDestroy");
    }
}

