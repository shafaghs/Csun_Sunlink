package com.csun_sunlink.csuncareercenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.csun_sunlink.csuncareercenter.Fragments.HomePageJobListingFragment;
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

    //private final CURRENTUSER = getCurrentUser();
    /*this is data to test home page class*/
    private String userName = "Matt Ross";
    private String userDegree = "Computer Science";
    private String screenHeader;
    private String event1 = "Event1";
    private String date1 ="11/1/16";
    private String event2 = "Event2";
    private String date2 ="11/2/16";
    private String event3 = "Event3";
    private String date3 ="11/3/16";

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

    String TITLES[] = {"Home","Profile","Search","My Career Center","Resources","Settings"};
    int ICONS[] = {R.drawable.ic_home_white_48dp,R.drawable.ic_person_white_48dp,R.drawable.ic_search_white_48dp,R.drawable.ic_business_center_white_48dp,R.drawable.ic_business_center_white_48dp, R.drawable.ic_settings_white_48dp};

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Olga Kup";
    String EMAIL = "olgakup@yahoo.com";
    int PROFILE = R.drawable.defaultpicture;
    private Toolbar toolbar;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Infosessions:
        mImageAdapter = new ImageAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mImageAdapter);

        // Get Curent User: currentUser = ParseUser.getCurrentUser(); + check if the user !null
        /*createHeader();
        TextView header = (TextView) findViewById(R.id.headerHomePage);
        header.setText(screenHeader);

        //Profile picture:
        //check if no profile picture on file use defualt:

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.defaultpicture);
        Bitmap circularBitmap = ProfileImageDrawable.getRoundedCornerBitmap(bitmap, 100);

        ImageView circularImageView = (ImageView)findViewById(R.id.imageView);
        circularImageView.setImageBitmap(circularBitmap); */

        //Main Menu Buttons:
        //Profile:
        prflButton =(ImageButton)findViewById(R.id.profile_button);
        prflButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(HomePage.this, ProfileActivity.class);
                // startActivity(intent);
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
        FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction=manager.beginTransaction();//create an instance of Fragment-transaction
        transaction.add(R.id.home_page_job_listing, jobListing, "Job Listing");
        transaction.commit();

        //DRAWER:
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MenuDrawerAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        if (!floatingMenu) {
            menuInflater.inflate(R.menu.eventcategoriesmenuhomepage, menu);
        }
        // else {
        //    menuInflater.inflate(R.menu.jobcategoriesmenuhomepage, menu);
        // }

    }

    /*public void createHeader() {
        this.screenHeader = this.userName + "\n" + this.userDegree;
    } */

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
}