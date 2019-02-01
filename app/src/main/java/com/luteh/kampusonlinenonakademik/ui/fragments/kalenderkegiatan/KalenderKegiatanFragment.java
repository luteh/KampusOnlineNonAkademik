package com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.kegiatan.KegiatanChild;
import com.luteh.kampusonlinenonakademik.model.kegiatan.KegiatanParent;
import com.luteh.kampusonlinenonakademik.ui.fragments.kalenderkegiatan.adapter.KegiatanAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class KalenderKegiatanFragment extends BaseFragment implements IKegiatanView {

    @BindView(R.id.cal_kegiatan)
    CalendarView cal_kegiatan;
    @BindView(R.id.rl_kegiatan_container)
    ViewGroup rl_kegiatan_container;
    @BindView(R.id.ll_progress_bar_container)
    LinearLayout ll_progress_bar_container;
    @BindView(R.id.rv_kegiatan)
    RecyclerView rv_kegiatan;

    private RecyclerView.Adapter mAdapter;

    private IKegiatanPresenter iKegiatanPresenter;

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

        Timber.d("onInit");

        initRecyclerView();

        iKegiatanPresenter = new KegiatanPresenterImp(this);

        iKegiatanPresenter.retrieveKegiatanData();

        getBaseActivity().onLoadingStarted(rl_kegiatan_container, ll_progress_bar_container);
    }

    private void initRecyclerView() {
        rv_kegiatan.setHasFixedSize(true);
        rv_kegiatan.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
    }

    @Override
    public void onSuccessRetrieveKegiatanData(List<KegiatanParent> kegiatanParents, HashMap<String, List<KegiatanChild>> mapList) {
        getBaseActivity().onLoadingFinished(rl_kegiatan_container, ll_progress_bar_container);
        Common.showSuccessMessage(context, "Retrieve Data success!");

        List<EventDay> events = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
//            Calendar cal = Calendar.getInstance();
            for (KegiatanParent kegiatanParent : kegiatanParents) {
                Timber.d("Tanggal: %s", kegiatanParent.tanggal);
                Date date = sdf.parse(kegiatanParent.tanggal);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                events.add(new EventDay(cal, R.drawable.sample_three_icons));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal_kegiatan.setEvents(events);

        // TODO: 01/02/2019 Refactor boilerplate codes to be readable codes

        cal_kegiatan.setOnDayClickListener(eventDay -> {
            SimpleDateFormat sdfs = new SimpleDateFormat("dd-MM-yyyy");
//                String strDate = eventDay.getCalendar().getTime().toString();
            String stringDate = sdfs.format(eventDay.getCalendar().getTime());
//                Date date = sdfs.parse(strDate);
            mAdapter = new KegiatanAdapter(mapList.get(stringDate));
            rv_kegiatan.setAdapter(mAdapter);
        });
    }


    @Override
    public void onFailure(String message) {
        getBaseActivity().onLoadingFinished(rl_kegiatan_container, ll_progress_bar_container);
        Common.showErrorMessage(context, message);
    }
}