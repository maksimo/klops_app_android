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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import com.koushikdutta.ion.Ion;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.klops.klops.adapter.GalleryContentPagerAdapter;
import ru.klops.klops.adapter.GalleryPagerAdapter;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.application.KlopsApplication;
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

    @BindView(R.id.backButton)
    ImageView back;
    @BindView(R.id.buttonFormat)
    ImageView format;
    @BindView(R.id.shareSocials)
    ImageView share;
    @BindView(R.id.toolbarArticle)
    Toolbar toolbar;
    KlopsApplication app;
    String articleType;
    AlertDialog.Builder shareBuilder;
    AlertDialog shareDialog;
    AlertDialog.Builder formatBuilder;
    AlertDialog formatDialog;
    View shareLayout;
    TextView shareTitle;
    TextView facebookTitle;
    TextView vkontakteTitle;
    RelativeLayout facebook;
    RelativeLayout vkontakte;
    View formatLayout;
    TextView formatTitle;
    TextView incrementTitle;
    TextView middleTitle;
    TextView decrementTitle;
    RelativeLayout increment;
    RelativeLayout middle;
    RelativeLayout decrement;
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
    @BindView(R.id.secondContent)
    RelativeLayout secondContent;
    @BindView(R.id.secondWeb)
    WebView secondWeb;
    @BindView(R.id.secondImage)
    ImageView secondImage;
    @BindView(R.id.secondDescription)
    TextView secondDescription;
    @BindView(R.id.thirdContent)
    RelativeLayout thirdContent;
    @BindView(R.id.thirdWeb)
    WebView thirdWeb;
    @BindView(R.id.thirdImage)
    ImageView thirdImage;
    @BindView(R.id.thirdDescription)
    TextView thirdDescription;
    @BindView(R.id.fourthContent)
    RelativeLayout fourthContent;
    @BindView(R.id.fourthWeb)
    WebView fourthWeb;
    @BindView(R.id.fourthImage)
    ImageView fourthImage;
    @BindView(R.id.fourthDescription)
    TextView fourthDescription;
    @BindView(R.id.fifthContent)
    RelativeLayout fifthContent;
    @BindView(R.id.fifthWeb)
    WebView fifthWeb;
    @BindView(R.id.fifthImage)
    ImageView fifthImage;
    @BindView(R.id.fifthDescription)
    TextView fifthDescription;
    @BindView(R.id.sixContent)
    RelativeLayout sixContent;
    @BindView(R.id.sixWeb)
    WebView sixWeb;
    @BindView(R.id.sixImage)
    ImageView sixImage;
    @BindView(R.id.sixDescription)
    TextView sixDescription;
    @BindView(R.id.sevenContent)
    RelativeLayout sevenContent;
    @BindView(R.id.sevenWeb)
    WebView sevenWeb;
    @BindView(R.id.sevenImage)
    ImageView sevenImage;
    @BindView(R.id.sevenDescription)
    TextView sevenDescription;
    @BindView(R.id.eightContent)
    RelativeLayout eightContent;
    @BindView(R.id.eightWeb)
    WebView eightWeb;
    @BindView(R.id.eightImage)
    ImageView eightImage;
    @BindView(R.id.eightDescription)
    TextView eightDescription;
    @BindView(R.id.nineContent)
    RelativeLayout nineContent;
    @BindView(R.id.nineWeb)
    WebView nineWeb;
    @BindView(R.id.nineImage)
    ImageView nineImage;
    @BindView(R.id.nineDescription)
    TextView nineDescription;
    @BindView(R.id.tenContent)
    RelativeLayout tenContent;
    @BindView(R.id.tenWeb)
    WebView tenWeb;
    @BindView(R.id.tenImage)
    ImageView tenImage;
    @BindView(R.id.tenDescription)
    TextView tenDescription;
    @BindView(R.id.elevenContent)
    RelativeLayout elevenContent;
    @BindView(R.id.elevenWeb)
    WebView elevenWeb;
    @BindView(R.id.elevenImage)
    ImageView elevenImage;
    @BindView(R.id.elevenDescription)
    TextView elevenDescription;
    @BindView(R.id.twelveContent)
    RelativeLayout twelveContent;
    @BindView(R.id.twelveWeb)
    WebView twelveWeb;
    @BindView(R.id.twelveImage)
    ImageView twelveImage;
    @BindView(R.id.twelveDescription)
    TextView twelveDescription;

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
    ArrayList<WebView> contentViews;
    ArrayList<TextView> contentDescriptions;
    ArrayList<Connected_items> connectedItemses;
    ArrayList<String> smallGallery;
    GalleryPagerAdapter littleAdapter;
    ShareDialog shareFacebookDialog;
    VKShareDialogBuilder vkShareDialog;
    ArrayList<Content> contents;
    ArrayList<Content> copy;
    Animation alpha;
    Bitmap bmp;
    String text;
    GalleryContentPagerAdapter gAdapter;
    ArrayList<Gallery> galleries;
    int count = 0;
    int countPager = 0;
    private Target loadTarget;
    Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_layers_activity);
        Log.d(LOG, "onCreate");
        shareLayout = LayoutInflater.from(this).inflate(R.layout.share_dialog, null);
        formatLayout = LayoutInflater.from(this).inflate(R.layout.format_dialog, null);
        unbinder = ButterKnife.bind(this);
        app = KlopsApplication.getINSTANCE();
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        item = getIntent().getParcelableExtra(Constants.ITEM);
        setSupportActionBar(toolbar);
        initSocials();
        setUpShare();
        setUpFormat();
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
        Picasso.with(context).load(imageUrl).into(imView, new Callback() {
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

    private void setUPContent() {
        Log.d(LOG, "setUpTextField");
        matchArticles.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-boldex.ttf"));
        contentViews = new ArrayList<>();
        contentDescriptions = new ArrayList<>();
        galleries = new ArrayList<>();
        contents = new ArrayList<>();
        contents.addAll(item.getContent());
        copy = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i).getText() == null || contents.get(i).getText().length() == 0) {
                String copyString = "";
                copy.add(new Content(copyString, contents.get(i).getPhotos(), contents.get(i).getGallery()));

            } else if (contents.get(i).getPhotos() == null || contents.get(i).getPhotos().size() == 0) {
                ArrayList<Photos> copyList = new ArrayList<>();
                copyList.add(new Photos("", ""));
                copy.add(new Content(contents.get(i).getText(), copyList, contents.get(i).getGallery()));
            } else if (contents.get(i).getGallery() == null || contents.get(i).getGallery().size() == 0) {
                ArrayList<Gallery> copyGallery = new ArrayList<>();
                copyGallery.add(new Gallery("", ""));
                copy.add(new Content(contents.get(i).getText(), contents.get(i).getPhotos(), copyGallery));
            }

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
                twelveWeb.setVisibility(View.VISIBLE);
                twelveWeb.getSettings().setDefaultFontSize(16);
                twelveWeb.loadData(contents.get(9).getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(twelveWeb);
            } else if (contents.get(11).getPhotos() != null) {
                twelveContent.setVisibility(View.VISIBLE);
                twelveImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(Constants.HARDCODED_BODY + contents.get(11).getPhotos().get(0).getImg_url()).into(twelveImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barTwelve.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barTwelve.setVisibility(View.GONE);
                    }
                });
                if (!contents.get(11).getPhotos().get(0).getDescription().equals("")) {
                    twelveDescription.setText(contents.get(11).getPhotos().get(0).getDescription());
                } else {
                    twelveDescription.setText("Отсутствует описание к данному фото");
                }
                twelveDescription.setVisibility(View.VISIBLE);
                twelveDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(twelveDescription);
            }
        }
    }

    private void checkEleventh() {
        if (contents.get(10) != null) {
            if (contents.get(10).getText() != null) {
                elevenContent.setVisibility(View.VISIBLE);
                elevenWeb.setVisibility(View.VISIBLE);
                elevenWeb.getSettings().setDefaultFontSize(16);
                elevenWeb.loadData(contents.get(10).getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(elevenWeb);
            } else if (contents.get(10).getPhotos() != null) {
                elevenContent.setVisibility(View.VISIBLE);
                elevenImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(Constants.HARDCODED_BODY + contents.get(10).getPhotos().get(0).getImg_url()).into(elevenImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barEleven.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barEleven.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(10).getPhotos().get(0).getDescription().equals("")) {
                    elevenDescription.setText(contents.get(10).getPhotos().get(0).getDescription());
                } else {
                    elevenDescription.setText("Отсутствует описание к данному фото");
                }
                elevenDescription.setVisibility(View.VISIBLE);
                elevenDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(elevenDescription);
            }
        }
    }

    private void checkTenth() {
        if (contents.get(9) != null) {
            if (contents.get(9).getText() != null) {
                tenContent.setVisibility(View.VISIBLE);
                tenWeb.setVisibility(View.VISIBLE);
                tenWeb.getSettings().setDefaultFontSize(16);
                tenWeb.loadData(Constants.HARDCODED_BODY + contents.get(9).getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(tenWeb);
            } else if (contents.get(9).getPhotos() != null) {
                tenContent.setVisibility(View.VISIBLE);
                tenImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(contents.get(9).getPhotos().get(0).getImg_url()).into(tenImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barTen.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barTen.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(9).getPhotos().get(0).getDescription().equals("")) {
                    tenDescription.setText(contents.get(9).getPhotos().get(0).getDescription());
                } else {
                    tenDescription.setText("Отсутствует описание к данному фото");
                }
                tenDescription.setVisibility(View.VISIBLE);
                tenDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(tenDescription);
            }
        }
    }

    private void checkNinth() {
        if (contents.get(8) != null) {
            if (contents.get(8).getText() != null) {
                nineContent.setVisibility(View.VISIBLE);
                nineWeb.setVisibility(View.VISIBLE);
                nineWeb.getSettings().setDefaultFontSize(16);
                nineWeb.loadData(Constants.HARDCODED_BODY + contents.get(8).getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(nineWeb);
            } else if (contents.get(8).getPhotos() != null) {
                nineContent.setVisibility(View.VISIBLE);
                nineImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(contents.get(8).getPhotos().get(0).getImg_url()).into(nineImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barNine.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barNine.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(8).getPhotos().get(0).getDescription().equals("")) {
                    nineDescription.setText(contents.get(8).getPhotos().get(0).getDescription());
                } else {
                    nineDescription.setText("Отсутствует описание к данному фото");
                }
                nineDescription.setVisibility(View.VISIBLE);
                nineDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(nineDescription);
            }
        }
    }

    private void checkEight() {
        if (contents.get(7) != null) {
            if (contents.get(7).getText() != null) {
                eightContent.setVisibility(View.VISIBLE);
                eightWeb.setVisibility(View.VISIBLE);
                eightWeb.getSettings().setDefaultFontSize(16);
                eightWeb.loadData(Constants.HARDCODED_BODY + contents.get(7).getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(eightWeb);
            } else if (contents.get(7).getPhotos() != null) {
                eightContent.setVisibility(View.VISIBLE);
                eightImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(contents.get(7).getPhotos().get(0).getImg_url()).into(eightImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barEight.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barEight.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(7).getPhotos().get(0).getDescription().equals("")) {
                    eightDescription.setText(contents.get(7).getPhotos().get(0).getDescription());
                } else {
                    eightDescription.setText("Отсутствует описание к данному фото");
                }
                eightDescription.setVisibility(View.VISIBLE);
                eightDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(eightDescription);
            }
        }
    }

    private void checkSeventh() {
        if (contents.get(6) != null) {
            if (contents.get(6).getText() != null) {
                sevenContent.setVisibility(View.VISIBLE);
                sevenWeb.setVisibility(View.VISIBLE);
                sevenWeb.getSettings().setDefaultFontSize(16);
                sevenWeb.loadData(Constants.HARDCODED_BODY + contents.get(6).getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(sevenWeb);
            } else if (contents.get(6).getPhotos() != null) {
                sevenContent.setVisibility(View.VISIBLE);
                sevenImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(contents.get(6).getPhotos().get(0).getImg_url()).into(sevenImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barSeven.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barSeven.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(6).getPhotos().get(0).getDescription().equals("")) {
                    sevenDescription.setText(contents.get(6).getPhotos().get(0).getDescription());
                } else {
                    sevenDescription.setText("Отсутствует описание к данному фото");
                }
                sevenDescription.setVisibility(View.VISIBLE);
                sevenDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(sevenDescription);

            }
        }
    }

    private void checkSixth() {
        if (contents.get(5) != null) {
            if (contents.get(5).getText() != null) {
                sixContent.setVisibility(View.VISIBLE);
                sixWeb.setVisibility(View.VISIBLE);
                sixWeb.getSettings().setDefaultFontSize(16);
                sixWeb.loadData(Constants.HARDCODED_BODY + contents.get(5).getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(sixWeb);
            } else if (contents.get(5).getPhotos() != null) {
                sixContent.setVisibility(View.VISIBLE);
                sixImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(contents.get(5).getPhotos().get(0).getImg_url()).into(sixImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barSix.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barSix.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(5).getPhotos().get(0).getDescription().equals("")) {
                    sixDescription.setText(contents.get(5).getPhotos().get(0).getDescription());
                } else {
                    sixDescription.setText("Отсутствует описание к данному фото");
                }
                sixDescription.setVisibility(View.VISIBLE);
                sixDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(sixDescription);

            }
        }
    }

    private void checkFifth() {
        if (contents.get(4) != null) {
            if (contents.get(4).getText() != null) {
                fifthContent.setVisibility(View.VISIBLE);
                fifthWeb.setVisibility(View.VISIBLE);
                fifthWeb.getSettings().setDefaultFontSize(16);
                fifthWeb.loadData(Constants.HARDCODED_BODY + contents.get(4).getText(), "text/html; charset=utf-8", "UTF-8");
                contentViews.add(fifthWeb);
            } else if (contents.get(4).getPhotos() != null) {
                fourthContent.setVisibility(View.VISIBLE);
                fifthImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(contents.get(4).getPhotos().get(0).getImg_url()).into(fifthImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barFive.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barFive.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(4).getPhotos().get(0).getDescription().equals("")) {
                    fifthDescription.setText(contents.get(4).getPhotos().get(0).getDescription());
                } else {
                    fifthDescription.setText("Отсутствует описание к данному фото");
                }
                fifthDescription.setVisibility(View.VISIBLE);
                fifthDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(fifthDescription);
            } else if (contents.get(4).getGallery() != null) {
                for (int i = 0; i < contents.get(4).getGallery().size(); i++) {
                    galleries.add(contents.get(4).getGallery().get(i));
                }
            }
        }
    }

    private void checkFourth() {
        if (contents.get(3) != null) {
            if (contents.get(3).getText() != null) {
                fourthContent.setVisibility(View.VISIBLE);
                fourthWeb.setVisibility(View.VISIBLE);
                fourthWeb.getSettings().setDefaultFontSize(16);
                fourthWeb.loadData(Constants.HARDCODED_BODY + contents.get(3).getText(), "text/html; chcarset=utf-8", "UTF-8");
                contentViews.add(fourthWeb);
            } else if (contents.get(3).getPhotos() != null) {
                fourthContent.setVisibility(View.VISIBLE);
                fourthImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(contents.get(3).getPhotos().get(0).getImg_url()).into(fourthImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barFour.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barFour.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(3).getPhotos().get(0).getDescription().equals("")) {
                    fourthDescription.setText(contents.get(3).getPhotos().get(0).getDescription());
                } else {
                    fourthDescription.setText("Отсутствует описание к данному фото");
                }
                fourthDescription.setVisibility(View.VISIBLE);
                fourthDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(fourthDescription);

            }
        }
    }

    private void checkThird() {
        if (contents.get(2) != null) {
            if (contents.get(2).getText() != null) {
                thirdContent.setVisibility(View.VISIBLE);
                thirdWeb.setVisibility(View.VISIBLE);
                thirdWeb.getSettings().setDefaultFontSize(16);
                thirdWeb.loadData(Constants.HARDCODED_BODY + contents.get(2).getText(), "text/html; charset=utf-8", "UTF-8");
                contentViews.add(thirdWeb);
            } else if (contents.get(2).getPhotos() != null) {
                thirdContent.setVisibility(View.VISIBLE);
                thirdImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(contents.get(2).getPhotos().get(0).getImg_url()).into(thirdImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barThree.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barThree.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(2).getPhotos().get(0).getDescription().equals("")) {
                    thirdDescription.setText(contents.get(2).getPhotos().get(0).getDescription());
                } else {
                    thirdDescription.setText("Отсутствует описание к данному фото");
                }
                thirdDescription.setVisibility(View.VISIBLE);
                thirdDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(thirdDescription);
            }
        }
    }

    private void checkSecond() {
        if (contents.get(1) != null) {
            if (contents.get(1).getText() != null) {
                secondContent.setVisibility(View.VISIBLE);
                secondWeb.setVisibility(View.VISIBLE);
                secondWeb.getSettings().setDefaultFontSize(16);
                secondWeb.loadData(Constants.HARDCODED_BODY + contents.get(1).getText(), "text/html; charset=utf-8", "UTF-8");
                contentViews.add(secondWeb);
            } else if (contents.get(1).getPhotos() != null) {
                secondContent.setVisibility(View.VISIBLE);
                secondImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(contents.get(1).getPhotos().get(0).getImg_url()).into(secondImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barTwo.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barTwo.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(1).getPhotos().get(0).getDescription().equals("")) {
                    secondDescription.setText(contents.get(1).getPhotos().get(0).getDescription());
                } else {
                    secondDescription.setText("Отсутствует описание к данному фото");
                }
                secondDescription.setVisibility(View.VISIBLE);
                secondDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(secondDescription);

            }
        }
    }

    private void checkFirst() {
        if (contents.get(0) != null) {
            if (contents.get(0).getText() != null) {
                firstContent.setVisibility(View.VISIBLE);
                firstWeb.setVisibility(View.VISIBLE);
                firstWeb.getSettings().setDefaultFontSize(16);
                firstWeb.loadData(contents.get(0).getText(), "text/html; charset=utf-8", "UTF-8");
                contentViews.add(firstWeb);
            } else if (contents.get(0).getPhotos() != null) {
                firstContent.setVisibility(View.VISIBLE);
                firstImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(contents.get(0).getPhotos().get(0).getImg_url()).into(firstImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        barOne.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        barOne.setVisibility(View.VISIBLE);
                    }
                });
                if (!contents.get(0).getPhotos().get(0).getDescription().equals("")) {
                    firstDescription.setText(contents.get(0).getPhotos().get(0).getDescription());
                } else {
                    firstDescription.setText("Отсутствует описание к данному фото");
                }
                fifthDescription.setVisibility(View.VISIBLE);
                firstDescription.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                contentDescriptions.add(firstDescription);
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
        String url = item.getUrl();
        final String newsOnWeb = url.replace("https://klops.ruhttps://klops.ru", "https://klops.ru");
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
                            .setContentUrl(Uri.parse(newsOnWeb))
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
                        }).setAttachmentLink("Отправлено с помощью приложения Klops.ru", newsOnWeb)
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

    private void setUpFormat() {
        Log.d(LOG, "setUpFormat");
        formatBuilder = new AlertDialog.Builder(this);
        formatBuilder.setView(formatLayout);
        formatBuilder.setCancelable(true);
        formatDialog = formatBuilder.create();
        increment = (RelativeLayout) formatLayout.findViewById(R.id.increment);
        formatTitle = (TextView) formatLayout.findViewById(R.id.formatTitle);
        formatTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        incrementTitle = (TextView) formatLayout.findViewById(R.id.incrementTitle);
        incrementTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        middleTitle = (TextView) formatLayout.findViewById(R.id.middleTitle);
        middleTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        decrementTitle = (TextView) formatLayout.findViewById(R.id.decrementTitle);
        decrementTitle.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment.startAnimation(alpha);
                FragmentManager fm = getSupportFragmentManager();
                List<Fragment> fragments = fm.getFragments();
                for (Fragment fragment : fragments) {
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
                    }
                    for (WebView web : contentViews) {
                        web.getSettings().setDefaultFontSize(17);
                    }
                    for (TextView text : contentDescriptions) {
                        text.setTextSize(17);
                    }
                    matchArticles.setTextSize(27);
                    formatDialog.dismiss();
                }
            }
        });
        middle = (RelativeLayout) formatLayout.findViewById(R.id.middle);
        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middle.startAnimation(alpha);
                FragmentManager fm = getSupportFragmentManager();
                List<Fragment> fragments = fm.getFragments();
                for (Fragment fragment : fragments) {
                    if (fragment instanceof SimpleTextArticleFragment) {
                        ((SimpleTextArticleFragment) fragment).formatDefault();
                    } else if (fragment instanceof SimpleWithImageArticleFragment) {
                        ((SimpleWithImageArticleFragment) fragment).formatDefault();
                    } else if (fragment instanceof LongArticleFragment) {
                        ((LongArticleFragment) fragment).formatDefault();
                    } else if (fragment instanceof InterviewArticleFragment) {
                        ((InterviewArticleFragment) fragment).formatDefault();
                    } else if (fragment instanceof AuthorArticleFragment) {
                        ((AuthorArticleFragment) fragment).formatDefault();
                    } else if (fragment instanceof NationalArticleFragment) {
                        ((NationalArticleFragment) fragment).formatDefault();
                    } else if (fragment instanceof ImportantArticleFragment) {
                        ((ImportantArticleFragment) fragment).formatDefault();
                    } else if (fragment instanceof GalleryOneArticleFragment) {
                        ((GalleryOneArticleFragment) fragment).formatDefault();
                    } else if (fragment instanceof GalleryTwoArticleFragment) {
                        ((GalleryTwoArticleFragment) fragment).formatDefault();
                    } else if (fragment instanceof SimpleWideArticleFragment) {
                        ((SimpleWideArticleFragment) fragment).formatDefault();
                    }
                    for (WebView web : contentViews) {
                        web.getSettings().setDefaultFontSize(16);
                    }
                    for (TextView text : contentDescriptions) {
                        text.setTextSize(16);
                    }
                    matchArticles.setTextSize(26);
                    formatDialog.dismiss();
                }
            }
        });
        decrement = (RelativeLayout) formatLayout.findViewById(R.id.decrement);
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement.startAnimation(alpha);
                FragmentManager fm = getSupportFragmentManager();
                List<Fragment> fragments = fm.getFragments();
                for (Fragment fragment : fragments) {
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
                    }

                    for (WebView web : contentViews) {
                        web.getSettings().setDefaultFontSize(15);
                    }
                    for (TextView text : contentDescriptions) {
                        text.setTextSize(15);
                    }
                    matchArticles.setTextSize(25);
                    formatDialog.dismiss();
                }
            }
        });

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
                GalleryOneArticleFragment galleryOneArticle = new GalleryOneArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                galleryOneArticle.setArguments(bundle);
                placeArticleFragment(galleryOneArticle);
                break;
            case Constants.GALLERY_SECOND_TEXT:
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
        }
    }

    public void placeArticleFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.articleContainer, fragment)
                .commit();
    }

    @OnClick(R.id.buttonFormat)
    public void openFormatDialog() {
        format.startAnimation(alpha);
        formatDialog.show();
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


