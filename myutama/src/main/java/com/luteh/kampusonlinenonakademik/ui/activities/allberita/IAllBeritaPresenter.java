package com.luteh.kampusonlinenonakademik.ui.activities.allberita;

/**
 * Created by Luthfan Maftuh on 26/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IAllBeritaPresenter {
    void submitNewBerita(String judul,
                         String image_url,
                         String deskripsi,
                         String tanggal_berita,
                         String post_by);
}
