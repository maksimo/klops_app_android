package ru.klops.klops.models.article;

import android.os.Parcel;
import android.os.Parcelable;

public class Associate implements Parcelable{
    public String title;
    public Integer url;

    public Associate() {
    }

    public Associate(String title, Integer url) {
        this.title = title;
        this.url = url;
    }

    protected Associate(Parcel in) {
        title = in.readString();
        url = in.readInt();
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

    public Integer getUrl() {
        return url;
    }

    public void setUrl(Integer url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Associate{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(url);
    }
}
