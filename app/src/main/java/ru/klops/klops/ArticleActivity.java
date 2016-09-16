package ru.klops.klops;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.klops.klops.adapter.GalleryContentPagerAdapter;
import ru.klops.klops.adapter.GalleryPagerAdapter;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.fragments.AdsArticleFragment;
import ru.klops.klops.fragments.AuthorArticleFragment;
import ru.klops.klops.fragments.ContentFragment;
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
    @BindView(R.id.articleLayer)
    RelativeLayout articleLayer;
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
    @BindView(R.id.secondContent)
    RelativeLayout secondContent;
    @BindView(R.id.thirdContent)
    RelativeLayout thirdContent;
    @BindView(R.id.fourthContent)
    RelativeLayout fourthContent;
    @BindView(R.id.fifthContent)
    RelativeLayout fifthContent;
    @BindView(R.id.sixContent)
    RelativeLayout sixContent;
    @BindView(R.id.sevenContent)
    RelativeLayout sevenContent;
    @BindView(R.id.eightContent)
    RelativeLayout eightContent;
    @BindView(R.id.nineContent)
    RelativeLayout nineContent;
    @BindView(R.id.tenContent)
    RelativeLayout tenContent;
    @BindView(R.id.elevenContent)
    RelativeLayout elevenContent;
    @BindView(R.id.twelveContent)
    RelativeLayout twelveContent;
    @BindView(R.id.thirteenContent)
    RelativeLayout thirteenContent;
    @BindView(R.id.fourteenContent)
    RelativeLayout fourteenContent;
    @BindView(R.id.fifteenContent)
    RelativeLayout fifteenContent;
    @BindView(R.id.sixteenContent)
    RelativeLayout sixteenContent;
    @BindView(R.id.seventeenContent)
    RelativeLayout seventeenContent;
    @BindView(R.id.eighteenContent)
    RelativeLayout eighteenContent;
    @BindView(R.id.nineteenContent)
    RelativeLayout nineteenContent;
    @BindView(R.id.twentyContent)
    RelativeLayout twentyContent;
    @BindView(R.id.twentyOneContent)
    RelativeLayout twentyOneContent;
    @BindView(R.id.twentyTwoContent)
    RelativeLayout twentyTwoContent;
    @BindView(R.id.twentyThreeContent)
    RelativeLayout twentyThreeContent;
    @BindView(R.id.twentyFourContent)
    RelativeLayout twentyFourContent;
    @BindView(R.id.twentyFiveContent)
    RelativeLayout twentyFiveContent;
    @BindView(R.id.twentySixContent)
    RelativeLayout twentySixContent;
    @BindView(R.id.twentySevenContent)
    RelativeLayout twentySevenContent;
    @BindView(R.id.twentyEightContent)
    RelativeLayout twentyEightContent;
    @BindView(R.id.twentyNineContent)
    RelativeLayout twentyNineContent;
    @BindView(R.id.thirtyContent)
    RelativeLayout thirtyContent;
    @BindView(R.id.thirtyOneContent)
    RelativeLayout thirtyOneContent;
    @BindView(R.id.thirtyTwoContent)
    RelativeLayout thirtyTwoContent;
    @BindView(R.id.thirtyThreeContent)
    RelativeLayout thirtyThreeContent;
    @BindView(R.id.thirtyFourContent)
    RelativeLayout thirtyFourContent;
    @BindView(R.id.thirtyFiveContent)
    RelativeLayout thirtyFiveContent;
    @BindView(R.id.thirtySixContent)
    RelativeLayout thirtySixContent;
    @BindView(R.id.thirtySevenContent)
    RelativeLayout thirtySevenContent;
    @BindView(R.id.thirtyEightContent)
    RelativeLayout thirtyEightContent;
    @BindView(R.id.thirtyNineContent)
    RelativeLayout thirtyNineContent;
    @BindView(R.id.fortyContent)
    RelativeLayout fortyContent;

    @BindView(R.id.fullArticleLayer)
    RelativeLayout fullArticleLayer;
    @BindView(R.id.splitterFour)
    View splitterFour;
    @BindView(R.id.littleSwitchPhotoIcon)
    ImageView littleSwitchPhotoIcon;
    @BindView(R.id.toolbarSeparatorArticle)
    View toolbarSeparatorArticle;
    @BindView(R.id.connectedSeparator)
    View connectedSeparator;
    @BindView(R.id.scrollArticleLayout)
    ScrollView scrollArticleLayout;
    ArrayList<Connected_items> connectedItemses;
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
    ArrayList<RelativeLayout> contentLayouts;
    Uri sharedBitmap;
    Tracker mTracker;
    String type;
    String contentType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.article_activity);
        Log.d(LOG, "onCreate");
        shareLayout = LayoutInflater.from(this).inflate(R.layout.share_dialog, null);
        unbinder = ButterKnife.bind(this);
        app = KlopsApplication.getINSTANCE();
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        mTracker = app.getDefaultTracker();
        item = getIntent().getParcelableExtra(Constants.ITEM);
        type = getIntent().getStringExtra(Constants.TYPE);
        contentType = getIntent().getStringExtra(Constants.CONTENT_TYPE);
        setSupportActionBar(toolbar);
        initSocials();
        setUpShare();
        drawFragment();
        setUPContent();
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
        loadArticle(connectedItemses.get(0).getDoc_list().getId(), connectedItemses.get(0).getDoc_list().getContent_type());
    }

    @OnClick(R.id.connectedNewsTwoLayer)
    public void openSecondMatchNews() {
        connectedNewsTwoLayer.startAnimation(alpha);
        loadArticle(connectedItemses.get(1).getDoc_list().getId(), connectedItemses.get(1).getDoc_list().getContent_type());
    }

    public void loadArticle(Integer id, String contentType) {
        PageApi articleApi = RetrofitServiceGenerator.createService(PageApi.class);
        Observable<Article> callArticle = articleApi.getItemById(id, contentType);
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
                        newMatchArticle.putExtra(Constants.ITEM, type);
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
        galleries = new ArrayList<>();
        contents = new ArrayList<>();
        contents.addAll(item.getContent());
        contentLayouts = new ArrayList<>();
        contentLayouts.add(firstContent);
        contentLayouts.add(secondContent);
        contentLayouts.add(thirdContent);
        contentLayouts.add(fourthContent);
        contentLayouts.add(fifthContent);
        contentLayouts.add(sixContent);
        contentLayouts.add(sevenContent);
        contentLayouts.add(eightContent);
        contentLayouts.add(nineContent);
        contentLayouts.add(tenContent);
        contentLayouts.add(elevenContent);
        contentLayouts.add(twelveContent);
        contentLayouts.add(thirteenContent);
        contentLayouts.add(fourteenContent);
        contentLayouts.add(fifteenContent);
        contentLayouts.add(sixteenContent);
        contentLayouts.add(seventeenContent);
        contentLayouts.add(eighteenContent);
        contentLayouts.add(nineteenContent);
        contentLayouts.add(twentyContent);
        contentLayouts.add(twentyOneContent);
        contentLayouts.add(twentyTwoContent);
        contentLayouts.add(twentyThreeContent);
        contentLayouts.add(twentyFourContent);
        contentLayouts.add(twentyFiveContent);
        contentLayouts.add(twentySixContent);
        contentLayouts.add(twentySevenContent);
        contentLayouts.add(twentyEightContent);
        contentLayouts.add(twentyNineContent);
        contentLayouts.add(thirtyContent);
        contentLayouts.add(thirtyOneContent);
        contentLayouts.add(thirtyTwoContent);
        contentLayouts.add(thirtyThreeContent);
        contentLayouts.add(thirtyFourContent);
        contentLayouts.add(thirtyFiveContent);
        contentLayouts.add(thirtySixContent);
        contentLayouts.add(thirtySevenContent);
        contentLayouts.add(thirtyEightContent);
        contentLayouts.add(thirtyNineContent);
        contentLayouts.add(fortyContent);

        for (int n = 0; n < contents.size(); n++) {
            if (contents.get(n).getGallery() != null && !contents.get(n).getGallery().isEmpty()) {
                galleries.addAll(contents.get(n).getGallery());
            }
        }

        if (contentType.equals(Constants.GALLERY_TYPE)) {
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
            matchLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
            connectedNewsOneDate.setTextColor(ContextCompat.getColor(this, R.color.greyText));
            connectedNewsOneText.setTextColor(ContextCompat.getColor(this, R.color.greyText));
            connectedNewsOneLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
            connectedNewsTwoDate.setTextColor(ContextCompat.getColor(this, R.color.greyText));
            connectedNewsTwoText.setTextColor(ContextCompat.getColor(this, R.color.greyText));
            connectedNewsTwoLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
            articleLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        }


        for (int m = 0; m < contents.size(); m++) {
            contentLayouts.get(m).setVisibility(View.VISIBLE);
            ContentFragment contentFragment = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.CONTENT, contents.get(m));
            bundle.putString(Constants.IS_GALLERY, item.getArticle_type());
            contentFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(contentLayouts.get(m).getId(), contentFragment)
                    .addToBackStack(null)
                    .commit();
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
            littleGallery.setCurrentItem(littleGallery.getCurrentItem() + 1);
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
                    FragmentManager contentFm = getSupportFragmentManager();
                    List<Fragment> fragmentsCont = contentFm.getFragments();
                    for (Fragment fragmCont : fragmentsCont) {
                        if (fragmCont instanceof ContentFragment) {
                            ((ContentFragment) fragmCont).increment();
                            ((ContentFragment) fragmCont).zoomIn();
                        }
                    }
                    matchArticles.setTextSize(27);
                    connectedNewsOneDate.setTextSize(12);
                    connectedNewsOneText.setTextSize(18);
                    connectedNewsTwoDate.setTextSize(12);
                    connectedNewsTwoText.setTextSize(18);
                    if (gAdapter != null) {
                        littlePhotoSwitchCounterTwo.setTextSize(18);
                    }
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
                    FragmentManager contentFm = getSupportFragmentManager();
                    List<Fragment> fragmentsCont = contentFm.getFragments();
                    for (Fragment fragmCont : fragmentsCont) {
                        if (fragmCont instanceof ContentFragment) {
                            ((ContentFragment) fragmCont).decrement();
                            ((ContentFragment) fragmCont).zoomOut();
                        }
                    }
                    matchArticles.setTextSize(25);
                    connectedNewsOneDate.setTextSize(10);
                    connectedNewsOneText.setTextSize(16);
                    connectedNewsTwoDate.setTextSize(10);
                    connectedNewsTwoText.setTextSize(16);
                    if (gAdapter != null) {
                        littlePhotoSwitchCounterTwo.setTextSize(16);
                    }
                }
                break;
        }
    }

    private void drawFragment() {
        Log.d(LOG, "drawFragment");
        Bundle bundle = new Bundle();
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

    private void galleryBackground() {
        format.setBackgroundResource(R.drawable.format_white);
        share.setBackgroundResource(R.drawable.share_icon_white);
        back.setBackgroundResource(R.drawable.back_white);
        toolbarSeparatorArticle.setBackgroundColor(ContextCompat.getColor(this, R.color.blackText));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.darkColor));
        fullArticleLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        splitterThird.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
        splitterFour.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        matchArticles.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        galleryBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
        littlePhotoSwitchCounter.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        littleSwitchPhotoIcon.setBackgroundResource(R.drawable.gallery_dark);
        matchLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.darkColor));
        connectedNewsOneDate.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        connectedNewsOneText.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        connectedNewsOneLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        connectedNewsTwoDate.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        connectedNewsTwoText.setTextColor(ContextCompat.getColor(this, R.color.greyText));
        connectedNewsTwoLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        articleLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        connectedSeparator.setBackgroundColor(ContextCompat.getColor(this, R.color.darkColor));
        scrollArticleLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
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


