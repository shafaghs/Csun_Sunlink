package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.csun_sunlink.csuncareercenter.MenuDrawerAdapter;
import com.csun_sunlink.csuncareercenter.R;

public class SearchDetail extends AppCompatActivity {
    private TextView jobTitle, companyName, positionType, companyAdd, postedDate, jobDes, jobDuties, essentialSkills, desiredSkills;
    private Button saveJob, applyJob;
    String jobId, address, method, differenceDate;
    private View rootView;
    private Context ctx;
    private String companyId;

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
        setContentView(R.layout.activity_search_detail);

        jobTitle = (TextView) findViewById(R.id.search_detail_job_title);
        companyName = (TextView) findViewById(R.id.search_detail_company_name);
        positionType = (TextView) findViewById(R.id.search_detail_position_type);
        companyAdd = (TextView) findViewById(R.id.search_detail_company_add);
        postedDate = (TextView) findViewById(R.id.search_detail_posted_date);
        jobDes = (TextView) findViewById(R.id.search_detail_job_des_detail);
        jobDuties = (TextView) findViewById(R.id.search_detail_job_duty_detail);
        essentialSkills = (TextView) findViewById(R.id.search_detail_ess_detail);
        desiredSkills = (TextView) findViewById(R.id.search_detail_des_detail);

        saveJob = (Button) findViewById(R.id.search_detail_save_button);
        applyJob = (Button) findViewById(R.id.search_detail_apply_button);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String userId = pref.getString("user_id", "");

        jobId = getIntent().getExtras().getString("jobId");
        address = getIntent().getExtras().getString("address");
        differenceDate = getIntent().getExtras().getString("postedDate");
        companyId = getIntent().getExtras().getString("companyId");

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        saveJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userId.equals("") && !jobId.equals("")) {
                    if (saveJob.getText().toString().equals("Save to Favorite")) {
                        method = "saveJob";
                        SearchDetailBgTask bgTask = new SearchDetailBgTask(ctx, rootView);
                        bgTask.execute(method,jobId,userId);
                    }
                    if (saveJob.getText().toString().equals("Unsave")) {
                        method = "unSaveJob";
                        SearchDetailBgTask bgTask = new SearchDetailBgTask(ctx, rootView);
                        bgTask.execute(method,jobId,userId);
                    }
                }
            }
        });

        applyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (!jobId.equals("") && !userId.equals("")) {
            method = "showJobDetail";
            SearchDetailBgTask bgTask = new SearchDetailBgTask(ctx, rootView);
            bgTask.execute(method, jobId, userId, address, differenceDate);
        }

        //DRAWER:
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MenuDrawerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer,
                R.string.closeDrawer) {

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
        Drawer.addDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();
    }
}
