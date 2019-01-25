package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.addmemberdialog;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luteh.kampusonlinenonakademik.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Luthfan Maftuh on 24/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class AddMemberDialogHolder {
    @BindView(R.id.iv_dialog_add_member)
    public ImageView iv_dialog_add_member;
    @BindView(R.id.til_dialog_add_member_npm)
    TextInputLayout til_dialog_add_member_npm;
    @BindView(R.id.til_dialog_add_member_nama)
    TextInputLayout til_dialog_add_member_nama;
    @BindView(R.id.til_dialog_add_member_no_hp)
    TextInputLayout til_dialog_add_member_no_hp;
    @BindView(R.id.et_dialog_add_member_npm)
    public TextInputEditText et_dialog_add_member_npm;
    @BindView(R.id.et_dialog_add_member_nama)
    public TextInputEditText et_dialog_add_member_nama;
    @BindView(R.id.et_dialog_add_member_no_hp)
    public TextInputEditText et_dialog_add_member_no_hp;
    @BindView(R.id.spn_dialog_add_member_tahun)
    public Spinner spn_dialog_add_member_tahun;
    @BindView(R.id.btn_dialog_add_member_done)
    public Button btn_dialog_add_member_done;

    private Context context;
    private OnAddMemberDialogClick onAddMemberDialogClick;

    public AddMemberDialogHolder(View view, OnAddMemberDialogClick onAddMemberDialogClick) {
        ButterKnife.bind(this, view);
        context = view.getContext();
        this.onAddMemberDialogClick = onAddMemberDialogClick;
    }

    public void initSpinner(List<String> daftarMemberTahuns) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, daftarMemberTahuns) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dialog_add_member_tahun.setAdapter(adapter);
//        spn_dialog_edit_member_jabatan.setSelection(adapter.getPosition(strukturOrganisasiResponse.jabatan));
    }

    @OnClick(R.id.iv_dialog_add_member)
    void onImageClicked() {
        onAddMemberDialogClick.onImageClicked();
    }

    @OnTextChanged(R.id.et_dialog_add_member_npm)
    void onNpmTextChanged(CharSequence text) {
        if (isEmpty(text))
            et_dialog_add_member_npm.setError("NPM is required!");
    }

    @OnTextChanged(R.id.et_dialog_add_member_nama)
    void onNamaextChanged(CharSequence text) {
        if (isEmpty(text))
            et_dialog_add_member_nama.setError("NPM is required!");
    }

    @OnTextChanged(R.id.et_dialog_add_member_no_hp)
    void onNoHpTextChanged(CharSequence text) {
        if (isEmpty(text))
            et_dialog_add_member_no_hp.setError("NPM is required!");
    }


    private boolean isEmpty(CharSequence text) {
        return TextUtils.isEmpty(text);
    }
}
