package com.luteh.kampusonlinenonakademik.ui.fragments.jobdesk;

import com.luteh.kampusonlinenonakademik.model.jobdesk.JobDesk;

import java.util.List;

/**
 * Created by Luthfan Maftuh on 27/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IJobDeskView {
    void onRetrieveDataSuccess(List<JobDesk> jobDesks);

    void onRetrieveDataFailure(String message);
}
