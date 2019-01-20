package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;

import com.luteh.kampusonlinenonakademik.model.strukturorganisasi.StrukturOrganisasiRequest;
import com.luteh.kampusonlinenonakademik.model.strukturorganisasi.StrukturOrganisasiResponse;

import java.util.List;

import de.blox.graphview.Graph;
import de.blox.graphview.GraphAdapter;

/**
 * Created by Luthfan Maftuh on 15/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IStrukturOrganisasiPresenter {
    void retrieveStrukturOrganisasiData();

    Graph createGraph(List<StrukturOrganisasiResponse> strukturOrganisasiResponses);

    void setAlgorithm(GraphAdapter adapter);

    void submitEditMember(StrukturOrganisasiRequest strukturOrganisasiRequest);
}
