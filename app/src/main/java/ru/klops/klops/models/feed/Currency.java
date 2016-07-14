package ru.klops.klops.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

public class Currency implements Parcelable {
    public String usd;
    public String eur;
    public String pln;

    public Currency() {
    }

    public Currency(String usd, String eur, String pln) {
        this.usd = usd;
        this.eur = eur;
        this.pln = pln;
    }

    protected Currency(Parcel in) {
        usd = in.readString();
        eur = in.readString();
        pln = in.readString();
    }

    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel in) {
            return new Currency(in);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getEur() {
        return eur;
    }

    public void setEur(String eur) {
        this.eur = eur;
    }

    public String getPln() {
        return pln;
    }

    public void setPln(String pln) {
        this.pln = pln;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "usd='" + usd + '\'' +
                ", eur='" + eur + '\'' +
                ", pln='" + pln + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(usd);
        dest.writeString(eur);
        dest.writeString(pln);
    }
}
