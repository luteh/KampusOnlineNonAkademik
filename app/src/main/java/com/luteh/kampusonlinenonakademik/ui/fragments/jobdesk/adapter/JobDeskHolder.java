package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.adapter;

import android.view.View;
import android.widget.TextView;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.model.jobdesk.JobDesk;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Luthfan Maftuh on 28/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class JobDeskHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_job_desk_divisi_item)
    protected TextView tv_job_desk_divisi_item;
    @BindView(R.id.tv_job_desk_job_item)
    protected TextView tv_job_desk_job_item;

    private OnJobDeskItemClicked onJobDeskItemClicked;
    private View view;
    protected JobDesk jobDesk;

    public JobDeskHolder(@NonNull View itemView, OnJobDeskItemClicked onJobDeskItemClicked) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        view = itemView;
        this.onJobDeskItemClicked = onJobDeskItemClicked;
    }

    @OnClick
    void onItemClick() {
        onJobDeskItemClicked.onItemClicked(view, jobDesk);
    }
}
