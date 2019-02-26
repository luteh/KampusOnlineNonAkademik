package com.luteh.kampusonlinenonakademik.ui.activities.allberita;

import com.luteh.kampusonlinenonakademik.model.home.News;

/**
 * Created by Luthfan Maftuh on 26/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IAllBeritaView {
    void onEmptyData(int emptyCode);

    void onSuccessSubmitNewData(News news);

    void onListChange(News news);
}
