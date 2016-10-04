package ru.klops.klops.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.klops.klops.PhotoActivity;
import ru.klops.klops.R;
import ru.klops.klops.models.article.Gallery;
import ru.klops.klops.utils.Constants;

public class GalleryPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Gallery> gallery;

    public GalleryPagerAdapter(Context context, ArrayList<Gallery> gallery) {
        super();
        this.context = context;
        this.gallery = gallery;

    }

    @Override
    public int getCount() {
        return gallery.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.gallery_item_simple, container, false);
        final ImageView photo = (ImageView) itemView.findViewById(R.id.galleryPagerSimplePhoto);
        Ion.with(context).load(gallery.get(position).getImg_url()).withBitmap().intoImageView(photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putParcelableArrayListExtra(Constants.PHOTOS, gallery);
                intent.putExtra(Constants.NUMBER, position + 1);
                context.startActivity(intent);
            }
        });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
