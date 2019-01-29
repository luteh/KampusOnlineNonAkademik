package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk;

import com.luteh.kampusonlinenonakademik.model.jobdesk.JobDesk;

/**
 * Created by Luthfan Maftuh on 27/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IJobDeskPresenter {
    void retrieveJobDeskData();

    void submitEditJobDesk(JobDesk jobDesk);
}
