package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.jobdesk.JobDesk;
import com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.adapter.JobDeskAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobDeskFragment extends BaseFragment implements IJobDeskView {
    @BindView(R.id.rv_job_desk)
    RecyclerView rv_job_desk;
    @BindView(R.id.ll_progress_bar_container)
    LinearLayout ll_progress_bar_container;

    private IJobDeskPresenter iJobDeskPresenter;

    private RecyclerView.Adapter mAdapter;

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

        mAdapter = new JobDeskAdapter(jobDesks);
        rv_job_desk.setAdapter(mAdapter);
    }

    @Override
    public void onRetrieveDataFailure(String message) {
        getBaseActivity().onLoadingFinished(rv_job_desk, ll_progress_bar_container);
        Common.showErrorMessage(context, message);
    }
}
