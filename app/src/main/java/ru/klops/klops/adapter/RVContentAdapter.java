package ru.klops.klops.adapter;

import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.models.article.Content;
import ru.klops.klops.models.article.Photos;

public class RVContentAdapter extends RecyclerView.Adapter<RVContentAdapter.ViewHolder> {
    ArticleActivity context;
    ArrayList<Content> contents;
    GalleryContentPagerAdapter adapter;
    ArrayList<Photos> photos;
    ArrayList<String> urls;
    String size;
    int counter = 0;
    Animation alpha;

    public RVContentAdapter(ArticleActivity context, ArrayList<Content> contents, ArrayList<Photos> photos, ArrayList<String> urls) {
        this.context = context;
        this.contents = contents;
        this.photos = photos;
        this.urls = urls;
        alpha = AnimationUtils.loadAnimation(context, R.anim.alpha);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.contentText)
        WebView contentText;
        @BindView(R.id.galleryAdapterLayer)
        RelativeLayout galleryAdapterLayer;
        @BindView(R.id.contentPhotos)
        ViewPager contentPhotos;
        @BindView(R.id.contentPhotoSwitcher)
        RelativeLayout contentPhotoSwitcher;
        @BindView(R.id.contentPhotoSwitchCounter)
        TextView contentPhotoSwitchCounter;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_card, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (contents.get(position).getText() != null || contents.get(position).getText().length() != 0) {
            holder.contentText.getSettings().setJavaScriptEnabled(true);
            holder.contentText.getSettings().setDefaultFontSize(16);
            holder.contentText.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            holder.contentText.loadData(contents.get(position).getText(), "text/html; charset=utf-8", "UTF-8");
        } else {
            holder.contentText.setVisibility(View.GONE);
        }
        if (contents.get(position).getPhotos()== null || contents.get(position).getPhotos().size() != 0) {
            holder.galleryAdapterLayer.setVisibility(View.GONE);
        } else {
//            adapter = new GalleryContentPagerAdapter(context, photos);
//            holder.contentPhotos.setAdapter(adapter);
        }
//        if (holder.galleryAdapterLayer.getVisibility() != View.GONE) {
//            size = String.valueOf(contents.get(position).getPhotos().size());
//            holder.contentPhotoSwitchCounter.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/akzidenzgroteskpro-boldex.ttf"));
//            holder.contentPhotoSwitchCounter.setText("1" + "/" + size);
//            holder.contentPhotoSwitcher.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (holder.contentPhotos.getCurrentItem() == 0) {
//                        counter++;
//                        holder.contentPhotos.setCurrentItem(counter);
//
//                    }
//                }
//            });
//        } else {
//            holder.galleryAdapterLayer.setVisibility(View.GONE);
//
//        }
//        holder.contentPhotos.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                holder.contentPhotoSwitchCounter.setText(String.valueOf(holder.contentPhotos.getCurrentItem()+1) + "/" + String.valueOf(contents.get(position).getPhotos().size()));
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
