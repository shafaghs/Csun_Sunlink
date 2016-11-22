package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.csun_sunlink.csuncareercenter.R;

public class SearchDetail extends AppCompatActivity {
    private TextView jobTitle, companyName, positionType, companyAdd, postedDate, jobDes, jobDuties, essentialSkills, desiredSkills;
    private Button saveJob, applyJob;
    String jobId, address,method, differenceDate;
    private View rootView;
    private Context ctx;

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

        saveJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        applyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        jobId = getIntent().getExtras().getString("jobId");
        address = getIntent().getExtras().getString("address");
        differenceDate = getIntent().getExtras().getString("postedDate");


        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        if (!jobId.equals("")) {
            SearchDetailBgTask bgTask = new SearchDetailBgTask(ctx, rootView);
            bgTask.execute(jobId,address,differenceDate);
        }
    }
}
