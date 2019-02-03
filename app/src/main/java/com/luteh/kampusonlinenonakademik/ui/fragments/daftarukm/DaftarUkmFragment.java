package com.luteh.kampusonlinenonakademik.ui.fragments.daftarukm;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarUkmFragment extends BaseFragment implements IDaftarUkmView {

    private IDaftarUkmPresenter iDaftarUkmPresenter;

    public DaftarUkmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daftar_ukm, container, false);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iDaftarUkmPresenter = new DaftarUkmPresenterImp(this);
    }
}
