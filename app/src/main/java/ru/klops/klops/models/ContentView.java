package ru.klops.klops.models;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContentView {
    RelativeLayout contentLayer;
    WebView contentView;
    ProgressBar loader;
    ImageView image;
    TextView description;
    TextView moreTitle;
    TextView moreUrl;

    public ContentView(RelativeLayout contentLayer, WebView contentView, ProgressBar loader, ImageView image, TextView description, TextView moreTitle, TextView moreUrl) {
        this.contentLayer = contentLayer;
        this.contentView = contentView;
        this.loader = loader;
        this.image = image;
        this.description = description;
        this.moreTitle = moreTitle;
        this.moreUrl = moreUrl;
    }

    public RelativeLayout getContentLayer() {
        return contentLayer;
    }

    public void setContentLayer(RelativeLayout contentLayer) {
        this.contentLayer = contentLayer;
    }

    public WebView getContentView() {
        return contentView;
    }

    public void setContentView(WebView contentView) {
        this.contentView = contentView;
    }

    public ProgressBar getLoader() {
        return loader;
    }

    public void setLoader(ProgressBar loader) {
        this.loader = loader;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public TextView getMoreTitle() {
        return moreTitle;
    }

    public void setMoreTitle(TextView moreTitle) {
        this.moreTitle = moreTitle;
    }

    public TextView getMoreUrl() {
        return moreUrl;
    }

    public void setMoreUrl(TextView moreUrl) {
        this.moreUrl = moreUrl;
    }
}
