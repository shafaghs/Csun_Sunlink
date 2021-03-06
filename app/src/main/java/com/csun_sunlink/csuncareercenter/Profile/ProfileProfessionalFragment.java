package com.csun_sunlink.csuncareercenter.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.csun_sunlink.csuncareercenter.R;

/**
 * Created by bigmatt76 on 11/29/16.
 */

public class ProfileProfessionalFragment extends Fragment {

    private View rootView;
    private Context ctx;
    protected UserProfessional userCurr;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_listing_home_page, container, false);
        // Inflate the layout for this fragment
        ListView listView =(ListView) view.findViewById(R.id.job_listing_fragment);
        ctx = getActivity().getApplicationContext();

        ProfileBgTask bgTask = new ProfileBgTask(ctx, listView);
        bgTask.execute("professionalFragment");
        userCurr = bgTask.getUserProfessional();
        return view;
    }
    public UserProfessional getUserCurrPF(){
        return this.userCurr;
    }
}
