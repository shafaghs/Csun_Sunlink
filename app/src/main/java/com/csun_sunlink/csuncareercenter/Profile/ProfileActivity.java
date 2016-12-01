package com.csun_sunlink.csuncareercenter.Profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.csun_sunlink.csuncareercenter.R;

/**
 * Created by bigmatt76 on 11/28/16.
 */

public class ProfileActivity extends AppCompatActivity {

    //Fragments
    FragmentTransaction transaction;
    ProfilePersonalFragment personalF;

    //Buttons:
    Button editPersonal;
    Button editAcademic;
    Button editProfessional;

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
        setContentView(R.layout.activity_profile_page);

        //Fragments:
        personalF = new ProfilePersonalFragment();
        ProfileAcademicFragment academicF = new ProfileAcademicFragment();
        ProfileProfessionalFragment profF = new ProfileProfessionalFragment();

        FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager
        transaction=manager.beginTransaction();//create an instance of Fragment-transaction

        transaction.add(R.id.personal_frame_layout, personalF, "Personal");
        transaction.add(R.id.academic_frame_layout,academicF, "Academic");
        transaction.add(R.id.professional_frame_layout, profF, "Professsional");
        transaction.commit();

        //Edit Buttons:

        //Profile:
        editPersonal =(Button)findViewById(R.id.edit_personal);
        editPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               removeFragment(personalF);
                // Intent intent = new Intent(HomePage.this, ProfileActivity.class);
                // startActivity(intent);
            }
        });
        //Academic:
        editAcademic =(Button)findViewById(R.id.edit_academic);
        editAcademic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(HomePage.this, ProfileActivity.class);
                // startActivity(intent);
            }
        });

        //Professional:
        editProfessional =(Button)findViewById(R.id.edit_personal);
        editProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(HomePage.this, ProfileActivity.class);
                // startActivity(intent);
            }
        });



        //DRAWER:
       /* mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
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
        Drawer.addDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        */
    }
    public void removeFragment(Fragment fragment){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

}