package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberParent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Luthfan Maftuh on 23/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarMemberParentAdapter extends RecyclerView.Adapter<DaftarMemberParentHolder> {
    private List<DaftarMemberParent> daftarMemberParents;
    private OnItemClicked onItemClicked;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public DaftarMemberParentAdapter(List<DaftarMemberParent> daftarMemberParents, OnItemClicked onItemClicked) {
        this.daftarMemberParents = daftarMemberParents;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public DaftarMemberParentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaftarMemberParentHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_member_parent_item, null)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarMemberParentHolder holder, int position) {
        DaftarMemberParent parent = daftarMemberParents.get(position);

        holder.tv_daftar_member_tahun.setText(parent.tahun);
        holder.rv_daftar_member_child.setLayoutManager(
                new LinearLayoutManager(holder.rv_daftar_member_child.getContext(),
                        LinearLayout.HORIZONTAL,
                        false)
        );
        holder.rv_daftar_member_child.setAdapter(
                new DaftarMemberChildAdapter(parent.daftarMemberChildList, onItemClicked)
        );
        holder.rv_daftar_member_child.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return daftarMemberParents.size();
    }
}
