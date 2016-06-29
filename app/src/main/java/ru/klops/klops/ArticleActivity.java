package ru.klops.klops;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.fragments.AdvertiseArticleFragment;
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
import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.feed.News;
import ru.klops.klops.utils.Constants;

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
    News received;
    String articleType;
    AlertDialog.Builder shareBuilder;
    AlertDialog shareDialog;
    AlertDialog.Builder formatBuilder;
    AlertDialog formatDialog;
    View shareLayout;
    RelativeLayout facebook;
    RelativeLayout vkontakte;
    RelativeLayout twitter;
    RelativeLayout instagram;
    View formatLayout;
    RelativeLayout increment;
    RelativeLayout middle;
    RelativeLayout decrement;
    Item item;
    Animation alpha;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_layers_activity);
        shareLayout = LayoutInflater.from(this).inflate(R.layout.share_dialog, null);
        formatLayout = LayoutInflater.from(this).inflate(R.layout.format_dialog, null);
        unbinder = ButterKnife.bind(this);
        app = KlopsApplication.getINSTANCE();
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        setSupportActionBar(toolbar);
//        initRequestData();
        setUpShare();
        setUpFormat();
        drawFragment();
        Log.d(LOG, "onCreate");
    }

    private void initRequestData() {
        Log.d(LOG, "initRequestData");
        received = new News();
        if (getIntent().getParcelableExtra(Constants.ARTICLE_FEED) != null) {
            received = getIntent().getParcelableExtra(Constants.ARTICLE_FEED);
            app.setRequestedArticle(received);
        } else if (getIntent().getParcelableExtra(Constants.ARTICLE_SEARCH) != null) {
            received = getIntent().getParcelableExtra(Constants.ARTICLE_SEARCH);
            app.setRequestedArticle(received);
        } else {
            Toast.makeText(ArticleActivity.this, "Данная статья не существует...", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpShare() {
        Log.d(LOG, "setUpShare");
        shareBuilder = new AlertDialog.Builder(this);
        shareBuilder.setView(shareLayout);
        shareBuilder.setCancelable(true);
        shareDialog = shareBuilder.create();
        facebook = (RelativeLayout) shareLayout.findViewById(R.id.shareViaFB);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook.startAnimation(alpha);
                shareDialog.dismiss();
            }
        });
        vkontakte = (RelativeLayout) shareLayout.findViewById(R.id.shareViaVK);
        vkontakte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vkontakte.startAnimation(alpha);
                shareDialog.dismiss();
            }
        });

        twitter = (RelativeLayout) shareLayout.findViewById(R.id.shareViaTW);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitter.startAnimation(alpha);
                shareDialog.dismiss();
            }
        });
        instagram = (RelativeLayout) shareLayout.findViewById(R.id.shareViaIN);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagram.startAnimation(alpha);
                shareDialog.dismiss();
            }
        });

    }

    private void setUpFormat() {
        Log.d(LOG, "setUpFormat");
        formatBuilder = new AlertDialog.Builder(this);
        formatBuilder.setView(formatLayout);
        formatBuilder.setCancelable(true);
        formatDialog = formatBuilder.create();
        increment = (RelativeLayout) formatLayout.findViewById(R.id.increment);
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
                    } else if (fragment instanceof AdvertiseArticleFragment) {
                        ((AdvertiseArticleFragment) fragment).formatIncrement();
                    } else if (fragment instanceof SimpleWideArticleFragment) {
                        ((SimpleWideArticleFragment) fragment).formatIncrement();
                    }
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
                    } else if (fragment instanceof AdvertiseArticleFragment) {
                        ((AdvertiseArticleFragment) fragment).formatDefault();
                    } else if (fragment instanceof SimpleWideArticleFragment) {
                        ((SimpleWideArticleFragment) fragment).formatIncrement();
                    }
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
                    } else if (fragment instanceof AdvertiseArticleFragment) {
                        ((AdvertiseArticleFragment) fragment).formatDecrement();
                    } else if (fragment instanceof SimpleWideArticleFragment) {
                        ((SimpleWideArticleFragment) fragment).formatDecrement();
                    }
                    formatDialog.dismiss();
                }
            }
        });

    }

    private void drawFragment() {
        Log.d(LOG, "drawFragment");
        item = getIntent().getParcelableExtra(Constants.ITEM);
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
            case Constants.ADS_TEXT:
                AdvertiseArticleFragment advertiseArticle = new AdvertiseArticleFragment();
                bundle.putParcelable(Constants.ARTICLE, item);
                advertiseArticle.setArguments(bundle);
                placeArticleFragment(advertiseArticle);
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
                .addToBackStack(null)
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


