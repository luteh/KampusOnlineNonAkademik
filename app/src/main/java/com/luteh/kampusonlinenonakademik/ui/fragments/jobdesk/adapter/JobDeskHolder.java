package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.adapter;

import android.view.View;
import android.widget.TextView;

import com.luteh.kampusonlinenonakademik.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Luthfan Maftuh on 28/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class JobDeskHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_job_desk_divisi_item)
    public TextView tv_job_desk_divisi_item;
    @BindView(R.id.tv_job_desk_job_item)
    public TextView tv_job_desk_job_item;

    public JobDeskHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
