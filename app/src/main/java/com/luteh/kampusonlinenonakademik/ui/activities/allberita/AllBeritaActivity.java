package com.luteh.kampusonlinenonakademik.ui.activities.allberita;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseActivity;
import com.luteh.kampusonlinenonakademik.common.utils.CustomDividerItemDecoration;
import com.luteh.kampusonlinenonakademik.model.home.News;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.adapter.AllBeritaAdapter;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.adapter.IAllBeritaAdapter;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.dialog.AllBeritaDialogHolder;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.dialog.IAllBeritaDialog;
import com.luteh.kampusonlinenonakademik.ui.activities.berita.BeritaActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.KEY_DETAIL_BERITA;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.KEY_LIST_BERITA;

public class AllBeritaActivity extends BaseActivity implements IAllBeritaAdapter,
        IAllBeritaDialog {

    @BindView(R.id.rv_allberita)
    RecyclerView rv_allberita;
    @BindView(R.id.fab_allberita_add)
    FloatingActionButton fab_allberita_add;

    private ArrayList<News> newsArrayList;

    private AllBeritaDialogHolder allBeritaDialogHolder;
    private AlertDialog allBeritaDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_berita);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onInit() {
        super.onInit();

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

    }

    @Override
    public void onClickCancelButton() {
        allBeritaDialog.dismiss();
    }

    @Override
    public void onClickImageEditText() {

    }
}
