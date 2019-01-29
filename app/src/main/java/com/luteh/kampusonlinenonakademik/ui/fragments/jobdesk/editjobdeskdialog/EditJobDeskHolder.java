package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.editjobdeskdialog;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luteh.kampusonlinenonakademik.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Luthfan Maftuh on 29/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class EditJobDeskHolder {
    @BindView(R.id.tv_dialog_job_desk_divisi)
    protected TextView tv_dialog_job_desk_divisi;
    @BindView(R.id.til_dialog_job_desk_job)
    protected TextInputLayout til_dialog_job_desk_job;
    @BindView(R.id.et_dialog_job_desk_job)
    protected TextInputEditText et_dialog_job_desk_job;
    @BindView(R.id.btn_dialog_job_desk_done)
    protected Button btn_dialog_job_desk_done;

    private OnJobDeskDialogClick onJobDeskDialogClick;

    public EditJobDeskHolder(View view, OnJobDeskDialogClick onJobDeskDialogClick) {
        ButterKnife.bind(this, view);
        this.onJobDeskDialogClick = onJobDeskDialogClick;
    }

    public void setDivisi(String divisi) {
        tv_dialog_job_desk_divisi.setText(divisi);
    }

    public void setJob(String job) {
        et_dialog_job_desk_job.setText(job);
    }

    public String getDivisi() {
        return tv_dialog_job_desk_divisi.getText().toString();
    }

    public String getJob() {
        return et_dialog_job_desk_job.getText().toString();
    }

    @OnClick(R.id.btn_dialog_job_desk_done)
    void onBtnClicked() {
        onJobDeskDialogClick.onBtnDialogClicked();
    }

}
