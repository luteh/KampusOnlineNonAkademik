package com.luteh.kampusonlinenonakademik.ui.activities.allberita;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseActivity;
import com.luteh.kampusonlinenonakademik.common.utils.CustomDividerItemDecoration;
import com.luteh.kampusonlinenonakademik.model.home.News;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.adapter.AllBeritaAdapter;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.adapter.IAllBeritaAdapter;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.dialog.AllBeritaDialogHolder;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.dialog.IAllBeritaDialog;
import com.luteh.kampusonlinenonakademik.ui.activities.berita.BeritaActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.KEY_DETAIL_BERITA;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.KEY_LIST_BERITA;

public class AllBeritaActivity extends BaseActivity implements IAllBeritaAdapter,
        IAllBeritaDialog,
        IAllBeritaView {

    @BindView(R.id.rv_allberita)
    RecyclerView rv_allberita;
    @BindView(R.id.fab_allberita_add)
    FloatingActionButton fab_allberita_add;

    private IAllBeritaPresenter iAllBeritaPresenter;

    private ArrayList<News> newsArrayList;

    private AllBeritaDialogHolder allBeritaDialogHolder;
    private AlertDialog allBeritaDialog;
    private String mUriImageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_berita);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iAllBeritaPresenter = new AllBeritaPresenterImp(this);

        newsArrayList = this.getIntent().getParcelableArrayListExtra(KEY_LIST_BERITA);

        Timber.d(newsArrayList.get(1).toString());

        initView();
        initRecyclerView();
    }

    private void initView() {
        if (!Common.isAdmin())
            fab_allberita_add.setVisibility(View.INVISIBLE);
        else
            fab_allberita_add.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        rv_allberita.setHasFixedSize(true);
        rv_allberita.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        );
        rv_allberita.addItemDecoration(new CustomDividerItemDecoration(this, R.color.colorBackground, 1));

        rv_allberita.setAdapter(new AllBeritaAdapter(newsArrayList, this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finishToRight();
        return true;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finishToRight();
    }

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DETAIL_BERITA, newsArrayList.get(position));

        startActivityFromRight(BeritaActivity.class, bundle);
    }

    @OnClick(R.id.fab_allberita_add)
    void onClickFabAllBerita() {
        showAllBeritaDialog();
    }

    private void showAllBeritaDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_berita, null);
        allBeritaDialogHolder = new AllBeritaDialogHolder(view, this);

//        view.addOnLayoutChangeListener(this);

//        iDaftarMemberPresenter.retrieveTahunAngkatanData();

        allBeritaDialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                .setView(view)
                .setTitle(getResources().getString(R.string.label_tambah_berita))
                .setCancelable(false)
                .create();

        allBeritaDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        allBeritaDialog.show();
    }

    @Override
    public void onClickAddButton() {
        iAllBeritaPresenter.submitNewBerita(
                allBeritaDialogHolder.et_dialog_add_berita_judul.getText().toString(),
                mUriImageDialog,
                allBeritaDialogHolder.et_dialog_add_berita_deskripsi.getText().toString(),
//                Common.convertDateToString(Calendar.getInstance().getTime()),
                Calendar.getInstance().getTime().toString(),
                "UKM " + AccountHelper.getUser().ukm
        );
    }

    @Override
    public void onClickCancelButton() {
        allBeritaDialog.dismiss();
    }

    @Override
    public void onClickImageEditText() {
        openImagePicker(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        mUriImageDialog = uri.toString();
        allBeritaDialogHolder.et_dialog_add_berita_image.setText(uri.getLastPathSegment());

        Toast.makeText(getContext(), "Picking image successful", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onEmptyData(int emptyCode) {
        allBeritaDialogHolder.clearError();

        switch (emptyCode) {
            case 0:
                allBeritaDialogHolder.et_dialog_add_berita_judul.setError(getResources().getString(R.string.label_msg_judul_required));
                break;
            case 1:
                allBeritaDialogHolder.et_dialog_add_berita_image.setError(getResources().getString(R.string.label_msg_image_required));
                break;
            case 2:
                allBeritaDialogHolder.et_dialog_add_berita_deskripsi.setError(getResources().getString(R.string.label_msg_deskripsi_required));
                break;
            case 3 | 4:
                Toast.makeText(this, getResources().getString(R.string.label_msg_add_berita_failed), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onSuccessSubmitNewData(News news) {
        /*allBeritaDialog.dismiss();

        newsArrayList.add(news);
        Collections.sort(newsArrayList, (o1, o2) -> o2.tanggal_berita.compareTo(o1.tanggal_berita));

        rv_allberita.getAdapter().notifyDataSetChanged();*/

        Common.showSuccessMessage(this, getResources().getString(R.string.label_msg_add_berita_success));
    }

    @Override
    public void onListChange(News news) {
        allBeritaDialog.dismiss();

        newsArrayList.add(news);
        Collections.sort(newsArrayList, (o1, o2) -> o2.tanggal_berita.compareTo(o1.tanggal_berita));

        rv_allberita.getAdapter().notifyDataSetChanged();
    }
}
