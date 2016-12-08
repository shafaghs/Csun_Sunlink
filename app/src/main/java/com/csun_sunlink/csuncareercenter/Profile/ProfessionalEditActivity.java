package com.csun_sunlink.csuncareercenter.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.csun_sunlink.csuncareercenter.MenuDrawerAdapter;
import com.csun_sunlink.csuncareercenter.R;

/**
 * Created by bigmatt76 on 12/7/16.
 */

public class ProfessionalEditActivity extends AppCompatActivity  {

    //Values:
    UserProfessional currUser;

    private Context ctx;
    //Edit Text:
    EditText ps;
    EditText exp;
    EditText skills;
    EditText projects;

    Button save;
    //Drawer
    private Toolbar toolbar;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currUser = (UserProfessional) getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_profile_edit_professional);

        //Personal Statmenet:
        ps = (EditText) findViewById(R.id.edit_ps_field);
        ps.setText(currUser.getPS(), TextView.BufferType.EDITABLE);

        //Experience:
        exp = (EditText) findViewById(R.id.edit_exp_field);
        exp.setText(currUser.getEXP(), TextView.BufferType.EDITABLE);

        //Skills:
        skills = (EditText) findViewById(R.id.edit_skills_field);
        skills.setText(currUser.getSkills(), TextView.BufferType.EDITABLE);

        //Projects:
        projects= (EditText) findViewById(R.id.edit_projects_field);
        projects.setText(currUser.getProjects(), TextView.BufferType.EDITABLE);


        //Save Button:
        save = (Button) findViewById(R.id.save_proff);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent intent = new Intent(ProfessionalEditActivity.this, ProfileActivity.class);
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
    }

    public void save() {

        currUser.setPS(ps.getText().toString());
        currUser.setEXP(exp.getText().toString());
        currUser.setProjects(projects.getText().toString());
        currUser.setSkills(skills.getText().toString());
        ProfileEditBgTask bgTask = new ProfileEditBgTask(ctx);
        bgTask.setProfessional(currUser);
        bgTask.execute("editProfessional");


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

}

