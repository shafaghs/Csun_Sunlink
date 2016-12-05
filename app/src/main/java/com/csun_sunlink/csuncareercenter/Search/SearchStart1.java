package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import com.csun_sunlink.csuncareercenter.R;

import static android.graphics.Color.WHITE;

public class SearchStart1 extends AppCompatActivity {

    private static String userId;
    private static Context ctx;
    private SearchView searchView;
    private String searchKey;
    private View rootView;
    SectionsPagerAdapter mSectionsPagerAdapter;
    String method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_start1);

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorHeight(10);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    Log.w("alarm","0");
                }
                if(tab.getPosition()==1){
                    Log.w("alarm","1");
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
                  if(tab.getPosition()==0){
                      Log.w("alarm","00");
                  }
                  if(tab.getPosition()==1){
                      Log.w("alarm","11");
                      method = "showSavedJob";
                      SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
                      bgTask.execute(method, userId);
                  }
             }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = pref.getString("user_id", "");

        searchView = (SearchView) findViewById(R.id.search_start_searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                String method = "searchJob";
                searchKey = searchView.getQuery().toString();
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_search_start1, container, false);
            ListView result = (ListView) rootView.findViewById(R.id.search_result_list1);
            result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle bundle = new Bundle();
                    String companyId = ((TextView) view.findViewById(R.id.search_company_id)).getText().toString();
                    bundle.putString("companyId", companyId);
                    String jobId = ((TextView) view.findViewById(R.id.search_job_id)).getText().toString();
                    bundle.putString("jobId", jobId);
                    String address = ((TextView) view.findViewById(R.id.event_location)).getText().toString();
                    bundle.putString("address", address);
                    String postedDate = ((TextView) view.findViewById(R.id.search_posted_date)).getText().toString();
                    bundle.putString("postedDate", postedDate);
                    Intent intent = new Intent(ctx, SearchDetail.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            int argNum = getArguments().getInt(ARG_SECTION_NUMBER);
            String method;
            switch (argNum) {
                case 1:

                    break;
                case 2:
                    method = "showSavedJob";
                    SearchStartBgTask bgTask = new SearchStartBgTask(ctx, rootView);
                    bgTask.execute(method, userId);
                    break;
            }
            //  textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Result";
                case 1:
                    return "Saved Jobs";

            }
            return null;
        }
    }
}
