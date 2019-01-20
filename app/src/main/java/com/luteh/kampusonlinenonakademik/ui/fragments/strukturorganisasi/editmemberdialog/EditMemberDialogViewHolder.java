package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.editmemberdialog;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.model.StrukturOrganisasi;
import com.squareup.picasso.Picasso;

/**
 * Created by Luthfan Maftuh on 19/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class EditMemberDialogViewHolder {
    @BindView(R.id.ll_edit_member_form_container)
    public View ll_edit_member_form_container;
    @BindView(R.id.iv_dialog_edit_member)
    public ImageView iv_dialog_edit_member;
    @BindView(R.id.til_dialog_edit_member_npm)
    public TextInputLayout til_dialog_edit_member_npm;
    @BindView(R.id.et_dialog_edit_member_npm)
    public TextInputEditText et_dialog_edit_member_npm;
    @BindView(R.id.til_dialog_edit_member_nama)
    public TextInputLayout til_dialog_edit_member_nama;
    @BindView(R.id.et_dialog_edit_member_nama)
    public TextInputEditText et_dialog_edit_member_nama;
    @BindView(R.id.spn_dialog_edit_member_jabatan)
    public Spinner spn_dialog_edit_member_jabatan;

    private StrukturOrganisasi strukturOrganisasi;
    private OnEditMemberDialogClick onEditMemberDialogClick;

    private Context context;

    private Unbinder unbinder;

    public EditMemberDialogViewHolder(View view,
                                      StrukturOrganisasi strukturOrganisasi,
                                      OnEditMemberDialogClick onEditMemberDialogClick) {
        this.context = view.getContext();
        unbinder = ButterKnife.bind(this, view);
        this.strukturOrganisasi = strukturOrganisasi;
        this.onEditMemberDialogClick = onEditMemberDialogClick;

        onInit();
    }

    private void onInit() {
        et_dialog_edit_member_npm.setText(strukturOrganisasi.npm);
        et_dialog_edit_member_nama.setText(strukturOrganisasi.nama);

        Picasso.get()
                .load(strukturOrganisasi.photo_url)
                .placeholder(R.drawable.ic_user_holo)
                .into(iv_dialog_edit_member);

        initSpinner();
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, context.getResources().getStringArray(R.array.label_jabatan_item));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dialog_edit_member_jabatan.setAdapter(adapter);

        spn_dialog_edit_member_jabatan.setSelection(adapter.getPosition(strukturOrganisasi.jabatan));
    }

    @OnClick(R.id.iv_dialog_edit_member)
    void onImageClicked() {
        onEditMemberDialogClick.onImageDialogClicked();
    }

    @OnClick(R.id.btn_dialog_edit_member_done)
    void onBtnDoneClicked(){
        onEditMemberDialogClick.onBtnDoneDialogClicked();
    }

    public void unbindView() {
        unbinder.unbind();
    }
}
