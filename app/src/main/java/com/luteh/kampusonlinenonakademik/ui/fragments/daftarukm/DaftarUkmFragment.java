package com.luteh.kampusonlinenonakademik.ui.fragments.daftarukm;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.common.utils.CustomGridRecyclerView;
import com.luteh.kampusonlinenonakademik.model.daftarukm.DaftarUkm;
import com.luteh.kampusonlinenonakademik.ui.fragments.daftarukm.adapter.DaftarUkmAdapter;
import com.luteh.kampusonlinenonakademik.ui.fragments.daftarukm.adapter.OnDaftarUkmItemClick;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarUkmFragment extends BaseFragment implements IDaftarUkmView,
        OnDaftarUkmItemClick {

    @BindView(R.id.rv_daftar_ukm)
    CustomGridRecyclerView rv_daftar_ukm;
    @BindView(R.id.ll_progress_bar_container)
    ViewGroup ll_progress_bar_container;

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

        initRecyclerView();

        iDaftarUkmPresenter = new DaftarUkmPresenterImp(this);

        iDaftarUkmPresenter.retrieveDaftarUkmData();

        getBaseActivity().onLoadingStarted(rv_daftar_ukm, ll_progress_bar_container);
    }

    private void initRecyclerView() {
        rv_daftar_ukm.setHasFixedSize(true);
        rv_daftar_ukm.setLayoutManager(new GridLayoutManager(context, 2));
    }

    @Override
    public void onSuccessRetrieveUkmData(List<DaftarUkm> daftarUkms) {
        Common.showSuccessMessage(context, "Retrieve data successfully!");
        getBaseActivity().onLoadingFinished(rv_daftar_ukm, ll_progress_bar_container);

        rv_daftar_ukm.setAdapter(new DaftarUkmAdapter(daftarUkms, this));
    }

    @Override
    public void onFailure(String message) {
        Common.showErrorMessage(context, message);

        getBaseActivity().onLoadingFinished(rv_daftar_ukm, ll_progress_bar_container);
    }

    @Override
    public void onItemClicked() {

    }
}
