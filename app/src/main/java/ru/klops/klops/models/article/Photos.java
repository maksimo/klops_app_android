package ru.klops.klops.models.article;

import android.os.Parcel;
import android.os.Parcelable;

public class Photos implements Parcelable{
    public String img_url;
    public String description;

    protected Photos(Parcel in) {
        img_url = in.readString();
        description = in.readString();
    }

    public Photos(String img_url, String description) {
        this.img_url = img_url;
        this.description = description;
    }

    public static final Creator<Photos> CREATOR = new Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel in) {
            return new Photos(in);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
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
        return "Photos{" +
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
