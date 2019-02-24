package com.luteh.kampusonlinenonakademik.model.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Luthfan Maftuh on 21/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class News implements Parcelable {
    public String judul;
    public String image_url;
    public String deskripsi;
    public String tanggal_berita;
    public String post_by;

    public News() {
    }

    @Override
    public String toString() {
        return "News{" +
                "judul='" + judul + '\'' +
                ", image_url='" + image_url + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", tanggal_berita='" + tanggal_berita + '\'' +
                ", post_by='" + post_by + '\'' +
                '}';
    }

    protected News(Parcel in) {
        judul = in.readString();
        image_url = in.readString();
        deskripsi = in.readString();
        tanggal_berita = in.readString();
        post_by = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(image_url);
        dest.writeString(deskripsi);
        dest.writeString(tanggal_berita);
        dest.writeString(post_by);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
