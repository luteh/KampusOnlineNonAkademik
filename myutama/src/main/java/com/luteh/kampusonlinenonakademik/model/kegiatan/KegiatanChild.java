package com.luteh.kampusonlinenonakademik.model.kegiatan;

/**
 * Created by Luthfan Maftuh on 01/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class KegiatanChild {
    public String key;
    public String jam_kegiatan;
    public String deskripsi_kegiatan;

    public KegiatanChild() {
    }

    public KegiatanChild(String key, String jam_kegiatan, String deskripsi_kegiatan) {
        this.key = key;
        this.jam_kegiatan = jam_kegiatan;
        this.deskripsi_kegiatan = deskripsi_kegiatan;
    }

    @Override
    public String toString() {
        return "KegiatanChild{" +
                "key='" + key + '\'' +
                ", jam_kegiatan='" + jam_kegiatan + '\'' +
                ", deskripsi_kegiatan='" + deskripsi_kegiatan + '\'' +
                '}';
    }
}
