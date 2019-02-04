package com.luteh.kampusonlinenonakademik.ui.fragments.daftarukm;

import com.luteh.kampusonlinenonakademik.model.daftarukm.DaftarUkm;

import java.util.List;

/**
 * Created by Luthfan Maftuh on 03/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IDaftarUkmView {
    void onSuccessRetrieveUkmData(List<DaftarUkm> daftarUkms);

    void onFailure(String message);
}
