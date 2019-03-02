package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.model.strukturorganisasi.StrukturOrganisasiResponse;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Luthfan Maftuh on 17/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class GraphViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_jabatan)
    public TextView tv_jabatan;
    @BindView(R.id.iv_struktur_org_item)
    public ImageView iv_struktur_org_item;
    @BindView(R.id.tv_npm)
    public TextView tv_npm;
    @BindView(R.id.tv_nama)
    public TextView tv_nama;

    public int position;
    public StrukturOrganisasiResponse strukturOrganisasiResponse;

    private OnGraphItemClicked onGraphItemClicked;

    public GraphViewHolder(View view, OnGraphItemClicked onGraphItemClicked) {
        super(view);
        ButterKnife.bind(this, view);
        this.onGraphItemClicked = onGraphItemClicked;
    }

    @OnClick
    public void onClick(){
        onGraphItemClicked.onItemClicked(strukturOrganisasiResponse, position);
    }
}
