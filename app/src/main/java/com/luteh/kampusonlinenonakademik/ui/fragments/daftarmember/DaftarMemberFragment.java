package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberParent;
import com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.adapter.DaftarMemberParentAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarMemberFragment extends BaseFragment implements IDaftarFragmentView {
    @BindView(R.id.tv_daftar_member_header)
    TextView tv_daftar_member_header;
    @BindView(R.id.rv_daftar_member_parent)
    ShimmerRecyclerView rv_daftar_member_parent;
    @BindView(R.id.fab_daftar_member_add)
    FloatingActionButton fab_daftar_member_add;

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

        tv_daftar_member_header.setText(String.format("%s Member", AccountHelper.getUser().ukm));

        iDaftarMemberPresenter = new DaftarMemberPresenterImp(this);

        iDaftarMemberPresenter.retrieveDaftarMemberData();
    }

    @Override
    public void onRetrieveDataSuccessed(List<DaftarMemberParent> daftarMemberParents) {
        initRecycler(daftarMemberParents);
    }

    private void initRecycler(List<DaftarMemberParent> daftarMemberParents) {
        rv_daftar_member_parent.setLayoutManager(
                new LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        );
        rv_daftar_member_parent.setAdapter(new DaftarMemberParentAdapter(daftarMemberParents));
    }
}
