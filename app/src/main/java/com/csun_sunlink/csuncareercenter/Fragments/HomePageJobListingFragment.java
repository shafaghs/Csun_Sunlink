package com.csun_sunlink.csuncareercenter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.csun_sunlink.csuncareercenter.R;

public class HomePageJobListingFragment extends Fragment {
    private View rootView;
    private Context ctx;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_listing_home_page, container, false);
        // Inflate the layout for this fragment
        ListView listView =(ListView) view.findViewById(R.id.job_listing_fragment);
        ctx = getActivity().getApplicationContext();

        FragmentBgTask bgTask = new FragmentBgTask(ctx, listView);
        bgTask.execute("jobListing");

        return view;
    }

}

