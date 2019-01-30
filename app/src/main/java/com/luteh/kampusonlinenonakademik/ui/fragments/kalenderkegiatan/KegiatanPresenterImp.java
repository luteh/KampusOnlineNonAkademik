package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan;

/**
 * Created by Luthfan Maftuh on 30/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class KegiatanPresenterImp implements IKegiatanPresenter {
    private IKegiatanView iKegiatanView;

    public KegiatanPresenterImp(IKegiatanView iKegiatanView) {
        this.iKegiatanView = iKegiatanView;
    }
}
