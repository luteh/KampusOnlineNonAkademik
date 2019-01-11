package com.luteh.kampusonlinenonakademik.common;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.luteh.kampusonlinenonakademik.MyApplication;
import com.luteh.kampusonlinenonakademik.model.User;

/**
 * Created by Luthfan Maftuh on 20/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class AccountHelper {
    public static User getUser() {
        String s = MyApplication.getSharedPreferences().getString("user", "");
        if (TextUtils.isEmpty(s)) {
            return null;
        } else {
            return new Gson().fromJson(s, User.class);
        }
    }

    public static String getToken() {
        String s = MyApplication.getSharedPreferences().getString("token", "");
        if (TextUtils.isEmpty(s)) {
            return null;
        } else {
            return s;
        }
    }

    public static boolean isLogin() {
        if (TextUtils.isEmpty(getToken())) {
            return false;
        } else {
            return true;
        }
    }


    public static void saveUser(User user) {
        MyApplication.getSharedPreferences().edit().putString("user", new Gson().toJson(user)).apply();
    }

    public static void saveToken(String sid) {
        MyApplication.getSharedPreferences().edit().putString("token", sid).apply();
    }

    public static void clearUserData(Context context) {
        MyApplication.getSharedPreferences().edit().putString("user", "").apply();
        MyApplication.getSharedPreferences().edit().putString("token", "").apply();

    }
}
