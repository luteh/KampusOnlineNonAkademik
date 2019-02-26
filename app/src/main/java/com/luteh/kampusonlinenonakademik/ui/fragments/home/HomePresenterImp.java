package com.luteh.kampusonlinenonakademik.ui.fragments.home;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.AppConstant;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.home.News;

import java.util.ArrayList;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
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

    private ArrayList<News> newsList = new ArrayList<>();
    private DatabaseReference databaseReference;

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
                        iHomeView.onRetrieveContentSuccessful(taskSnapshot);
                    }, throwable -> {
                        Timber.e(throwable.getMessage());
                    });
        }
    }

    @Override
    public void getNewsData() {
        if (AccountHelper.getUser() != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child(AppConstant.ARG_NEWS);

            RxFirebaseDatabase.observeSingleValueEvent(databaseReference, dataSnapshot -> {
//                ArrayList<News> newsList = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    newsList.add(postSnapshot.getValue(News.class));
                }

                return newsList;
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(news -> {
                        Timber.d("Get news data successful!");
                        iHomeView.onSuccessGetNewsData(news);
                        }, throwable -> {
                        iHomeView.onFailureGetNewsData(throwable.getMessage());
                        throwable.printStackTrace();
                    });
        }
    }
}
