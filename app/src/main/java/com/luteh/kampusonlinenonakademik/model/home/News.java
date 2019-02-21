package com.luteh.kampusonlinenonakademik.model.home;

/**
 * Created by Luthfan Maftuh on 21/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class News {
    public String judul;
    public String image_url;
    public String deskripsi;
    public String tanggal_berita;
    public String post_by;

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
}
