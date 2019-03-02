package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember;

/**
 * Created by Luthfan Maftuh on 23/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IDaftarMemberPresenter {
    void retrieveDaftarMemberData();

    void retrieveTahunAngkatanData();

    void submitMemberData(String imageUri, String npm, String nama, String noHp, String angkatan);
}
