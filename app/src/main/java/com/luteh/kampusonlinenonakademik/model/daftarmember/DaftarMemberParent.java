package com.luteh.kampusonlinenonakademik.model.daftarmember;

import java.util.List;

/**
 * Created by Luthfan Maftuh on 23/01/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class DaftarMemberParent {
    public String tahun;
    public List<DaftarMemberChild> daftarMemberChildList;

    public DaftarMemberParent(String tahun, List<DaftarMemberChild> daftarMemberChildList) {
        this.tahun = tahun;
        this.daftarMemberChildList = daftarMemberChildList;
    }
}
