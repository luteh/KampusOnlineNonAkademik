package com.luteh.kampusonlinenonakademik.ui.activities.allberita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.home.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Luthfan Maftuh on 24/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class AllBeritaAdapter extends RecyclerView.Adapter<AllBeritaView> {

    private Context context;
    private ArrayList<News> newsArrayList;
    private IAllBeritaAdapter iAllBeritaAdapter;

    public AllBeritaAdapter(ArrayList<News> newsArrayList, IAllBeritaAdapter iAllBeritaAdapter) {
        this.newsArrayList = newsArrayList;
        this.iAllBeritaAdapter = iAllBeritaAdapter;
    }

    @NonNull
    @Override
    public AllBeritaView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new AllBeritaView(
                LayoutInflater.from(context).inflate(R.layout.allberita_item, null),
                iAllBeritaAdapter
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AllBeritaView holder, int position) {
        News news = newsArrayList.get(position);

        holder.tv_allberita_item_judul.setText(news.judul);
        holder.tv_allberita_item_post_by.setText(news.post_by);
        holder.tv_allberita_item_tanggal.setText(Common.getStringDateRange(news.tanggal_berita));

        Picasso.with(context)
                .load(news.image_url)
                .into(holder.iv_allberita_item);
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }
}
