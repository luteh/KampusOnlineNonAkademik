package com.luteh.kampusonlinenonakademik.ui.activities.allberita.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luteh.kampusonlinenonakademik.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Luthfan Maftuh on 24/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class AllBeritaHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_allberita_item_judul)
    protected TextView tv_allberita_item_judul;
    @BindView(R.id.tv_allberita_item_post_by)
    protected TextView tv_allberita_item_post_by;
    @BindView(R.id.tv_allberita_item_tanggal)
    protected TextView tv_allberita_item_tanggal;
    @BindView(R.id.iv_allberita_item)
    protected ImageView iv_allberita_item;

    private IAllBeritaAdapter iAllBeritaAdapter;

    public AllBeritaHolder(@NonNull View itemView, IAllBeritaAdapter iAllBeritaAdapter) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.iAllBeritaAdapter = iAllBeritaAdapter;
    }

    @OnClick()
    void onClick(){
        iAllBeritaAdapter.onItemClick(getAdapterPosition());
    }
}
