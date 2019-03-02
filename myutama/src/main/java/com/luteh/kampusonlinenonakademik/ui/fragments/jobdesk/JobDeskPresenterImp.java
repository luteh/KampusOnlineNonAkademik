package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.jobdesk.JobDesk;

import java.util.ArrayList;
import java.util.List;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.ARG_JOB_DESK;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.FIELD_JOB;

/**
 * Created by Luthfan Maftuh on 27/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class JobDeskPresenterImp implements IJobDeskPresenter {
    private IJobDeskView iJobDeskView;


    public JobDeskPresenterImp(IJobDeskView iJobDeskView) {
        this.iJobDeskView = iJobDeskView;
    }

    @Override
    public void retrieveJobDeskData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_JOB_DESK);

        RxFirebaseDatabase.observeSingleValueEvent(databaseReference, dataSnapshot -> {
            List<JobDesk> jobDesks = new ArrayList<>();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                jobDesks.add(postSnapshot.getValue(JobDesk.class));
            }
            return jobDesks;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jobDesks -> {
                            Timber.d("Retrieve job desk data successfully");
                            iJobDeskView.onRetrieveDataSuccess(jobDesks);
                        },
                        throwable -> {
                            Timber.e(throwable.getMessage());
                            iJobDeskView.onRetrieveDataFailure(throwable.getMessage());
                        }
                );
    }

    @Override
    public void submitEditJobDesk(JobDesk jobDesk) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_JOB_DESK)
                .child(Common.formatChildName(jobDesk.divisi))
                .child(FIELD_JOB);

        RxFirebaseDatabase.setValue(databaseReference, jobDesk.job)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.d("Job Desk Updated");
                    iJobDeskView.onUpdateJobDeskSuccess();
                }, throwable -> {
                    Timber.e(throwable.getMessage());
                    iJobDeskView.onUpdateJobDeskFailure("Update Job Desk Failed");
                });
    }
}
