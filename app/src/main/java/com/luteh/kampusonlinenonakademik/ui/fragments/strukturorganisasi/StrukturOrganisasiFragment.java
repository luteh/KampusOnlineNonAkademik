package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.StrukturOrganisasi;

import java.util.List;

import butterknife.BindView;
import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StrukturOrganisasiFragment extends BaseFragment implements IStrukturOrganisasiView {

    @BindView(R.id.graph_struktur_org)
    GraphView graph_struktur_org;
    @BindView(R.id.fab_struktur_org_add_node)
    FloatingActionButton fab_struktur_org_add_node;
    @BindView(R.id.ll_progress_bar_container)
    LinearLayout ll_progress_bar_container;
    @BindView(R.id.rl_struktur_org_container)
    RelativeLayout rl_struktur_org_container;

    protected BaseGraphAdapter<GraphViewHolder> adapter;

    private IStrukturOrganisasiPresenter iStrukturOrganisasiPresenter;

    public StrukturOrganisasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_struktur_organisasi, container, false);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iStrukturOrganisasiPresenter = new StrukturOrganisasiPresenterImp(getContext(), this);

        iStrukturOrganisasiPresenter.retrieveStrukturOrganisasiData();

        ll_progress_bar_container.setVisibility(View.VISIBLE);
        rl_struktur_org_container.setVisibility(View.INVISIBLE);

    }

    private void setupAdapter(Graph graph, List<StrukturOrganisasi> strukturOrganisasis) {
        adapter = new GraphAdapter(getContext(), R.layout.node_struktur_organisasi, graph, strukturOrganisasis);

        iStrukturOrganisasiPresenter.setAlgorithm(adapter);

        graph_struktur_org.setAdapter(adapter);
        graph_struktur_org.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Common.showToastMessage(getContext(), "Clicker on position " + position);
            }
        });
    }

    @Override
    public void onDataRetrieved(List<StrukturOrganisasi> strukturOrganisasis) {

        Graph graph = iStrukturOrganisasiPresenter.createGraph(strukturOrganisasis);

        setupAdapter(graph, strukturOrganisasis);

        ll_progress_bar_container.setVisibility(View.INVISIBLE);
        rl_struktur_org_container.setVisibility(View.VISIBLE);
    }
}
