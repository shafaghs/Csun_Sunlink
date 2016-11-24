package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.csun_sunlink.csuncareercenter.MenuDrawerAdapter;
import com.csun_sunlink.csuncareercenter.R;

public class SearchStart extends AppCompatActivity {
    ListView listView;
    private String searchKey;
    private View rootView;
    private Context ctx;
    private SearchView searchView;

    //Drawer
    private Toolbar toolbar;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;

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

        //DRAWER:
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MenuDrawerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar, R.string.openDrawer,
                R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
    }
}
