package com.luteh.kampusonlinenonakademik.model;

/**
 * Created by Luthfan Maftuh on 14/11/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class User {
    public String fakultas;
    public String jurusan;
    public String nama;
    public String npm;
    public String photo_url;
    public String ukm;
    public String jabatan_ukm;
    public boolean isMember;
    public boolean isHasAccess;

    @Override
    public String toString() {
        return "User{" +
                "fakultas='" + fakultas + '\'' +
                ", jurusan='" + jurusan + '\'' +
                ", nama='" + nama + '\'' +
                ", npm='" + npm + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", ukm='" + ukm + '\'' +
                ", jabatan_ukm='" + jabatan_ukm + '\'' +
                ", isMember=" + isMember +
                ", isHasAccess=" + isHasAccess +
                '}';
    }
}
