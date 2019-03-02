package com.luteh.kampusonlinenonakademik.model.home;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by Luthfan Maftuh on 21/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class News implements Parcelable, INews {
    public String judul;
    public String image_url;
    public String deskripsi;
    public String tanggal_berita;
    public String post_by;

    public News() {
    }

    public News(String judul, String image_url, String deskripsi, String tanggal_berita, String post_by) {
        this.judul = judul;
        this.image_url = image_url;
        this.deskripsi = deskripsi;
        this.tanggal_berita = tanggal_berita;
        this.post_by = post_by;
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

    @Override
    public int isValidData() {
        if(TextUtils.isEmpty(judul)) return 0;
        else if(TextUtils.isEmpty(image_url)) return 1;
        else if(TextUtils.isEmpty(deskripsi)) return 2;
        else if(TextUtils.isEmpty(tanggal_berita)) return 3;
        else if(TextUtils.isEmpty(post_by)) return 4;
        else return -1;
    }
}
