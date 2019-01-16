package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;

import android.content.Context;

/**
 * Created by Luthfan Maftuh on 15/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class StrukturOrganisasiPresenterImp implements IStrukturOrganisasiPresenter {

    private Context context;
    private IStrukturOrganisasiView iStrukturOrganisasiView;

    public StrukturOrganisasiPresenterImp(Context context, IStrukturOrganisasiView iStrukturOrganisasiView) {
        this.context = context;
        this.iStrukturOrganisasiView = iStrukturOrganisasiView;
    }
}
