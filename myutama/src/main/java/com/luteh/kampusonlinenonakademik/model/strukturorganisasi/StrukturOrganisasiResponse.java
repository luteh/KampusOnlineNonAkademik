package com.luteh.kampusonlinenonakademik.model.strukturorganisasi;

import androidx.annotation.NonNull;

/**
 * Created by Luthfan Maftuh on 17/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class StrukturOrganisasiResponse {
    public String photo_url;
    public String npm;
    public String nama;
    public String jabatan;
    public Integer tree_level;

    @NonNull
    @Override
    public String toString() {
        return String.format("Photo URL: %s \n" +
                        "NPM: %s \n" +
                        "Nama: %s \n" +
                        "Jabatan: %s",
                photo_url, npm, nama, jabatan);
    }
}
