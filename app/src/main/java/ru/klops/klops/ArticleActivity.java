package ru.klops.klops;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
    @BindView(R.id.ViewsContent)
    LinearLayout viewsContent;
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
    ArrayList<Connected_items> connectedItemses;
    ArrayList<String> smallGallery;
    GalleryPagerAdapter littleAdapter;
    ShareDialog shareFacebookDialog;
    VKShareDialogBuilder vkShareDialog;
    ArrayList<Content> contents;
    ArrayList<Content> copy;
    ArrayList<WebView> viewsWeb;
    ArrayList<String> urls;
    ArrayList<String> description;
    Animation alpha;
    Bitmap bmp;
    String text;
    GalleryContentPagerAdapter gAdapter;
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
        setUPPager();
        setUPContent();
        setUpMatchNews();
    }

    private void setUpMatchNews() {
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
    }

    @OnClick(R.id.connectedNewsOneLayer)
    public void openFirstMatchNews(){
        connectedNewsOneLayer.startAnimation(alpha);
        loadArticle(connectedItemses.get(0).getDoc_list().getId());
    }

    @OnClick(R.id.connectedNewsTwoLayer)
    public void openSecondMatchNews(){
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
        contents = new ArrayList<>();
        contents.addAll(item.getContent());
        copy = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i).getText() == null || contents.get(i).getText().length() == 0) {
                String copyString = "";
                copy.add(new Content(copyString, contents.get(i).getPhotos()));

            } else if (contents.get(i).getPhotos() == null || contents.get(i).getPhotos().size() == 0) {
                ArrayList<Photos> copyList = new ArrayList<>();
                copyList.add(new Photos("", ""));
                copy.add(new Content(contents.get(i).getText(), copyList));
            }
        }

        viewsWeb = new ArrayList<>();
        urls = new ArrayList<>();
        description = new ArrayList<>();
        WebView webView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int n = 0; n < copy.size(); n++) {
            LinearLayout viewsLayout = new LinearLayout(this);
            viewsLayout.setOrientation(LinearLayout.HORIZONTAL);
            if (!copy.get(n).getText().equals("")) {
                webView = new WebView(this);
                webView.getSettings().setDefaultFontSize(16);
                webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                webView.loadData(copy.get(n).getText(), "text/html; charset=utf-8", "UTF-8");
                webView.setId(n + 1);
                webView.setLayoutParams(params);
                viewsLayout.addView(webView);
                viewsWeb.add(webView);
            } else if (!copy.get(n).getPhotos().get(0).getImg_url().equals("")) {
                for (int m = 0; m < copy.get(n).getPhotos().size(); m++) {
                    urls.add(copy.get(n).getPhotos().get(m).getImg_url());
                    description.add(copy.get(n).getPhotos().get(m).getDescription());
                }
                gAdapter = new GalleryContentPagerAdapter(this, urls, description);
                pager.setAdapter(gAdapter);
                pager.setCurrentItem(0);
                pager.setId(n + 1);
                pager.setLayoutParams(params);
                viewsLayout.addView(pager);
            }
            viewsContent.addView(viewsLayout);
        }

    }

    @OnClick(R.id.littlePhotoSwitcherTwo)
    public void pagerClick() {
        countPager = pager.getCurrentItem();
        countPager++;
        pager.setCurrentItem(countPager);
    }

    private void setUPPager() {
        matchArticles.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/akzidenzgroteskpro-boldex.ttf"));
        smallGallery = new ArrayList<>();
        if (!item.getPhotos().isEmpty() && item.getPhotos() != null) {
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
                    for (WebView webs : viewsWeb) {
                        webs.getSettings().setDefaultFontSize(17);
                    }
                    matchArticles.setTextSize(33);


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
                    for (WebView webs : viewsWeb) {
                        webs.getSettings().setDefaultFontSize(16);
                    }
                    matchArticles.setTextSize(32);

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
                    for (WebView webs : viewsWeb) {
                        webs.getSettings().setDefaultFontSize(15);
                    }
                    matchArticles.setTextSize(31);

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


