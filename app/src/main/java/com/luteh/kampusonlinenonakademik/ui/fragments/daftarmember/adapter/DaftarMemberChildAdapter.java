package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberChild;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Luthfan Maftuh on 23/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarMemberChildAdapter extends RecyclerView.Adapter<DaftarMemberChildHolder> {
    private List<DaftarMemberChild> daftarMemberChildren;

    public DaftarMemberChildAdapter(List<DaftarMemberChild> daftarMemberChildren) {
        this.daftarMemberChildren = daftarMemberChildren;
    }

    @NonNull
    @Override
    public DaftarMemberChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaftarMemberChildHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_member_child_item, null)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarMemberChildHolder holder, int position) {
        DaftarMemberChild child = daftarMemberChildren.get(position);

        Picasso.get()
                .load(child.photo_url)
                .into(holder.iv_daftar_member_child);

        holder.tv_daftar_member_child_npm.setText(child.npm);
        holder.tv_daftar_member_child_nama.setText(child.nama);
        holder.tv_daftar_member_child_no_hp.setText(child.no_hp);
    }

    @Override
    public int getItemCount() {
        return daftarMemberChildren.size();
    }
}
