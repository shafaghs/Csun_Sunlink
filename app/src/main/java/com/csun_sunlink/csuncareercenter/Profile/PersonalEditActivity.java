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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csun_sunlink.csuncareercenter.MenuDrawerAdapter;
import com.csun_sunlink.csuncareercenter.R;

/**
 * Created by bigmatt76 on 12/3/16.
 */

public class PersonalEditActivity extends AppCompatActivity {

    //Values:
    UserPersonal currUser;

    private Context ctx;
    //Edit Text:
    EditText fName;
    EditText mName;
    EditText lName;
    EditText phone;
    EditText address;

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
        currUser = (UserPersonal) getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_profile_edit_personal);

        //First Name:
        fName = (EditText) findViewById(R.id.edit_firstname_field);
        fName.setText(currUser.getFirstName(), TextView.BufferType.EDITABLE);

        //Middle Name:
        mName = (EditText) findViewById(R.id.edit_middlename_field);
        mName.setText(currUser.getMiddleName(), TextView.BufferType.EDITABLE);

        //Last Name:
        lName = (EditText) findViewById(R.id.edit_lastname_field);
        lName.setText(currUser.getLastName(), TextView.BufferType.EDITABLE);

        //Phone:
        phone = (EditText) findViewById(R.id.edit_phone_field);
        phone.setText(currUser.getPhone(), TextView.BufferType.EDITABLE);
        mName = (EditText) findViewById(R.id.edit_middlename_field);

        //Address:
        address = (EditText) findViewById(R.id.edit_address_field);
        address.setText(currUser.getAddress(), TextView.BufferType.EDITABLE);

        //Save Button:
        save = (Button) findViewById(R.id.save_personal);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent intent = new Intent(PersonalEditActivity.this, ProfileActivity.class);
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

        currUser.setFirstName(fName.getText().toString());
        currUser.setMiddleName(mName.getText().toString());
        currUser.setLastName(lName.getText().toString());
        currUser.setPhone(phone.getText().toString());
        currUser.setAddress(address.getText().toString());

        ProfileEditBgTask bgTask = new ProfileEditBgTask();
        bgTask.setUser(currUser);
        bgTask.execute("editPersonalFragment");


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
