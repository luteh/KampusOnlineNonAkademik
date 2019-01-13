package com.luteh.kampusonlinenonakademik.ui.activities.login;

/**
 * Created by Luthfan Maftuh on 13/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface ILoginPresenter {
    /**
     * Login
     *
     * @param email    the email
     * @param password the password
     */
    void submitLogin(String email, String password);
}
