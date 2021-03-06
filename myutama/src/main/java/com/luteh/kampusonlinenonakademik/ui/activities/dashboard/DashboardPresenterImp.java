package com.luteh.kampusonlinenonakademik.ui.activities.dashboard;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.User;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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

                                Timber.d("User Info: %s", AccountHelper.getUser().toString());

                                iDashboardView.onRetrieveUserInfoSuccess();
                            }, throwable -> {
                                Common.dismissProgressBar();
                                Timber.e(throwable.getMessage());
                            }
                    );
        }
    }
}
