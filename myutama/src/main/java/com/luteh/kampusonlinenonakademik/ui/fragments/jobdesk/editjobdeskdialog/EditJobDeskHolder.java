package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.editjobdeskdialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luteh.kampusonlinenonakademik.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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

    @OnTextChanged(R.id.et_dialog_job_desk_job)
    void onJobTextChanged(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            et_dialog_job_desk_job.setError("Job Desk description is required!");
            btn_dialog_job_desk_done.setEnabled(false);
        } else {
            btn_dialog_job_desk_done.setEnabled(true);
        }
    }

}
