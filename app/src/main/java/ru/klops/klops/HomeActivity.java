package ru.klops.klops;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.util.List;

import ru.klops.klops.api.KlopsApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.custom.CircleImageView;
import ru.klops.klops.fragments.BaseFragment;
import ru.klops.klops.fragments.NewDataNewsFragment;
import ru.klops.klops.fragments.PopularDataNewsFragment;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HomeActivity extends AppCompatActivity {
    final String LOG = "HomeActivity";
    KlopsApplication app;
    private Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_home);
        Log.d(LOG, "onCreate");
        app = KlopsApplication.getINSTANCE();
        mTracker = app.getDefaultTracker();
        hideKeyboard();
        placeBaseFragment();
    }


    public void placeBaseFragment() {
        Log.d(LOG, "placeBaseFragment");
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.home_container, new BaseFragment()).commit();
    }


    public void replaceFragmentFadeIn(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.home_container, fragment).setCustomAnimations(R.anim.fade_in, R.anim.fade_out).commit();
    }

    public void scrollList() {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof NewDataNewsFragment){
                ((NewDataNewsFragment)fragment).scrollNewToTop();
            }else if (fragment instanceof PopularDataNewsFragment){
                ((PopularDataNewsFragment)fragment).scrollPopularToTop();
            }
        }

    }

    public void loadArticle(Integer id, final String type, final String contentType){
        KlopsApi.ArticleApi api = RetrofitServiceGenerator.createService(KlopsApi.ArticleApi.class);
        Observable<Article> call = api.getItemById(id, contentType);
        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d("SearchAdapter", "Загружаю статью...");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("SearchAdapter", "Ошибка при открытии...");
                    }

                    @Override
                    public void onNext(Article article) {
                        Intent articleIntent = new Intent(HomeActivity.this, ArticleActivity.class);
                        articleIntent.putExtra(Constants.ITEM, article.getItem());
                        articleIntent.putExtra(Constants.TYPE, type);
                        articleIntent.putExtra(Constants.CONTENT_TYPE, contentType);
                        startActivity(articleIntent);
                    }
                });
    }

    public void loadPhoto(String input, ImageView output, final ProgressBar loader) {
        Ion.with(this).load(input).progressHandler(new ProgressCallback() {
            @Override
            public void onProgress(long downloaded, long total) {
                loader.setVisibility(View.VISIBLE);
            }
        }).intoImageView(output).setCallback(new FutureCallback<android.widget.ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                loader.setVisibility(View.GONE);
            }
        });
    }

    public void loadPhotoCircle(String input, ImageView output, final ProgressBar loader) {
        Ion.with(this).load(input).progressHandler(new ProgressCallback() {
            @Override
            public void onProgress(long downloaded, long total) {
                loader.setVisibility(View.VISIBLE);
            }
        }).withBitmap().transform(new CircleImageView()).intoImageView(output).setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                loader.setVisibility(View.GONE);
            }
        });
    }


    public void loadFoundArticle(Integer id, final String article_type) {
        KlopsApi.ArticleApi api = RetrofitServiceGenerator.createService(KlopsApi.ArticleApi.class);
        final Observable<Article> call = api.getItemById(id, Constants.ARTICLE_TYPE);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d("SearchAdapter", "Loading search article complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("SearchAdapter", "Loading failed");
                    }

                    @Override
                    public void onNext(Article article) {
                        Intent articleIntent = new Intent(HomeActivity.this, ArticleActivity.class);
                        articleIntent.putExtra(Constants.ITEM, article.getItem());
                        articleIntent.putExtra(Constants.TYPE, article_type);
                        articleIntent.putExtra(Constants.CONTENT_TYPE, Constants.ARTICLE_TYPE);
                        call.unsubscribeOn(Schedulers.io());
                        startActivity(articleIntent);
                    }
                });
    }

    @Override
    protected void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
        FlurryAgent.onStartSession(this, Constants.FLURRY_API_KEY);
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Home Activity")
                .setAction("Start Home Activity")
                .build());
    }

    @Override
    protected void onPause() {
        Log.d(LOG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(LOG, "onResume");
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG, "onBackPressed");
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_container, new BaseFragment()).setCustomAnimations(R.anim.fade_out, R.anim.fade_in).commit();
        }

    }

    public void hideKeyboard(){
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        );
    }

    @Override
    protected void onStop() {
        Log.d(LOG, "onStop");
        super.onStop();
        FlurryAgent.onEndSession(this);
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
    }
}


