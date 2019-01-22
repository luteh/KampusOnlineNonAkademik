package com.luteh.kampusonlinenonakademik.ui.fragments.strukturorganisasi;

import com.luteh.kampusonlinenonakademik.model.strukturorganisasi.StrukturOrganisasiResponse;

import java.util.List;

/**
 * Created by Luthfan Maftuh on 15/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IStrukturOrganisasiView {
    void onDataRetrieved(List<StrukturOrganisasiResponse> strukturOrganisasiResponses);

    /**
     * Show the email error message
     *
     * @param message the error message
     */
    void onNamaError(String message);

    /**
     * Show the password error message
     *
     * @param message the error message
     */
    void onNpmError(String message);

    /**
     * Clear the error effect on all fields
     **/
    void clearError();

    void onDataIsSame();

    void onStrukturOrganisasiDataUpdated();

    void onUpdateStrukturOrganisasiFailed(String message);
}
