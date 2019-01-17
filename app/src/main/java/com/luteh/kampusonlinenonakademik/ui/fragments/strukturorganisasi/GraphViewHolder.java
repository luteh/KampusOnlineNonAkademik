package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luteh.kampusonlinenonakademik.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Luthfan Maftuh on 17/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class GraphViewHolder {
    @BindView(R.id.tv_jabatan)
    public TextView tv_jabatan;
    @BindView(R.id.iv_struktur_org_item)
    public ImageView iv_struktur_org_item;
    @BindView(R.id.tv_npm)
    public TextView tv_npm;
    @BindView(R.id.tv_nama)
    public TextView tv_nama;

    public GraphViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
