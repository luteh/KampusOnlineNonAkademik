package com.luteh.kampusonlinenonakademik.model.kegiatan;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Luthfan Maftuh on 01/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class KegiatanParent {
    public String tanggal;
    public List<KegiatanChild> kegiatanChildren;

    public KegiatanParent(String tanggal, List<KegiatanChild> kegiatanChildren) {
        this.tanggal = tanggal;
        this.kegiatanChildren = kegiatanChildren;
    }

    public HashMap<String, List<KegiatanChild>> toMapList() {
        HashMap<String, List<KegiatanChild>> map = new HashMap<>();
        map.put(tanggal, kegiatanChildren);
        return map;
    }
}
