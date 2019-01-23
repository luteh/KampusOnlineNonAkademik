package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberParent;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarMemberFragment extends BaseFragment implements IDaftarFragmentView {

    private IDaftarMemberPresenter iDaftarMemberPresenter;

    public DaftarMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daftar_member, container, false);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iDaftarMemberPresenter = new DaftarMemberPresenterImp(this);

        iDaftarMemberPresenter.retrieveDaftarMemberData();
    }

    @Override
    public void onRetrieveDataSuccessed(List<DaftarMemberParent> daftarMemberParents) {

    }
}
