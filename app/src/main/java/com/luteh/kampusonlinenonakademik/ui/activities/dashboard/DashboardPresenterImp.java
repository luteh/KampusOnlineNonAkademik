package com.luteh.kampusonlinenonakademik.ui.activities.dashboard;

import android.content.Context;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.User;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import java.util.ArrayList;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.ARG_USERS;

/**
 * Created by Luthfan Maftuh on 14/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DashboardPresenterImp implements IDashboardPresenter {

    private Context context;
    private IDashboardView iDashboardView;

    public DashboardPresenterImp(Context context, IDashboardView iDashboardView) {
        this.context = context;
        this.iDashboardView = iDashboardView;
    }

    @Override
    public void getUserInfo() {
        if (AccountHelper.getToken() != null) {
            Common.showProgressBar(context);

            DatabaseReference databaseReference =
                    FirebaseDatabase.getInstance().getReference()
                            .child(ARG_USERS)
                            .child(AccountHelper.getToken());

            RxFirebaseDatabase.observeSingleValueEvent(databaseReference,
                    dataSnapshot -> dataSnapshot.getValue(User.class))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(user -> {
                                Common.dismissProgressBar();

                                AccountHelper.saveUser(user);
                                iDashboardView.onRetrieveUserInfoSuccess();
                            }, throwable -> {
                                Common.dismissProgressBar();
                                Timber.e(throwable.getMessage());
                            }
                    );
        }
    }
}
