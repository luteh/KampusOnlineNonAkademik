package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;

import android.content.Context;

import android.net.Uri;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.AppConstant;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.strukturorganisasi.StrukturOrganisasiRequest;
import com.luteh.kampusonlinenonakademik.model.strukturorganisasi.StrukturOrganisasiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.blox.graphview.Graph;
import de.blox.graphview.GraphAdapter;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.ARG_MEMBER_IMAGE;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.ARG_UKM;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.FIELD_NPM;

/**
 * Created by Luthfan Maftuh on 15/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class StrukturOrganisasiPresenterImp implements IStrukturOrganisasiPresenter {

    private Context context;
    private IStrukturOrganisasiView iStrukturOrganisasiView;

    public StrukturOrganisasiPresenterImp(Context context, IStrukturOrganisasiView iStrukturOrganisasiView) {
        this.context = context;
        this.iStrukturOrganisasiView = iStrukturOrganisasiView;
    }

    @Override
    public void retrieveStrukturOrganisasiData() {
        if (AccountHelper.getUser() != null) {
            DatabaseReference databaseReference =
                    FirebaseDatabase.getInstance().getReference()
                            .child(AppConstant.ARG_STRUKTUR_ORG)
                            .child("ukm_" + Common.formatChildName(AccountHelper.getUser().ukm));

            RxFirebaseDatabase.observeSingleValueEvent(databaseReference,
                    dataSnapshot -> {
                        List<StrukturOrganisasiResponse> strukturOrganisasiResponseList = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            strukturOrganisasiResponseList.add(postSnapshot.getValue(StrukturOrganisasiResponse.class));
                        }
                        return strukturOrganisasiResponseList;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(strukturOrganisasis -> {
                                Timber.d(strukturOrganisasis.get(0).toString());
                                iStrukturOrganisasiView.onDataRetrieved(strukturOrganisasis);
                            }, throwable -> Timber.e(throwable.getMessage())
                    );
        }
    }

    @Override
    public Graph createGraph(List<StrukturOrganisasiResponse> strukturOrganisasiResponses) {
        final Graph graph = new Graph();
        Node[] nodes = new Node[strukturOrganisasiResponses.size()];
        int i = 0;
        for (StrukturOrganisasiResponse strukturOrganisasiResponse : strukturOrganisasiResponses) {
            nodes[i] = new Node(strukturOrganisasiResponse);
            if (i >= 1) {
                if (strukturOrganisasiResponse.tree_level == 2) {
                    graph.addEdge(nodes[0], nodes[i]);
                }
                if (strukturOrganisasiResponse.tree_level == 3) {
                    graph.addEdge(nodes[1], nodes[i]);
                }
            }
            i++;
        }
        return graph;
    }

    @Override
    public void setAlgorithm(GraphAdapter adapter) {
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(300)
                .setSubtreeSeparation(300)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));
    }

    @Override
    public void submitEditMember(StrukturOrganisasiRequest strukturOrganisasiRequest) {
        switch (strukturOrganisasiRequest.isValidData()) {
            case 1:
                iStrukturOrganisasiView.onNpmError(context.getResources().getString(R.string.label_msg_npm_required));
                break;
            case 2:
                iStrukturOrganisasiView.onNamaError(context.getResources().getString(R.string.label_msg_nama_required));
                break;
            case RESULT_OK:
                Timber.d("Data Valid!");

                updateMemberInfo(strukturOrganisasiRequest);
                break;
        }
    }

    // to update member info
    private void updateMemberInfo(StrukturOrganisasiRequest strukturOrganisasiRequest) {
        // set tree level for implement into graph
        strukturOrganisasiRequest.setTreeLevel(getJabatanPosition(strukturOrganisasiRequest.getJabatan()));

        if (strukturOrganisasiRequest.getImageUri() != null) {
            putMemberImage(strukturOrganisasiRequest);
        } else updateMemberField(strukturOrganisasiRequest);
    }

    // to put new image of member into firebase storage
    private void putMemberImage(StrukturOrganisasiRequest strukturOrganisasiRequest) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(ARG_UKM)
                .child(Common.formatChildName(AccountHelper.getUser().ukm))
                .child(ARG_MEMBER_IMAGE)
                .child(Common.formatChildName(strukturOrganisasiRequest.getNpm() + ".png"));

        RxFirebaseStorage.putFile(storageReference, strukturOrganisasiRequest.getImageUri())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskSnapshot ->
                                taskSnapshot.getStorage().getDownloadUrl()
                                        .addOnCompleteListener(task -> {
                                            Timber.d("Download URL: %s", task.getResult());
                                            strukturOrganisasiRequest.setImageUri(task.getResult());
                                            updateMemberField(strukturOrganisasiRequest);
                                        })
                                        .addOnFailureListener(e -> {
                                            Timber.e(e.getMessage());
                                        })
                        ,
                        throwable -> Timber.e(throwable.getMessage())
                );
    }

    // to update firebase database children of ukm member
    private void updateMemberField(StrukturOrganisasiRequest strukturOrganisasiRequest) {
        Timber.d("Current Thread: %s", Thread.currentThread().getName());
        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference()
                        .child(AppConstant.ARG_STRUKTUR_ORG)
                        .child("ukm_" + Common.formatChildName(AccountHelper.getUser().ukm))
                        .child("" + strukturOrganisasiRequest.getId());

        String key = databaseReference.push().getKey();
        Timber.d("Key: %s", key);

        // still have issues on updating database fields
        RxFirebaseDatabase.setValue(databaseReference.child(FIELD_NPM), strukturOrganisasiRequest.getNpm())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("Member info updated!"),
                        throwable -> Timber.e(throwable.getMessage())
                );
        /*RxFirebaseDatabase.updateChildren(databaseReference, toMapMemberField(databaseReference, strukturOrganisasiRequest.toMap()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("Member info updated!"),
                        throwable -> Timber.e(throwable.getMessage())
                );*/
    }

    private Map<String, Object> toMapMemberField(DatabaseReference databaseReference, Map<String, Object> memberFieldMap) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(databaseReference.push().getKey(), memberFieldMap);

        return childUpdates;
    }

    // to set tree level (for graph) according to its jabatan
    private Integer getJabatanPosition(String jabatan) {
        int i = 0;
        for (String jabatanItem : context.getResources().getStringArray(R.array.label_jabatan_item)) {
            if (jabatan.equals(jabatanItem)) return i + 1;

            i++;
        }
        return null;
    }
}
