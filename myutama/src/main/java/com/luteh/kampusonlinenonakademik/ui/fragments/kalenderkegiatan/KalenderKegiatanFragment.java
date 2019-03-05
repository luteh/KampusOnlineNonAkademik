package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.common.utils.RecyclerTouchListener;
import com.luteh.kampusonlinenonakademik.model.kegiatan.KegiatanChild;
import com.luteh.kampusonlinenonakademik.model.kegiatan.KegiatanParent;
import com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan.adapter.KegiatanAdapter;
import com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan.dialog.AddKegiatanHolder;
import com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan.dialog.OnKegiatanDialogClick;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class KalenderKegiatanFragment extends BaseFragment implements IKegiatanView,
        OnKegiatanDialogClick,
        RecyclerTouchListener.ItemClickListener {

    @BindView(R.id.cal_kegiatan)
    CalendarView cal_kegiatan;
    @BindView(R.id.rl_kegiatan_container)
    ViewGroup rl_kegiatan_container;
    @BindView(R.id.ll_progress_bar_container)
    LinearLayout ll_progress_bar_container;
    @BindView(R.id.rv_kegiatan)
    RecyclerView rv_kegiatan;
    @BindView(R.id.fab_add_kegiatan)
    FloatingActionButton fab_add_kegiatan;

    private RecyclerView.Adapter mAdapter;

    private IKegiatanPresenter iKegiatanPresenter;

    private AddKegiatanHolder kegiatanHolder;
    private AlertDialog kegiatanDialog;

    private String stringDate;
    private List<KegiatanChild> kegiatanChildren;
    private HashMap<String, List<KegiatanChild>> mapKegiatan;

    public KalenderKegiatanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kalender_kegiatan, container, false);
    }

    @Override
    protected void onInit() {
        super.onInit();

        initView();

        Timber.d("onInit");

        initRecyclerView();

        iKegiatanPresenter = new KegiatanPresenterImp(this);

        iKegiatanPresenter.retrieveKegiatanData();

//        getBaseActivity().onLoadingStarted(rl_kegiatan_container, ll_progress_bar_container);
    }

    private void initView() {
        if (Common.isAdmin())
            fab_add_kegiatan.setVisibility(View.VISIBLE);
        else
            fab_add_kegiatan.setVisibility(View.INVISIBLE);
    }

    private void initRecyclerView() {
        rv_kegiatan.setHasFixedSize(true);
        rv_kegiatan.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        rv_kegiatan.addOnItemTouchListener(new RecyclerTouchListener(context, rv_kegiatan, this));
    }

    @Override
    public void onSuccessRetrieveKegiatanData(List<KegiatanParent> kegiatanParents, HashMap<String, List<KegiatanChild>> mapList) {
        getBaseActivity().onLoadingFinished(rl_kegiatan_container, ll_progress_bar_container);
//        Common.showSuccessMessage(context, "Retrieve Data success!");

        mapKegiatan = mapList;

        List<EventDay> events = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
//            Calendar cal = Calendar.getInstance();
            for (KegiatanParent kegiatanParent : kegiatanParents) {
                Timber.d("Tanggal: %s", kegiatanParent.tanggal);
//                Convert String to Date
                Date date = sdf.parse(kegiatanParent.tanggal);

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                events.add(new EventDay(cal, R.drawable.ic_dot_));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal_kegiatan.setEvents(events);

        // TODO: 01/02/2019 Refactor boilerplate codes to be readable codes

        cal_kegiatan.setOnDayClickListener(eventDay -> {
//            Convert Date to String
            stringDate = sdf.format(eventDay.getCalendar().getTime());

            kegiatanChildren = mapKegiatan.get(stringDate);

            mAdapter = new KegiatanAdapter(kegiatanChildren);

            Timber.d("Current item count after init: %d", mAdapter.getItemCount());

            rv_kegiatan.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(rv_kegiatan.getContext(), R.anim.layout_anim_fall_down));
//            mAdapter.notifyDataSetChanged();
            rv_kegiatan.setAdapter(mAdapter);
            rv_kegiatan.scheduleLayoutAnimation();
        });
    }

    @Override
    public void onFailure(String message) {
        getBaseActivity().onLoadingFinished(rl_kegiatan_container, ll_progress_bar_container);
        Common.showErrorMessage(context, message);
    }

    @Override
    public void onSuccessSubmitNewKegiatanData() {
        Common.showSuccessMessage(context, "Submit new kegiatan data Successfully");

       /* if (mAdapter != null)
            rv_kegiatan.getAdapter().notifyDataSetChanged();*/
    }

    @Override
    public void onSuccessDeleteKegiatan() {
        Toast.makeText(context, "Delete Kegiatan Successful!", Toast.LENGTH_SHORT).show();

        iKegiatanPresenter.retrieveKegiatanData();
    }

    @OnClick(R.id.fab_add_kegiatan)
    void onFabKegiatanClick() {
        showAddKegiatanDialog();
    }

    private void showAddKegiatanDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_kegiatan, null);
        kegiatanHolder = new AddKegiatanHolder(view, this);

        kegiatanDialog = new AlertDialog.Builder(context, R.style.DialogTheme)
                .setView(view)
                .setTitle(context.getResources().getText(R.string.label_tambah_kegiatan))
                .create();

        kegiatanDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        kegiatanDialog.show();
    }

    @Override
    public void OnBtnAddClicked() {
        kegiatanDialog.dismiss();

        iKegiatanPresenter.submitNewKegiatanToDatabase(
                kegiatanHolder.getTanggal(),
                kegiatanHolder.getJam(),
                kegiatanHolder.getDeskripsi()
        );

        Toast.makeText(context, "Button Add Clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view, int position) {
        showMenuItem(view, position);
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    private void showMenuItem(View view, int position) {
        PopupMenu menu = new PopupMenu(context, view);

        menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_edit:
                    Toast.makeText(context, "Edit position " + position, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.item_delete:
                    Timber.d("Item count before : %d", mAdapter.getItemCount());

                    Timber.d("Kegiatam Item : %s", ((KegiatanAdapter) mAdapter).getItem(position).toString());
                    iKegiatanPresenter.deleteKegiatan(stringDate, ((KegiatanAdapter) mAdapter).getItem(position).key);
//                    kegiatanChildren.remove(position);
                    mapKegiatan.get(stringDate).remove(position);
                    mAdapter.notifyDataSetChanged();

                    Timber.d("Item count after: %d", mAdapter.getItemCount());
                    break;
            }
            return true;
        });

        menu.inflate(R.menu.menu_item);
        menu.show();
    }
}
