package com.luteh.kampusonlinenonakademik.model;

import android.net.Uri;

/**
 * Created by Luthfan Maftuh on 14/11/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class User {
    private Uri uri;
    private String name;
    private String npm;
    private String fakultas;
    private String jurusan;

    public User() {
    }

    public User(Uri uri) {
        this.uri = uri;
    }

    public User(String name, String npm, String fakultas, String jurusan) {
        this.name = name;
        this.npm = npm;
        this.fakultas = fakultas;
        this.jurusan = jurusan;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
}
