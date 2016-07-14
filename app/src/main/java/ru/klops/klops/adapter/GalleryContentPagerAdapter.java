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

import ru.klops.klops.R;

public class GalleryContentPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<String> urls;
    ArrayList<String> description;


    public GalleryContentPagerAdapter(Context context, ArrayList<String> urls, ArrayList<String> description) {
        super();
        this.context = context;
        this.urls = urls;
        this.description = description;

    }


    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.gallery_item, container, false);
        final ImageView photo = (ImageView) itemView.findViewById(R.id.galleryPagerPhoto);
        final TextView descr = (TextView) itemView.findViewById(R.id.photoDescription);
        if (urls.get(position).equals("")) {
            photo.setVisibility(View.GONE);
            descr.setVisibility(View.GONE);
        } else {
            Ion.with(context).load(urls.get(position)).withBitmap().intoImageView(photo);
            photo.setVisibility(View.VISIBLE);
        }
        if (description.get(position).equals("")) {
            descr.setText("Отсутствует описание к данному фото");
        } else {
            descr.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
            descr.setText(description.get(position));
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
