package ru.klops.klops.models.article;

import android.os.Parcel;
import android.os.Parcelable;

public class Connected_items implements Parcelable{

    private Doc_list doc_list;

    public Connected_items(Doc_list doc_list) {
        this.doc_list = doc_list;
    }

    protected Connected_items(Parcel in) {
        doc_list = in.readParcelable(Doc_list.class.getClassLoader());
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

    public Doc_list getDoc_list() {
        return doc_list;
    }

    public void setDoc_list(Doc_list doc_list) {
        this.doc_list = doc_list;
    }

    @Override
    public String toString() {
        return "Connected_items{" +
                "doc_list=" + doc_list +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(doc_list, flags);
    }
}
