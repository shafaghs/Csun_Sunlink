package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.csun_sunlink.csuncareercenter.MenuDrawerAdapter;
import com.csun_sunlink.csuncareercenter.R;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;

public class SearchStart2 extends AppCompatActivity {

    private static String userId;
    private static Context ctx;
    private SearchView searchView;
    private String searchKey;
    private View rootView;
    private String method;
    private SharedPreferences pref;
    //Drawer
    private Toolbar toolbar;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_start2);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = pref.getString("user_id", "");
        searchKey = pref.getString("searchKey","");

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Result"));
        tabLayout.addTab(tabLayout.newTab().setText("Saved Jobs"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorHeight(10);
        tabLayout.setTabTextColors(BLUE,WHITE);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        if(viewPager.getCurrentItem()==0 && !searchKey.equals("")){
            method = "searchJob";
            SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
            bgTask.execute(method, searchKey);
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    searchKey = pref.getString("searchKey","");
                    if(!searchKey.equals("")){
                        method = "searchJob";
                        SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
                        bgTask.execute(method, searchKey);
                    }
                }
                if(tab.getPosition()==1){
                    method = "showSavedJob";
                    SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
                    bgTask.execute(method, userId);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchView = (SearchView) findViewById(R.id.search_start_searchView);
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {return false;}

            @Override
            public boolean onQueryTextSubmit(String query) {
                String method = "searchJob";
                searchKey = searchView.getQuery().toString();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("searchKey",searchKey);
                editor.apply();
                SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
                bgTask.execute(method, searchKey);
                return false;
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

    @Override
    protected void onResume() {
        super.onResume();
        searchKey = pref.getString("searchKey","");
        if(!searchKey.equals("") && !userId.equals("")){
            Log.w("onresume",searchKey);
            method = "searchJob";
            SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
            bgTask.execute(method, searchKey);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w("onrestart",searchKey);
        searchKey = pref.getString("searchKey","");
        if(!searchKey.equals("")){
            method = "searchJob";
            SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
            bgTask.execute(method, searchKey);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("onstart",searchKey);
        searchKey = pref.getString("searchKey","");
        if(!searchKey.equals("")){
            method = "searchJob";
            SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
            bgTask.execute(method, searchKey);
        }
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