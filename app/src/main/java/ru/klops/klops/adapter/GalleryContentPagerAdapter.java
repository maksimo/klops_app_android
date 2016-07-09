package ru.klops.klops.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.models.article.Photos;

public class GalleryContentPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Photos> content;


    public GalleryContentPagerAdapter(Context context, ArrayList<Photos> content) {
        super();
        this.context = context;
        this.content = content;

    }


    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.gallery_item, container, false);
        final ImageView photo = (ImageView) itemView.findViewById(R.id.galleryPagerPhoto);
        final TextView descr = (TextView) itemView.findViewById(R.id.photoDescription);
        if (content.get(position).getImg_url().equals("")) {
            photo.setVisibility(View.GONE);
            descr.setVisibility(View.GONE);
        } else {
            Ion.with(context).load("https://klops.ru".concat(content.get(position).getImg_url())).withBitmap().intoImageView(photo);
            photo.setVisibility(View.VISIBLE);
        }
        if (content.get(position).getDescription().equals("")) {
            descr.setVisibility(View.GONE);
        } else {
            descr.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
            descr.setText(content.get(position).getDescription());
            descr.setVisibility(View.VISIBLE);
        }
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
