package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberChild;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by Luthfan Maftuh on 23/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarMemberChildHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_daftar_member_child)
    public ImageView iv_daftar_member_child;
    @BindView(R.id.tv_daftar_member_child_npm)
    public TextView tv_daftar_member_child_npm;
    @BindView(R.id.tv_daftar_member_child_nama)
    public TextView tv_daftar_member_child_nama;
    @BindView(R.id.tv_daftar_member_child_no_hp)
    public TextView tv_daftar_member_child_no_hp;

    public int position;
    public DaftarMemberChild daftarMemberChild;

    private OnItemClicked onItemClicked;
    private View view;

    public DaftarMemberChildHolder(@NonNull View itemView, OnItemClicked onItemClicked) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        view = itemView;
        this.onItemClicked = onItemClicked;
    }


    @OnClick
    void onItemClick() {
        Timber.d("Position %d clicked!", position);
        onItemClicked.onChildItemClicked(view, daftarMemberChild, position);
    }
}
