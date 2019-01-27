package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk;

/**
 * Created by Luthfan Maftuh on 27/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class JobDeskPresenterImp implements IJobDeskPresenter{
    private IJobDeskView iJobDeskView;

    public JobDeskPresenterImp(IJobDeskView iJobDeskView) {
        this.iJobDeskView = iJobDeskView;
    }
}
