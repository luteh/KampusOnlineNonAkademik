package com.luteh.kampusonlinenonakademik.ui.fragments.home;


import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;
import com.luteh.kampusonlinenonakademik.model.home.News;
import com.luteh.kampusonlinenonakademik.ui.activities.allberita.AllBeritaActivity;
import com.luteh.kampusonlinenonakademik.ui.activities.berita.BeritaActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.KEY_DETAIL_BERITA;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.KEY_LIST_BERITA;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements IHomeView,
        ViewPagerEx.OnPageChangeListener,
        BaseSliderView.OnSliderClickListener {

    private IHomePresenter iHomePresenter;

    @BindView(R.id.iv_home_ukm_logo)
    ImageView iv_home_ukm_logo;
    @BindView(R.id.slider_home_news)
    SliderLayout slider_home_news;
    @BindView(R.id.pb_home_news)
    ProgressBar pb_home_news;
    @BindView(R.id.pb_home_ukm_logo)
    ProgressBar pb_home_ukm_logo;
    @BindView(R.id.custom_indicator)
    PagerIndicator custom_indicator;
    @BindView(R.id.btn_berita_see_more)
    Button btn_berita_see_more;

    private ArrayList<News> newsList = new ArrayList<News>();

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

        iHomePresenter.getNewsData();
        iHomePresenter.getHomeContent();

        getBaseActivity().onLoadingStarted(slider_home_news, pb_home_news);
        getBaseActivity().onLoadingStarted(iv_home_ukm_logo, pb_home_ukm_logo);
    }

    @Override
    public void onRetrieveContentSuccessful(Uri uri) {
        Picasso.with(context)
                .load(uri)
                .into(iv_home_ukm_logo);

        getBaseActivity().onLoadingFinished(iv_home_ukm_logo, pb_home_ukm_logo);
    }

    @Override
    public void onSuccessGetNewsData(ArrayList<News> newsList) {
        Collections.sort(newsList, (o1, o2) -> o2.tanggal_berita.compareTo(o1.tanggal_berita));
        this.newsList = newsList;

        for (int i = 0; i < 4; i++) {
            News news = newsList.get(i);

            if (news != null) {
                TextSliderView textSliderView = new TextSliderView(context);
                textSliderView.description(String.format("%s \nPosted by %s on %s",
                        news.judul,
                        news.post_by,
                        news.tanggal_berita))
                        .image(news.image_url)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putParcelable(KEY_DETAIL_BERITA, news);

                slider_home_news.addSlider(textSliderView);
            }
        }

        slider_home_news.setPresetTransformer(SliderLayout.Transformer.Accordion);
//        slider_home_news.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider_home_news.setCustomIndicator(custom_indicator);
        slider_home_news.setCustomAnimation(new DescriptionAnimation());
        slider_home_news.setDuration(4000);
        slider_home_news.addOnPageChangeListener(this);

        getBaseActivity().onLoadingFinished(slider_home_news, pb_home_news);
    }

    @Override
    public void onFailureGetNewsData(String message) {
        Common.showErrorMessage(context, message);

        getBaseActivity().onLoadingFinished(slider_home_news, pb_home_news);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Timber.d("Page Changed: %d", position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
//        Toast.makeText(context, slider.getBundle().get(KEY_DETAIL_BERITA) + "", Toast.LENGTH_SHORT).show();

        getBaseActivity().startActivityFromRight(BeritaActivity.class, slider.getBundle());
    }

    @OnClick(R.id.btn_berita_see_more)
    void onBtnSeeMoreClick() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_LIST_BERITA, newsList);

        getBaseActivity().startActivityFromRight(AllBeritaActivity.class, bundle);
    }

    @Override
    public void onStop() {
        slider_home_news.stopAutoCycle();
        super.onStop();
    }
}
