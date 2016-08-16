package ru.klops.klops.models.popular;

import android.os.Parcel;
import android.os.Parcelable;

public class Og_image implements Parcelable{

    public String url;
    public String title;

    public Og_image(String url, String title) {
        this.url = url;
        this.title = title;
    }

    protected Og_image(Parcel in) {
        url = in.readString();
        title = in.readString();
    }

    public static final Creator<Og_image> CREATOR = new Creator<Og_image>() {
        @Override
        public Og_image createFromParcel(Parcel in) {
            return new Og_image(in);
        }

        @Override
        public Og_image[] newArray(int size) {
            return new Og_image[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Og_image{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
    }
}
