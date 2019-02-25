package com.luteh.kampusonlinenonakademik.ui.activities.allberita.dialog;

import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luteh.kampusonlinenonakademik.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Luthfan Maftuh on 25/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class AllBeritaDialogHolder {
    @BindView(R.id.til_dialog_add_berita_judul)
    public TextInputLayout til_dialog_add_berita_judul;
    @BindView(R.id.til_dialog_add_berita_image)
    public TextInputLayout til_dialog_add_berita_image;
    @BindView(R.id.til_dialog_add_berita_deskripsi)
    public TextInputLayout til_dialog_add_berita_deskripsi;
    @BindView(R.id.et_dialog_add_berita_judul)
    public TextInputEditText et_dialog_add_berita_judul;
    @BindView(R.id.et_dialog_add_berita_image)
    public TextInputEditText et_dialog_add_berita_image;
    @BindView(R.id.et_dialog_add_berita_deskripsi)
    public TextInputEditText et_dialog_add_berita_deskripsi;
    @BindView(R.id.btn_dialog_add_berita)
    public Button btn_dialog_add_berita;
    @BindView(R.id.btn_dialog_cancel_berita)
    public Button btn_dialog_cancel_berita;

    private View view;
    private IAllBeritaDialog iAllBeritaDialog;

    public AllBeritaDialogHolder(View view, IAllBeritaDialog iAllBeritaDialog) {
        ButterKnife.bind(this, view);
        this.view = view;
        this.iAllBeritaDialog = iAllBeritaDialog;
    }

    @OnClick(R.id.et_dialog_add_berita_image)
    void onClickImageBerita() {
        iAllBeritaDialog.onClickImageEditText();
    }

    @OnClick(R.id.btn_dialog_add_berita)
    void onClickAddBerita() {
        iAllBeritaDialog.onClickAddButton();
    }

    @OnClick(R.id.btn_dialog_cancel_berita)
    void onClickCancelBerita() {
        iAllBeritaDialog.onClickCancelButton();
    }
}
