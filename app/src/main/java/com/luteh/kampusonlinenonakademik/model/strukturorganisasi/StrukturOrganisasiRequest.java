package com.luteh.kampusonlinenonakademik.model.strukturorganisasi;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.*;

/**
 * Created by Luthfan Maftuh on 20/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class StrukturOrganisasiRequest {
    private int id;

    private Uri imageUri;
    private String npm;
    private String nama;
    private String jabatan;
    private Integer treeLevel;

    public StrukturOrganisasiRequest(int id, Uri imageUri, String npm, String nama, String jabatan) {
        this.id = id;
        this.imageUri = imageUri;
        this.nama = nama;
        this.npm = npm;
        this.jabatan = jabatan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        else return RESULT_OK;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        if (getImageUri() != null) result.put(FIELD_PHOTO_URL, getImageUri());
        result.put(FIELD_NPM, getNpm());
        result.put(FIELD_NAMA, getNama());
        result.put(FIELD_JABATAN, getJabatan());
        result.put(FIELD_TREE_LEVEL, getTreeLevel());

        return result;
    }
}
