package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan;

/**
 * Created by Luthfan Maftuh on 30/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IKegiatanPresenter {
    void retrieveKegiatanData();

    void submitNewKegiatanToDatabase(String tanggal, String jam, String deskripsi);

    void deleteKegiatan(String stringDate, String key);
}
