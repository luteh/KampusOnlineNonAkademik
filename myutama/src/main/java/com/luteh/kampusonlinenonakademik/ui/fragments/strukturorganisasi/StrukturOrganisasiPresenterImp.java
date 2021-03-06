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

import java.lang.reflect.Array;
import java.util.*;

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

/**
 * Created by Luthfan Maftuh on 15/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class StrukturOrganisasiPresenterImp implements IStrukturOrganisasiPresenter {

    private Context context;
    private IStrukturOrganisasiView iStrukturOrganisasiView;

    private StrukturOrganisasiResponse strukturOrganisasiResponse;
    private StrukturOrganisasiRequest strukturOrganisasiRequest;

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
//        Collections.sort(strukturOrganisasiResponses, (o1, o2) -> o1.tree_level.compareTo(o2.tree_level));

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
    public void submitEditMember(StrukturOrganisasiRequest strukturOrganisasiRequest,
                                 StrukturOrganisasiResponse strukturOrganisasiResponse) {
        this.strukturOrganisasiResponse = strukturOrganisasiResponse;
        this.strukturOrganisasiRequest = strukturOrganisasiRequest;

        iStrukturOrganisasiView.clearError();

        switch (strukturOrganisasiRequest.isValidData()) {
            case 1:
                iStrukturOrganisasiView.onNpmError(context.getResources().getString(R.string.label_msg_npm_required));
                break;
            case 2:
                iStrukturOrganisasiView.onNamaError(context.getResources().getString(R.string.label_msg_nama_required));
                break;
            case RESULT_OK:
                Timber.d("Data Valid!");

                updateMemberInfo();
                break;
        }
    }

    // to update member info
    private void updateMemberInfo() {
        // set tree level for implement into graph
        strukturOrganisasiRequest.setTree_level(getJabatanPosition(strukturOrganisasiRequest.getJabatan()));

        if (strukturOrganisasiRequest.getPhoto_url() != null) {
            putMemberImage();
        } else {
            strukturOrganisasiRequest.setPhoto_url(strukturOrganisasiResponse.photo_url);
            if (isTheDataSame())
                iStrukturOrganisasiView.onDataIsSame();
            else
                updateMemberField();
        }
    }

    //to check if new data is same with old data
    private boolean isTheDataSame() {
        return strukturOrganisasiRequest.toString().equals(strukturOrganisasiResponse.toString());
    }

    // to put new image of member into firebase storage
    private void putMemberImage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(ARG_UKM)
                .child(Common.formatChildName(AccountHelper.getUser().ukm))
                .child(ARG_MEMBER_IMAGE)
                .child(Common.formatChildName(strukturOrganisasiRequest.getNpm() + ".png"));

        RxFirebaseStorage.putFile(storageReference, Uri.parse(strukturOrganisasiRequest.getPhoto_url()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskSnapshot ->
                                taskSnapshot.getStorage().getDownloadUrl()
                                        .addOnCompleteListener(task -> {
                                            Timber.d("Download URL: %s", task.getResult());
                                            strukturOrganisasiRequest.setPhoto_url(task.getResult().toString());
                                            updateMemberField();
                                        })
                                        .addOnFailureListener(e -> {
                                            Timber.e(e.getMessage());
                                            iStrukturOrganisasiView.onUpdateStrukturOrganisasiFailed(e.getMessage());
                                        })
                        ,
                        throwable -> {
                            Timber.e(throwable.getMessage());
                            iStrukturOrganisasiView.onUpdateStrukturOrganisasiFailed(throwable.getMessage());
                        }
                );
    }

    // to update firebase database children of ukm member
    private void updateMemberField() {
        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference()
                        .child(AppConstant.ARG_STRUKTUR_ORG)
                        .child("ukm_" + Common.formatChildName(AccountHelper.getUser().ukm))
                        .child("" + strukturOrganisasiRequest.getId());

        // still have issues on updating database fields
        RxFirebaseDatabase.setValue(databaseReference, strukturOrganisasiRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            Timber.d("Member info updated!");
                            iStrukturOrganisasiView.onStrukturOrganisasiDataUpdated();
                        },
                        throwable -> {
                            Timber.e(throwable.getMessage());
                            iStrukturOrganisasiView.onUpdateStrukturOrganisasiFailed(throwable.getMessage());
                        }
                );
    }

    // to set tree level (for graph) according to its jabatan
    private Integer getJabatanPosition(String jabatan) {
        switch (jabatan.toLowerCase()) {
            case "ketua":
                return 1;
            case "wakil ketua":
                return 2;
            case "bendahara":
                return 3;
            case "sekretaris":
                return 3;
            default:
                return null;
        }
        /*int i = 0;
        for (String jabatanItem : context.getResources().getStringArray(R.array.label_jabatan_item)) {
            if (jabatan.equals(jabatanItem)) return i + 1;

            i++;
        }
        return null;*/
    }
}
