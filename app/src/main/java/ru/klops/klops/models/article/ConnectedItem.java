package ru.klops.klops.models.article;

import android.os.Parcel;
import android.os.Parcelable;

public class ConnectedItem implements Parcelable {

    private DocList docList;

    public ConnectedItem(DocList docList) {
        this.docList = docList;
    }

    protected ConnectedItem(Parcel in) {
        docList = in.readParcelable(DocList.class.getClassLoader());
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

    public DocList getDocList() {
        return docList;
    }

    public void setDocList(DocList docList) {
        this.docList = docList;
    }

    @Override
    public String toString() {
        return "ConnectedItem{" +
                "docList=" + docList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(docList, flags);
    }
}
