package com.luteh.kampusonlinenonakademik.common.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.luteh.kampusonlinenonakademik.R;

/**
 * Created by Luthfan Maftuh on 15/11/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class HeaderViewHolder {
    @BindView(R.id.imgProfile)
    public ImageView imgProfile;
    @BindView(R.id.tvProfileName)
    public TextView tvProfileName;
    @BindView(R.id.tvProfileNpm)
    public TextView tvProfileNpm;

    public HeaderViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
