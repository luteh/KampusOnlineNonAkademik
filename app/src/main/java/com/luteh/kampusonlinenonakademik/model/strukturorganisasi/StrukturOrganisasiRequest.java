package com.luteh.kampusonlinenonakademik.model.strukturorganisasi;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by Luthfan Maftuh on 20/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class StrukturOrganisasiRequest {
    private Uri imageUri;
    private String nama;
    private String npm;
    private String jabatan;

    private Integer treeLevel;

    public StrukturOrganisasiRequest(Uri imageUri, String npm, String nama, String jabatan) {
        this.imageUri = imageUri;
        this.nama = nama;
        this.npm = npm;
        this.jabatan = jabatan;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public Integer getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    public int isValidData() {
//        if (getImageUri() == null) return 0;
         if (TextUtils.isEmpty(getNpm())) return 1;
        else if (TextUtils.isEmpty(getNama())) return 2;
        else return -1;
    }
}
