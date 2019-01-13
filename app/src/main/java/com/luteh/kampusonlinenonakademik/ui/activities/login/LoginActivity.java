package com.luteh.kampusonlinenonakademik.ui.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.AppConstant;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.common.base.BaseActivity;
import com.luteh.kampusonlinenonakademik.ui.activities.DashboardActivity;

public class LoginActivity extends BaseActivity implements ILoginView {

    private ILoginPresenter iLoginPresenter;

    @BindView(R.id.etEmailLogin)
    TextInputEditText etEmailLogin;
    @BindView(R.id.etPasswordLogin)
    TextInputEditText etPasswordLogin;
    @BindView(R.id.tilEmailLogin)
    TextInputLayout tilEmailLogin;
    @BindView(R.id.tilPasswordLogin)
    TextInputLayout tilPasswordLogin;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
//            Common.showSuccessMessage(this, "Transition to the next page");
            Common.currentUID = currentUser.getUid();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.KEY_UID, currentUser.getUid());
            finish();
            startActivityFromRight(DashboardActivity.class, bundle);
        } /*else {
            Common.showErrorMessage(this, "Current User is null");
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iLoginPresenter = new LoginPresenterImp(this, this);
    }

    @OnClick(R.id.btnLogin)
    protected void submitLogin() {
        Common.showSpotsProgress(this, getResources().getString(R.string.title_message_loading));
        iLoginPresenter.submitLogin(etEmailLogin.getText().toString(), etPasswordLogin.getText().toString());
    }

    @Override
    public void onLoginSuccess(FirebaseUser user) {
        updateUI(user);
        Common.dismissSpotsProgress();
    }

    @Override
    public void onLoginError(String message) {
        Common.showErrorMessage(this, message);
        Common.dismissSpotsProgress();
    }

    @Override
    public void onEmailError(String message) {
        tilEmailLogin.setError(message);
        showSoftKeyboard(etEmailLogin);
        Common.dismissSpotsProgress();
    }

    @Override
    public void onPasswordError(String message) {
        tilPasswordLogin.setError(message);
        showSoftKeyboard(etPasswordLogin);
        Common.dismissSpotsProgress();
    }

    @Override
    public void clearError() {
        tilEmailLogin.setError(null);
        tilEmailLogin.setErrorEnabled(false);
        tilPasswordLogin.setError(null);
    }
}
