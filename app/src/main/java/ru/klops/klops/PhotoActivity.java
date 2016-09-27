package ru.klops.klops;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.klops.klops.R;
import ru.klops.klops.adapter.PhotoAdapter;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.custom.Circle;
import ru.klops.klops.custom.TextViewProMd;
import ru.klops.klops.custom.TextViewProRegular;
import ru.klops.klops.models.article.Gallery;
import ru.klops.klops.utils.Constants;

public class PhotoActivity extends AppCompatActivity {
    private static final String LOG = "PhotoActivity";
    Unbinder unbinder;
    @BindView(R.id.quitPhoto)
    Circle quitPhoto;
    @BindView(R.id.bigSwitcherCounter)
    TextViewProRegular bigSwitcherCounter;
    @BindView(R.id.photosPager)
    ViewPager photosPager;
    Animation alpha;
    PhotoAdapter adapter;
    ArrayList<Gallery> photos;
    int photoNumber;
    int countPager = 0;
    KlopsApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_photo);
        app = KlopsApplication.getINSTANCE();
        unbinder = ButterKnife.bind(this);
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        photoNumber = getIntent().getIntExtra(Constants.NUMBER, 0);
        photos = getIntent().getParcelableArrayListExtra(Constants.PHOTOS);
        setUpPager();
        Log.d(LOG, "onCreate");
    }

    private void setUpPager() {
        adapter = new PhotoAdapter(this, photos);
        photosPager.setAdapter(adapter);
        photosPager.setCurrentItem(photoNumber - 1);
        final int count = photos.size();
        bigSwitcherCounter.setText(String.valueOf(photoNumber) + "/" + photos.size());
        photosPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bigSwitcherCounter.setText(String.valueOf(photosPager.getCurrentItem() + 1) + "/" + String.valueOf(count));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.bigSwitcherCounter)
    public void pagerClick() {
       if (countPager != adapter.getCount() - 1) {
            photosPager.setCurrentItem(photosPager.getCurrentItem() + 1);
            countPager++;
        } else {
            countPager = 0;
            photosPager.setCurrentItem(countPager);
        }
    }

    @OnClick(R.id.quitPhoto)
    public void close() {
        quitPhoto.startAnimation(alpha);
        finish();
    }

    @Override
    protected void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
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
        app.savePhotoPosition(photosPager.getCurrentItem());
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onStop() {
        Log.d(LOG, "onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        unbinder.unbind();
        super.onDestroy();
    }

}

