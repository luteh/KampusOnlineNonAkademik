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
import java.util.Map;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

        String key = databaseReference.getKey();

        RxFirebaseDatabase.setValue(databaseReference, new KegiatanChild(key, jam, deskripsi))
                .subscribe(() -> {
                    Timber.d("Submit new kegiatan successfully");
                    iKegiatanView.onSuccessSubmitNewKegiatanData();
                }, throwable -> {
                    Timber.e(throwable.getMessage());
                    iKegiatanView.onFailure(throwable.getMessage());
                });
    }

    @Override
    public void deleteKegiatan(String stringDate, String key) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_KALENDER_KEGIATAN)
                .child("ukm_" + Common.formatChildName(AccountHelper.getUser().ukm))
                .child(stringDate)
                .child(key);

        RxFirebaseDatabase.setValue(databaseReference, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.d("Delete kegiatan item successful!");
                    iKegiatanView.onSuccessDeleteKegiatan();
                }, throwable -> throwable.printStackTrace());
    }

    @Override
    public void editKegiatanData(String key, String newStringDate, String newJamKegiatan, String newDeskripsiKegiatan) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_KALENDER_KEGIATAN)
                .child("ukm_" + Common.formatChildName(AccountHelper.getUser().ukm))
                .child(newStringDate)
                .child(key);

        Map<String, Object> map = new HashMap<>();
        map.put("jam_kegiatan", newJamKegiatan);
        map.put("deskripsi_kegiatan", newDeskripsiKegiatan);

        RxFirebaseDatabase.updateChildren(databaseReference, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.d("Edit kegiatan successful!");
                    iKegiatanView.onSuccessEditKegiatan();
                }, throwable -> throwable.printStackTrace());

    }

    @Override
    public void editKegiatanData(String key, String oldStringDate, String newStringDate, String newJamKegiatan, String newDeskripsiKegiatan) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_KALENDER_KEGIATAN)
                .child("ukm_" + Common.formatChildName(AccountHelper.getUser().ukm));

        RxFirebaseDatabase.setValue(databaseReference.child(oldStringDate).child(key), null)
                .subscribe();

        DatabaseReference newRef = databaseReference.child(newStringDate).push();

        String newKey = newRef.getKey();

        KegiatanChild kegiatanChild = new KegiatanChild(newKey, newJamKegiatan, newDeskripsiKegiatan);

        RxFirebaseDatabase.setValue(newRef, kegiatanChild)
                .subscribe(() -> {
                    Timber.d("Edit kegiatan successful");
                    iKegiatanView.onSuccessEditKegiatan();
                }, throwable -> throwable.printStackTrace());
    }
}
