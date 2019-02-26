package com.luteh.kampusonlinenonakademik.ui.activities.allberita;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.Common;
import com.luteh.kampusonlinenonakademik.model.home.News;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.luteh.kampusonlinenonakademik.common.AppConstant.ARG_IMAGE;
import static com.luteh.kampusonlinenonakademik.common.AppConstant.ARG_NEWS;

/**
 * Created by Luthfan Maftuh on 26/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public class AllBeritaPresenterImp implements IAllBeritaPresenter {

    private IAllBeritaView iAllBeritaView;

    private News news;

    public AllBeritaPresenterImp(IAllBeritaView iAllBeritaView) {
        this.iAllBeritaView = iAllBeritaView;
    }

    @Override
    public void submitNewBerita(String judul, String image_url, String deskripsi, String tanggal_berita, String post_by) {
        news = new News(judul, image_url, deskripsi, tanggal_berita, post_by);

        switch (news.isValidData()) {
            case 0:
                iAllBeritaView.onEmptyData(0);
                break;
            case 1:
                iAllBeritaView.onEmptyData(1);
                break;
            case 2:
                iAllBeritaView.onEmptyData(2);
                break;
            case 3:
                iAllBeritaView.onEmptyData(3);
                break;
            case 4:
                iAllBeritaView.onEmptyData(4);
                break;
            case -1:
                putImageIntoStorage();
                break;
        }
    }

    private void putImageIntoStorage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(ARG_NEWS)
                .child(ARG_IMAGE)
                .child(Common.formatChildName(AccountHelper.getUser().npm +
                        AccountHelper.getUser().ukm +
                        news.image_url + ".png"));

        RxFirebaseStorage.putFile(storageReference, Uri.parse(news.image_url))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskSnapshot -> {
                    RxFirebaseStorage.getDownloadUrl(storageReference)
                            .subscribe(uri -> {
                                news.image_url = uri.toString();
                                submitDataIntoDatabase();
                            });
                });
    }

    private void submitDataIntoDatabase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(ARG_NEWS)
                .push();

        RxFirebaseDatabase.setValue(databaseReference, news)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            Timber.d("Submit data Successful!");
                            iAllBeritaView.onSuccessSubmitNewData(news);
                        }, throwable -> throwable.printStackTrace()
                );
    }
}
