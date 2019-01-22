package com.luteh.kampusonlinenonakademik.model.strukturorganisasi;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.json.JSONException;
import org.json.JSONObject;

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

    @SerializedName("photo_url")
    @Expose
    private String photo_url;
    @SerializedName("npm")
    @Expose
    private String npm;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("jabatan")
    @Expose
    private String jabatan;
    @SerializedName("tree_level")
    @Expose
    private Integer tree_level;

    public StrukturOrganisasiRequest(int id, String photo_url, String npm, String nama, String jabatan) {
        this.id = id;
        this.photo_url = photo_url;
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

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
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

    public Integer getTree_level() {
        return tree_level;
    }

    public void setTree_level(Integer tree_level) {
        this.tree_level = tree_level;
    }

    public int isValidData() {
//        if (getPhoto_url() == null) return 0;
        if (TextUtils.isEmpty(getNpm())) return 1;
        else if (TextUtils.isEmpty(getNama())) return 2;
        else return RESULT_OK;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Photo URL: %s \n" +
                        "NPM: %s \n" +
                        "Nama: %s \n" +
                        "Jabatan: %s",
                getPhoto_url(), getNpm(), getNama(), getJabatan());
    }
}
