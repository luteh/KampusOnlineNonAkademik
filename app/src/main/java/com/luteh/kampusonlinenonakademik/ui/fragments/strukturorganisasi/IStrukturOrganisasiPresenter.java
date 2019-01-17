package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;

import com.luteh.kampusonlinenonakademik.model.StrukturOrganisasi;

import java.util.List;

import de.blox.graphview.Graph;
import de.blox.graphview.GraphAdapter;

/**
 * Created by Luthfan Maftuh on 15/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IStrukturOrganisasiPresenter {
    void retrieveStrukturOrganisasiData();

    Graph createGraph(List<StrukturOrganisasi> strukturOrganisasis);

    void setAlgorithm(GraphAdapter adapter);
}
