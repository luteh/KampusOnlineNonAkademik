package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.adapter;

import android.view.View;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.luteh.kampusonlinenonakademik.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Luthfan Maftuh on 23/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarMemberParentHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_daftar_member_tahun)
    public TextView tv_daftar_member_tahun;
    @BindView(R.id.rv_daftar_member_child)
    public RecyclerView rv_daftar_member_child;

    public DaftarMemberParentHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
