package ru.klops.klops.models.article;

import android.os.Parcel;
import android.os.Parcelable;

public class Gallery implements Parcelable{
    public String img_url;
    public String description;

    public Gallery() {
    }

    public Gallery(String img_url, String description) {
        this.img_url = img_url;
        this.description = description;
    }

    protected Gallery(Parcel in) {
        img_url = in.readString();
        description = in.readString();
    }

    public static final Creator<Gallery> CREATOR = new Creator<Gallery>() {
        @Override
        public Gallery createFromParcel(Parcel in) {
            return new Gallery(in);
        }

        @Override
        public Gallery[] newArray(int size) {
            return new Gallery[size];
        }
    };

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "img_url='" + img_url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img_url);
        dest.writeString(description);
    }
}
