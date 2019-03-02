package com.luteh.kampusonlinenonakademik.ui.fragments.daftarukm.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.model.daftarukm.DaftarUkm;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Luthfan Maftuh on 04/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarUkmAdapter extends RecyclerView.Adapter<DaftarUkmHolder> {

    private List<DaftarUkm> daftarUkms;
    private OnDaftarUkmItemClick onDaftarUkmItemClick;

    public DaftarUkmAdapter(List<DaftarUkm> daftarUkms, OnDaftarUkmItemClick onDaftarUkmItemClick) {
        this.daftarUkms = daftarUkms;
        this.onDaftarUkmItemClick = onDaftarUkmItemClick;
    }

    @NonNull
    @Override
    public DaftarUkmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaftarUkmHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_ukm_item, null),
                onDaftarUkmItemClick
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarUkmHolder holder, int position) {
        DaftarUkm daftarUkm = daftarUkms.get(position);
        holder.setDaftarUkm(daftarUkm);

        holder.setDaftarUkmImage(daftarUkm.logo);
        holder.setDaftarUkmTitle(daftarUkm.nama_ukm);
    }

    @Override
    public int getItemCount() {
        return daftarUkms.size();
    }
}
