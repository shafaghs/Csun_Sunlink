package com.csun_sunlink.csuncareercenter.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.csun_sunlink.csuncareercenter.MenuDrawerAdapter;
import com.csun_sunlink.csuncareercenter.R;

/**
 * Created by bigmatt76 on 11/28/16.
 */

public class ProfileActivity extends AppCompatActivity {

    String currID;
    //Fragments
    FragmentTransaction transaction;
    ProfilePersonalFragment personalF;
    ProfileProfessionalFragment profF;

    UserPersonal currPersonal;
    UserProfessional currPF;

    //Buttons:
    Button edit;

    //Drawer
    private Toolbar toolbar;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //Fragments:
        personalF = new ProfilePersonalFragment();
        ProfileAcademicFragment academicF = new ProfileAcademicFragment();
        profF = new ProfileProfessionalFragment();

        FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager
        transaction=manager.beginTransaction();//create an instance of Fragment-transaction

        transaction.add(R.id.personal_frame_layout, personalF, "Personal");
        transaction.add(R.id.academic_frame_layout,academicF, "Academic");
        transaction.add(R.id.professional_frame_layout, profF, "Professsional");
        transaction.commit();

        //TOP BAR:
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String fName = pref.getString("first_name","");
        String lName = pref.getString("family_name","");
        String name = fName + " " +lName;
        String email = pref.getString("user_email","");
        currID = pref.getString("user_id","");
        TextView newName = (TextView) findViewById(R.id.header_name_profile);
        newName.setText(name);
        TextView newEmail = (TextView) findViewById(R.id.header_degree_profile);
        newEmail.setText(email);
        //Edit Buttons:

        //Profile:
        edit =(Button)findViewById(R.id.edit_profile);
        edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showMenu(v);

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
    public void removeFragment(Fragment fragment){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    public void editPersonal() {

        currPersonal = personalF.getUserCurr();
        currPersonal.setUserID(currID);
        Intent intent = new Intent(ProfileActivity.this, PersonalEditActivity.class);
        intent.putExtra("user", currPersonal);
        startActivity(intent);

    }

    public void editProfessional() {

        currPF = profF.getUserCurrPF();
        currPF.setUserID(currID);
        Intent intent = new Intent(ProfileActivity.this, ProfessionalEditActivity.class);
        intent.putExtra("user", currPF);
        startActivity(intent);

    }
    public void showMenu(View v) {
        Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenu);
        PopupMenu popup = new PopupMenu(wrapper, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.editprofilemenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.pmenu_personal:
                        editPersonal();
                        return true;
                    case R.id.pmenu_professional:
                        editProfessional();
                        return true;
                    default:
                        return false;
                }
            }
        });
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