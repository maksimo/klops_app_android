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
import ru.klops.klops.models.ContentView;
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
    ArrayList<ContentView> viwes;


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
        viwes = new ArrayList<>();
        viwes.add(new ContentView(firstContent, firstWeb, barOne,firstImage, firstDescription, firstMore, firstUrl));
        viwes.add(new ContentView(secondContent, secondWeb, barTwo, secondImage, secondDescription, secondMore, secondUrl));
        viwes.add(new ContentView(thirdContent, thirdWeb, barThree, thirdImage, thirdDescription, thirdMore, thirdUrl));
        viwes.add(new ContentView(fourthContent, fourthWeb, barFour, fourthImage, fourthDescription, fourthMore, fourthUrl));
        viwes.add(new ContentView(fifthContent, fifthWeb, barFive, fifthImage, fifthDescription, fifthMore, fifthUrl));
        viwes.add(new ContentView(sixContent, sixWeb, barSix, sixImage, sixDescription, sixMore, sixUrl));
        viwes.add(new ContentView(sevenContent, sevenWeb, barSeven, sevenImage, sevenDescription, sevenMore, sevenUrl));
        viwes.add(new ContentView(eightContent, eightWeb, barEight, eightImage, eightDescription, eightMore, eightUrl));
        viwes.add(new ContentView(nineContent, nineWeb, barNine, nineImage, nineDescription, nineMore, nineUrl));
        viwes.add(new ContentView(tenContent, tenWeb, barTen, tenImage, tenDescription, tenMore, tenUrl));
        viwes.add(new ContentView(elevenContent, elevenWeb, barEleven, elevenImage, elevenDescription, elevenMore, elevenUrl));
        viwes.add(new ContentView(twelveContent, twelveWeb, barTwelve, twelveImage, twelveDescription, twelveMore, twelveUrl));

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
        contentViews = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++){
            if (contents.get(i)!=null) {
                initContent(contents.get(i), viwes.get(i).getContentLayer(), viwes.get(i).getContentView(), viwes.get(i).getLoader(),
                        viwes.get(i).getImage(), viwes.get(i).getDescription(),
                        viwes.get(i).getMoreTitle(), viwes.get(i).getMoreUrl());
            }
        }
    }

    public void initContent(final Content content, RelativeLayout contentLayer, final WebView contentView, final ProgressBar loader, ImageView image, TextView description, final TextView moreTitle, final TextView moreUrl){
        if (content!= null){
            if (content.getText() != null) {
                contentLayer.setVisibility(View.VISIBLE);
                contentView.getSettings().setDefaultFixedFontSize(16);
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
                        return false;
                    }
                });
                contentView.getSettings().setJavaScriptEnabled(true);
                contentView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                contentView.setWebChromeClient(new WebChromeClient());
                contentView.loadData(Constants.HARDCODED_BODY + content.getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(contentView);
            } else if (content.getPhotos() != null) {
                contentLayer.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                loader.setVisibility(View.VISIBLE);
                loadPhoto(ArticleActivity.this, content.getPhotos().get(0).getImg_url(), image, loader);
                if (!content.getPhotos().get(0).getDescription().equals("")) {
                    description.setText(content.getPhotos().get(0).getDescription());
                    description.setVisibility(View.VISIBLE);
                } else {
                    description.setText("");
                    description.setVisibility(View.GONE);
                }
                description.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(description);
            } else if (content.getAssociate() != null) {
                contentLayer.setVisibility(View.VISIBLE);
                moreTitle.setVisibility(View.VISIBLE);
                moreUrl.setVisibility(View.VISIBLE);
                moreTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                moreUrl.setText(content.getAssociate().getTitle());
                moreUrl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                moreUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (content.getAssociate().getId() != null) {
                            loadArticle(content.getAssociate().getId());
                        }else if (content.getAssociate().getUrl() != null) {
                            moreUrl.setVisibility(View.GONE);
                            contentView.loadUrl(content.getAssociate().getUrl());
                            contentView.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    loader.setVisibility(View.GONE);
                                    contentView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                    super.onPageStarted(view, url, favicon);
                                    loader.setVisibility(View.VISIBLE);
                                    contentView.setVisibility(View.GONE);
                                }

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return false;
                                }
                            });
                            contentView.getSettings().setJavaScriptEnabled(true);
                            contentView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            contentView.setWebChromeClient(new WebChromeClient());
                            contentView.setVisibility(View.VISIBLE);
                            moreTitle.setVisibility(View.GONE);
                        }
                    }
                });
                contentMore.add(moreTitle);
                contentUrl.add(moreUrl);
            } else if (content.getVideo_url() != null) {
                contentLayer.setVisibility(View.VISIBLE);
                contentView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                contentView.setWebViewClient(new WebViewClient());
                contentView.loadUrl(content.getVideo_url());
                contentView.getSettings().setJavaScriptEnabled(true);
                contentView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                contentView.setWebChromeClient(new WebChromeClient());
                contentView.setVisibility(View.VISIBLE);
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


