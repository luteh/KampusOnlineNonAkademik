package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberChild;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberParent;
import com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.adapter.DaftarMemberParentAdapter;
import com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.adapter.OnItemClicked;
import com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.addmemberdialog.AddMemberDialogHolder;
import com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.addmemberdialog.OnAddMemberDialogClick;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.luteh.kampusonlinenonakademik.common.Common.isEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarMemberFragment extends BaseFragment implements IDaftarFragmentView,
        OnItemClicked,
        OnAddMemberDialogClick,
        View.OnLayoutChangeListener {
    @BindView(R.id.tv_daftar_member_header)
    TextView tv_daftar_member_header;
    @BindView(R.id.rv_daftar_member_parent)
    RecyclerView rv_daftar_member_parent;
    @BindView(R.id.fab_daftar_member_add)
    FloatingActionButton fab_daftar_member_add;
    @BindView(R.id.rl_daftar_member_container)
    RelativeLayout rl_daftar_member_container;
    @BindView(R.id.ll_progress_bar_container)
    LinearLayout ll_progress_bar_container;

    private IDaftarMemberPresenter iDaftarMemberPresenter;

    private AlertDialog addMemberDialog;
    private AddMemberDialogHolder addMemberHolder;
    private String mUriImageDialog;

    public DaftarMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daftar_member, container, false);
    }

    @Override
    protected void onInit() {
        super.onInit();

        initView();

        tv_daftar_member_header.setText(String.format("%s Member", AccountHelper.getUser().ukm));

        iDaftarMemberPresenter = new DaftarMemberPresenterImp(this);

        iDaftarMemberPresenter.retrieveDaftarMemberData();

        onLoadingStarted();
    }

    private void initView() {
        if (Common.isAdmin())
            fab_daftar_member_add.setVisibility(View.VISIBLE);
        else
            fab_daftar_member_add.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRetrieveDataSuccessed(List<DaftarMemberParent> daftarMemberParents) {
        onLoadingFinished();
        initRecycler(daftarMemberParents);

    }

    @Override
    public void onRetrieveTahunAngkatanDataSuccessed(List<String> daftarMemberTahuns) {
        addMemberHolder.initSpinner(daftarMemberTahuns);
    }

    @Override
    public void onAddNewDataSuccessfully() {
//        onLoadingFinished();
        iDaftarMemberPresenter.retrieveDaftarMemberData();
        Common.showSuccessMessage(context, "Add new Data Success");
    }

    @Override
    public void onAddNewDataFailed(String message) {
        onLoadingFinished();

        Common.showErrorMessage(context, message);
    }

    private void initRecycler(List<DaftarMemberParent> daftarMemberParents) {
        rv_daftar_member_parent.setLayoutManager(
                new LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        );
        rv_daftar_member_parent.setAdapter(new DaftarMemberParentAdapter(daftarMemberParents, this));
    }

    @OnClick(R.id.fab_daftar_member_add)
    void onFabClick() {
        showAddMemberDialog();
    }

    private void showAddMemberDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_member, null);
        addMemberHolder = new AddMemberDialogHolder(view, this);

        view.addOnLayoutChangeListener(this);

        iDaftarMemberPresenter.retrieveTahunAngkatanData();

        addMemberDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();

        addMemberDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        addMemberDialog.show();
//        editMemberDialog.getWindow().getAttributes().windowAnimations = R.style.pdlg_default_animation;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (addMemberHolder != null) {
            if (mUriImageDialog != null &&
                    !isEmpty(addMemberHolder.et_dialog_add_member_npm.getText()) &&
                    !isEmpty(addMemberHolder.et_dialog_add_member_nama.getText()) &&
                    !isEmpty(addMemberHolder.et_dialog_add_member_no_hp.getText()) &&
                    addMemberHolder.spn_dialog_add_member_tahun.getSelectedItemPosition() != 0) {
                addMemberHolder.btn_dialog_add_member_done.setEnabled(true);
            } else {
                if (addMemberHolder.btn_dialog_add_member_done.isEnabled())
                    addMemberHolder.btn_dialog_add_member_done.setEnabled(false);
            }
        }
    }

    @Override
    public void onChildItemClicked(View view, DaftarMemberChild daftarMemberChild, int position) {
        /*if (Common.isAdmin()) {
            showMenuItem(view);
        }*/
    }

    private void showMenuItem(View view) {
        PopupMenu menu = new PopupMenu(context, view);

        menu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.item_edit:
                    Common.showToastMessage(context, "Edit Clicked");
                    break;
                case R.id.item_delete:
                    Common.showToastMessage(context, "Delete Clicked");
                    break;
            }
            return true;
        });
        menu.inflate(R.menu.menu_item);
        menu.show();
    }

    private void onLoadingStarted() {
        if (rl_daftar_member_container.isShown()) {
            rl_daftar_member_container.setVisibility(View.INVISIBLE);
            ll_progress_bar_container.setVisibility(View.VISIBLE);
        }
    }

    private void onLoadingFinished() {
        if (!rl_daftar_member_container.isShown()) {
            rl_daftar_member_container.setVisibility(View.VISIBLE);
            ll_progress_bar_container.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onImageClicked() {
        getBaseActivity().openImagePicker(true);
    }

    @Override
    public void onBtnAddClicked() {
        addMemberDialog.dismiss();
        onLoadingStarted();

        iDaftarMemberPresenter.submitMemberData(mUriImageDialog,
                addMemberHolder.et_dialog_add_member_npm.getText().toString(),
                addMemberHolder.et_dialog_add_member_nama.getText().toString(),
                addMemberHolder.et_dialog_add_member_no_hp.getText().toString(),
                addMemberHolder.spn_dialog_add_member_tahun.getSelectedItem().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                handleImagePickerResult(result.getUri());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void handleImagePickerResult(Uri uri) {
        addMemberHolder.iv_dialog_add_member.setImageURI(uri);

        mUriImageDialog = uri.toString();

        Toast.makeText(getContext(), "Picking image successful", Toast.LENGTH_LONG)
                .show();
    }


}
