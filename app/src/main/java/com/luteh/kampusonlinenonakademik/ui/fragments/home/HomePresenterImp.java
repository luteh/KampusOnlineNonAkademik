package com.luteh.kampusonlinenonakademik.ui.fragments.home;

import android.content.Context;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.AppConstant;
import com.luteh.kampusonlinenonakademik.common.Common;
import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Luthfan Maftuh on 16/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class HomePresenterImp implements IHomePresenter {

    private Context context;
    private IHomeView iHomeView;

    public HomePresenterImp(Context context, IHomeView iHomeView) {
        this.context = context;
        this.iHomeView = iHomeView;
    }

    @Override
    public void getHomeContent() {
        if (AccountHelper.getUser() != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child(AppConstant.ARG_UKM +
                            "/" +
                            Common.formatChildName(AccountHelper.getUser().ukm) +
                            AppConstant.ARG_PNG_EXTENSION);

            RxFirebaseStorage.getDownloadUrl(storageReference)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(taskSnapshot -> {
                        Timber.d("Uri: %s", taskSnapshot);
                        iHomeView.onRetrieveContentSuccessed(taskSnapshot);
                    }, throwable -> {
                        Timber.e(throwable.getMessage());
                    });
        }
    }
}
