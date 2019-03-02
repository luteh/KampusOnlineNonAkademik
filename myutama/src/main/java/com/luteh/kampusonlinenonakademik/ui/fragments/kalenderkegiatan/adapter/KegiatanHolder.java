package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan.adapter;

import android.view.View;
import android.widget.TextView;

import com.luteh.kampusonlinenonakademik.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Luthfan Maftuh on 01/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class KegiatanHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_kegiatan_jam_item)
    public TextView tv_kegiatan_jam_item;
    @BindView(R.id.tv_kegiatan_deskripsi_item)
    public TextView tv_kegiatan_deskripsi_item;


    public KegiatanHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
