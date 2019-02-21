package com.luteh.kampusonlinenonakademik.ui.activities.dashboard;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseActivity;
import com.luteh.kampusonlinenonakademik.common.utils.HeaderViewHolder;
import com.luteh.kampusonlinenonakademik.ui.activities.login.LoginActivity;
import com.luteh.kampusonlinenonakademik.ui.fragments.daftarmember.DaftarMemberFragment;
import com.luteh.kampusonlinenonakademik.ui.fragments.daftarukm.DaftarUkmFragment;
import com.luteh.kampusonlinenonakademik.ui.fragments.home.HomeFragment;
import com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk.JobDeskFragment;
import com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan.KalenderKegiatanFragment;
import com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi.StrukturOrganisasiFragment;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
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

    private void initDrawerMenu() {
        Menu navMenu = navigationView.getMenu();

        if (AccountHelper.getUser().isMember) {
            navMenu.findItem(R.id.menu_nav_home).setVisible(true);
            navMenu.findItem(R.id.menu_nav_struktur_organisasi).setVisible(true);
            navMenu.findItem(R.id.menu_nav_daftar_member).setVisible(true);
            navMenu.findItem(R.id.menu_nav_job_desk_divisi).setVisible(true);
            navMenu.findItem(R.id.menu_nav_kalender_kegiatan).setVisible(true);
            navMenu.findItem(R.id.menu_nav_proposal).setVisible(true);
            navMenu.findItem(R.id.menu_nav_daftar_ukm).setVisible(false);
            navMenu.findItem(R.id.menu_nav_logout).setVisible(true);
        } else {
            navMenu.findItem(R.id.menu_nav_home).setVisible(true);
            navMenu.findItem(R.id.menu_nav_struktur_organisasi).setVisible(false);
            navMenu.findItem(R.id.menu_nav_daftar_member).setVisible(false);
            navMenu.findItem(R.id.menu_nav_job_desk_divisi).setVisible(false);
            navMenu.findItem(R.id.menu_nav_kalender_kegiatan).setVisible(false);
            navMenu.findItem(R.id.menu_nav_proposal).setVisible(false);
            navMenu.findItem(R.id.menu_nav_daftar_ukm).setVisible(true);
            navMenu.findItem(R.id.menu_nav_logout).setVisible(true);
        }

        navigationView.setCheckedItem(R.id.menu_nav_home);
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
            case R.id.menu_nav_home:
                replaceFragment(new HomeFragment(), R.string.title_fragment_home);
                break;
            case R.id.menu_nav_struktur_organisasi:
                replaceFragment(new StrukturOrganisasiFragment(), R.string.title_fragment_struktur_organisasi);
                break;
            case R.id.menu_nav_daftar_member:
                replaceFragment(new DaftarMemberFragment(), R.string.title_fragment_daftar_member);
                break;
            case R.id.menu_nav_job_desk_divisi:
                replaceFragment(new JobDeskFragment(), R.string.title_fragment_jobdesk_divisi);
                break;
            case R.id.menu_nav_kalender_kegiatan:
                replaceFragment(new KalenderKegiatanFragment(), R.string.title_fragment_kalender_kegiatan);
                break;
            case R.id.menu_nav_proposal:
//                replaceFragment(new RencanaStudiFragment(), R.string.title_rencana_studi_fragment);
                break;
            case R.id.menu_nav_daftar_ukm:
                replaceFragment(new DaftarUkmFragment(), R.string.title_daftar_ukm);
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
            Picasso.with(getContext())
                    .load(getUser().photo_url)
                    .into(headerViewHolder.imgProfile);

            headerViewHolder.tvProfileName.setText(getUser().nama);
            headerViewHolder.tvProfileNpm.setText(getUser().npm);
        }

        initFragment();
        initDrawerMenu();
    }

    private void initFragment() {
        // Create and set Android Fragment as default.
        Fragment homeFragment = new HomeFragment();
        setDefaultFragment(homeFragment);
    }
}
