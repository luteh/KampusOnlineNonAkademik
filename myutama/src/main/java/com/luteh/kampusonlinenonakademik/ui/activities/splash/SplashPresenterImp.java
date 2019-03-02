package com.luteh.kampusonlinenonakademik.ui.activities.splash;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import androidx.core.app.ActivityCompat;

/**
 * Created by Luthfan Maftuh on 12/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class SplashPresenterImp implements ISplashPresenter {

    private ISplashView iSplashView;
    private Context context;

    private Handler splashScreenHandler = null;
    private Runnable splashScreenRunnable = null;
    private final int SPLASH_TIME_MILLISECOND = 2000;

    public SplashPresenterImp(ISplashView iSplashView, Context context) {
        this.iSplashView = iSplashView;
        this.context = context;
    }

    @Override
    public void initPermissions() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS)
                        == PackageManager.PERMISSION_GRANTED)
            iSplashView.onPermissionGranted();
        else
            iSplashView.onPermissionNotGranted();

    }

    @Override
    public void initSplash() {
        if (splashScreenRunnable == null) {
            splashScreenRunnable = new Runnable() {
                @Override
                public void run() {
                    iSplashView.onSplashScreenFinished();
                }
            };
        }

        if (splashScreenHandler == null) {
            splashScreenHandler = new Handler();
        }

        splashScreenHandler.postDelayed(splashScreenRunnable, SPLASH_TIME_MILLISECOND);
    }
}
