package com.luteh.kampusonlinenonakademik;

import android.content.Context;
import android.content.SharedPreferences;

import com.luteh.kampusonlinenonakademik.log.ReleaseTree;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import timber.log.Timber;

/**
 * Created by Luthfan Maftuh on 11/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication singleton;

    public static MyApplication getInstance() {
        return singleton;
    }

    public static void saveConfig(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
        editor.clear();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        singleton = this;
        
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }
    
    public static SharedPreferences getSharedPreferences() {
        return MyApplication.getInstance().getSharedPreferences("mypref", Context.MODE_PRIVATE);
    }
}
