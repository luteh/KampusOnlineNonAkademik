package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberChild;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberParent;

import java.util.ArrayList;
import java.util.List;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.ARG_DAFTAR_MEMBER;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.ARG_TAHUN;

/**
 * Created by Luthfan Maftuh on 23/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarMemberPresenterImp implements IDaftarMemberPresenter {

    private IDaftarFragmentView iDaftarFragmentView;

    public DaftarMemberPresenterImp(IDaftarFragmentView iDaftarFragmentView) {
        this.iDaftarFragmentView = iDaftarFragmentView;
    }

    @Override
    public void retrieveDaftarMemberData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_DAFTAR_MEMBER)
                .child("ukm_" + Common.formatChildName(AccountHelper.getUser().ukm));

        List<DaftarMemberParent> daftarMemberParents = new ArrayList<>();

        RxFirebaseDatabase.observeChildEvent(databaseReference, dataSnapshotRxFirebaseChildEvent -> {
            List<DaftarMemberChild> daftarMemberChildren = new ArrayList<>();

            for (DataSnapshot dataSnapshot : dataSnapshotRxFirebaseChildEvent.getValue().getChildren()) {
                Timber.d("Children Value: %s", dataSnapshot.getValue());
                daftarMemberChildren.add(dataSnapshot.getValue(DaftarMemberChild.class));
            }

            daftarMemberParents.add(
                    new DaftarMemberParent(dataSnapshotRxFirebaseChildEvent.getKey(),
                            daftarMemberChildren)
            );
            return dataSnapshotRxFirebaseChildEvent;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataSnapshotRxFirebaseChildEvent ->
                                iDaftarFragmentView.onRetrieveDataSuccessed(daftarMemberParents),
                        throwable -> Timber.d(throwable.getMessage())
                );
    }

    @Override
    public void retrieveTahunAngkatanData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_TAHUN);

        List<String> daftarMemberTahuns = new ArrayList<>();

        RxFirebaseDatabase.observeChildEvent(databaseReference, dataSnapshotRxFirebaseChildEvent -> {
            for (DataSnapshot dataSnapshot : dataSnapshotRxFirebaseChildEvent.getValue().getChildren()) {
                daftarMemberTahuns.add(dataSnapshot.getValue().toString());
            }
            return dataSnapshotRxFirebaseChildEvent;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataSnapshotRxFirebaseChildEvent ->
                                iDaftarFragmentView.onRetrieveTahunAngkatanDataSuccessed(daftarMemberTahuns),
                        throwable -> Timber.e(throwable.getMessage())
                );
    }
}
