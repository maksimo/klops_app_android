package ru.klops.klops.models.search;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;




public class Search implements Parcelable{

    private List<News> news = new ArrayList<>();

    public Search() {
    }

    public Search(List<News> news) {
        this.news = news;
    }

    protected Search(Parcel in) {
        news = in.createTypedArrayList(News.CREATOR);
    }

    public static final Creator<Search> CREATOR = new Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "Search{" +
                "news=" + news +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(news);
    }
}
