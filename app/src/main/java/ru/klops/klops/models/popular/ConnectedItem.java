package ru.klops.klops.models.popular;

import android.os.Parcel;
import android.os.Parcelable;

public class ConnectedItem implements Parcelable{

    public ConnectedItem() {
    }

    protected ConnectedItem(Parcel in) {
    }

    public static final Creator<ConnectedItem> CREATOR = new Creator<ConnectedItem>() {
        @Override
        public ConnectedItem createFromParcel(Parcel in) {
            return new ConnectedItem(in);
        }

        @Override
        public ConnectedItem[] newArray(int size) {
            return new ConnectedItem[size];
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
