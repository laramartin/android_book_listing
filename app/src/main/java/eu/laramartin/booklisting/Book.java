package eu.laramartin.booklisting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lara on 11/09/2016.
 */
public class Book implements Parcelable {

    String author;
    String title;

    public Book(String author, String title) {
        this.author = author;
        this.title = title;
    }

    protected Book(Parcel in) {
        author = in.readString();
        title = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(title);
    }
}
