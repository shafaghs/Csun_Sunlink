package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.csun_sunlink.csuncareercenter.R;

public class SearchStart2 extends AppCompatActivity {

    private static String userId;
    private static Context ctx;
    private SearchView searchView;
    private String searchKey;
    private View rootView;
    private String method;
    private SharedPreferences pref;

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                String method = "searchJob";
                searchKey = searchView.getQuery().toString();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("searchKey",searchKey);
                editor.apply();
                SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
                bgTask.execute(method, searchKey);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        });
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
}