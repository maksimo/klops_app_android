package ru.klops.klops.adapter;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.klops.klops.R;
import ru.klops.klops.custom.CircleImageView;
import ru.klops.klops.custom.TextViewProMd;
import ru.klops.klops.custom.TextViewProRegular;
import ru.klops.klops.utils.Constants;

public class PhotoActivity extends AppCompatActivity {
    final String LOG = "PhotoActivity";
    @BindView(R.id.bigPhoto)
    ImageView bigPhoto;
    @BindView(R.id.bigDescription)
    TextViewProMd textView;
    Unbinder unbinder;
    @BindView(R.id.quitPhoto)
    CircleImageView quitPhoto;
    @BindView(R.id.bigSwitcherCounter)
    TextViewProRegular bigSwitcherCounter;
    @BindView(R.id.bigProgress)
    ProgressBar bar;
    Animation alpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_photo);
        unbinder = ButterKnife.bind(this);
        alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        String photo = getIntent().getStringExtra(Constants.PHOTO);
        String text = getIntent().getStringExtra(Constants.TEXT);
        String counter = getIntent().getStringExtra(Constants.NUMBER);
        Ion.with(this).load(photo).progressHandler(new ProgressCallback() {
            @Override
            public void onProgress(long downloaded, long total) {
                bar.setVisibility(View.VISIBLE);
            }
        }).intoImageView(bigPhoto).setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                bar.setVisibility(View.GONE);
            }
        });
        textView.setText(text);
        bigSwitcherCounter.setText(counter);
        Log.d(LOG, "onCreate");
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

