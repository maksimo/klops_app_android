package ru.klops.klops.models.search;

import android.os.Parcel;
import android.os.Parcelable;


public class News implements Parcelable{

    public Integer id;
    public String date;
    public String title;
    public String shortdecription;
    public String update_status;
    public String article_type;
    public String url;
    public Integer timestamp;
    public String author;
    public String source;

    public News() {
    }

    protected News(Parcel in) {
        id = in.readInt();
        date = in.readString();
        title = in.readString();
        shortdecription = in.readString();
        update_status = in.readString();
        article_type = in.readString();
        url = in.readString();
        author = in.readString();
        source = in.readString();
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

    public String getUpdate_status() {
        if (update_status == null){
            setUpdate_status("");
        }
        return update_status;
    }

    public void setUpdate_status(String update_status) {
        this.update_status = update_status;
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

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", shortdecription='" + shortdecription + '\'' +
                ", update_status='" + update_status + '\'' +
                ", article_type='" + article_type + '\'' +
                ", url='" + url + '\'' +
                ", timestamp=" + timestamp +
                ", author='" + author + '\'' +
                ", source='" + source + '\'' +
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
        dest.writeString(update_status);
        dest.writeString(article_type);
        dest.writeString(url);
        dest.writeString(author);
        dest.writeString(source);
    }
}
