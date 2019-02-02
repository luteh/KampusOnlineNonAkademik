package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Luthfan Maftuh on 02/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class AddKegiatanHolder implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        View.OnLayoutChangeListener {

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

    private View view;
    private OnKegiatanDialogClick onKegiatanDialogClick;

    private Calendar myCalendar = Calendar.getInstance();

    public AddKegiatanHolder(View view, OnKegiatanDialogClick onKegiatanDialogClick) {
        ButterKnife.bind(this, view);
        this.view = view;
        this.onKegiatanDialogClick = onKegiatanDialogClick;

        view.addOnLayoutChangeListener(this);
    }

    @OnClick(R.id.btn_dialog_add_kegiatan)
    void onBtnAddClick() {
        onKegiatanDialogClick.OnBtnAddClicked();
    }

    @OnClick(R.id.et_dialog_add_kegiatan_tanggal)
    void onEtTanggalClick() {
        new DatePickerDialog(view.getContext(),
                this,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    @OnClick(R.id.et_dialog_add_kegiatan_jam)
    void onEtJamClick() {
        new TimePickerDialog(view.getContext(),
                this,
                0,
                0,
                true)
                .show();
    }

    @OnTextChanged(R.id.et_dialog_add_kegiatan_deskripsi)
    void onEtDeskripsiTextChanged(CharSequence text) {
        if (TextUtils.isEmpty(text))
            et_dialog_add_kegiatan_deskripsi.setError(view.getContext().getResources().getString(R.string.label_msg_deskripsi_required));
    }

    //    set Date to Tanggal EditText if date has changed
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        et_dialog_add_kegiatan_tanggal.setText(Common.convertDateToString(myCalendar.getTime()));
    }

    //    set Time to Jam EditText if time has changed
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);

        et_dialog_add_kegiatan_jam.setText(Common.convertTimeToString(myCalendar.getTime()));
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (TextUtils.isEmpty(et_dialog_add_kegiatan_jam.getText()) ||
                TextUtils.isEmpty(et_dialog_add_kegiatan_tanggal.getText()) ||
                TextUtils.isEmpty(et_dialog_add_kegiatan_deskripsi.getText())) {
            if (btn_dialog_add_kegiatan.isEnabled())
                btn_dialog_add_kegiatan.setEnabled(false);
        } else {
            if (!btn_dialog_add_kegiatan.isEnabled())
                btn_dialog_add_kegiatan.setEnabled(true);
        }
    }
}
