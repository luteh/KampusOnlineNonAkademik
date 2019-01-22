package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.strukturorganisasi.StrukturOrganisasiRequest;
import com.luteh.kampusonlinenonakademik.model.strukturorganisasi.StrukturOrganisasiResponse;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter.GraphAdapter;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter.GraphViewHolder;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter.OnGraphItemClicked;

import java.util.List;

import butterknife.BindView;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.editmemberdialog.EditMemberDialogViewHolder;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.editmemberdialog.OnEditMemberDialogClick;
import com.theartofdev.edmodo.cropper.CropImage;
import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class StrukturOrganisasiFragment extends BaseFragment implements
        IStrukturOrganisasiView,
        OnGraphItemClicked,
        OnEditMemberDialogClick {

    @BindView(R.id.graph_struktur_org)
    GraphView graph_struktur_org;
    @BindView(R.id.fab_struktur_org_add_node)
    FloatingActionButton fab_struktur_org_add_node;
    @BindView(R.id.ll_progress_bar_container)
    LinearLayout ll_progress_bar_container;
    @BindView(R.id.rl_struktur_org_container)
    RelativeLayout rl_struktur_org_container;

    private EditMemberDialogViewHolder editMemberDialogHolder;
    private AlertDialog editMemberDialog;

    protected BaseGraphAdapter<GraphViewHolder> adapter;

    private IStrukturOrganisasiPresenter iStrukturOrganisasiPresenter;

    private String mUriImageDialog;

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

    private void setupAdapter(Graph graph, List<StrukturOrganisasiResponse> strukturOrganisasiResponses) {
        adapter = new GraphAdapter(getContext(), R.layout.node_struktur_organisasi, graph, strukturOrganisasiResponses, this);

        iStrukturOrganisasiPresenter.setAlgorithm(adapter);

        graph_struktur_org.setAdapter(adapter);
    }

    private void showEditMemberDialog(int position, StrukturOrganisasiResponse strukturOrganisasiResponse) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_member, null);
        editMemberDialogHolder = new EditMemberDialogViewHolder(view, position, strukturOrganisasiResponse, this);

        editMemberDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();

        editMemberDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        editMemberDialog.show();
//        editMemberDialog.getWindow().getAttributes().windowAnimations = R.style.pdlg_default_animation;
    }

    @Override
    public void onDataRetrieved(List<StrukturOrganisasiResponse> strukturOrganisasiResponses) {

        Graph graph = iStrukturOrganisasiPresenter.createGraph(strukturOrganisasiResponses);

        setupAdapter(graph, strukturOrganisasiResponses);

        ll_progress_bar_container.setVisibility(View.INVISIBLE);
        rl_struktur_org_container.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNamaError(String message) {
        editMemberDialogHolder.til_dialog_edit_member_nama.setError(message);
        getBaseActivity().showSoftKeyboard(editMemberDialogHolder.et_dialog_edit_member_nama);
//        Common.dismissSpotsProgress();
    }

    @Override
    public void onNpmError(String message) {
        editMemberDialogHolder.til_dialog_edit_member_npm.setError(message);
        getBaseActivity().showSoftKeyboard(editMemberDialogHolder.et_dialog_edit_member_npm);
//        Common.dismissSpotsProgress();
    }

    @Override
    public void clearError() {
        editMemberDialogHolder.til_dialog_edit_member_nama.setError(null);
        editMemberDialogHolder.til_dialog_edit_member_npm.setError(null);
    }

    @Override
    public void onDataIsSame() {
        editMemberDialog.dismiss();
    }

    @Override
    public void onStrukturOrganisasiDataUpdated() {
        editMemberDialog.dismiss();
        Common.showSuccessMessage(context, getString(R.string.label_msg_update_member_success));

        iStrukturOrganisasiPresenter.retrieveStrukturOrganisasiData();

        ll_progress_bar_container.setVisibility(View.VISIBLE);
        rl_struktur_org_container.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onUpdateStrukturOrganisasiFailed(String message) {
        editMemberDialog.dismiss();
        Common.showErrorMessage(context, message);
    }

    @Override
    public void onItemClicked(StrukturOrganisasiResponse strukturOrganisasiResponse, int position) {
        Common.showToastMessage(getContext(), "Clicker on position " + position);
        Timber.d("Info: %s", strukturOrganisasiResponse.toString());

        showEditMemberDialog(position, strukturOrganisasiResponse);
    }

    @Override
    public void onImageDialogClicked() {
        if (!Common.isPermissionGranted(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE))
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
        else
            getBaseActivity().startPickImage();
    }

    @Override
    public void onBtnDoneDialogClicked() {
        iStrukturOrganisasiPresenter.submitEditMember(
                new StrukturOrganisasiRequest(editMemberDialogHolder.getPosition(),
                        mUriImageDialog,
                        editMemberDialogHolder.et_dialog_edit_member_npm.getText().toString(),
                        editMemberDialogHolder.et_dialog_edit_member_nama.getText().toString(),
                        editMemberDialogHolder.spn_dialog_edit_member_jabatan.getSelectedItem().toString()),
                editMemberDialogHolder.strukturOrganisasiResponse
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getBaseActivity().startPickImage();
            } else {
                Toast.makeText(getContext(), "Cancelling, required permissions are not granted", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("Request Code: %d , Result Code: %d", requestCode, resultCode);
        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Timber.d("Image Crop Uri: %s", result.getUri());
                editMemberDialogHolder.iv_dialog_edit_member.setImageURI(result.getUri());

                mUriImageDialog = result.getUri().toString();

                Toast.makeText(
                        getContext(), "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        editMemberDialogHolder.unbindView();
    }
}
