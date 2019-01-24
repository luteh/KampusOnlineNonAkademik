package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.addmemberdialog;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luteh.kampusonlinenonakademik.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Luthfan Maftuh on 24/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class AddMemberDialogHolder {
    @BindView(R.id.iv_dialog_add_member)
    ImageView iv_dialog_add_member;
    @BindView(R.id.til_dialog_add_member_npm)
    TextInputLayout til_dialog_add_member_npm;
    @BindView(R.id.til_dialog_add_member_nama)
    TextInputLayout til_dialog_add_member_nama;
    @BindView(R.id.til_dialog_add_member_no_hp)
    TextInputLayout til_dialog_add_member_no_hp;
    @BindView(R.id.et_dialog_add_member_npm)
    TextInputEditText et_dialog_add_member_npm;
    @BindView(R.id.et_dialog_add_member_nama)
    TextInputEditText et_dialog_add_member_nama;
    @BindView(R.id.et_dialog_add_member_no_hp)
    TextInputEditText et_dialog_add_member_no_hp;
    @BindView(R.id.spn_dialog_add_member_tahun)
    Spinner spn_dialog_add_member_tahun;
    @BindView(R.id.btn_dialog_add_member_done)
    Button btn_dialog_add_member_done;

    public AddMemberDialogHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
