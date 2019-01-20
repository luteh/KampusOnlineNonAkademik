package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.model.strukturorganisasi.StrukturOrganisasiResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;

/**
 * Created by Luthfan Maftuh on 17/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class GraphAdapter extends BaseGraphAdapter<GraphViewHolder> {

    private List<StrukturOrganisasiResponse> strukturOrganisasiResponses;
    private OnGraphItemClicked onGraphItemClicked;

    public GraphAdapter(@NonNull Context context, int layoutRes) {
        super(context, layoutRes);
    }

    public GraphAdapter(@NonNull Context context,
                        int layoutRes, @NonNull Graph graph,
                        List<StrukturOrganisasiResponse> strukturOrganisasiResponses,
                        OnGraphItemClicked onGraphItemClicked) {
        super(context, layoutRes, graph);
        this.strukturOrganisasiResponses = strukturOrganisasiResponses;
        this.onGraphItemClicked = onGraphItemClicked;
    }

    @NonNull
    @Override
    public GraphViewHolder onCreateViewHolder(View view) {
        return new GraphViewHolder(view, onGraphItemClicked);
    }

    @Override
    public void onBindViewHolder(GraphViewHolder holder, Object data, int position) {
        holder.tv_jabatan.setText(strukturOrganisasiResponses.get(position).jabatan);
        holder.tv_nama.setText(strukturOrganisasiResponses.get(position).nama);
        holder.tv_npm.setText(strukturOrganisasiResponses.get(position).npm);

        if (!TextUtils.isEmpty(strukturOrganisasiResponses.get(position).photo_url)) {
            Picasso.get()
                    .load(strukturOrganisasiResponses.get(position).photo_url)
                    .placeholder(R.drawable.ic_user_holo)
                    .into(holder.iv_struktur_org_item);
        }

        holder.position = position;
        holder.strukturOrganisasiResponse = strukturOrganisasiResponses.get(position);
    }
}
