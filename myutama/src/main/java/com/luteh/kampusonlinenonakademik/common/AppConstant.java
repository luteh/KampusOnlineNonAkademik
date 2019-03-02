package com.luteh.kampusonlinenonakademik.common;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Luthfan Maftuh on 13/11/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class AppConstant {
    public static final String KEY_PDF_ASSET = "pdf_asset";
    public static final String KEY_UID = "uid";

    public static final String ARG_USERS = "users";
    public static final String ARG_FRS = "frs";
    public static final String ARG_STRUKTUR_ORG = "struktur_organisasi";
    public static final String ARG_UKM = "ukm";
    public static final String ARG_NEWS = "news";
    public static final String ARG_IMAGE = "image";
    public static final String ARG_PNG_EXTENSION = ".png";
    public static final String ARG_MEMBER_IMAGE = "member_image";
    public static final String ARG_DAFTAR_MEMBER = "daftar_member";
    public static final String ARG_TAHUN = "tahun";
    public static final String ARG_JOB_DESK = "job_desk";
    public static final String ARG_KALENDER_KEGIATAN = "kalender_kegiatan";
    public static final String ARG_DAFTAR_UKM = "daftar_ukm";

    public static final String FIELD_PHOTO_URL = "photo_url";
    public static final String FIELD_NPM = "npm";
    public static final String FIELD_NAMA = "nama";
    public static final String FIELD_JABATAN = "jabatan";
    public static final String FIELD_TREE_LEVEL = "tree_level";
    public static final String FIELD_JOB = "job";

    public static final String KEY_DETAIL_BERITA = "detail_berita";
    public static final String KEY_LIST_BERITA = "list_berita";

    public static final int REQUEST_CODE_INTERNET = 1000;

    public static final DatabaseReference REF_NEWS = FirebaseDatabase.getInstance().getReference()
            .child(ARG_NEWS);
}
