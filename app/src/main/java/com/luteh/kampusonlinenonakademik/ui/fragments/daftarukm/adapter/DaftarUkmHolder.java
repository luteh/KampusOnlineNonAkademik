package com.luteh.kampusonlinenonakademik.ui.fragments.daftarukm.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luteh.kampusonlinenonakademik.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Luthfan Maftuh on 04/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarUkmHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_daftar_ukm_item)
    ImageView iv_daftar_ukm_item;
    @BindView(R.id.tv_daftar_ukm_title_item)
    TextView tv_daftar_ukm_title_item;

    private OnDaftarUkmItemClick onDaftarUkmItemClick;

    public DaftarUkmHolder(@NonNull View itemView, OnDaftarUkmItemClick onDaftarUkmItemClick) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.onDaftarUkmItemClick = onDaftarUkmItemClick;
    }

    @OnClick
    void onItemClick() {
        onDaftarUkmItemClick.onItemClicked();
    }

    void setDaftarUkmImage(String logo) {
        Picasso.get()
                .load(logo)
                .placeholder(R.drawable.ic_user_holo)
                .into(iv_daftar_ukm_item);
    }

    void setDaftarUkmTitle(String nama_ukm) {
        tv_daftar_ukm_title_item.setText(nama_ukm);
    }
}
