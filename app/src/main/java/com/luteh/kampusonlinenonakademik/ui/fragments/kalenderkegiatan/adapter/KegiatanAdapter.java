package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.model.kegiatan.KegiatanChild;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Luthfan Maftuh on 01/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanHolder> {
    private List<KegiatanChild> kegiatanChildren;
    private Context context;

    public KegiatanAdapter(List<KegiatanChild> kegiatanChildren) {
        this.kegiatanChildren = kegiatanChildren;
    }

    @NonNull
    @Override
    public KegiatanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new KegiatanHolder(
                LayoutInflater.from(context).inflate(R.layout.kegiatan_item, null)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull KegiatanHolder holder, int position) {
        if (kegiatanChildren != null) {
            KegiatanChild kegiatanChild = kegiatanChildren.get(position);

            holder.tv_kegiatan_jam_item.setText(String.format("%s WIB", kegiatanChild.jam_kegiatan));
            holder.tv_kegiatan_deskripsi_item.setText(kegiatanChild.deskripsi_kegiatan);
        } else {
            holder.tv_kegiatan_jam_item.setText(context.getResources().getText(R.string.label_msg_tidak_ada_kegiatan));
        }
    }

    @Override
    public int getItemCount() {
        if (kegiatanChildren != null)
            return kegiatanChildren.size();
        else
            return 1;
    }
}
