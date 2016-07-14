package ru.klops.klops.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class News implements Parcelable {
    private Integer id;
    private String date;
    private String title;
    private String shortdecription;
    private String image;
    private String update_status;
    private List<String> photos;
    private String article_type;
    private String url;
    private String og_image;
    private Object timestamp;
    private String author;

    public News() {
    }

    public News(Integer id, String date, String title, String shortdecription, String image, String update_status, List<String> photos, String article_type, String url, String og_image, Object timestamp, String author) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.shortdecription = shortdecription;
        this.image = image;
        this.update_status = update_status;
        this.photos = photos;
        this.article_type = article_type;
        this.url = url;
        this.og_image = og_image;
        this.timestamp = timestamp;
        this.author = author;
    }

    public News(Parcel in) {
        date = in.readString();
        title = in.readString();
        shortdecription = in.readString();
        image = in.readString();
        update_status = in.readString();
        photos = in.createStringArrayList();
        article_type = in.readString();
        url = in.readString();
        og_image = in.readString();
        author = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortdecription() {
        return shortdecription;
    }

    public void setShortdecription(String shortdecription) {
        this.shortdecription = shortdecription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(String update_status) {
        this.update_status = update_status;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getArticle_type() {
        return article_type;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOg_image() {
        return og_image;
    }

    public void setOg_image(String og_image) {
        this.og_image = og_image;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(title);
        dest.writeString(shortdecription);
        dest.writeString(image);
        dest.writeString(update_status);
        dest.writeStringList(photos);
        dest.writeString(article_type);
        dest.writeString(url);
        dest.writeString(og_image);
        dest.writeString(author);
    }
}