package ru.klops.klops.models.article;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Content implements Parcelable{
    public String text;
    public List<Photos> photos;
    public List<Gallery> gallery;
    public Associate associate;


    public Content(String text, List<Photos> photos, List<Gallery> gallery, Associate associate) {
        this.text = text;
        this.photos = photos;
        this.gallery = gallery;
        this.associate = associate;
    }


    protected Content(Parcel in) {
        text = in.readString();
        photos = in.createTypedArrayList(Photos.CREATOR);
        gallery = in.createTypedArrayList(Gallery.CREATOR);
        associate = in.readParcelable(Associate.class.getClassLoader());
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photos> photos) {
        this.photos = photos;
    }

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    @Override
    public String toString() {
        return "Content{" +
                "text='" + text + '\'' +
                ", photos=" + photos +
                ", gallery=" + gallery +
                ", associate=" + associate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeTypedList(photos);
        dest.writeTypedList(gallery);
        dest.writeParcelable(associate, flags);
    }
}
