package ru.klops.klops;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

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
import ru.klops.klops.api.KlopsApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.fragments.ArticleFragment;
import ru.klops.klops.fragments.ContentFragment;
import ru.klops.klops.fragments.GalleryFragment;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.models.article.Connected_items;
import ru.klops.klops.models.article.Content;
import ru.klops.klops.models.article.Gallery;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.feed.Page;
import ru.klops.klops.models.popular.Popular;
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
    View shareLayout;
    Item item;
    @BindView(R.id.articleLayer)
    RelativeLayout articleLayer;
    @BindView(R.id.textMatch)
    TextView matchArticles;
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
    @BindView(R.id.toolbarSeparatorArticle)
    View toolbarSeparatorArticle;
    @BindView(R.id.connectedSeparator)
    View connectedSeparator;
    @BindView(R.id.scrollArticleLayout)
    ScrollView scrollArticleLayout;

    @BindView(R.id.galleryDescription)
    TextView galleryDescription;

    ArrayList<Connected_items> connectedItemses;
    ArrayList<Content> contents;
    Animation alpha;
    GalleryPagerAdapter gAdapter;
    ArrayList<Gallery> galleries;
    int countPager = 0;
    int formatCount = 0;
    Unbinder unbinder;
    ArrayList<RelativeLayout> contentLayouts;
    Tracker mTracker;
    String contentType;
    @BindView(R.id.galleryLayer)
    RelativeLayout galleryLayer;
    @BindView(R.id.littleSwitchPhotoIconTwo)
    ImageView littleSwitchPhotoIconTwo;
    String intentData;
    Intent webIntent;

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
        contentType = getIntent().getStringExtra(Constants.CONTENT_TYPE);
        setSupportActionBar(toolbar);
        drawFragment();
        setUPContent();
        setUpGalleries();
        setUpMatchNews();
    }

    private void setUpGalleries() {
        if (galleries.size() != 0) {
            ArrayList<String> photos = new ArrayList<>();
            for (Gallery gallery : galleries) {
                photos.add(gallery.getImg_url());
            }
            gAdapter = new GalleryPagerAdapter(this, galleries);
            pager.setAdapter(gAdapter);
            pager.setCurrentItem(0);
            contentGallery.setVisibility(View.VISIBLE);
            splitterThird.setVisibility(View.VISIBLE);
            galleryDescription.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
            galleryDescription.setText(galleries.get(0).getDescription());
            littlePhotoSwitchCounterTwo.setText("1/" + String.valueOf(galleries.size()));
            final int count = galleries.size();
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    littlePhotoSwitchCounterTwo.setText(String.valueOf(pager.getCurrentItem() + 1) + "/" + String.valueOf(count));
                    galleryDescription.setText(galleries.get(position).getDescription());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
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

    public void loadArticle(Integer id, final String contentType) {
        Log.d(LOG, "loadArticle");
        KlopsApi.ArticleApi articleApi = RetrofitServiceGenerator.createService(KlopsApi.ArticleApi.class);
        Observable<Article> callArticle = articleApi.getItemById(id, contentType);
        callArticle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "loadArticle - onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, "loadArticle - onError" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Article article) {
                        Log.d(LOG, "loadArticle - onNext");
                        Intent newMatchArticle = getIntent();
                        newMatchArticle.putExtra(Constants.ITEM, article.getItem());
                        newMatchArticle.putExtra(Constants.TYPE, contentType);
                        startActivity(newMatchArticle);
                    }
                });
    }

    private void loadWebArticle(Integer id, final String contentType) {
        Log.d(LOG, "loadWebArticle");
        KlopsApi.ArticleApi articleApi = RetrofitServiceGenerator.createService(KlopsApi.ArticleApi.class);
        Observable<Article> callArticle = articleApi.getItemById(id, contentType);
        callArticle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "loadArticle - onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, "loadArticle - onError" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Article article) {
                        Log.d(LOG, "loadArticle - onNext");
                        Intent intent = getIntent();
                        intent.putExtra(Constants.ITEM, article.getItem());
                        intent.putExtra(Constants.CONTENT_TYPE, contentType);
                        finish();
                        startActivity(intent);
                    }
                });
    }

    private void loadPhoto(ArticleActivity context, String imageUrl, ImageView imView, final ProgressBar bar) {
        Log.d(LOG, "loadPhoto");
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

    @OnClick(R.id.buttonFormat)
    public void openFormatDialog() {
        FragmentManager fminc = getSupportFragmentManager();
        List<Fragment> fragmentsInc = fminc.getFragments();
        format.startAnimation(alpha);
        switch (formatCount) {
            case 0:
                formatCount++;
                for (Fragment fragment : fragmentsInc) {
                    if (fragment instanceof ArticleFragment) {
                        ((ArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof GalleryFragment) {
                        ((GalleryFragment) fragment).formatIncrement();
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
                    galleryDescription.setTextSize(18);
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
                    if (fragment instanceof ArticleFragment) {
                        ((ArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof GalleryFragment) {
                        ((GalleryFragment) fragment).formatDecrement();
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
                    galleryDescription.setTextSize(16);
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
        switch (contentType) {
            case Constants.ARTICLE_TYPE:
                ArticleFragment articleFragment = new ArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                articleFragment.setArguments(bundle);
                placeArticleFragment(articleFragment);
                break;
            case Constants.GALLERY_TYPE:
                galleryBackground();
                GalleryFragment galleryFragment = new GalleryFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                galleryFragment.setArguments(bundle);
                placeArticleFragment(galleryFragment);
                break;
        }
    }

    private void galleryBackground() {
        Log.d(LOG, "setUPGalleryBackground");
        format.setBackgroundResource(R.drawable.format_white);
        share.setBackgroundResource(R.drawable.share_icon_white);
        back.setBackgroundResource(R.drawable.back_white);
        toolbarSeparatorArticle.setBackgroundColor(ContextCompat.getColor(this, R.color.blackText));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.darkColor));
        fullArticleLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        splitterThird.setBackgroundColor(ContextCompat.getColor(this, R.color.greyText));
        splitterFour.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        matchArticles.setTextColor(ContextCompat.getColor(this, R.color.greyText));
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
        galleryLayer.setBackgroundColor(ContextCompat.getColor(this, R.color.galleryCard));
        littleSwitchPhotoIconTwo.setBackgroundResource(R.drawable.gallery_dark);
        littlePhotoSwitcherTwo.setBackgroundResource(R.drawable.gallery_switch_shape_dark);
        littlePhotoSwitchCounterTwo.setTextColor(ContextCompat.getColor(this, R.color.darkGreyText));
        galleryDescription.setTextColor(ContextCompat.getColor(this, R.color.darkGreyText));
    }

    public void placeArticleFragment(Fragment fragment) {
        Log.d(LOG, "placeArticleFragment");
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
    }

    private Uri getLocalBitmapUri(Bitmap bmp) {
        Log.d(LOG, "getLocalBitmapUri");
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
        Log.d(LOG, "shareNews");
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
    }

    @Override
    public void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
        FlurryAgent.onStartSession(this, Constants.FLURRY_API_KEY);
    }

    @Override
    public void onResume() {
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
        if (app.getFlag() == 0) {
            finish();
            super.onBackPressed();
        } else if (app.getFlag() == 1) {
            loadFeeds();
        } else if (app.getFlag() == 2) {
            loadFeeds();
        }
    }

    private void loadFeeds() {
        KlopsApi.FeedApi apiNew = RetrofitServiceGenerator.createService(KlopsApi.FeedApi.class);
        Observable<Page> callNew = apiNew.getAllNews();
        callNew.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Page>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG, "loadFeeds + onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, "loadFeeds + onError" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Page page) {
                        Log.d(LOG, "loadFeeds + onNext");
                        app.setFirstPage(page);
                        KlopsApi.FeedApi apiPopular = RetrofitServiceGenerator.createService(KlopsApi.FeedApi.class);
                        Observable<Popular> callPopular = apiPopular.getPopularNews();
                        callPopular.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Popular>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.d(LOG, "loadFeeds + onCompleted");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(LOG, "loadFeeds + onError" + e.getLocalizedMessage());

                                    }

                                    @Override
                                    public void onNext(Popular popular) {
                                        Log.d(LOG, "loadFeeds + onNext");
                                        app.setPopularPage(popular);
                                        Intent intent = new Intent(ArticleActivity.this, HomeActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                    }
                });
    }

    @Override
    public void onDestroy() {
        Log.d(LOG, "onDestroy");
        unbinder.unbind();
        finish();
        super.onDestroy();
    }
}



