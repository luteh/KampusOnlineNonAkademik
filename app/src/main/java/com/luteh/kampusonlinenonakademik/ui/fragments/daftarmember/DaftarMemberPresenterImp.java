package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember;

import android.net.Uri;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.AppConstant;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberAdd;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberChild;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberParent;

import java.util.ArrayList;
import java.util.List;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.*;

/**
 * Created by Luthfan Maftuh on 23/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarMemberPresenterImp implements IDaftarMemberPresenter {

    private IDaftarFragmentView iDaftarFragmentView;

    private DaftarMemberAdd daftarMemberAdd;

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

    @Override
    public void submitMemberData(String imageUri, String npm, String nama, String noHp, String angkatan) {
        daftarMemberAdd = new DaftarMemberAdd(imageUri, npm, nama, noHp, angkatan);
        putMemberImage();
    }

    private void putMemberImage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(ARG_UKM)
                .child(Common.formatChildName(AccountHelper.getUser().ukm))
                .child(ARG_MEMBER_IMAGE)
                .child(Common.formatChildName(daftarMemberAdd.getNpm() + ".png"));

        RxFirebaseStorage.putFile(storageReference, Uri.parse(daftarMemberAdd.getPhoto_url()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskSnapshot ->
                                taskSnapshot.getStorage().getDownloadUrl()
                                        .addOnCompleteListener(task -> {
                                            Timber.d("Download URL: %s", task.getResult());
                                            daftarMemberAdd.setPhoto_url(task.getResult().toString());
                                            addNewMemberField();
                                        })
                                        .addOnFailureListener(e -> {
                                            Timber.e(e.getMessage());
                                            iDaftarFragmentView.onAddNewDataFailed(e.getMessage());
                                        })
                        ,
                        throwable -> {
                            Timber.e(throwable.getMessage());
                            iDaftarFragmentView.onAddNewDataFailed(throwable.getMessage());
                        }
                );
    }

    private void addNewMemberField() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(AppConstant.ARG_DAFTAR_MEMBER)
                .child("ukm_" + Common.formatChildName(AccountHelper.getUser().ukm))
                .child(daftarMemberAdd.getAngkatan())
                .push();

        // still have issues on updating database fields
        RxFirebaseDatabase.setValue(databaseReference, daftarMemberAdd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            Timber.d("Member info updated!");
                            iDaftarFragmentView.onAddNewDataSuccessfully();
                        },
                        throwable -> {
                            Timber.e(throwable.getMessage());
                            iDaftarFragmentView.onAddNewDataFailed(throwable.getMessage());
                        }
                );
    }
}
