package com.luteh.kampusonlinenonakademik.ui.activities.allberita;

import android.os.Bundle;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.base.BaseActivity;
import com.luteh.kampusonlinenonakademik.model.home.News;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.adapter.AllBeritaAdapter;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.adapter.IAllBeritaAdapter;
import com.luteh.kampusonlinenonakademik.ui.activities.berita.BeritaActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.KEY_DETAIL_BERITA;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.KEY_LIST_BERITA;

public class AllBeritaActivity extends BaseActivity implements IAllBeritaAdapter {

    @BindView(R.id.rv_allberita)
    RecyclerView rv_allberita;

    private ArrayList<News> newsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_berita);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onInit() {
        super.onInit();

        newsArrayList = this.getIntent().getParcelableArrayListExtra(KEY_LIST_BERITA);

        Timber.d(newsArrayList.get(1).toString());

        initRecyclerView();
    }

    private void initRecyclerView() {
        rv_allberita.setHasFixedSize(true);
        rv_allberita.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        );
        rv_allberita.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rv_allberita.setAdapter(new AllBeritaAdapter(newsArrayList, this));
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

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DETAIL_BERITA, newsArrayList.get(position));

        startActivityFromRight(BeritaActivity.class, bundle);
    }
}
