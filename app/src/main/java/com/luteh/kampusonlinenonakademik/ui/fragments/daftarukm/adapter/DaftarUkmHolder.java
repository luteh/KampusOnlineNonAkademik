package com.luteh.kampusonlinenonakademik.ui.fragments.daftarukm.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.model.daftarukm.DaftarUkm;
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

    private View view;
    private OnDaftarUkmItemClick onDaftarUkmItemClick;
    private DaftarUkm daftarUkm;

    public DaftarUkmHolder(@NonNull View itemView, OnDaftarUkmItemClick onDaftarUkmItemClick) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        view = itemView;
        this.onDaftarUkmItemClick = onDaftarUkmItemClick;
    }

    public void setDaftarUkm(DaftarUkm daftarUkm) {
        this.daftarUkm = daftarUkm;
    }

    @OnClick
    void onItemClick() {
        onDaftarUkmItemClick.onItemClicked(view, daftarUkm);
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
