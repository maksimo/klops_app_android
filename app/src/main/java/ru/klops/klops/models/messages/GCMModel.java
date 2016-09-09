package ru.klops.klops.models.messages;

import android.os.Parcel;
import android.os.Parcelable;


public class GCMModel implements Parcelable {

    Integer newsID;

    public GCMModel(Integer newsID) {
        this.newsID = newsID;
    }

    protected GCMModel(Parcel in) {
    }

    public static final Creator<GCMModel> CREATOR = new Creator<GCMModel>() {
        @Override
        public GCMModel createFromParcel(Parcel in) {
            return new GCMModel(in);
        }

        @Override
        public GCMModel[] newArray(int size) {
            return new GCMModel[size];
        }
    };

    public Integer getNewsID() {
        return newsID;
    }

    public void setNewsID(Integer newsID) {
        this.newsID = newsID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
