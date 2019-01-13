package com.luteh.kampusonlinenonakademik.model.login;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by Luthfan Maftuh on 12/11/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class LoginRequest implements ILoginRequest {
    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int isValidData() {
        if (TextUtils.isEmpty(getEmail())) return 0;
        else if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()) return 1;
        else if (TextUtils.isEmpty(getPassword())) return 2;
        else return -1;
    }
}
