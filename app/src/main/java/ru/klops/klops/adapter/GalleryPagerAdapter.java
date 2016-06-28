package ru.klops.klops.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ru.klops.klops.R;

public class GalleryPagerAdapter extends PagerAdapter{
    Context context;
    LayoutInflater inflater;
    List<String> photos;
    ProgressBar progressBar;

    public GalleryPagerAdapter(Context context, List<String> photos) {
        this.context = context;
        this.photos = photos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View item = inflater.inflate(R.layout.gallery_item, container, false);
        ImageView photo = (ImageView) item.findViewById(R.id.galleryPagerPhoto);
        Picasso.with(context).load(photos.get(position)).into(photo, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
               progressBar.setVisibility(View.VISIBLE);
            }
        });
        container.addView(item);
        return item;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
