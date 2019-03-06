package com.luteh.kampusonlinenonakademik.ui.fragments.proposal;


import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.luteh.kampusonlinenonakademik.R;
import com.luteh.kampusonlinenonakademik.common.AccountHelper;
import com.luteh.kampusonlinenonakademik.common.base.BaseFragment;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProposalFragment extends BaseFragment {


    public ProposalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proposal, container, false);
    }

    @OnClick(R.id.btn_proposal_send_email)
    void onClickBtnSendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(initEmailData()));

        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
            Toast.makeText(context, context.getResources().getString(R.string.label_msg_dont_have_email_app), Toast.LENGTH_SHORT).show();
        }
    }

    private String initEmailData() {
        String subject = "Proposal Kegiatan UKM " + AccountHelper.getUser().ukm;

        return "mailto:bob@example.org" +
                "?cc=" + "alice@example.com" +
                "&subject=" + Uri.encode(subject);
//                "&body=" + Uri.encode(bodyText);
    }
}
