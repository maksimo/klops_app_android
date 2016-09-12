package ru.klops.klops;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.util.Log;
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
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
import ru.klops.klops.fragments.MainShortFragment;
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

    @BindView(R.id.thirteenContent)
    RelativeLayout thirteenContent;
    @BindView(R.id.thirteenWeb)
    WebView thirteenWeb;
    @BindView(R.id.thirteenImage)
    ImageView thirteenImage;
    @BindView(R.id.thirteenDescription)
    TextView thirteenDescription;
    @BindView(R.id.thirteenMore)
    TextView thirteenMore;
    @BindView(R.id.thirteenUrl)
    TextView thirteenUrl;
    @BindView(R.id.fourteenContent)
    RelativeLayout fourteenContent;
    @BindView(R.id.fourteenWeb)
    WebView fourteenWeb;
    @BindView(R.id.fourteenImage)
    ImageView fourteenImage;
    @BindView(R.id.fourteenDescription)
    TextView fourteenDescription;
    @BindView(R.id.fourteenMore)
    TextView fourteenMore;
    @BindView(R.id.fourteenUrl)
    TextView fourteenUrl;
    @BindView(R.id.fifteenContent)
    RelativeLayout fifteenContent;
    @BindView(R.id.fifteenWeb)
    WebView fifteenWeb;
    @BindView(R.id.fifteenImage)
    ImageView fifteenImage;
    @BindView(R.id.fifteenDescription)
    TextView fifteenDescription;
    @BindView(R.id.fifteenMore)
    TextView fifteenMore;
    @BindView(R.id.fifteenUrl)
    TextView fifteenUrl;
    @BindView(R.id.sixteenContent)
    RelativeLayout sixteenContent;
    @BindView(R.id.sixteenWeb)
    WebView sixteenWeb;
    @BindView(R.id.sixteenImage)
    ImageView sixteenImage;
    @BindView(R.id.sixteenDescription)
    TextView sixteenDescription;
    @BindView(R.id.sixteenMore)
    TextView sixteenMore;
    @BindView(R.id.sixteenUrl)
    TextView sixteenUrl;
    @BindView(R.id.seventeenContent)
    RelativeLayout seventeenContent;
    @BindView(R.id.seventeenWeb)
    WebView seventeenWeb;
    @BindView(R.id.seventeenImage)
    ImageView seventeenImage;
    @BindView(R.id.seventeenDescription)
    TextView seventeenDescription;
    @BindView(R.id.seventeenMore)
    TextView seventeenMore;
    @BindView(R.id.seventeenUrl)
    TextView seventeenUrl;
    @BindView(R.id.eighteenContent)
    RelativeLayout eighteenContent;
    @BindView(R.id.eighteenWeb)
    WebView eighteenWeb;
    @BindView(R.id.eighteenImage)
    ImageView eighteenImage;
    @BindView(R.id.eighteenDescription)
    TextView eighteenDescription;
    @BindView(R.id.eighteenMore)
    TextView eighteenMore;
    @BindView(R.id.eighteenUrl)
    TextView eighteenUrl;
    @BindView(R.id.nineteenContent)
    RelativeLayout nineteenContent;
    @BindView(R.id.nineteenWeb)
    WebView nineteenWeb;
    @BindView(R.id.nineteenImage)
    ImageView nineteenImage;
    @BindView(R.id.nineteenDescription)
    TextView nineteenDescription;
    @BindView(R.id.nineteenMore)
    TextView nineteenMore;
    @BindView(R.id.nineteenUrl)
    TextView nineteenUrl;
    @BindView(R.id.twentyContent)
    RelativeLayout twentyContent;
    @BindView(R.id.twentyWeb)
    WebView twentyWeb;
    @BindView(R.id.twentyImage)
    ImageView twentyImage;
    @BindView(R.id.twentyDescription)
    TextView twentyDescription;
    @BindView(R.id.twentyMore)
    TextView twentyMore;
    @BindView(R.id.twentyUrl)
    TextView twentyUrl;
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

    @BindView(R.id.fullArticleLayer)
    RelativeLayout fullArticleLayer;
    @BindView(R.id.splitterFour)
    View splitterFour;
    @BindView(R.id.littleSwitchPhotoIcon)
    ImageView littleSwitchPhotoIcon;
    @BindView(R.id.toolbarSeparatorArticle)
    View toolbarSeparatorArticle;
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
    @BindView(R.id.firstVideo)
    WebView firstVideo;
    @BindView(R.id.secondVideo)
    WebView secondVideo;
    @BindView(R.id.thirdVideo)
    WebView thirdVideo;
    @BindView(R.id.fourthVideo)
    WebView fourthVideo;
    @BindView(R.id.fifthVideo)
    WebView fifthVideo;
    @BindView(R.id.sixVideo)
    WebView sixVideo;
    @BindView(R.id.sevenVideo)
    WebView sevenVideo;
    @BindView(R.id.eightVideo)
    WebView eightVideo;
    @BindView(R.id.nineVideo)
    WebView nineVideo;
    @BindView(R.id.tenVideo)
    WebView tenVideo;
    @BindView(R.id.elevenVideo)
    WebView elevenVideo;
    @BindView(R.id.twelveVideo)
    WebView twelveVideo;
    Uri sharedBitmap;
    Tracker mTracker;
    String type;

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
        mTracker = app.getDefaultTracker();
        item = getIntent().getParcelableExtra(Constants.ITEM);
        type = getIntent().getStringExtra(Constants.TYPE);
        setSupportActionBar(toolbar);
        initSocials();
        setUpShare();
        drawFragment();
        setUPContent();
        getAllContents();
        setUpGalleries();
        setUpMatchNews();
    }

    private void initShareImage() {
        if (!item.getImage().equals("")) {
            Ion.with(this).load(item.getImage()).withBitmap().asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {
                            sharedBitmap = getLocalBitmapUri(result);

                        }
                    });
        }
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
        viwes.add(new ContentView(firstContent, firstWeb, barOne, firstImage, firstDescription, firstMore, firstUrl, firstVideo));
        viwes.add(new ContentView(secondContent, secondWeb, barTwo, secondImage, secondDescription, secondMore, secondUrl, secondVideo));
        viwes.add(new ContentView(thirdContent, thirdWeb, barThree, thirdImage, thirdDescription, thirdMore, thirdUrl, thirdVideo));
        viwes.add(new ContentView(fourthContent, fourthWeb, barFour, fourthImage, fourthDescription, fourthMore, fourthUrl, fourthVideo));
        viwes.add(new ContentView(fifthContent, fifthWeb, barFive, fifthImage, fifthDescription, fifthMore, fifthUrl, fifthVideo));
        viwes.add(new ContentView(sixContent, sixWeb, barSix, sixImage, sixDescription, sixMore, sixUrl, sixVideo));
        viwes.add(new ContentView(sevenContent, sevenWeb, barSeven, sevenImage, sevenDescription, sevenMore, sevenUrl, sevenVideo));
        viwes.add(new ContentView(eightContent, eightWeb, barEight, eightImage, eightDescription, eightMore, eightUrl, eightVideo));
        viwes.add(new ContentView(nineContent, nineWeb, barNine, nineImage, nineDescription, nineMore, nineUrl, nineVideo));
        viwes.add(new ContentView(tenContent, tenWeb, barTen, tenImage, tenDescription, tenMore, tenUrl, tenVideo));
        viwes.add(new ContentView(elevenContent, elevenWeb, barEleven, elevenImage, elevenDescription, elevenMore, elevenUrl, elevenVideo));
        viwes.add(new ContentView(twelveContent, twelveWeb, barTwelve, twelveImage, twelveDescription, twelveMore, twelveUrl, twelveVideo));

        for (int n = 0; n < contents.size(); n++) {
            if (contents.get(n).getGallery() != null && !contents.get(n).getGallery().isEmpty()) {
                galleries.addAll(contents.get(n).getGallery());
            }
        }

    }

    public void getAllContents() {
        contentViews = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i) != null) {
                initContent(contents.get(i), viwes.get(i).getContentLayer(), viwes.get(i).getContentView(), viwes.get(i).getLoader(),
                        viwes.get(i).getImage(), viwes.get(i).getDescription(),
                        viwes.get(i).getMoreTitle(), viwes.get(i).getMoreUrl(), viwes.get(i).getWebVideo());
            }
        }
    }

    public void initContent(final Content content, RelativeLayout contentLayer, final WebView contentView, final ProgressBar loader, ImageView image, TextView description, final TextView moreTitle, final TextView moreUrl, WebView webVideo) {
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
                contentView.loadDataWithBaseURL(null, content.getText().replace("font-size:18px", "font-size:16px"), "text/html", "UTF-8", null);
//                }
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
                                loadArticle(articleId);
                            }
                        } else if (!url.contains("id=")) {
                            Intent browser = new Intent(ArticleActivity.this, AppBrowserActivity.class);
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
                contentView.setWebChromeClient(new WebChromeClient());
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
                        } else {
                            Intent browser = new Intent(ArticleActivity.this, AppBrowserActivity.class);
                            browser.putExtra(Constants.URL, content.getAssociate().getUrl());
                            startActivity(browser);
                        }
                    }
                });
                contentMore.add(moreTitle);
                contentUrl.add(moreUrl);
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


    @OnClick(R.id.littlePhotoSwitcherTwo)
    public void pagerClick() {
        if (countPager != gAdapter.getCount() - 1) {
            pager.setCurrentItem(countPager + 1);
            countPager++;
        } else {
            countPager = 0;
            pager.setCurrentItem(countPager);
        }
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
        if (count != littleAdapter.getCount() - 1) {
            littleGallery.setCurrentItem(count + 1);
            count++;
        } else {
            count = 0;
            littleGallery.setCurrentItem(count);
        }
    }

    private void initSocials() {
        if (!item.getImage().equals("")) {
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
                            .setImageUrl(Uri.parse(item.getImage()))
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
        FragmentManager fminc = getSupportFragmentManager();
        List<Fragment> fragmentsInc = fminc.getFragments();
        format.startAnimation(alpha);
        switch (formatCount) {
            case 0:
                formatCount++;
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
                    } else if (fragment instanceof MainShortFragment) {
                        ((MainShortFragment) fragment).formatIncrement();
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

                    matchArticles.setTextSize(27);
                }
                break;
            case 1:
                formatCount = 0;
                for (Fragment fragment : fragmentsInc) {
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
                    } else if (fragment instanceof MainShortFragment) {
                        ((MainShortFragment) fragment).formatDecrement();
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

                    matchArticles.setTextSize(25);
                }
                break;
        }
    }

    private void drawFragment() {
        Log.d(LOG, "drawFragment");
        Bundle bundle = new Bundle();
//        articleType = item.getArticle_type();
        switch (type) {
            case Constants.SIMPLE_TEXT:
                SimpleTextArticleFragment simpleText = new SimpleTextArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                simpleText.setArguments(bundle);
                placeArticleFragment(simpleText);
                break;
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
                UrgentArticleFragment urgentArticleFragment = new UrgentArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                urgentArticleFragment.setArguments(bundle);
                placeArticleFragment(urgentArticleFragment);
                break;
            case Constants.MAIN_SHORT_TEXT:
                MainShortFragment mainShortFragment = new MainShortFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                mainShortFragment.setArguments(bundle);
                placeArticleFragment(mainShortFragment);
                break;
        }
    }

    private void scaleImage(ImageView view) throws NoSuchElementException {
        Bitmap bitmap = null;
        try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            bitmap = Ion.with(view).getBitmap();
        }

        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = dpToPx(250);

        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth();
        height = scaledBitmap.getHeight();
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);

        view.setImageDrawable(result);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);

    }

    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    private void galleryBackground() {
        format.setBackgroundResource(R.drawable.format_white);
        share.setBackgroundResource(R.drawable.share_icon_white);
        back.setBackgroundResource(R.drawable.back_white);
        toolbarSeparatorArticle.setBackgroundColor(ContextCompat.getColor(this, R.color.blackText));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.darkColor));
        fullArticleLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        splitterThird.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
        splitterFour.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        galleryBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
        littlePhotoSwitchCounter.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        littleSwitchPhotoIcon.setBackgroundResource(R.drawable.gallery_dark);
        firstDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        firstWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        secondDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        secondWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        thirdDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        thirdWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        fourthDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        fourthWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        fifthDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        fifthWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        sixDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        sixWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        sevenDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        sevenWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        eightDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        eightWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        nineDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        nineWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        tenDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        tenWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        tenMore.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tenUrl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        elevenDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        elevenWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        twelveDescription.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        twelveWeb.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));

    }

    public void placeArticleFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.articleContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.backButton)
    public void back() {
        back.startAnimation(alpha);
        onBackPressed();
    }

    @OnClick(R.id.shareSocials)
    public void openShareDialog() {
        share.startAnimation(alpha);
        shareNews();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Article Activity action")
                .setAction("Share news")
                .build());
    }

    private Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "sharedKlopsImage" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public void shareNews() {
        final Intent shareIntent = new Intent();
        final String title = item.getTitle();
        final String description = item.getShortdecription();
        final String url = item.getUrl();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, description);
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (!item.getImage().equals("")) {
            Ion.with(this).load(item.getImage()).withBitmap().asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {
                            shareIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(result));
                            startActivity(Intent.createChooser(shareIntent, "Поделиться новостью"));

                        }
                    });
        } else {
            startActivity(Intent.createChooser(shareIntent, "Поделиться новостью"));
        }
//
//        try {
//            List<Intent> targetedShareIntents = new ArrayList<Intent>();
//            Intent share = new Intent(android.content.Intent.ACTION_SEND);
//            share.setType("image/*");
//            List<ResolveInfo> resInfo = getPackageManager()
//                    .queryIntentActivities(share, 0);
//            if (!resInfo.isEmpty()) {
//                for (ResolveInfo info : resInfo) {
//                    final Intent targetedShare = new Intent(
//                            android.content.Intent.ACTION_SEND);
//                    targetedShare.setType("image/*");
//                    if (info.activityInfo.packageName.contains(
//                            "facebook")
//                            || info.activityInfo.name.contains(
//                            "vk")|| info.activityInfo.name.contains(
//                            "viber")|| info.activityInfo.name.contains(
//                            "telegram")|| info.activityInfo.name.contains(
//                            "twitter")|| info.activityInfo.name.contains(
//                            "odnoklasniki")|| info.activityInfo.name.contains(
//                            "twitter")|| info.activityInfo.name.contains("whatsapp")) {
//                        targetedShare.putExtra(Intent.EXTRA_TEXT, item.getTitle());
//                        targetedShare.putExtra(Intent.EXTRA_TEXT, item.getShortdecription());
//                        targetedShare.putExtra(Intent.EXTRA_TEXT, item.getUrl());
//                        if (sharedBitmap!= null) {
//                            targetedShare.putExtra(Intent.EXTRA_STREAM, sharedBitmap);
//                        }
//                        targetedShare.setPackage(info.activityInfo.packageName);
//                        targetedShareIntents.add(targetedShare);
//                    }
//                }
//                Intent chooserIntent = Intent.createChooser(
//                        targetedShareIntents.remove(0), "Выберите приложение");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
//                        targetedShareIntents.toArray(new Parcelable[]{}));
//                startActivity(chooserIntent);
//            }
//        } catch (Exception e) {
//            Log.v("VM",
//                    "Exception while sending data" + e.getMessage());
//        }
    }

    @Override
    public void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
        FlurryAgent.onStartSession(this, Constants.FLURRY_API_KEY);
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Article Activity")
                .setAction("Article Activity Start")
                .build());
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
        FlurryAgent.onEndSession(this);
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG, "onBackPressed");
        finish();
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


