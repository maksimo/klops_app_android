package ru.klops.klops.models.article;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Item implements Parcelable {
    public Integer id;
    public String date;
    public String title;
    public String shortdecription;
    public String image;
    public String update_status;
    public List<String> photos;
    public String article_type;
    public String url;
    public String og_image;
    public List<Content> content;
    public Integer timestamp;
    public String author;
    public String source;
    public List<Connected_items> connected_items;
    public Integer promoted;

    public Item() {
    }


    public Item(Integer id, String date, String title, String shortdecription, String image, String update_status, List<String> photos, String article_type, String url, String og_image, List<Content> content, Integer timestamp, String author, String source,List<Connected_items> connected_items, Integer promoted) {
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
        this.content = content;
        this.timestamp = timestamp;
        this.author = author;
        this.source = source;
        this.connected_items = connected_items;
        this.promoted = promoted;
    }

    protected Item(Parcel in) {
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
        content = in.createTypedArrayList(Content.CREATOR);
        author = in.readString();
        source = in.readString();
        connected_items = in.createTypedArrayList(Connected_items.CREATOR);
        promoted = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
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

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Connected_items> getConnected_items() {
        return connected_items;
    }

    public void setConnected_items(List<Connected_items> connected_items) {
        this.connected_items = connected_items;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getPromoted() {
        return promoted;
    }

    public void setPromoted(Integer promoted) {
        this.promoted = promoted;
    }

    @Override
    public String toString() {
        return "Item{" +
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
                ", content=" + content +
                ", timestamp=" + timestamp +
                ", author='" + author + '\'' +
                ", source='" + source + '\'' +
                ", connected_items=" + connected_items +
                ", promoted=" + promoted +
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
        dest.writeTypedList(content);
        dest.writeString(author);
        dest.writeTypedList(connected_items);
        dest.writeString(source);
        dest.writeInt(promoted);
    }
}
