package com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.adapter;

import android.view.View;

import com.luteh.kampusonlinenonakademik.model.daftarmember.DaftarMemberChild;

/**
 * Created by Luthfan Maftuh on 24/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface OnItemClicked {
    void onChildItemClicked(View view, DaftarMemberChild daftarMemberChild, int position);
}
