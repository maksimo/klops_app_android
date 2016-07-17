package ru.klops.klops.models.popular;

import android.os.Parcel;
import android.os.Parcelable;

public class Connected_items implements Parcelable{

    public Connected_items() {
    }

    protected Connected_items(Parcel in) {
    }

    public static final Creator<Connected_items> CREATOR = new Creator<Connected_items>() {
        @Override
        public Connected_items createFromParcel(Parcel in) {
            return new Connected_items(in);
        }

        @Override
        public Connected_items[] newArray(int size) {
            return new Connected_items[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
