package ru.klops.klops.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.klops.klops.AppBrowserActivity;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.article.Content;
import ru.klops.klops.utils.Constants;

public class ContentFragment extends Fragment {
    final String LOG = "ContentFragment";
    View fragmentView;

    @BindView(R.id.contentFragment)
    RelativeLayout contentLayout;
    @BindView(R.id.contentWeb)
    WebView contentWeb;
    @BindView(R.id.contentImage)
    ImageView contentImage;
    @BindView(R.id.contentDescription)
    TextView contentDescription;
    @BindView(R.id.contentMore)
    TextView contentMore;
    @BindView(R.id.contentUrl)
    TextView contentUrl;
    @BindView(R.id.contentProgress)
    ProgressBar bar;
    @BindView(R.id.contentVideo)
    WebView contentVideo;
    Unbinder unbinder;
    Content content;
    KlopsApplication app;
    ArticleActivity activity;
    String gallery;

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
        fragmentView = inflater.inflate(R.layout.content_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        content = getArguments().getParcelable(Constants.CONTENT);
        gallery = getArguments().getString(Constants.IS_GALLERY);
        checkIfGalleryArticle(gallery);
        initContent(content, contentLayout, contentWeb, bar, contentImage, contentDescription, contentMore, contentUrl, contentVideo, 0);
        Log.d(LOG, "onCreateView");
        return fragmentView;
    }

    private void checkIfGalleryArticle(String type) {
        if (type.equals(Constants.GALLERY_FIRST_TEXT) || type.equals(Constants.GALLERY_SECOND_TEXT)) {
            contentDescription.setTextColor(ContextCompat.getColor(this.getContext(), R.color.greyText));
            contentWeb.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.galleryCard));
        }

    }

    public void initContent(final Content content, RelativeLayout contentLayer, final WebView contentView, final ProgressBar loader, ImageView image, TextView description, final TextView moreTitle, final TextView moreUrl, WebView webVideo, final int size) {
        if (content != null) {
            if (content.getText() != null) {
                String contentText;
                contentLayer.setVisibility(View.VISIBLE);
                contentView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                contentView.setScrollContainer(false);
//                contentText = content.getText().replace("font-size:18px", "font-size:16px");
//                if (contentText.contains("class=\"social-embed\"")) {
//                    String splitt = contentText;
//                    String contentStrings[] = splitt.split("class=\"social-embed\"");
//                    contentText = contentStrings[0];
//                    contentView.loadDataWithBaseURL(null, contentText, "text/html", "UTF-8", null);
//                } else {
                switch (size){
                    case 0:
                        contentView.loadDataWithBaseURL(null, content.getText().replace("font-size:18px", "font-size:16px"), "text/html", "UTF-8", null);
                        break;
                    case 120:
                        contentView.loadDataWithBaseURL(null, content.getText().replace("font-size:16px", "font-size:18px"), "text/html", "UTF-8", null);
                        break;
                }
                contentView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        loader.setVisibility(View.VISIBLE);
                        contentView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        loader.setVisibility(View.GONE);
                        contentView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        if (url.contains("id=")) {
                            SpannableString link = new SpannableString(url);
                            String spliterableMain = url;
                            String firstSlpit[] = spliterableMain.split("id=");
                            String idString = firstSlpit[1];
                            String spliterableId[] = idString.split("\"");
                            String linkId = spliterableId[0];
                            final Pattern word = Pattern.compile(linkId);
                            Matcher matcher = word.matcher(link);
                            if (matcher.find()) {
                                int articleId = Integer.parseInt(linkId);
                                activity.loadArticle(articleId, Constants.ARTICLE_TYPE);
                            }
                        } else if (!url.contains("id=")) {
                            Intent browser = new Intent(getContext(), AppBrowserActivity.class);
                            browser.putExtra(Constants.URL, url);
                            startActivity(browser);
                            view.loadDataWithBaseURL(null, content.getText().replace("font-size:18px", "font-size:16px"), "text/html", "UTF-8", null);

                        }
                        return false;
                    }

                });
                contentView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                contentView.getSettings().setJavaScriptEnabled(true);
                contentView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                contentView.getSettings().setSupportZoom(true);
                contentView.setWebChromeClient(new WebChromeClient());

            } else if (content.getPhotos() != null) {
                contentLayer.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                loader.setVisibility(View.VISIBLE);
                loadPhoto(this, content.getPhotos().get(0).getImg_url(), image, loader);
                if (!content.getPhotos().get(0).getDescription().equals("")) {
                    description.setText(content.getPhotos().get(0).getDescription());
                    description.setVisibility(View.VISIBLE);
                } else {
                    description.setText("");
                    description.setVisibility(View.GONE);
                }
                description.setTypeface(Typeface.createFromAsset(this.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
            } else if (content.getAssociate() != null) {
                contentLayer.setVisibility(View.VISIBLE);
                moreTitle.setVisibility(View.VISIBLE);
                moreUrl.setVisibility(View.VISIBLE);
                moreTitle.setTypeface(Typeface.createFromAsset(this.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                moreUrl.setText(content.getAssociate().getTitle());
                moreUrl.setTypeface(Typeface.createFromAsset(this.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                moreUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (content.getAssociate().getId() != null) {
                            activity.loadArticle(content.getAssociate().getId(), Constants.ARTICLE_TYPE);
                        } else {
                            Intent browser = new Intent(getContext(), AppBrowserActivity.class);
                            browser.putExtra(Constants.URL, content.getAssociate().getUrl());
                            startActivity(browser);
                        }
                    }
                });
            } else if (content.getVideo_url() != null) {
                contentLayer.setVisibility(View.VISIBLE);
                webVideo.setVisibility(View.VISIBLE);
                webVideo.setWebViewClient(new WebViewClient());
                webVideo.loadUrl(content.getVideo_url());
                webVideo.getSettings().setJavaScriptEnabled(true);
                webVideo.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webVideo.setWebChromeClient(new WebChromeClient());
            }
        }
    }

    private void loadPhoto(ContentFragment context, String imageUrl, ImageView imView, final ProgressBar bar) {
        Ion.with(context).load(imageUrl).progressHandler(new ProgressCallback() {
            @Override
            public void onProgress(long downloaded, long total) {
                bar.setVisibility(View.VISIBLE);
            }
        }).intoImageView(imView).setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                bar.setVisibility(View.GONE);
            }
        });
    }

    public void increment() {
        contentDescription.setTextSize(18);
        contentMore.setTextSize(12);
        contentUrl.setTextSize(18);

    }

    public void decrement() {
        contentDescription.setTextSize(16);
        contentMore.setTextSize(10);
        contentUrl.setTextSize(16);

    }

    public void zoomIn() {
        initContent(content, contentLayout, contentWeb, bar, contentImage, contentDescription, contentMore, contentUrl, contentVideo, 120);
    }

    public void zoomOut() {
        initContent(content, contentLayout, contentWeb, bar, contentImage, contentDescription, contentMore, contentUrl, contentVideo, 0);
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
