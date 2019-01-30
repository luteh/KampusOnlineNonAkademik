package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan;


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
public class KalenderKegiatanFragment extends BaseFragment implements IKegiatanView {

    private IKegiatanPresenter iKegiatanPresenter;

    public KalenderKegiatanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kalender_kegiatan, container, false);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iKegiatanPresenter = new KegiatanPresenterImp(this);
    }
}
