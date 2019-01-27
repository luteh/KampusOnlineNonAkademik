package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobDeskFragment extends BaseFragment implements IJobDeskView {

    private IJobDeskPresenter iJobDeskPresenter;

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

        iJobDeskPresenter = new JobDeskPresenterImp(this);
    }
}
