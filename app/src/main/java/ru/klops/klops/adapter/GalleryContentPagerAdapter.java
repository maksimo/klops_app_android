package ru.klops.klops.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.loader.StreamLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.models.article.Gallery;
import ru.klops.klops.models.article.Photos;
import ru.klops.klops.utils.Constants;

public class GalleryContentPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Gallery> adapterPhotos;
    TextView descr;

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
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.gallery_item, container, false);
        final ImageView photo = (ImageView) itemView.findViewById(R.id.galleryPagerPhoto);
        descr = (TextView) itemView.findViewById(R.id.photoDescription);
        descr.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        Ion.with(context.getApplicationContext()).load(adapterPhotos.get(position).getImg_url()).withBitmap().intoImageView(photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putExtra(Constants.PHOTO, adapterPhotos.get(position).getImg_url());
                intent.putExtra(Constants.TEXT, adapterPhotos.get(position).getDescription());
                intent.putExtra(Constants.NUMBER, String.valueOf(position + 1) + "/" + String.valueOf(adapterPhotos.size()));
                context.startActivity(intent);
            }
        });
        if (!adapterPhotos.get(position).getDescription().equals("")) {
            descr.setText(adapterPhotos.get(position).getDescription());
        }else {
            descr.setVisibility(View.INVISIBLE);
        }
        container.addView(itemView);
        return itemView;
    }

    public void incrementDesc(){
        descr.setTextSize(18);
    }

    public void decrDesc(){
        descr.setTextSize(16);
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
