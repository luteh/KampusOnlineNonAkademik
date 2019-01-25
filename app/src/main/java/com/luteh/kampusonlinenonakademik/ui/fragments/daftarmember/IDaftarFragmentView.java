package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember;

import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberParent;

import java.util.List;

/**
 * Created by Luthfan Maftuh on 23/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IDaftarFragmentView {
    void onRetrieveDataSuccessed(List<DaftarMemberParent> daftarMemberParents);

    void onRetrieveTahunAngkatanDataSuccessed(List<String> daftarMemberTahuns);
}
