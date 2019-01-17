package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.AppConstant;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.StrukturOrganisasi;

import java.util.ArrayList;
import java.util.List;

import de.blox.graphview.Graph;
import de.blox.graphview.GraphAdapter;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
                        List<StrukturOrganisasi> strukturOrganisasiList = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            strukturOrganisasiList.add(postSnapshot.getValue(StrukturOrganisasi.class));
                        }
                        return strukturOrganisasiList;
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
    public Graph createGraph(List<StrukturOrganisasi> strukturOrganisasis) {
        final Graph graph = new Graph();
        Node[] nodes = new Node[strukturOrganisasis.size()];
        int i = 0;
        for (StrukturOrganisasi strukturOrganisasi : strukturOrganisasis) {
            nodes[i] = new Node(strukturOrganisasi);
            if (i >= 1) {
                if (strukturOrganisasi.tree_level == 2) {
                    graph.addEdge(nodes[0], nodes[i]);
                }
                if (strukturOrganisasi.tree_level == 3) {
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
}
