package com.luteh.kampusonlinenonakademik.model.daftarmember;

/**
 * Created by Luthfan Maftuh on 26/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarMemberAdd {
    private String photo_url;
    private String npm;
    private String nama;
    private String no_hp;
    private String angkatan;

    public DaftarMemberAdd(String photo_url, String npm, String nama, String no_hp, String angkatan) {
        this.photo_url = photo_url;
        this.npm = npm;
        this.nama = nama;
        this.no_hp = no_hp;
        this.angkatan = angkatan;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }
}
