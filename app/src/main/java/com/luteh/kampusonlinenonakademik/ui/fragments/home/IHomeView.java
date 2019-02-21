package com.luteh.kampusonlinenonakademik.ui.fragments.home;

import android.net.Uri;

import com.luteh.kampusonlinenonakademik.model.home.News;

import java.util.List;

/**
 * Created by Luthfan Maftuh on 16/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IHomeView {
    void onRetrieveContentSuccessful(Uri uri);

    void onSuccessGetNewsData(List<News> newsList);

    void onFailureGetNewsData(String message);
}
