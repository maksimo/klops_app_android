package ru.klops.klops.models.article;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Objects;

public class Doc_list implements Parcelable {
    private Integer id;
    private String date;
    private String title;
    private String shortdecription;
    private String image;
    private String update_status;
    private List<String> photos;
    private String article_type;
    private String url;
    private Og_image og_image;
    private Integer timestamp;
    private String author;
    private String source;
    private Integer promoted;
    private String authors_regalia;
    private String author_photo;
    private String content_type;

    public Doc_list(Integer id, String date, String title, String shortdecription, String image, String update_status, List<String> photos, String article_type, String url, Og_image og_image, Integer timestamp, String author, String source, Integer promoted, String authors_regalia, String author_photo, String content_type) {
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
        this.authors_regalia = authors_regalia;
        this.author_photo = author_photo;
        this.content_type = content_type;
    }

    protected Doc_list(Parcel in) {
        id = in.readInt();
        date = in.readString();
        title = in.readString();
        shortdecription = in.readString();
        image = in.readString();
        update_status = in.readString();
        photos = in.createStringArrayList();
        article_type = in.readString();
        url = in.readString();
        og_image = in.readParcelable(Og_image.class.getClassLoader());
        author = in.readString();
        source = in.readString();
        promoted = in.readInt();
        authors_regalia = in.readString();
        author_photo = in.readString();
        content_type = in.readString();
    }

    public static final Creator<Doc_list> CREATOR = new Creator<Doc_list>() {
        @Override
        public Doc_list createFromParcel(Parcel in) {
            return new Doc_list(in);
        }

        @Override
        public Doc_list[] newArray(int size) {
            return new Doc_list[size];
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

    public Og_image getOg_image() {
        return og_image;
    }

    public void setOg_image(Og_image og_image) {
        this.og_image = og_image;
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

    public String getContent_type() {
        if (content_type == null) {
            setContent_type("article");
        }
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getSource() {
        if (source == null) {
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

    public String getAuthors_regalia() {
        if (authors_regalia == null){
            setAuthors_regalia("");
        }
        return authors_regalia;
    }

    public void setAuthors_regalia(String authors_regalia) {
        this.authors_regalia = authors_regalia;
    }

    public String getAuthor_photo() {
        if (author_photo == null){
            setAuthor_photo("");
        }
        return author_photo;
    }

    public void setAuthor_photo(String author_photo) {
        this.author_photo = author_photo;
    }

    @Override
    public String toString() {
        return "Doc_list{" +
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
        dest.writeParcelable(og_image, flags);
        dest.writeString(author);
        dest.writeString(content_type);
        dest.writeString(source);
        dest.writeInt(promoted);
        dest.writeString(authors_regalia);
        dest.writeString(author_photo);
    }
}
