package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;


import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.StrukturOrganisasi;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter.GraphAdapter;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter.GraphViewHolder;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter.OnGraphItemClicked;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;

import java.util.List;

import butterknife.BindView;
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

    //    Edit Member Dialog Views
    @BindView(R.id.ll_edit_member_form_container)
    View ll_edit_member_form_container;
    @BindView(R.id.iv_dialog_edit_member)
    ImageView iv_dialog_edit_member;
    @BindView(R.id.til_dialog_edit_member_npm)
    TextInputLayout til_dialog_edit_member_npm;
    @BindView(R.id.et_dialog_edit_member_npm)
    TextInputEditText et_dialog_edit_member_npm;
    @BindView(R.id.til_dialog_edit_member_nama)
    TextInputLayout til_dialog_edit_member_nama;
    @BindView(R.id.et_dialog_edit_member_nama)
    TextInputEditText et_dialog_edit_member_nama;
    @BindView(R.id.spn_dialog_edit_member_jabatan)
    Spinner spn_dialog_edit_member_jabatan;

    private SlideUp slideUp;

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

        initSlideUp();

    }

    private void initSlideUp() {
        slideUp = new SlideUpBuilder(ll_edit_member_form_container)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent >= 20) rl_struktur_org_container.setAlpha(percent / 100);
                        Timber.d("Percent: %f", percent);
                       /* if (fab.isShown() && percent < 100) {
                            fab.hide();
                        }*/
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        /*if (visibility == View.VISIBLE) {
                            graph_struktur_org.setHasClickableChildren(false);
                        }
                        if (visibility == View.GONE) {
                            graph_struktur_org.setHasClickableChildren(true);
                        }*/
                    }
                })
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withGesturesEnabled(true)
//                .withSlideFromOtherView(findViewById(R.id.rootView))
                .build();
    }

    private void setupAdapter(Graph graph, List<StrukturOrganisasi> strukturOrganisasis) {
        adapter = new GraphAdapter(getContext(), R.layout.node_struktur_organisasi, graph, strukturOrganisasis, this);


        iStrukturOrganisasiPresenter.setAlgorithm(adapter);

        graph_struktur_org.setAdapter(adapter);
        /*graph_struktur_org.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Common.showToastMessage(getContext(), "Clicker on position " + position);
                Timber.d("Position: %d", position);
            }
        });*/

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
        et_dialog_edit_member_npm.setText(strukturOrganisasi.npm);

        slideUp.show();
    }
}
