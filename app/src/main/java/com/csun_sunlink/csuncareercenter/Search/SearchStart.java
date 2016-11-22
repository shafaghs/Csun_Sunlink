package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.csun_sunlink.csuncareercenter.R;

public class SearchStart extends AppCompatActivity {
    ListView listView;
    private String searchKey;
    private View rootView;
    private Context ctx;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_start);

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        searchView = (SearchView) findViewById(R.id.search_start_searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                searchKey = searchView.getQuery().toString();
                SearchBgTask bgTask = new SearchBgTask(ctx, rootView);
                bgTask.execute(searchKey);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        });

        ListView result = (ListView) findViewById(R.id.search_result_list);
        result.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                String jobId= ((TextView) view.findViewById(R.id.search_job_id)).getText().toString();
                bundle.putString("jobId",jobId);
                String address= ((TextView) view.findViewById(R.id.search_company_area)).getText().toString();
                bundle.putString("address",address);
                String postedDate= ((TextView) view.findViewById(R.id.search_posted_date)).getText().toString();
                bundle.putString("postedDate",postedDate);
                Intent intent = new Intent(getApplication(), SearchDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
