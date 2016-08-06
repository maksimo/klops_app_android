package ru.klops.klops;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.dialogs.VKShareDialog;
import com.vk.sdk.dialogs.VKShareDialogBuilder;
import com.vk.sdk.util.VKUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.klops.klops.adapter.GalleryContentPagerAdapter;
import ru.klops.klops.adapter.GalleryPagerAdapter;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.custom.TextViewProRegular;
import ru.klops.klops.fragments.AdsArticleFragment;
import ru.klops.klops.fragments.AuthorArticleFragment;
import ru.klops.klops.fragments.GalleryOneArticleFragment;
import ru.klops.klops.fragments.GalleryTwoArticleFragment;
import ru.klops.klops.fragments.ImportantArticleFragment;
import ru.klops.klops.fragments.InterviewArticleFragment;
import ru.klops.klops.fragments.LongArticleFragment;
import ru.klops.klops.fragments.NationalArticleFragment;
import ru.klops.klops.fragments.SimpleTextArticleFragment;
import ru.klops.klops.fragments.SimpleWideArticleFragment;
import ru.klops.klops.fragments.SimpleWithImageArticleFragment;
import ru.klops.klops.fragments.UrgentArticleFragment;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.models.article.Connected_items;
import ru.klops.klops.models.article.Content;
import ru.klops.klops.models.article.Gallery;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.article.Photos;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ArticleActivity extends AppCompatActivity {
    final String LOG = "ArticleActivity";

    @BindView(R.id.toolbarArticle)
    Toolbar toolbar;
    @BindView(R.id.backButton)
    ImageView back;
    @BindView(R.id.buttonFormat)
    ImageView format;
    @BindView(R.id.shareSocials)
    ImageView share;
    KlopsApplication app;
    String articleType;
    AlertDialog.Builder shareBuilder;
    AlertDialog shareDialog;
    View shareLayout;
    TextView shareTitle;
    TextView facebookTitle;
    TextView vkontakteTitle;
    RelativeLayout facebook;
    RelativeLayout vkontakte;
    Item item;
    @BindView(R.id.textMatch)
    TextView matchArticles;
    @BindView(R.id.littlePhotoSwitcher)
    RelativeLayout littlePhotoSwitcher;
    @BindView(R.id.littlePhotoSwitchCounter)
    TextView littlePhotoSwitchCounter;
    @BindView(R.id.galleryBackground)
    RelativeLayout galleryBackground;
    @BindView(R.id.splitterThird)
    View splitterThird;
    @BindView(R.id.ViewsPhotos)
    ViewPager pager;
    @BindView(R.id.contentGallery)
    RelativeLayout contentGallery;
    @BindView(R.id.littlePhotoSwitchCounterTwo)
    TextView littlePhotoSwitchCounterTwo;
    @BindView(R.id.littlePhotoSwitcherTwo)
    RelativeLayout littlePhotoSwitcherTwo;
    @BindView(R.id.littleGallery)
    ViewPager littleGallery;
    @BindView(R.id.matchLayout)
    RelativeLayout matchLayout;
    @BindView(R.id.connectedNewsOneLayer)
    RelativeLayout connectedNewsOneLayer;
    @BindView(R.id.connectedNewsOnePhoto)
    ImageView connectedNewsOnePhoto;
    @BindView(R.id.connectedNewsOneImageLoading)
    ProgressBar connectedNewsOneImageLoading;
    @BindView(R.id.connectedNewsOneDate)
    TextView connectedNewsOneDate;
    @BindView(R.id.connectedNewsOneText)
    TextView connectedNewsOneText;
    @BindView(R.id.connectedNewsTwoLayer)
    RelativeLayout connectedNewsTwoLayer;
    @BindView(R.id.connectedNewsTwoPhoto)
    ImageView connectedNewsTwoPhoto;
    @BindView(R.id.connectedNewsTwoLoading)
    ProgressBar connectedNewsTwoLoading;
    @BindView(R.id.connectedNewsTwoDate)
    TextView connectedNewsTwoDate;
    @BindView(R.id.connectedNewsTwoText)
    TextView connectedNewsTwoText;

    @BindView(R.id.firstContent)
    RelativeLayout firstContent;
    @BindView(R.id.firstWeb)
    WebView firstWeb;
    @BindView(R.id.firstImage)
    ImageView firstImage;
    @BindView(R.id.firstDescription)
    TextView firstDescription;
    @BindView(R.id.firstMore)
    TextView firstMore;
    @BindView(R.id.firstUrl)
    TextView firstUrl;
    @BindView(R.id.secondContent)
    RelativeLayout secondContent;
    @BindView(R.id.secondWeb)
    WebView secondWeb;
    @BindView(R.id.secondImage)
    ImageView secondImage;
    @BindView(R.id.secondDescription)
    TextView secondDescription;
    @BindView(R.id.secondMore)
    TextView secondMore;
    @BindView(R.id.secondUrl)
    TextView secondUrl;
    @BindView(R.id.thirdContent)
    RelativeLayout thirdContent;
    @BindView(R.id.thirdWeb)
    WebView thirdWeb;
    @BindView(R.id.thirdImage)
    ImageView thirdImage;
    @BindView(R.id.thirdDescription)
    TextView thirdDescription;
    @BindView(R.id.thirdMore)
    TextView thirdMore;
    @BindView(R.id.thirdUrl)
    TextView thirdUrl;
    @BindView(R.id.fourthContent)
    RelativeLayout fourthContent;
    @BindView(R.id.fourthWeb)
    WebView fourthWeb;
    @BindView(R.id.fourthImage)
    ImageView fourthImage;
    @BindView(R.id.fourthDescription)
    TextView fourthDescription;
    @BindView(R.id.fourthMore)
    TextView fourthMore;
    @BindView(R.id.fourthUrl)
    TextView fourthUrl;
    @BindView(R.id.fifthContent)
    RelativeLayout fifthContent;
    @BindView(R.id.fifthWeb)
    WebView fifthWeb;
    @BindView(R.id.fifthImage)
    ImageView fifthImage;
    @BindView(R.id.fifthDescription)
    TextView fifthDescription;
    @BindView(R.id.fifthMore)
    TextView fifthMore;
    @BindView(R.id.fifthUrl)
    TextView fifthUrl;
    @BindView(R.id.sixContent)
    RelativeLayout sixContent;
    @BindView(R.id.sixWeb)
    WebView sixWeb;
    @BindView(R.id.sixImage)
    ImageView sixImage;
    @BindView(R.id.sixDescription)
    TextView sixDescription;
    @BindView(R.id.sixMore)
    TextView sixMore;
    @BindView(R.id.sixUrl)
    TextView sixUrl;
    @BindView(R.id.sevenContent)
    RelativeLayout sevenContent;
    @BindView(R.id.sevenWeb)
    WebView sevenWeb;
    @BindView(R.id.sevenImage)
    ImageView sevenImage;
    @BindView(R.id.sevenDescription)
    TextView sevenDescription;
    @BindView(R.id.sevenMore)
    TextView sevenMore;
    @BindView(R.id.sevenUrl)
    TextView sevenUrl;
    @BindView(R.id.eightContent)
    RelativeLayout eightContent;
    @BindView(R.id.eightWeb)
    WebView eightWeb;
    @BindView(R.id.eightImage)
    ImageView eightImage;
    @BindView(R.id.eightDescription)
    TextView eightDescription;
    @BindView(R.id.eightMore)
    TextView eightMore;
    @BindView(R.id.eightUrl)
    TextView eightUrl;
    @BindView(R.id.nineContent)
    RelativeLayout nineContent;
    @BindView(R.id.nineWeb)
    WebView nineWeb;
    @BindView(R.id.nineImage)
    ImageView nineImage;
    @BindView(R.id.nineDescription)
    TextView nineDescription;
    @BindView(R.id.nineMore)
    TextView nineMore;
    @BindView(R.id.nineUrl)
    TextView nineUrl;
    @BindView(R.id.tenContent)
    RelativeLayout tenContent;
    @BindView(R.id.tenWeb)
    WebView tenWeb;
    @BindView(R.id.tenImage)
    ImageView tenImage;
    @BindView(R.id.tenDescription)
    TextView tenDescription;
    @BindView(R.id.tenMore)
    TextView tenMore;
    @BindView(R.id.tenUrl)
    TextView tenUrl;
    @BindView(R.id.elevenContent)
    RelativeLayout elevenContent;
    @BindView(R.id.elevenWeb)
    WebView elevenWeb;
    @BindView(R.id.elevenImage)
    ImageView elevenImage;
    @BindView(R.id.elevenDescription)
    TextView elevenDescription;
    @BindView(R.id.elevenMore)
    TextView elevenMore;
    @BindView(R.id.elevenUrl)
    TextView elevenUrl;
    @BindView(R.id.twelveContent)
    RelativeLayout twelveContent;
    @BindView(R.id.twelveWeb)
    WebView twelveWeb;
    @BindView(R.id.twelveImage)
    ImageView twelveImage;
    @BindView(R.id.twelveDescription)
    TextView twelveDescription;
    @BindView(R.id.twelveMore)
    TextView twelveMore;
    @BindView(R.id.twelveUrl)
    TextView twelveUrl;

    @BindView(R.id.oneProgress)
    ProgressBar barOne;
    @BindView(R.id.twoProgress)
    ProgressBar barTwo;
    @BindView(R.id.threeProgress)
    ProgressBar barThree;
    @BindView(R.id.fourProgress)
    ProgressBar barFour;
    @BindView(R.id.fiveProgress)
    ProgressBar barFive;
    @BindView(R.id.sixProgress)
    ProgressBar barSix;
    @BindView(R.id.sevenProgress)
    ProgressBar barSeven;
    @BindView(R.id.eightProgress)
    ProgressBar barEight;
    @BindView(R.id.nineProgress)
    ProgressBar barNine;
    @BindView(R.id.tenProgress)
    ProgressBar barTen;
    @BindView(R.id.elevenProgress)
    ProgressBar barEleven;
    @BindView(R.id.twelveProgress)
    ProgressBar barTwelve;
    @BindView(R.id.promotionLayer)
    RelativeLayout promotionLayer;
    @BindView(R.id.promotionIcon)
    ImageView promotionIcon;
    @BindView(R.id.promotionText)
    TextViewProRegular promotionText;
    @BindView(R.id.fullArticleLayer)
    RelativeLayout fullArticleLayer;
    @BindView(R.id.splitterFour)
    View splitterFour;
    @BindView(R.id.littleSwitchPhotoIcon)
    ImageView littleSwitchPhotoIcon;
    ArrayList<WebView> contentViews;
    ArrayList<TextView> contentDescriptions;
    ArrayList<Connected_items> connectedItemses;
    ArrayList<TextView> contentMore;
    ArrayList<TextView> contentUrl;
    ArrayList<String> smallGallery;
    GalleryPagerAdapter littleAdapter;
    ShareDialog shareFacebookDialog;
    VKShareDialogBuilder vkShareDialog;
    ArrayList<Content> contents;
    Animation alpha;
    Bitmap bmp;
    String text;
    GalleryContentPagerAdapter gAdapter;
    ArrayList<Gallery> galleries;
    int count = 0;
    int countPager = 0;
    int formatCount = 0;
    private Target loadTarget;
    Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.article_layers_activity);
        Log.d(LOG, "onCreate");
        shareLayout = LayoutInflater.from(this).inflate(R.layout.share_dialog, null);
        unbinder = ButterKnife.bind(this);
        app = KlopsApplication.getINSTANCE();
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        item = getIntent().getParcelableExtra(Constants.ITEM);
        setSupportActionBar(toolbar);
        initSocials();
        setUpShare();
        drawFragment();
        setUPContent();
        getAllContents();
        setUpGalleries();
        setUpMatchNews();
    }

    private void setUpGalleries() {
        if (galleries.size() != 0) {
            gAdapter = new GalleryContentPagerAdapter(this, galleries);
            pager.setAdapter(gAdapter);
            pager.setCurrentItem(0);
            contentGallery.setVisibility(View.VISIBLE);
            splitterThird.setVisibility(View.VISIBLE);
            littlePhotoSwitchCounterTwo.setText("1/" + String.valueOf(galleries.size()));
            final int count = galleries.size();
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    littlePhotoSwitchCounterTwo.setText(String.valueOf(pager.getCurrentItem() + 1) + "/" + String.valueOf(count));
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            setUPPager();
        }
    }

    private void setUpMatchNews() {
        if (item.getConnected_items() != null && item.getConnected_items().size() != 0 && !item.getConnected_items().isEmpty()) {
            connectedItemses = new ArrayList<>();
            for (int a = 0; a < item.getConnected_items().size(); a++) {
                connectedItemses.add(new Connected_items(item.getConnected_items().get(a).getDoc_list()));
            }
            if (!connectedItemses.get(0).getDoc_list().getImage().equals("") || !connectedItemses.get(0).getDoc_list().getImage().isEmpty()) {
                loadPhoto(this, connectedItemses.get(0).getDoc_list().getImage(), connectedNewsOnePhoto, connectedNewsOneImageLoading);
                connectedNewsOnePhoto.setVisibility(View.VISIBLE);
            } else {
                connectedNewsOnePhoto.setVisibility(View.GONE);
            }
            connectedNewsOneDate.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
            connectedNewsOneDate.setText(connectedItemses.get(0).getDoc_list().getDate());
            connectedNewsOneText.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
            connectedNewsOneText.setText(connectedItemses.get(0).getDoc_list().getTitle());

            if (!connectedItemses.get(1).getDoc_list().getImage().equals("") || !connectedItemses.get(1).getDoc_list().getImage().isEmpty()) {
                loadPhoto(this, connectedItemses.get(1).getDoc_list().getImage(), connectedNewsTwoPhoto, connectedNewsTwoLoading);
                connectedNewsTwoPhoto.setVisibility(View.VISIBLE);
            } else {
                connectedNewsTwoPhoto.setVisibility(View.GONE);
            }
            connectedNewsTwoDate.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
            connectedNewsTwoDate.setText(connectedItemses.get(1).getDoc_list().getDate());
            connectedNewsTwoText.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
            connectedNewsTwoText.setText(connectedItemses.get(1).getDoc_list().getTitle());
        } else {
            connectedNewsOneLayer.setVisibility(View.GONE);
            connectedNewsTwoLayer.setVisibility(View.GONE);
            matchLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.connectedNewsOneLayer)
    public void openFirstMatchNews() {
        connectedNewsOneLayer.startAnimation(alpha);
        loadArticle(connectedItemses.get(0).getDoc_list().getId());
    }

    @OnClick(R.id.connectedNewsTwoLayer)
    public void openSecondMatchNews() {
        connectedNewsTwoLayer.startAnimation(alpha);
        loadArticle(connectedItemses.get(1).getDoc_list().getId());
    }

    private void loadArticle(Integer id) {
        PageApi articleApi = RetrofitServiceGenerator.createService(PageApi.class);
        Observable<Article> callArticle = articleApi.getItemById(id);
        callArticle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "Task completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, "Error: " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Article article) {
                        Intent newMatchArticle = getIntent();
                        newMatchArticle.putExtra(Constants.ITEM, article.getItem());
                        finish();
                        startActivity(newMatchArticle);
                    }
                });
    }

    private void loadPhoto(ArticleActivity context, String imageUrl, ImageView imView, final ProgressBar bar) {
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

    private void setUPContent() {
        Log.d(LOG, "setUpTextField");
        matchArticles.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-boldex.ttf"));
        contentViews = new ArrayList<>();
        contentDescriptions = new ArrayList<>();
        contentMore = new ArrayList<>();
        contentUrl = new ArrayList<>();
        galleries = new ArrayList<>();
        contents = new ArrayList<>();
        contents.addAll(item.getContent());

        if (item.getPromoted() == 1) {
            promotionLayer.setVisibility(View.VISIBLE);
        }

        for (int n = 0; n < contents.size(); n++) {
            if (contents.get(n).getGallery() != null && !contents.get(n).getGallery().isEmpty()) {
                galleries.addAll(contents.get(n).getGallery());
            }
        }

    }

    public void getAllContents() {
        switch (contents.size()) {
            case 1:
                checkFirst();
                break;
            case 2:
                checkFirst();
                checkSecond();
                break;
            case 3:
                checkFirst();
                checkSecond();
                checkThird();
                break;
            case 4:
                checkFirst();
                checkSecond();
                checkThird();
                checkFourth();
                break;
            case 5:
                checkFirst();
                checkSecond();
                checkThird();
                checkFourth();
                checkFifth();
                break;
            case 6:
                checkFirst();
                checkSecond();
                checkThird();
                checkFourth();
                checkFifth();
                checkSixth();
                break;
            case 7:
                checkFirst();
                checkSecond();
                checkThird();
                checkFourth();
                checkFifth();
                checkSixth();
                checkSeventh();
                break;
            case 8:
                checkFirst();
                checkSecond();
                checkThird();
                checkFourth();
                checkFifth();
                checkSixth();
                checkSeventh();
                checkEight();
                break;
            case 9:
                checkFirst();
                checkSecond();
                checkThird();
                checkFourth();
                checkFifth();
                checkSixth();
                checkSeventh();
                checkEight();
                checkNinth();
                break;
            case 10:
                checkFirst();
                checkSecond();
                checkThird();
                checkFourth();
                checkFifth();
                checkSixth();
                checkSeventh();
                checkEight();
                checkNinth();
                checkTenth();
                break;
            case 11:
                checkFirst();
                checkSecond();
                checkThird();
                checkFourth();
                checkFifth();
                checkSixth();
                checkSeventh();
                checkEight();
                checkNinth();
                checkTenth();
                checkEleventh();
                break;
            case 12:
                checkFirst();
                checkSecond();
                checkThird();
                checkFourth();
                checkFifth();
                checkSixth();
                checkSeventh();
                checkEight();
                checkNinth();
                checkTenth();
                checkEleventh();
                checkTwelve();
                break;
        }

    }

    private void checkTwelve() {
        if (contents.get(11) != null) {
            if (contents.get(11).getText() != null) {
                twelveContent.setVisibility(View.VISIBLE);
                twelveWeb.getSettings().setDefaultFontSize(16);
                twelveWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barTwelve.setVisibility(View.VISIBLE);
                        twelveWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barTwelve.setVisibility(View.GONE);
                        twelveWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                twelveWeb.getSettings().setJavaScriptEnabled(true);
                twelveWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                twelveWeb.setWebChromeClient(new WebChromeClient());
                twelveWeb.loadData(Constants.HARDCODED_BODY + contents.get(11).getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(twelveWeb);
            } else if (contents.get(11).getPhotos() != null) {
                twelveContent.setVisibility(View.VISIBLE);
                twelveImage.setVisibility(View.VISIBLE);
                barTwelve.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(11).getPhotos().get(0).getImg_url(), twelveImage, barTwelve);
                if (!contents.get(11).getPhotos().get(0).getDescription().equals("")) {
                    twelveDescription.setText(contents.get(11).getPhotos().get(0).getDescription());
                    twelveDescription.setVisibility(View.VISIBLE);
                } else {
                    twelveDescription.setText("");
                    twelveDescription.setVisibility(View.GONE);
                }
                twelveDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(twelveDescription);
            } else if (contents.get(11).getAssociate() != null) {
                twelveContent.setVisibility(View.VISIBLE);
                twelveMore.setVisibility(View.VISIBLE);
                twelveUrl.setVisibility(View.VISIBLE);
                twelveMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                twelveUrl.setText(contents.get(11).getAssociate().getTitle());
                twelveUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                twelveUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(11).getAssociate().getId() != null) {
                            loadArticle(contents.get(11).getAssociate().getId());
                        }else if (contents.get(11).getAssociate().getUrl() != null) {
                            twelveUrl.setVisibility(View.GONE);
                            twelveWeb.loadUrl(contents.get(11).getAssociate().getUrl());
                            twelveWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barTwelve.setVisibility(View.GONE);
                                    twelveWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barTwelve.setVisibility(View.VISIBLE);
                                    twelveWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            twelveWeb.getSettings().setJavaScriptEnabled(true);
                            twelveWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            twelveWeb.setWebChromeClient(new WebChromeClient());
                            twelveWeb.setVisibility(View.VISIBLE);
                            twelveMore.setVisibility(View.GONE);
                        }
                    }
                });
                contentMore.add(twelveMore);
                contentUrl.add(twelveUrl);
            } else if (contents.get(11).getVideo_url() != null) {
                twelveContent.setVisibility(View.VISIBLE);
                twelveWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                twelveWeb.setWebViewClient(new WebViewClient());
                twelveWeb.loadUrl(contents.get(11).getVideo_url());
                twelveWeb.getSettings().setJavaScriptEnabled(true);
                twelveWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                twelveWeb.setWebChromeClient(new WebChromeClient());
                twelveWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkEleventh() {
        if (contents.get(10) != null) {
            if (contents.get(10).getText() != null) {
                elevenContent.setVisibility(View.VISIBLE);
                elevenWeb.getSettings().setDefaultFontSize(16);
                elevenWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barEleven.setVisibility(View.VISIBLE);
                        elevenWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barEleven.setVisibility(View.GONE);
                        elevenWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                elevenWeb.getSettings().setJavaScriptEnabled(true);
                elevenWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                elevenWeb.setWebChromeClient(new WebChromeClient());
                elevenWeb.loadData(Constants.HARDCODED_BODY + contents.get(10).getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(elevenWeb);
            } else if (contents.get(10).getPhotos() != null) {
                elevenContent.setVisibility(View.VISIBLE);
                elevenImage.setVisibility(View.VISIBLE);
                barEleven.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(10).getPhotos().get(0).getImg_url(), elevenImage, barEleven);
                if (!contents.get(10).getPhotos().get(0).getDescription().equals("")) {
                    elevenDescription.setText(contents.get(10).getPhotos().get(0).getDescription());
                    elevenDescription.setVisibility(View.VISIBLE);
                } else {
                    elevenDescription.setText("");
                    elevenDescription.setVisibility(View.GONE);
                }
                elevenDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(elevenDescription);
            } else if (contents.get(10).getAssociate() != null) {
                elevenContent.setVisibility(View.VISIBLE);
                elevenMore.setVisibility(View.VISIBLE);
                elevenUrl.setVisibility(View.VISIBLE);
                elevenMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                elevenUrl.setText(contents.get(10).getAssociate().getTitle());
                elevenUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                elevenUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(10).getAssociate().getId() != null) {
                            loadArticle(contents.get(10).getAssociate().getId());
                        }else if (contents.get(10).getAssociate().getUrl() != null) {
                            elevenUrl.setVisibility(View.GONE);
                            elevenWeb.loadUrl(contents.get(10).getAssociate().getUrl());
                            elevenWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barEleven.setVisibility(View.GONE);
                                    elevenWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barEleven.setVisibility(View.VISIBLE);
                                    elevenWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            elevenWeb.getSettings().setJavaScriptEnabled(true);
                            elevenWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            elevenWeb.setWebChromeClient(new WebChromeClient());
                            elevenWeb.setVisibility(View.VISIBLE);
                        }
                    }
                });
                contentMore.add(elevenMore);
                contentUrl.add(elevenUrl);
            } else if (contents.get(10).getVideo_url() != null) {
                elevenContent.setVisibility(View.VISIBLE);
                elevenWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                elevenWeb.setWebViewClient(new WebViewClient());
                elevenWeb.loadUrl(contents.get(10).getVideo_url());
                elevenWeb.getSettings().setJavaScriptEnabled(true);
                elevenWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                elevenWeb.setWebChromeClient(new WebChromeClient());
                elevenWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkTenth() {
        if (contents.get(9) != null) {
            if (contents.get(9).getText() != null) {
                tenContent.setVisibility(View.VISIBLE);
                tenWeb.getSettings().setDefaultFontSize(16);
                tenWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barTen.setVisibility(View.VISIBLE);
                        tenWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barTen.setVisibility(View.GONE);
                        tenWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                tenWeb.getSettings().setJavaScriptEnabled(true);
                tenWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                tenWeb.setWebChromeClient(new WebChromeClient());
                tenWeb.loadData(Constants.HARDCODED_BODY + contents.get(9).getText().replace("//www", "https://www"), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(tenWeb);
            } else if (contents.get(9).getPhotos() != null) {
                tenContent.setVisibility(View.VISIBLE);
                tenImage.setVisibility(View.VISIBLE);
                barTen.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(9).getPhotos().get(0).getImg_url(), tenImage, barTen);
                if (!contents.get(9).getPhotos().get(0).getDescription().equals("")) {
                    tenDescription.setText(contents.get(9).getPhotos().get(0).getDescription());
                    tenDescription.setVisibility(View.VISIBLE);

                } else {
                    tenDescription.setVisibility(View.GONE);
                }
                tenDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(tenDescription);
            } else if (contents.get(9).getAssociate() != null) {
                tenContent.setVisibility(View.VISIBLE);
                tenMore.setVisibility(View.VISIBLE);
                tenUrl.setVisibility(View.VISIBLE);
                tenMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                tenUrl.setText(contents.get(9).getAssociate().getTitle());
                tenUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                tenUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(9).getAssociate().getId() != null) {
                            loadArticle(contents.get(9).getAssociate().getId());
                        }else if (contents.get(9).getAssociate().getUrl() != null) {
                            tenUrl.setVisibility(View.GONE);
                            tenWeb.loadUrl(contents.get(9).getAssociate().getUrl());
                            tenWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barTen.setVisibility(View.GONE);
                                    tenWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barTen.setVisibility(View.VISIBLE);
                                    tenWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            tenWeb.getSettings().setJavaScriptEnabled(true);
                            tenWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            tenWeb.setWebChromeClient(new WebChromeClient());
                            tenWeb.setVisibility(View.VISIBLE);
                        }
                    }
                });
                contentMore.add(tenMore);
                contentUrl.add(tenUrl);
            } else if (contents.get(9).getVideo_url() != null) {
                tenContent.setVisibility(View.VISIBLE);
                tenWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                tenWeb.setWebViewClient(new WebViewClient());
                tenWeb.loadUrl(contents.get(9).getVideo_url());
                tenWeb.getSettings().setJavaScriptEnabled(true);
                tenWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                tenWeb.setWebChromeClient(new WebChromeClient());
                tenWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkNinth() {
        if (contents.get(8) != null) {
            if (contents.get(8).getText() != null) {
                nineContent.setVisibility(View.VISIBLE);
                nineWeb.getSettings().setDefaultFontSize(16);
                nineWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barNine.setVisibility(View.VISIBLE);
                        nineWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barNine.setVisibility(View.GONE);
                        nineWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                nineWeb.getSettings().setJavaScriptEnabled(true);
                nineWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                nineWeb.setWebChromeClient(new WebChromeClient());
                nineWeb.loadData(Constants.HARDCODED_BODY + contents.get(8).getText().replace("//www", "https://www"), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(nineWeb);
            } else if (contents.get(8).getPhotos() != null) {
                nineContent.setVisibility(View.VISIBLE);
                nineImage.setVisibility(View.VISIBLE);
                barNine.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(8).getPhotos().get(0).getImg_url(), nineImage, barNine);
                if (!contents.get(8).getPhotos().get(0).getDescription().equals("")) {
                    nineDescription.setText(contents.get(8).getPhotos().get(0).getDescription());
                    nineDescription.setVisibility(View.VISIBLE);
                } else {
                    nineDescription.setText("Отсутствует описание к данному фото");
                    nineDescription.setVisibility(View.GONE);
                }
                nineDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(nineDescription);
            } else if (contents.get(8).getAssociate() != null) {
                nineContent.setVisibility(View.VISIBLE);
                nineMore.setVisibility(View.VISIBLE);
                nineUrl.setVisibility(View.VISIBLE);
                nineMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                nineUrl.setText(contents.get(8).getAssociate().getTitle());
                nineUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                nineUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(8).getAssociate().getId() != null) {
                            loadArticle(contents.get(8).getAssociate().getId());
                        }else if (contents.get(8).getAssociate().getUrl() != null) {
                            nineUrl.setVisibility(View.GONE);
                            nineWeb.loadUrl(contents.get(8).getAssociate().getUrl());
                            nineWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barNine.setVisibility(View.GONE);
                                    nineWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barNine.setVisibility(View.VISIBLE);
                                    nineWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            nineWeb.getSettings().setJavaScriptEnabled(true);
                            nineWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            nineWeb.setWebChromeClient(new WebChromeClient());
                            nineWeb.setVisibility(View.VISIBLE);
                        }
                    }
                });
                contentMore.add(nineMore);
                contentUrl.add(nineUrl);
            } else if (contents.get(8).getVideo_url() != null) {
                nineContent.setVisibility(View.VISIBLE);
                nineWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                nineWeb.setWebViewClient(new WebViewClient());
                nineWeb.loadUrl(contents.get(8).getVideo_url());
                nineWeb.getSettings().setJavaScriptEnabled(true);
                nineWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                nineWeb.setWebChromeClient(new WebChromeClient());
                nineWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkEight() {
        if (contents.get(7) != null) {
            if (contents.get(7).getText() != null) {
                eightContent.setVisibility(View.VISIBLE);
                eightWeb.getSettings().setDefaultFontSize(16);
                eightWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barEight.setVisibility(View.VISIBLE);
                        eightWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barEight.setVisibility(View.GONE);
                        eightWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                eightWeb.getSettings().setJavaScriptEnabled(true);
                eightWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                eightWeb.setWebChromeClient(new WebChromeClient());
                eightWeb.loadData(Constants.HARDCODED_BODY + contents.get(7).getText().replace("//www", "https://www"), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(eightWeb);
            } else if (contents.get(7).getPhotos() != null) {
                eightContent.setVisibility(View.VISIBLE);
                eightImage.setVisibility(View.VISIBLE);
                barEight.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(7).getPhotos().get(0).getImg_url(), eightImage, barEight);
                if (!contents.get(7).getPhotos().get(0).getDescription().equals("")) {
                    eightDescription.setText(contents.get(7).getPhotos().get(0).getDescription());
                    eightDescription.setVisibility(View.VISIBLE);
                } else {
                    eightDescription.setText("Отсутствует описание к данному фото");
                    eightDescription.setVisibility(View.GONE);
                }
                eightDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(eightDescription);
            } else if (contents.get(7).getAssociate() != null) {
                eightContent.setVisibility(View.VISIBLE);
                eightMore.setVisibility(View.VISIBLE);
                eightUrl.setVisibility(View.VISIBLE);
                eightMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                eightUrl.setText(contents.get(7).getAssociate().getTitle());
                eightUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                eightUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(7).getAssociate().getId() != null) {
                            loadArticle(contents.get(7).getAssociate().getId());
                        }else if (contents.get(7).getAssociate().getUrl() != null) {
                            eightUrl.setVisibility(View.GONE);
                            eightWeb.loadUrl(contents.get(7).getAssociate().getUrl());
                            eightWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barEight.setVisibility(View.GONE);
                                    eightWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barEight.setVisibility(View.VISIBLE);
                                    eightWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            eightWeb.getSettings().setJavaScriptEnabled(true);
                            eightWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            eightWeb.setWebChromeClient(new WebChromeClient());
                            eightWeb.setVisibility(View.VISIBLE);
                        }
                    }
                });
                contentMore.add(eightMore);
                contentUrl.add(eightUrl);
            } else if (contents.get(7).getVideo_url() != null) {
                eightContent.setVisibility(View.VISIBLE);
                eightWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                eightWeb.setWebViewClient(new WebViewClient());
                eightWeb.loadUrl(contents.get(7).getVideo_url());
                eightWeb.getSettings().setJavaScriptEnabled(true);
                eightWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                eightWeb.setWebChromeClient(new WebChromeClient());
                eightWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkSeventh() {
        if (contents.get(6) != null) {
            if (contents.get(6).getText() != null) {
                sevenContent.setVisibility(View.VISIBLE);
                sevenWeb.getSettings().setDefaultFontSize(16);
                sevenWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barSeven.setVisibility(View.VISIBLE);
                        sevenWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barSeven.setVisibility(View.GONE);
                        sevenWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                sevenWeb.getSettings().setJavaScriptEnabled(true);
                sevenWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                sevenWeb.setWebChromeClient(new WebChromeClient());
                sevenWeb.loadData(Constants.HARDCODED_BODY + contents.get(6).getText().replace("//www", "https://www"), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(sevenWeb);
            } else if (contents.get(6).getPhotos() != null) {
                sevenContent.setVisibility(View.VISIBLE);
                sevenImage.setVisibility(View.VISIBLE);
                barSeven.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(6).getPhotos().get(0).getImg_url(), sevenImage, barSeven);
                if (!contents.get(6).getPhotos().get(0).getDescription().equals("")) {
                    sevenDescription.setText(contents.get(6).getPhotos().get(0).getDescription());
                    sevenDescription.setVisibility(View.VISIBLE);
                } else {
                    sevenDescription.setText("");
                    sevenDescription.setVisibility(View.GONE);
                }

                sevenDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(sevenDescription);

            } else if (contents.get(6).getAssociate() != null) {
                sevenContent.setVisibility(View.VISIBLE);
                sevenMore.setVisibility(View.VISIBLE);
                sevenUrl.setVisibility(View.VISIBLE);
                sevenMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                sevenUrl.setText(contents.get(6).getAssociate().getTitle());
                sevenUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                sevenUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(6).getAssociate().getId() != null) {
                            loadArticle(contents.get(6).getAssociate().getId());
                        }else if (contents.get(6).getAssociate().getUrl() != null) {
                            sevenUrl.setVisibility(View.GONE);
                            sevenWeb.loadUrl(contents.get(6).getAssociate().getUrl());
                            sevenWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barSeven.setVisibility(View.GONE);
                                    sevenWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barSeven.setVisibility(View.VISIBLE);
                                    sevenWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            sevenWeb.getSettings().setJavaScriptEnabled(true);
                            sevenWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            sevenWeb.setWebChromeClient(new WebChromeClient());
                            sevenWeb.setVisibility(View.VISIBLE);
                        }
                    }
                });
                contentMore.add(sevenMore);
                contentUrl.add(sevenUrl);
            } else if (contents.get(6).getVideo_url() != null) {
                sevenContent.setVisibility(View.VISIBLE);
                sevenWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                sevenWeb.setWebViewClient(new WebViewClient());
                sevenWeb.loadUrl(contents.get(6).getVideo_url());
                sevenWeb.getSettings().setJavaScriptEnabled(true);
                sevenWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                sevenWeb.setWebChromeClient(new WebChromeClient());
                sevenWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkSixth() {
        if (contents.get(5) != null) {
            if (contents.get(5).getText() != null) {
                sixContent.setVisibility(View.VISIBLE);
                sixWeb.getSettings().setDefaultFontSize(16);
                sixWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barSix.setVisibility(View.VISIBLE);
                        sixWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barSix.setVisibility(View.GONE);
                        sixWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                sixWeb.getSettings().setJavaScriptEnabled(true);
                sixWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                sixWeb.setWebChromeClient(new WebChromeClient());
                sixWeb.loadData(Constants.HARDCODED_BODY + contents.get(5).getText().replace("//www", "https://www"), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(sixWeb);
            } else if (contents.get(5).getPhotos() != null) {
                sixContent.setVisibility(View.VISIBLE);
                sixImage.setVisibility(View.VISIBLE);
                barSix.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(5).getPhotos().get(0).getImg_url(), sixImage, barSix);
                if (!contents.get(5).getPhotos().get(0).getDescription().equals("")) {
                    sixDescription.setText(contents.get(5).getPhotos().get(0).getDescription());
                    sixDescription.setVisibility(View.VISIBLE);
                } else {
                    sixDescription.setText("Отсутствует описание к данному фото");
                    sixDescription.setVisibility(View.GONE);
                }
                sixDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(sixDescription);

            } else if (contents.get(5).getAssociate() != null) {
                sixContent.setVisibility(View.VISIBLE);
                sixMore.setVisibility(View.VISIBLE);
                sixUrl.setVisibility(View.VISIBLE);
                sixMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                sixUrl.setText(contents.get(5).getAssociate().getTitle());
                sixUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                sixUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(5).getAssociate().getId() != null) {
                            loadArticle(contents.get(5).getAssociate().getId());
                        }else if (contents.get(5).getAssociate().getUrl() != null) {
                            sixUrl.setVisibility(View.GONE);
                            sixWeb.loadUrl(contents.get(5).getAssociate().getUrl());
                            sixWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barSix.setVisibility(View.GONE);
                                    sixWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barSix.setVisibility(View.VISIBLE);
                                    sixWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            sixWeb.getSettings().setJavaScriptEnabled(true);
                            sixWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            sixWeb.setWebChromeClient(new WebChromeClient());
                            sixWeb.setVisibility(View.VISIBLE);
                        }
                    }
                });
                contentMore.add(sixMore);
                contentUrl.add(sixUrl);
            } else if (contents.get(5).getVideo_url() != null) {
                sixContent.setVisibility(View.VISIBLE);
                sixWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                sixWeb.setWebViewClient(new WebViewClient());
                sixWeb.loadUrl(contents.get(5).getVideo_url());
                sixWeb.getSettings().setJavaScriptEnabled(true);
                sixWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                sixWeb.setWebChromeClient(new WebChromeClient());
                sixWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkFifth() {
        if (contents.get(4) != null) {
            if (contents.get(4).getText() != null) {
                fifthContent.setVisibility(View.VISIBLE);
                fifthWeb.getSettings().setDefaultFontSize(16);
                fifthWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barFive.setVisibility(View.VISIBLE);
                        fifthWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barFive.setVisibility(View.GONE);
                        fifthWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                fifthWeb.getSettings().setJavaScriptEnabled(true);
                fifthWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                fifthWeb.setWebChromeClient(new WebChromeClient());
                fifthWeb.loadData(Constants.HARDCODED_BODY + contents.get(4).getText().replace("//www", "https://www"), "text/html; charset=utf-8", "UTF-8");
                contentViews.add(fifthWeb);
            } else if (contents.get(4).getPhotos() != null) {
                fifthContent.setVisibility(View.VISIBLE);
                fifthImage.setVisibility(View.VISIBLE);
                barFive.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(4).getPhotos().get(0).getImg_url(), fifthImage, barFive);
                if (!contents.get(4).getPhotos().get(0).getDescription().equals("")) {
                    fifthDescription.setText(contents.get(4).getPhotos().get(0).getDescription());
                    fifthDescription.setVisibility(View.VISIBLE);
                } else {
                    fifthDescription.setText("Отсутствует описание к данному фото");
                }
                fifthDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(fifthDescription);
            } else if (contents.get(4).getGallery() != null) {
                for (int i = 0; i < contents.get(4).getGallery().size(); i++) {
                    galleries.add(contents.get(4).getGallery().get(i));
                }
            } else if (contents.get(4).getAssociate() != null) {
                fifthContent.setVisibility(View.VISIBLE);
                fifthMore.setVisibility(View.VISIBLE);
                fifthUrl.setVisibility(View.VISIBLE);
                fifthMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                fifthUrl.setText(contents.get(4).getAssociate().getTitle());
                fifthUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                fifthUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(4).getAssociate().getId() != null) {
                            loadArticle(contents.get(4).getAssociate().getId());
                        }else if (contents.get(4).getAssociate().getUrl() != null) {
                            fifthUrl.setVisibility(View.GONE);
                            fifthWeb.loadUrl(contents.get(4).getAssociate().getUrl());
                            fifthWeb.setWebViewClient(new WebViewClient());
                            fifthWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barFive.setVisibility(View.GONE);
                                    fifthWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barFive.setVisibility(View.VISIBLE);
                                    fifthWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            fifthWeb.getSettings().setJavaScriptEnabled(true);
                            fifthWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            fifthWeb.setWebChromeClient(new WebChromeClient());
                            fifthWeb.setVisibility(View.VISIBLE);
                        }
                    }
                });
                contentMore.add(fifthMore);
                contentUrl.add(fifthUrl);
            } else if (contents.get(4).getVideo_url() != null) {
                fifthContent.setVisibility(View.VISIBLE);
                fifthWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                fifthWeb.setWebViewClient(new WebViewClient());
                fifthWeb.loadUrl(contents.get(4).getVideo_url());
                fifthWeb.getSettings().setJavaScriptEnabled(true);
                fifthWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                fifthWeb.setWebChromeClient(new WebChromeClient());
                fifthWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkFourth() {
        if (contents.get(3) != null) {
            if (contents.get(3).getText() != null) {
                fourthContent.setVisibility(View.VISIBLE);
                fourthWeb.getSettings().setDefaultFontSize(16);
                fourthWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barFour.setVisibility(View.VISIBLE);
                        fourthWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barFour.setVisibility(View.GONE);
                        fourthWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                fourthWeb.getSettings().setJavaScriptEnabled(true);
                fourthWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                fourthWeb.setWebChromeClient(new WebChromeClient());
                fourthWeb.loadData(Constants.HARDCODED_BODY + contents.get(3).getText().replace("//www", "https://www"), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(fourthWeb);
            } else if (contents.get(3).getPhotos() != null) {
                fourthContent.setVisibility(View.VISIBLE);
                fourthImage.setVisibility(View.VISIBLE);
                barFour.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(3).getPhotos().get(0).getImg_url(), fourthImage, barFour);
                if (!contents.get(3).getPhotos().get(0).getDescription().equals("")) {
                    fourthDescription.setText(contents.get(3).getPhotos().get(0).getDescription());
                    fourthDescription.setVisibility(View.VISIBLE);
                } else {
                    fourthDescription.setText("Отсутствует описание к данному фото");
                }
                fourthDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(fourthDescription);
            } else if (contents.get(3).getAssociate() != null) {
                fourthContent.setVisibility(View.VISIBLE);
                fourthMore.setVisibility(View.VISIBLE);
                fourthUrl.setVisibility(View.VISIBLE);
                fourthMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                fourthUrl.setText(contents.get(3).getAssociate().getTitle());
                fourthUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                fourthUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(3).getAssociate().getId() != null) {
                            loadArticle(contents.get(3).getAssociate().getId());
                        }else if (contents.get(3).getAssociate().getUrl()!= null) {
                            fourthUrl.setVisibility(View.GONE);
                            fourthWeb.loadUrl(contents.get(3).getAssociate().getUrl());
                            fourthWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barFour.setVisibility(View.GONE);
                                    fourthWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barFour.setVisibility(View.VISIBLE);
                                    fourthWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            fourthWeb.getSettings().setJavaScriptEnabled(true);
                            fourthWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            fourthWeb.setWebChromeClient(new WebChromeClient());
                            fourthWeb.setVisibility(View.VISIBLE);
                        }
                    }
                });
                contentMore.add(fourthMore);
                contentUrl.add(fourthUrl);
            } else if (contents.get(3).getVideo_url() != null) {
                fourthContent.setVisibility(View.VISIBLE);
                fourthWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                fourthWeb.setWebViewClient(new WebViewClient());
                fourthWeb.loadUrl(contents.get(3).getVideo_url());
                fourthWeb.getSettings().setJavaScriptEnabled(true);
                fourthWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                fourthWeb.setWebChromeClient(new WebChromeClient());
                fourthWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkThird() {
        if (contents.get(2) != null) {
            if (contents.get(2).getText() != null) {
                thirdContent.setVisibility(View.VISIBLE);
                thirdWeb.getSettings().setDefaultFontSize(16);
                thirdWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barThree.setVisibility(View.VISIBLE);
                        thirdWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barThree.setVisibility(View.GONE);
                        thirdWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                thirdWeb.getSettings().setJavaScriptEnabled(true);
                thirdWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                thirdWeb.setWebChromeClient(new WebChromeClient());
                thirdWeb.loadData(Constants.HARDCODED_BODY + contents.get(2).getText().replace("//www", "https://www"), "text/html; charset=utf-8", "UTF-8");
                contentViews.add(thirdWeb);
            } else if (contents.get(2).getPhotos() != null) {
                thirdContent.setVisibility(View.VISIBLE);
                thirdImage.setVisibility(View.VISIBLE);
                barThree.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(2).getPhotos().get(0).getImg_url(), thirdImage, barThree);
                if (!contents.get(2).getPhotos().get(0).getDescription().equals("")) {
                    thirdDescription.setText(contents.get(2).getPhotos().get(0).getDescription());
                    thirdDescription.setVisibility(View.VISIBLE);
                } else {
                    thirdDescription.setText("Отсутствует описание к данному фото");
                }
                thirdDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(thirdDescription);
            } else if (contents.get(2).getAssociate() != null) {
                thirdContent.setVisibility(View.VISIBLE);
                thirdMore.setVisibility(View.VISIBLE);
                thirdUrl.setVisibility(View.VISIBLE);
                thirdMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                thirdUrl.setText(contents.get(2).getAssociate().getTitle());
                thirdUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                thirdUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(2).getAssociate().getId() != null) {
                            loadArticle(contents.get(2).getAssociate().getId());
                        }else if (contents.get(2).getAssociate().getUrl() != null) {
                        thirdUrl.setVisibility(View.GONE);
                        thirdWeb.loadUrl(contents.get(2).getAssociate().getUrl());
                        thirdWeb.setWebViewClient(new WebViewClient(){
                            @Override
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);
                                barThree.setVisibility(View.GONE);
                                thirdWeb.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                super.onPageStarted(view, url, favicon);
                                barThree.setVisibility(View.VISIBLE);
                                thirdWeb.setVisibility(View.GONE);
                            }

                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return false;
                            }
                        });
                        thirdWeb.getSettings().setJavaScriptEnabled(true);
                        thirdWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                        thirdWeb.setWebChromeClient(new WebChromeClient());
                        thirdWeb.setVisibility(View.VISIBLE);
                    }
                    }
                });
                contentMore.add(thirdMore);
                contentUrl.add(thirdUrl);
            } else if (contents.get(2).getVideo_url() != null) {
                thirdContent.setVisibility(View.VISIBLE);
                thirdWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                thirdWeb.setWebViewClient(new WebViewClient());
                thirdWeb.loadUrl(contents.get(2).getVideo_url());
                thirdWeb.getSettings().setJavaScriptEnabled(true);
                thirdWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                thirdWeb.setWebChromeClient(new WebChromeClient());
                thirdWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkSecond() {
        if (contents.get(1) != null) {
            if (contents.get(1).getText() != null) {
                secondContent.setVisibility(View.VISIBLE);
                secondWeb.getSettings().setDefaultFontSize(16);
                secondWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barTwo.setVisibility(View.VISIBLE);
                        secondWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barTwo.setVisibility(View.GONE);
                        secondWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                secondWeb.getSettings().setJavaScriptEnabled(true);
                secondWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                secondWeb.setWebChromeClient(new WebChromeClient());
                secondWeb.loadData(Constants.HARDCODED_BODY + contents.get(1).getText().replace("//www", "https://www"), "text/html; charset=utf-8", "UTF-8");
                contentViews.add(secondWeb);
            } else if (contents.get(1).getPhotos() != null) {
                secondContent.setVisibility(View.VISIBLE);
                secondImage.setVisibility(View.VISIBLE);
                barTwo.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(1).getPhotos().get(0).getImg_url(), secondImage, barTwo);
                if (!contents.get(1).getPhotos().get(0).getDescription().equals("")) {
                    secondDescription.setText(contents.get(1).getPhotos().get(0).getDescription());
                    secondDescription.setVisibility(View.VISIBLE);
                } else {
                    secondDescription.setText("Отсутствует описание к данному фото");
                }
                secondDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(secondDescription);

            } else if (contents.get(1).getAssociate() != null) {
                secondContent.setVisibility(View.VISIBLE);
                secondMore.setVisibility(View.VISIBLE);
                secondUrl.setVisibility(View.VISIBLE);
                secondMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                secondUrl.setText(contents.get(1).getAssociate().getTitle());
                secondUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                secondUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(1).getAssociate().getId() != null) {
                            loadArticle(contents.get(1).getAssociate().getId());
                        }else if (contents.get(1).getAssociate().getUrl() != null) {
                            secondUrl.setVisibility(View.GONE);
                            secondWeb.loadUrl(contents.get(1).getAssociate().getUrl());
                            secondWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barTwo.setVisibility(View.GONE);
                                    secondWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barTwo.setVisibility(View.VISIBLE);
                                    secondWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            secondWeb.getSettings().setJavaScriptEnabled(true);
                            secondWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            secondWeb.setWebChromeClient(new WebChromeClient());
                            secondWeb.setVisibility(View.VISIBLE);
                        }
                    }
                });
                contentMore.add(secondMore);
                contentUrl.add(secondUrl);
            } else if (contents.get(1).getVideo_url() != null) {
                secondContent.setVisibility(View.VISIBLE);
                secondWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                secondWeb.setWebViewClient(new WebViewClient());
                secondWeb.loadUrl(contents.get(1).getVideo_url());
                secondWeb.getSettings().setJavaScriptEnabled(true);
                secondWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                secondWeb.setWebChromeClient(new WebChromeClient());
                secondWeb.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkFirst() {
        if (contents.get(0) != null) {
            if (contents.get(0).getText() != null) {
                firstContent.setVisibility(View.VISIBLE);
                firstWeb.getSettings().setDefaultFontSize(16);
                firstWeb.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        barOne.setVisibility(View.VISIBLE);
                        firstWeb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        barOne.setVisibility(View.GONE);
                        firstWeb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                        return false;
                    }
                });
                firstWeb.loadData(contents.get(0).getText(), "text/html; charset=utf-8", "UTF-8");
                firstWeb.getSettings().setJavaScriptEnabled(true);
                firstWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                firstWeb.setWebChromeClient(new WebChromeClient());
                contentViews.add(firstWeb);
            } else if (contents.get(0).getPhotos() != null) {
                firstContent.setVisibility(View.VISIBLE);
                firstImage.setVisibility(View.VISIBLE);
                barOne.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, contents.get(0).getPhotos().get(0).getImg_url(), fifthImage, barOne);
                if (!contents.get(0).getPhotos().get(0).getDescription().equals("")) {
                    firstDescription.setText(contents.get(0).getPhotos().get(0).getDescription());
                    fifthDescription.setVisibility(View.VISIBLE);
                } else {
                    firstDescription.setText("Отсутствует описание к данному фото");
                }
                firstDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(firstDescription);
            } else if (contents.get(0).getAssociate() != null) {
                firstContent.setVisibility(View.VISIBLE);
                firstMore.setVisibility(View.VISIBLE);
                firstUrl.setVisibility(View.VISIBLE);
                firstMore.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                firstUrl.setText(contents.get(0).getAssociate().getTitle());
                firstUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                firstUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (contents.get(0).getAssociate().getId() != null) {
                            loadArticle(contents.get(0).getAssociate().getId());
                        }else if (contents.get(0).getAssociate().getUrl() != null) {
                            firstUrl.setVisibility(View.GONE);
                            firstWeb.loadUrl(contents.get(0).getAssociate().getUrl());
                            firstWeb.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    barOne.setVisibility(View.GONE);
                                    firstWeb.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    barOne.setVisibility(View.VISIBLE);
                                    firstWeb.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            firstWeb.getSettings().setJavaScriptEnabled(true);
                            firstWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            firstWeb.setWebChromeClient(new WebChromeClient());
                            firstWeb.setVisibility(View.VISIBLE);
                        }
                    }
                });
                contentMore.add(firstMore);
                contentUrl.add(firstUrl);
            } else if (contents.get(0).getVideo_url() != null) {
                firstContent.setVisibility(View.VISIBLE);
                firstWeb.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                firstWeb.setWebViewClient(new WebViewClient());
                firstWeb.loadUrl(contents.get(0).getVideo_url());
                firstWeb.getSettings().setJavaScriptEnabled(true);
                firstWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                firstWeb.setWebChromeClient(new WebChromeClient());
                firstWeb.setVisibility(View.VISIBLE);
            }
        }

    }

    @OnClick(R.id.littlePhotoSwitcherTwo)
    public void pagerClick() {
        countPager = pager.getCurrentItem();
        countPager++;
        pager.setCurrentItem(countPager);
    }

    private void setUPPager() {
        smallGallery = new ArrayList<>();
        if (!item.getPhotos().isEmpty() && item.getPhotos() != null) {
            if (item.getPhotos().size() > 1) {
                smallGallery.addAll(item.getPhotos());
                littleAdapter = new GalleryPagerAdapter(this, smallGallery);
                littleGallery.setAdapter(littleAdapter);
                littleGallery.setCurrentItem(0);
                littlePhotoSwitchCounter.setText("1/" + String.valueOf(item.getPhotos().size()));
                splitterThird.setVisibility(View.GONE);
                galleryBackground.setVisibility(View.VISIBLE);
                littleGallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        littlePhotoSwitchCounter.setText(String.valueOf(littleGallery.getCurrentItem() + 1) + "/" + String.valueOf(item.getPhotos().size()));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            } else {
                splitterThird.setVisibility(View.VISIBLE);
                galleryBackground.setVisibility(View.GONE);
            }
        } else {
            splitterThird.setVisibility(View.VISIBLE);
            galleryBackground.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.littlePhotoSwitchCounter)
    public void nextPhoto() {
        count = littleGallery.getCurrentItem();
        count++;
        littleGallery.setCurrentItem(count);

    }

    private void initSocials() {
        if (!item.getOg_image().equals("")) {
            loadBitmap(item.getImage());
        }
        text = item.getTitle() + "\n" + "\n" + item.getShortdecription();
        FacebookSdk.sdkInitialize(getApplicationContext());
        VKSdk.initialize(getApplicationContext());
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
    }

    private void setUpShare() {
        Log.d(LOG, "setUpShare");
        shareBuilder = new AlertDialog.Builder(this);
        shareBuilder.setView(shareLayout);
        shareBuilder.setCancelable(true);
        shareDialog = shareBuilder.create();
        shareTitle = (TextView) shareLayout.findViewById(R.id.shareTitle);
        shareTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        facebookTitle = (TextView) shareLayout.findViewById(R.id.facebookTitle);
        facebookTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        vkontakteTitle = (TextView) shareLayout.findViewById(R.id.vkontakteTitle);
        vkontakteTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        facebook = (RelativeLayout) shareLayout.findViewById(R.id.shareViaFB);
        shareFacebookDialog = new ShareDialog(this);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook.startAnimation(alpha);
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(item.getTitle())
                            .setImageUrl(Uri.parse(item.getOg_image()))
                            .setContentDescription(item.getShortdecription())
                            .setContentUrl(Uri.parse(item.getUrl()))
                            .build();
                    shareFacebookDialog.show(linkContent, ShareDialog.Mode.NATIVE);
                }
                shareDialog.dismiss();
            }
        });
        vkontakte = (RelativeLayout) shareLayout.findViewById(R.id.shareViaVK);
        vkontakte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vkontakte.startAnimation(alpha);
                VKSdk.login(ArticleActivity.this, VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS, VKScope.OFFLINE);
                VKPhotoArray photos = new VKPhotoArray();

                vkShareDialog = new VKShareDialogBuilder()
                        .setText(text)
                        .setUploadedPhotos(photos)
                        .setAttachmentImages(new VKUploadImage[]{
                                new VKUploadImage(bmp, VKImageParameters.pngImage())
                        }).setAttachmentLink("Отправлено с помощью приложения Klops.ru", item.getUrl())
                        .setShareDialogListener(new VKShareDialog.VKShareDialogListener() {
                            @Override
                            public void onVkShareComplete(int postId) {
                                Toast.makeText(getApplicationContext(), "Вы поделились новостью", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVkShareCancel() {
                                Toast.makeText(getApplicationContext(), "Вы отменили операцию", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVkShareError(VKError error) {
                                Log.d(LOG, String.valueOf(error.apiError.errorCode));
                                Log.d(LOG, error.apiError.errorMessage);
                            }
                        });
                vkShareDialog.show(getSupportFragmentManager(), "VK_SHARE_DIALOG");
                shareDialog.dismiss();
            }
        });

    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public void loadBitmap(String url) {
        if (loadTarget == null) loadTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                handleBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.with(this).load(url).into(loadTarget);
    }

    private void handleBitmap(Bitmap bitmap) {
        setBmp(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Toast.makeText(getApplicationContext(), "Пользователь успешно авторизирован", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(), "Произошла ошибка авторизации", Toast.LENGTH_SHORT).show();
            }
        }))
            super.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick(R.id.buttonFormat)
    public void openFormatDialog() {
        format.startAnimation(alpha);
        switch (formatCount) {
            case 0:
                formatCount++;
                FragmentManager fminc = getSupportFragmentManager();
                List<Fragment> fragmentsInc = fminc.getFragments();
                for (Fragment fragment : fragmentsInc) {
                    if (fragment instanceof SimpleTextArticleFragment) {
                        ((SimpleTextArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof SimpleWithImageArticleFragment) {
                        ((SimpleWithImageArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof LongArticleFragment) {
                        ((LongArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof InterviewArticleFragment) {
                        ((InterviewArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof AuthorArticleFragment) {
                        ((AuthorArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof NationalArticleFragment) {
                        ((NationalArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof ImportantArticleFragment) {
                        ((ImportantArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof GalleryOneArticleFragment) {
                        ((GalleryOneArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof GalleryTwoArticleFragment) {
                        ((GalleryTwoArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof SimpleWideArticleFragment) {
                        ((SimpleWideArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof UrgentArticleFragment) {
                        ((UrgentArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof AdsArticleFragment) {
                        ((AdsArticleFragment) fragment).formatIncrement();
                    }

                    for (TextView text : contentDescriptions) {
                        text.setTextSize(18);
                    }

                    for (TextView more : contentMore) {
                        more.setTextSize(12);
                    }

                    for (TextView url : contentUrl) {
                        url.setTextSize(18);
                    }

                    promotionIcon.setLayoutParams(new RelativeLayout.LayoutParams(25, 25));
                    promotionText.setTextSize(12);
                    matchArticles.setTextSize(28);
                }
                break;
            case 1:
                formatCount = 0;
                FragmentManager fmndec = getSupportFragmentManager();
                List<Fragment> fragmentsDec = fmndec.getFragments();
                for (Fragment fragment : fragmentsDec) {
                    if (fragment instanceof SimpleTextArticleFragment) {
                        ((SimpleTextArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof SimpleWithImageArticleFragment) {
                        ((SimpleWithImageArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof LongArticleFragment) {
                        ((LongArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof InterviewArticleFragment) {
                        ((InterviewArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof AuthorArticleFragment) {
                        ((AuthorArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof NationalArticleFragment) {
                        ((NationalArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof ImportantArticleFragment) {
                        ((ImportantArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof GalleryOneArticleFragment) {
                        ((GalleryOneArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof GalleryTwoArticleFragment) {
                        ((GalleryTwoArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof SimpleWideArticleFragment) {
                        ((SimpleWideArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof UrgentArticleFragment) {
                        ((UrgentArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof AdsArticleFragment) {
                        ((AdsArticleFragment) fragment).formatDecrement();
                    }

                    for (TextView text : contentDescriptions) {
                        text.setTextSize(16);
                    }

                    for (TextView more : contentMore) {
                        more.setTextSize(10);
                    }

                    for (TextView url : contentUrl) {
                        url.setTextSize(16);
                    }

                    promotionIcon.setLayoutParams(new RelativeLayout.LayoutParams(20, 20));
                    promotionText.setTextSize(10);
                    matchArticles.setTextSize(26);
                }
                break;
        }
    }

    private void drawFragment() {
        Log.d(LOG, "drawFragment");
        Bundle bundle = new Bundle();
        articleType = item.getArticle_type();
        switch (articleType) {
            case Constants.SIMPLE_TEXT:
                SimpleTextArticleFragment simpleText = new SimpleTextArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                simpleText.setArguments(bundle);
                placeArticleFragment(simpleText);
            case Constants.SIMPLE_IMAGE_TEXT:
                SimpleWithImageArticleFragment simpleImage = new SimpleWithImageArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                simpleImage.setArguments(bundle);
                placeArticleFragment(simpleImage);
                break;
            case Constants.LONG_TEXT:
                LongArticleFragment longArticle = new LongArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                longArticle.setArguments(bundle);
                placeArticleFragment(longArticle);
                break;
            case Constants.INTERVIEW_TEXT:
                InterviewArticleFragment interviewArticle = new InterviewArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                interviewArticle.setArguments(bundle);
                placeArticleFragment(interviewArticle);
                break;
            case Constants.AUTHORS_TEXT:
                AuthorArticleFragment authorArticle = new AuthorArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                authorArticle.setArguments(bundle);
                placeArticleFragment(authorArticle);
                break;
            case Constants.NATIONAL_TEXT:
                NationalArticleFragment nationalArticle = new NationalArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                nationalArticle.setArguments(bundle);
                placeArticleFragment(nationalArticle);
                break;
            case Constants.IMPORTANT_TEXT:
                ImportantArticleFragment importantArticle = new ImportantArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                importantArticle.setArguments(bundle);
                placeArticleFragment(importantArticle);
                break;
            case Constants.GALLERY_FIRST_TEXT:
                galleryBackground();
                GalleryOneArticleFragment galleryOneArticle = new GalleryOneArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                galleryOneArticle.setArguments(bundle);
                placeArticleFragment(galleryOneArticle);
                break;
            case Constants.GALLERY_SECOND_TEXT:
                galleryBackground();
                GalleryTwoArticleFragment galleryTwoArticle = new GalleryTwoArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                galleryTwoArticle.setArguments(bundle);
                placeArticleFragment(galleryTwoArticle);
                break;
            case Constants.SIMPLE_WIDE_TEXT:
                SimpleWideArticleFragment simpleWideArticle = new SimpleWideArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                simpleWideArticle.setArguments(bundle);
                placeArticleFragment(simpleWideArticle);
                break;
            case Constants.ADS_TEXT:
                AdsArticleFragment adsArticleFragment = new AdsArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                adsArticleFragment.setArguments(bundle);
                placeArticleFragment(adsArticleFragment);
                break;
            case Constants.URGENT_TEXT:
                urgentBackground();
                UrgentArticleFragment urgentArticleFragment = new UrgentArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                urgentArticleFragment.setArguments(bundle);
                placeArticleFragment(urgentArticleFragment);
        }
    }

    private void urgentBackground() {
        fullArticleLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        splitterThird.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        splitterFour.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        galleryBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        promotionLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        promotionText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        littlePhotoSwitchCounter.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        firstDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        firstWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        firstMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        firstUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        secondDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        secondWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        secondMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        secondUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        thirdDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        thirdWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        thirdMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        thirdUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        fourthDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        fourthWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        fourthMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        fourthUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        fifthDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        fifthWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        fifthMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        fifthUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sixDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sixWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        sixMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sixUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sevenDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sevenWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        sevenMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sevenUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        eightDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        eightWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        eightMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        eightUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        nineDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        nineWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        nineMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        nineUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tenDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tenWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        tenMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tenUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        elevenDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        elevenWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        elevenMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        elevenUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        twelveDescription.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        twelveWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        twelveMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        twelveUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    private void galleryBackground() {
        format.setBackgroundResource(R.drawable.format_white);
        share.setBackgroundResource(R.drawable.share_icon_white);
        back.setBackgroundResource(R.drawable.back_white);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.darkColor));
        fullArticleLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        splitterThird.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
        splitterFour.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        promotionLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        galleryBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
        littlePhotoSwitchCounter.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        littleSwitchPhotoIcon.setBackgroundResource(R.drawable.gallery_dark);
        firstDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        firstWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        firstMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        firstUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        secondDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        secondWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        secondMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        secondUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        thirdDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        thirdWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        thirdMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        thirdUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        fourthDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        fourthWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        fourthMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        fourthUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        fifthDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        fifthWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        fifthMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        fifthUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sixDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        sixWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        sixMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sixUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sevenDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        sevenWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        sevenMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        sevenUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        eightDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        eightWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        eightMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        eightUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        nineDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        nineWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        nineMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        nineUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tenDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        tenWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        tenMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tenUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        elevenDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        elevenWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        elevenMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        elevenUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        twelveDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        twelveWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        twelveMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        twelveUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));

    }

    public void placeArticleFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.articleContainer, fragment)
                .commit();
    }

    @OnClick(R.id.backButton)
    public void back() {
        back.startAnimation(alpha);
        finish();
        onBackPressed();
    }

    @OnClick(R.id.shareSocials)
    public void openShareDialog() {
        share.startAnimation(alpha);
        shareDialog.show();
    }

    @Override
    public void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        AppEventsLogger.activateApp(app);
        Log.d(LOG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(LOG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(LOG, "onStop");
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG, "onBackPressed");
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG, "onDestroy");
        unbinder.unbind();
        finish();
        super.onDestroy();
    }
}


