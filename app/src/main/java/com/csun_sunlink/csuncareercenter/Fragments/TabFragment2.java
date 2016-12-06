package com.csun_sunlink.csuncareercenter.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.csun_sunlink.csuncareercenter.R;
import com.csun_sunlink.csuncareercenter.Search.SearchDetail;

public class TabFragment2 extends Fragment {
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_2, container, false);
        ctx = getActivity().getApplicationContext();

        ListView result = (ListView) rootView.findViewById(R.id.search_result_list_tab2);

        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                String companyId = ((TextView) view.findViewById(R.id.search_company_id)).getText().toString();
                bundle.putString("companyId", companyId);
                String jobId = ((TextView) view.findViewById(R.id.search_job_id)).getText().toString();
                bundle.putString("jobId", jobId);
                String address = ((TextView) view.findViewById(R.id.event_location)).getText().toString();
                bundle.putString("address", address);
                String postedDate = ((TextView) view.findViewById(R.id.search_posted_date)).getText().toString();
                bundle.putString("postedDate", postedDate);
                Intent intent = new Intent(ctx, SearchDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return rootView;
    }
}