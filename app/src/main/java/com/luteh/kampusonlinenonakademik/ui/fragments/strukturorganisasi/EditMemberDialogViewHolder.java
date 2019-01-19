package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;

import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luteh.kampusonlinenonakademik.R;

/**
 * Created by Luthfan Maftuh on 19/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class EditMemberDialogViewHolder {
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

    private Unbinder unbinder;

    public EditMemberDialogViewHolder(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    public void unbindView(){
        unbinder.unbind();
    }
}
