package ru.klops.klops.models.popular;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class News implements Parcelable{
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
    private Integer timestamp;
    private String author;
    private String source;
    private Integer promoted;
    private Integer views_count;

    public News() {
    }

    public News(Integer id, String date, String title, String shortdecription, String image, String update_status, List<String> photos, String article_type, String url, String og_image, Integer timestamp, String author, String source, Integer promoted, Integer views_count) {
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
        this.source = source;
        this.promoted = promoted;
        this.views_count = views_count;
    }

    protected News(Parcel in) {
        id = in.readInt();
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
        source = in.readString();
        promoted = in.readInt();
        views_count = in.readInt();
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
        if (date == null){
            setDate("");
        }
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        if (title == null){
            setTitle("");
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortdecription() {
        if (shortdecription == null){
            setShortdecription("");
        }
        return shortdecription;
    }

    public void setShortdecription(String shortdecription) {
        this.shortdecription = shortdecription;
    }

    public String getImage() {
        if (image == null){
            setImage("");
        }
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUpdate_status() {
        if (update_status == null){
            setUpdate_status("");
        }
        return update_status;
    }

    public void setUpdate_status(String update_status) {
        this.update_status = update_status;
    }

    public List<String> getPhotos() {
        if (photos== null){
            setPhotos(new ArrayList<String>());
        }
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getArticle_type() {
        if (article_type == null){
            setArticle_type("");
        }
        return article_type;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
    }

    public String getUrl() {
        if (url == null){
            setUrl("");
        }
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

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthor() {
        if (author == null){
            setAuthor("");
        }
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        if (source == null){
            setSource("");
        }
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getPromoted() {
        if (promoted == null){
            setPromoted(0);
        }
        return promoted;
    }

    public void setPromoted(Integer promoted) {
        this.promoted = promoted;
    }

    public Integer getViews_count() {
        if (views_count == null){
            setViews_count(0);
        }
        return views_count;
    }

    public void setViews_count(Integer views_count) {
        this.views_count = views_count;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", shortdecription='" + shortdecription + '\'' +
                ", image='" + image + '\'' +
                ", update_status='" + update_status + '\'' +
                ", photos=" + photos +
                ", article_type='" + article_type + '\'' +
                ", url='" + url + '\'' +
                ", og_image='" + og_image + '\'' +
                ", timestamp=" + timestamp +
                ", author='" + author + '\'' +
                ", source='" + source + '\'' +
                ", promoted=" + promoted +
                ", views_count=" + views_count +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
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
        dest.writeString(source);
        dest.writeInt(promoted);
        dest.writeInt(views_count);
    }
}
