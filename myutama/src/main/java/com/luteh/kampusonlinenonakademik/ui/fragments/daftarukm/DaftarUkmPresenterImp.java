package com.luteh.kampusonlinenonakademik.ui.fragments.daftarukm;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luteh.kampusonlinenonakademik.model.daftarukm.DaftarUkm;

import java.util.ArrayList;
import java.util.List;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.ARG_DAFTAR_UKM;

/**
 * Created by Luthfan Maftuh on 03/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarUkmPresenterImp implements IDaftarUkmPresenter {
    private IDaftarUkmView iDaftarUkmView;

    public DaftarUkmPresenterImp(IDaftarUkmView iDaftarUkmView) {
        this.iDaftarUkmView = iDaftarUkmView;
    }

    @Override
    public void retrieveDaftarUkmData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_DAFTAR_UKM);



        RxFirebaseDatabase.observeSingleValueEvent(databaseReference, dataSnapshot -> {
            List<DaftarUkm> daftarUkms = new ArrayList<>();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                daftarUkms.add(postSnapshot.getValue(DaftarUkm.class));
            }

            return daftarUkms;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(daftarUkms -> {
                    Timber.d("Retrieve data success: %d datas", daftarUkms.size());
                    iDaftarUkmView.onSuccessRetrieveUkmData(daftarUkms);
                }, throwable -> {
                    Timber.e(throwable.getMessage());
                    iDaftarUkmView.onFailure(throwable.getMessage());
                });
    }
}
