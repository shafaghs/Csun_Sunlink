package com.csun_sunlink.csuncareercenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by olgak on 12/8/16.
 */

public class EventDetail extends AppCompatActivity {
    private TextView eventTitle, eventLocation, eventDate, eventTime, eventInfo;
    private Button saveEvent, gotoWebsite;
    String jobId, address,method, differenceDate;
    private View rootView;
    private Context ctx;

    private String eventId;

    //Drawer

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        eventId = getIntent().getExtras().getString("eventId");
        String title = getIntent().getExtras().getString("eventTitle");
        String date = getIntent().getExtras().getString("eventDate");
        String[] newD =splitDate(date);
        String location = getIntent().getExtras().getString("eventLocation");

        eventTitle = (TextView) findViewById(R.id.search_detail_job_title);
        eventTitle.setText(title);
        eventLocation = (TextView) findViewById(R.id.search_detail_company_name);
        eventLocation.setText(location);
        eventDate = (TextView) findViewById(R.id.search_detail_position_type);
        eventDate.setText(newD[0]);
        eventTime = (TextView) findViewById(R.id.search_detail_company_add);
        eventTime.setText("At: " + newD[1]);
        eventInfo= (TextView) findViewById(R.id.search_detail_job_description);
        /*jobDes = (TextView) findViewById(R.id.search_detail_job_des_detail);
        jobDuties = (TextView) findViewById(R.id.search_detail_job_duty_detail);
        essentialSkills = (TextView) findViewById(R.id.search_detail_ess_detail);
        desiredSkills = (TextView) findViewById(R.id.search_detail_des_detail); */

        saveEvent = (Button) findViewById(R.id.search_detail_save_button);
        gotoWebsite = (Button) findViewById(R.id.search_detail_apply_button);
        gotoWebsite.setText("More Info");
        gotoWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse("http://www.csun.edu/engineering-computer-science/techfest-information"));
                startActivity(openURL);
            }
        });;

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String userId = pref.getString("user_id","");



        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userId.equals("")){

                }
            }
        });

        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*if (!jobId.equals("")) {
            SearchDetailBgTask bgTask = new SearchDetailBgTask(ctx, rootView);
            bgTask.execute(jobId,address,differenceDate,companyId);
        } */

        //DRAWER:
       mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MenuDrawerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
    }
    //Toolbar Drawer Button:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.drawer_b:
                if(Drawer.isDrawerOpen(Gravity.LEFT))
                    Drawer.closeDrawers();
                else
                    Drawer.openDrawer(Gravity.LEFT);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String[] splitDate(String date) {
        String[] parts = date.split(" ");
        return parts;
    }
}

