package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan.dialog;

import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luteh.kampusonlinenonakademik.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Luthfan Maftuh on 02/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class AddKegiatanHolder {
    @BindView(R.id.til_dialog_add_kegiatan_tanggal)
    protected TextInputLayout til_dialog_add_kegiatan_tanggal;
    @BindView(R.id.til_dialog_add_kegiatan_jam)
    protected TextInputLayout til_dialog_add_kegiatan_jam;
    @BindView(R.id.til_dialog_add_kegiatan_deskripsi)
    protected TextInputLayout til_dialog_add_kegiatan_deskripsi;
    @BindView(R.id.et_dialog_add_kegiatan_tanggal)
    protected TextInputEditText et_dialog_add_kegiatan_tanggal;
    @BindView(R.id.et_dialog_add_kegiatan_jam)
    protected TextInputEditText et_dialog_add_kegiatan_jam;
    @BindView(R.id.et_dialog_add_kegiatan_deskripsi)
    protected TextInputEditText et_dialog_add_kegiatan_deskripsi;
    @BindView(R.id.btn_dialog_add_kegiatan)
    protected Button btn_dialog_add_kegiatan;

    private OnKegiatanDialogClick onKegiatanDialogClick;

    public AddKegiatanHolder(View view, OnKegiatanDialogClick onKegiatanDialogClick) {
        ButterKnife.bind(this, view);
        this.onKegiatanDialogClick = onKegiatanDialogClick;
    }

    @OnClick(R.id.btn_dialog_add_kegiatan)
    void onBtnAddClick() {
        onKegiatanDialogClick.OnBtnAddClicked();
    }
}
