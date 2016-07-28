package ru.klops.klops.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.models.article.Gallery;
import ru.klops.klops.models.article.Photos;

public class GalleryContentPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Gallery> adapterPhotos;

    public GalleryContentPagerAdapter(Context context, ArrayList<Gallery> adapterPhotos) {
        super();
        this.context = context;
        this.adapterPhotos = adapterPhotos;
    }


    @Override
    public int getCount() {
        return adapterPhotos.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.gallery_item, container, false);
        final ImageView photo = (ImageView) itemView.findViewById(R.id.galleryPagerPhoto);
        final TextView descr = (TextView) itemView.findViewById(R.id.photoDescription);
        descr.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        Ion.with(context.getApplicationContext()).load(adapterPhotos.get(position).getImg_url()).withBitmap().intoImageView(photo);
        if (!adapterPhotos.get(position).getDescription().equals("")) {
            descr.setText(adapterPhotos.get(position).getDescription());
        }else {
            photo.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250));
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
