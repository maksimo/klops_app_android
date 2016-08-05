package ru.klops.klops.models.article;

import android.os.Parcel;
import android.os.Parcelable;

public class Associate implements Parcelable{
    public String title;
    public String url;
    public Integer id;

    public Associate() {
    }

    public Associate(String title, String url, Integer id) {
        this.title = title;
        this.url = url;
        this.id = id;
    }

    protected Associate(Parcel in) {
        title = in.readString();
        url = in.readString();
        id =in.readInt();
    }

    public static final Creator<Associate> CREATOR = new Creator<Associate>() {
        @Override
        public Associate createFromParcel(Parcel in) {
            return new Associate(in);
        }

        @Override
        public Associate[] newArray(int size) {
            return new Associate[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Associate{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeInt(id);
    }
}

