package com.luteh.kampusonlinenonakademik.ui.fragments.home;


import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.BindView;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements IHomeView {

    private IHomePresenter iHomePresenter;

    @BindView(R.id.rl_home_container)
    RelativeLayout rl_home_container;
    @BindView(R.id.iv_home_ukm_logo)
    ImageView iv_home_ukm_logo;
    @BindView(R.id.ll_progress_bar_container)
    LinearLayout ll_progress_bar_container;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iHomePresenter = new HomePresenterImp(getContext(), this);

        iHomePresenter.getHomeContent();

        rl_home_container.setVisibility(View.GONE);
        ll_progress_bar_container.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRetrieveContentSuccessed(Uri uri) {
        Picasso.get()
                .load(uri)
                .into(iv_home_ukm_logo);

        ll_progress_bar_container.setVisibility(View.GONE);
        rl_home_container.setVisibility(View.VISIBLE);
    }
}
