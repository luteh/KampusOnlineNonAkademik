package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.jobdesk.JobDesk;
import com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.adapter.JobDeskAdapter;
import com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.adapter.OnJobDeskItemClicked;
import com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.editjobdeskdialog.EditJobDeskHolder;
import com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.editjobdeskdialog.OnJobDeskDialogClick;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobDeskFragment extends BaseFragment implements IJobDeskView,
        OnJobDeskItemClicked,
        OnJobDeskDialogClick {

    @BindView(R.id.rv_job_desk)
    RecyclerView rv_job_desk;
    @BindView(R.id.ll_progress_bar_container)
    LinearLayout ll_progress_bar_container;

    private IJobDeskPresenter iJobDeskPresenter;

    private RecyclerView.Adapter mAdapter;

    private JobDesk jobDesk;

    private EditJobDeskHolder editJobDeskHolder;
    private AlertDialog jobDeskDialog;

    public JobDeskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_desk, container, false);
    }

    @Override
    protected void onInit() {
        super.onInit();

        rv_job_desk.setHasFixedSize(true);
        rv_job_desk.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        iJobDeskPresenter = new JobDeskPresenterImp(this);

        iJobDeskPresenter.retrieveJobDeskData();

        getBaseActivity().onLoadingStarted(rv_job_desk, ll_progress_bar_container);
    }

    @Override
    public void onRetrieveDataSuccess(List<JobDesk> jobDesks) {
        getBaseActivity().onLoadingFinished(rv_job_desk, ll_progress_bar_container);

        mAdapter = new JobDeskAdapter(jobDesks, this);
        rv_job_desk.setAdapter(mAdapter);
    }

    @Override
    public void onRetrieveDataFailure(String message) {
        getBaseActivity().onLoadingFinished(rv_job_desk, ll_progress_bar_container);
        Common.showErrorMessage(context, message);
    }

    @Override
    public void onItemClicked(View view, JobDesk jobDesk) {
        this.jobDesk = jobDesk;
        showMenuItem(view);
    }

    private void showMenuItem(View view) {
        PopupMenu menu = new PopupMenu(context, view);

        menu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.item_edit:
                    Common.showToastMessage(context, "Edit Clicked");
                    showEditJobDeskDialog();
                    break;
            }
            return true;
        });

        menu.inflate(R.menu.menu_item);
        menu.getMenu().getItem(1).setVisible(false);
        menu.show();
    }

    private void showEditJobDeskDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_job_desk, null);
        editJobDeskHolder = new EditJobDeskHolder(view, this);

        editJobDeskHolder.setDivisi(jobDesk.divisi);
        editJobDeskHolder.setJob(jobDesk.job);

        jobDeskDialog = new androidx.appcompat.app.AlertDialog.Builder(context)
                .setView(view)
                .create();

        jobDeskDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        jobDeskDialog.show();
    }

    @Override
    public void onBtnDialogClicked() {
        jobDesk.setDivisi(editJobDeskHolder.getDivisi());
        jobDesk.setJob(editJobDeskHolder.getJob());

        jobDeskDialog.dismiss();

        mAdapter.notifyDataSetChanged();
    }
}
