package com.luteh.kampusonlinenonakademik.ui.activities.berita;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseActivity;
import com.luteh.kampusonlinenonakademik.model.home.News;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.KEY_DETAIL_BERITA;
import static com.luteh.kampusonlinenonakademik.common.Common.paragraphTextStyle;

public class BeritaActivity extends BaseActivity implements IBeritaView {

    @BindView(R.id.tv_berita_judul)
    TextView tv_berita_judul;
    @BindView(R.id.tv_berita_post_by)
    TextView tv_berita_post_by;
    @BindView(R.id.tv_berita_tanggal)
    TextView tv_berita_tanggal;
    @BindView(R.id.tv_berita_deskripsi)
    TextView tv_berita_deskripsi;
    @BindView(R.id.iv_berita)
    ImageView iv_berita;

    private IBeritaPresenter iBeritaPresenter;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iBeritaPresenter = new BeritaPresenterImp(this);

        Intent mIntent = getIntent();
        news = mIntent.getParcelableExtra(KEY_DETAIL_BERITA);

        Timber.d(news.toString());

        initView();
    }

    private void initView() {
        tv_berita_judul.setText(news.judul);
        tv_berita_post_by.setText(news.post_by);
        tv_berita_deskripsi.setText(paragraphTextStyle(news.deskripsi));
        tv_berita_tanggal.setText(Common.getStringDateRange(news.tanggal_berita));

        Picasso.with(getContext())
                .load(news.image_url)
                .into(iv_berita);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finishToRight();
        return true;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finishToRight();
    }
}
