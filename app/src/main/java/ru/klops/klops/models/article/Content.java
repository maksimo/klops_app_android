package ru.klops.klops.models.article;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Content implements Parcelable{
    public String text;
    public List<Photos> photos;

    protected Content(Parcel in) {
        text = in.readString();
        photos = in.createTypedArrayList(Photos.CREATOR);
    }

    public Content(String text, List<Photos> photos) {
        this.text = text;
        this.photos = photos;
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photos> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Content{" +
                "text='" + text + '\'' +
                ", photos=" + photos +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeTypedList(photos);
    }
}
