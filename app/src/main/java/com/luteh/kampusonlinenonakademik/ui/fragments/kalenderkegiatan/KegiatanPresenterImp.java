package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.kegiatan.KegiatanChild;
import com.luteh.kampusonlinenonakademik.model.kegiatan.KegiatanParent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.ARG_KALENDER_KEGIATAN;

/**
 * Created by Luthfan Maftuh on 30/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class KegiatanPresenterImp implements IKegiatanPresenter {
    private IKegiatanView iKegiatanView;

    public KegiatanPresenterImp(IKegiatanView iKegiatanView) {
        this.iKegiatanView = iKegiatanView;
    }

    @Override
    public void retrieveKegiatanData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_KALENDER_KEGIATAN)
                .child("ukm_" + Common.formatChildName(AccountHelper.getUser().ukm));

        List<KegiatanParent> kegiatanParents = new ArrayList<>();
        HashMap<String, List<KegiatanChild>> listHashMap = new HashMap<>();

        RxFirebaseDatabase.observeChildEvent(databaseReference, dataSnapshotRxFirebaseChildEvent -> {
            List<KegiatanChild> kegiatanChildren = new ArrayList<>();

            for (DataSnapshot dataSnapshot : dataSnapshotRxFirebaseChildEvent.getValue().getChildren()) {
                kegiatanChildren.add(dataSnapshot.getValue(KegiatanChild.class));
            }

            kegiatanParents.add(new KegiatanParent(dataSnapshotRxFirebaseChildEvent.getKey(),
                    kegiatanChildren));

            listHashMap.put(dataSnapshotRxFirebaseChildEvent.getKey(), kegiatanChildren);

            return dataSnapshotRxFirebaseChildEvent;
        })
                .subscribe(dataSnapshotRxFirebaseChildEvent -> {
                            Timber.d("Retrieve kegiatan data success");
                            iKegiatanView.onSuccessRetrieveKegiatanData(kegiatanParents, listHashMap);
                        }, throwable -> {
                            Timber.e(throwable.getMessage());
                            iKegiatanView.onFailure(throwable.getMessage());
                        }
                );
    }

    @Override
    public void submitNewKegiatanToDatabase(String tanggal, String jam, String deskripsi) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_KALENDER_KEGIATAN)
                .child("ukm_" + Common.formatChildName(AccountHelper.getUser().ukm))
                .child(tanggal)
                .push();

        RxFirebaseDatabase.setValue(databaseReference, new KegiatanChild(jam, deskripsi))
                .subscribe(() -> {
                    Timber.d("Submit new kegiatan successfully");
                    iKegiatanView.onSuccessSubmitNewKegiatanData();
                }, throwable -> {
                    Timber.e(throwable.getMessage());
                    iKegiatanView.onFailure(throwable.getMessage());
                });
    }
}
