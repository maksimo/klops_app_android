package ru.klops.klops.models.popular;

import android.os.Parcel;
import android.os.Parcelable;

public class Content implements Parcelable{

    public String text;

    protected Content(Parcel in) {
        text = in.readString();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
    }

    @Override
    public String toString() {
        return "Content{" +
                "text='" + text + '\'' +
                '}';
    }
}
