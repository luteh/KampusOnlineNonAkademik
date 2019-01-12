package com.luteh.kampusonlinenonakademik.ui.activities.splash;

/**
 * Created by Luthfan Maftuh on 12/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface ISplashView {
    void onPermissionGranted();

    void onPermissionNotGranted();

    void onSplashScreenFinished();
}
