package com.luteh.kampusonlinenonakademik.model;

import androidx.annotation.NonNull;

/**
 * Created by Luthfan Maftuh on 17/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class StrukturOrganisasi {
    public String photo_url;
    public String npm;
    public String nama;
    public String jabatan;
    public Integer tree_level;

    @NonNull
    @Override
    public String toString() {
        return String.format("Nama: %s \n NPM: %s \n Jabatan: %s",nama, npm, jabatan);
    }
}
