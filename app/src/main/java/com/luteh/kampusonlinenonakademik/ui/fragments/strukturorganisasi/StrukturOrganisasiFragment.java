package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.StrukturOrganisasi;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter.GraphAdapter;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter.GraphViewHolder;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter.OnGraphItemClicked;

import java.util.List;

import butterknife.BindView;
import com.squareup.picasso.Picasso;
import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class StrukturOrganisasiFragment extends BaseFragment implements
        IStrukturOrganisasiView,
        OnGraphItemClicked {

    @BindView(R.id.graph_struktur_org)
    GraphView graph_struktur_org;
    @BindView(R.id.fab_struktur_org_add_node)
    FloatingActionButton fab_struktur_org_add_node;
    @BindView(R.id.ll_progress_bar_container)
    LinearLayout ll_progress_bar_container;
    @BindView(R.id.rl_struktur_org_container)
    RelativeLayout rl_struktur_org_container;

    private EditMemberDialogViewHolder dialogHolder;

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

    private void initSpinner(StrukturOrganisasi strukturOrganisasi) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.label_jabatan_item));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogHolder.spn_dialog_edit_member_jabatan.setAdapter(adapter);

        dialogHolder.spn_dialog_edit_member_jabatan.setSelection(adapter.getPosition(strukturOrganisasi.jabatan));
    }

    private void setupAdapter(Graph graph, List<StrukturOrganisasi> strukturOrganisasis) {
        adapter = new GraphAdapter(getContext(), R.layout.node_struktur_organisasi, graph, strukturOrganisasis, this);

        iStrukturOrganisasiPresenter.setAlgorithm(adapter);

        graph_struktur_org.setAdapter(adapter);
    }

    @Override
    public void onDataRetrieved(List<StrukturOrganisasi> strukturOrganisasis) {

        Graph graph = iStrukturOrganisasiPresenter.createGraph(strukturOrganisasis);

        setupAdapter(graph, strukturOrganisasis);

        ll_progress_bar_container.setVisibility(View.INVISIBLE);
        rl_struktur_org_container.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClicked(StrukturOrganisasi strukturOrganisasi, int position) {
        Common.showToastMessage(getContext(), "Clicker on position " + position);
        Timber.d("Info: %s", strukturOrganisasi.toString());

        showEditMemberDialog(strukturOrganisasi);
    }

    private void showEditMemberDialog(StrukturOrganisasi strukturOrganisasi) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_member, null);
        dialogHolder = new EditMemberDialogViewHolder(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        initSpinner(strukturOrganisasi);

        dialogHolder.et_dialog_edit_member_npm.setText(strukturOrganisasi.npm);
        dialogHolder.et_dialog_edit_member_nama.setText(strukturOrganisasi.nama);

        Picasso.get()
                .load(strukturOrganisasi.photo_url)
                .placeholder(R.drawable.ic_user_holo)
                .into(dialogHolder.iv_dialog_edit_member);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.pdlg_default_animation;
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        dialogHolder.unbindView();
    }
}
