package com.csun_sunlink.csuncareercenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.csun_sunlink.csuncareercenter.Fragments.HomePageEventListingFragment;
import com.csun_sunlink.csuncareercenter.Fragments.HomePageJobListingFragment;
import com.csun_sunlink.csuncareercenter.Profile.ProfileActivity;
import com.csun_sunlink.csuncareercenter.Search.SearchStart;

/**
 * Created by olgak on 11/7/16.
 */

public class HomePage extends AppCompatActivity{

    //Variables:
    boolean floatingMenu; //true - jobs, false - events

    // InfoSessions:
    ImageAdapter mImageAdapter;
    ViewPager mViewPager;

    //Buttons:
    ImageButton prflButton;
    ImageButton srchButton;
    ImageButton careerButton;
    ImageButton rsrcButton;
    ImageButton sttngButton;

    //Buttons for floating menus:
    Button eventcategories;
    Button jobcategories;

    //Drawer
    private Toolbar toolbar;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    Context context;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Toolbar
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/

        //Infosessions:
        mImageAdapter = new ImageAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mImageAdapter);


        //Main Menu Buttons:
        //Profile:
        prflButton =(ImageButton)findViewById(R.id.profile_button);
        prflButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        //Search:
        srchButton =(ImageButton)findViewById(R.id.search_button);
        srchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),SearchStart.class);
                startActivity(intent);
            }
        });
        //Career Center:
        careerButton =(ImageButton)findViewById(R.id.careercenter_button);
        careerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(HomePage.this, CareerCenterActivity.class);
                // startActivity(intent);
            }
        });
        //Resources:
        rsrcButton = (ImageButton)findViewById(R.id.resources_button);
        rsrcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(HomePage.this, ResourcesActivity.class);
                // startActivity(intent);
            }
        });
        //Settings:
        sttngButton =(ImageButton)findViewById(R.id.settings_button);
        sttngButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(HomePage.this, SettingsActivity.class);
                // startActivity(intent);
            }
        });

        //Upcoming Events:
        eventcategories = (Button)findViewById(R.id.eventsc_button);
        eventcategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingMenu = false;
                showMenu(v);

            }
        });


        //Job Posts:
        jobcategories = (Button)findViewById(R.id.jobc_button);
        jobcategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingMenu = true;
                showMenu(v);
            }
        });
        //Fragment:
        HomePageJobListingFragment jobListing=new HomePageJobListingFragment();
        HomePageEventListingFragment eventListing=new HomePageEventListingFragment();

        FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction=manager.beginTransaction();//create an instance of Fragment-transaction

        transaction.add(R.id.home_page_job_listing, jobListing, "Job Listing");
        transaction.add(R.id.home_page_event_listing, eventListing, "Event Listing");
        transaction.commit();

        //DRAWER:
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MenuDrawerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);



    }


    public void showMenu(View v) {
        Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenu);
        PopupMenu popup = new PopupMenu(wrapper, v);
        MenuInflater inflater = popup.getMenuInflater();
        //popup.setOnMenuItemClickListener(this);
        if (!floatingMenu) {
            inflater.inflate(R.menu.eventcategoriesmenuhomepage, popup.getMenu());
        }
        else {
            inflater.inflate(R.menu.jobcategoriesmenuhomepage, popup.getMenu());
        }
        popup.show();
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