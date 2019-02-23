package com.luteh.kampusonlinenonakademik.ui.activities.berita;

/**
 * Created by Luthfan Maftuh on 23/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class BeritaPresenterImp implements IBeritaPresenter {

    private IBeritaView iBeritaView;

    public BeritaPresenterImp(IBeritaView iBeritaView) {
        this.iBeritaView = iBeritaView;
    }
}
