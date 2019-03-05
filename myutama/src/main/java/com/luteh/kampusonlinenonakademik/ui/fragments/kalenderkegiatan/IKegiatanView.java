package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan;

import com.luteh.kampusonlinenonakademik.model.kegiatan.KegiatanChild;
import com.luteh.kampusonlinenonakademik.model.kegiatan.KegiatanParent;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Luthfan Maftuh on 30/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IKegiatanView {
    void onSuccessRetrieveKegiatanData(List<KegiatanParent> kegiatanParents, HashMap<String, List<KegiatanChild>> mapList);

    void onFailure(String message);

    void onSuccessSubmitNewKegiatanData();

    void onSuccessDeleteKegiatan();
}
