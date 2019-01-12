package com.luteh.kampusonlinenonakademik.ui.activities.splash;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.base.BaseActivity;
import com.luteh.kampusonlinenonakademik.ui.activities.login.LoginActivity;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.REQUEST_CODE_INTERNET;

public class SplashActivity extends BaseActivity implements ISplashView {

    private ISplashPresenter iSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iSplashPresenter = new SplashPresenterImp(this, this);

        iSplashPresenter.initPermissions();
    }

    @Override
    public void onPermissionGranted() {
        iSplashPresenter.initSplash();
    }

    @Override
    public void onPermissionNotGranted() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.GET_ACCOUNTS
        }, REQUEST_CODE_INTERNET);
    }

    @Override
    public void onSplashScreenFinished() {
        startActivityFromRight(LoginActivity.class);
    }

    // to show alert dialog if permission has denied by user
    private void showPermissionDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppTheme);

        builder.setTitle(getResources().getString(R.string.label_warning));
        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.label_message_apps_requirement));
        builder.setPositiveButton(getResources().getString(R.string.label_show_permission), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onPermissionNotGranted();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.label_close_app), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_INTERNET:
                if (grantResults.length > 0) {
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        onPermissionGranted();

                    } else if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
                        showPermissionDialog();
                    }
                }
        }
    }

}
