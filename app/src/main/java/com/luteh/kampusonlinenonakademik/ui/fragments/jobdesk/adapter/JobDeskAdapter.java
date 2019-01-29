package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.adapter;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.jobdesk.JobDesk;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Luthfan Maftuh on 28/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class JobDeskAdapter extends RecyclerView.Adapter<JobDeskHolder> {
    private List<JobDesk> jobDesks;
    private OnJobDeskItemClicked onJobDeskItemClicked;

    public JobDeskAdapter(List<JobDesk> jobDesks, OnJobDeskItemClicked onJobDeskItemClicked) {
        this.jobDesks = jobDesks;
        this.onJobDeskItemClicked = onJobDeskItemClicked;
    }

    @NonNull
    @Override
    public JobDeskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobDeskHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.job_desk_item, null),
                onJobDeskItemClicked
        );
    }

    @Override
    public void onBindViewHolder(@NonNull JobDeskHolder holder, int position) {
        JobDesk jobDesk = jobDesks.get(position);
        holder.jobDesk = jobDesk;

        holder.tv_job_desk_divisi_item.setText(jobDesk.divisi);
        holder.tv_job_desk_job_item.setText(Common.paragraphTextStyle(jobDesk.job));
    }

    @Override
    public int getItemCount() {
        return jobDesks.size();
    }
}
