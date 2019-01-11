package com.luteh.kampusonlinenonakademik.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Luthfan Maftuh on 10/11/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class BaseFragment extends Fragment {

    private BaseActivity baseActivity;
    protected Context context;
    private Unbinder unbinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        this.context = getContext();
        onInit();
    }

    protected void onInit() {
        // override
    }

    protected BaseActivity getBaseActivity() {
        return baseActivity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
