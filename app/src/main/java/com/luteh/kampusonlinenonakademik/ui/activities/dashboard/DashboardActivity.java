package com.luteh.kampusonlinenonakademik.ui.activities.dashboard;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseActivity;
import com.luteh.kampusonlinenonakademik.common.utils.HeaderViewHolder;
import com.luteh.kampusonlinenonakademik.ui.activities.login.LoginActivity;
import com.luteh.kampusonlinenonakademik.ui.fragments.home.HomeFragment;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.StrukturOrganisasiFragment;
import com.squareup.picasso.Picasso;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AccountHelper.getUser;

/**
 * Created by Luthfan Maftuh on 14/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DashboardActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        IDashboardView {

    private IDashboardPresenter iDashboardPresenter;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iDashboardPresenter = new DashboardPresenterImp(this, this);

        if ((AccountHelper.getUser() == null))
            iDashboardPresenter.getUserInfo();
        else
            onRetrieveUserInfoSuccess();

        Timber.d("Token : %s", AccountHelper.getToken());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                Common.showToastMessage(this, "Logout Clicked!");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_nav_struktur_organisasi:
                replaceFragment(new StrukturOrganisasiFragment(), R.string.title_fragment_struktur_organisasi);
                break;
            case R.id.menu_nav_daftar_member:
                break;
            case R.id.menu_nav_job_desk_divisi:
//                replaceFragment(new HasilStudiFragment(), R.string.title_hasil_studi_fragment);
                break;
            case R.id.menu_nav_kalender_kegiatan:
//                replaceFragment(new JadwalFragment(), R.string.title_jadwal_fragment);
                break;
            case R.id.menu_nav_proposal:
//                replaceFragment(new RencanaStudiFragment(), R.string.title_rencana_studi_fragment);
                break;
            case R.id.menu_nav_logout:
                FirebaseAuth.getInstance().signOut();
                AccountHelper.clearUserData(this);
                finishToRight();
                startActivity(LoginActivity.class);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRetrieveUserInfoSuccess() {
        View headerLayout = navigationView.getHeaderView(0);
        HeaderViewHolder headerViewHolder = new HeaderViewHolder(headerLayout);

        if (getUser() != null) {
            Picasso.get()
                    .load(getUser().photo_url)
                    .into(headerViewHolder.imgProfile);

            headerViewHolder.tvProfileName.setText(getUser().nama);
            headerViewHolder.tvProfileNpm.setText(getUser().npm);
        }

        initFragment();
    }

    private void initFragment() {
        // Create and set Android Fragment as default.
        Fragment homeFragment = new HomeFragment();
        setDefaultFragment(homeFragment);
    }
}
