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
 * Created by bigmatt76 on 12/3/16.
 */

public class PersonalEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Values:
    UserPersonal currUser;

    private Context ctx;
    //Edit Text:
    EditText fName;
    EditText mName;
    EditText lName;
    EditText phone;
    EditText address;
    TextView geoNew;
    TextView statusNew;
    TextView workNew;
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

        //Status:
        statusNew = (TextView)findViewById(R.id.status_text_view);
        statusNew.setText(currUser.getStatus());
        Spinner statusspinner = (Spinner) findViewById(R.id.status_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusspinner.setAdapter(adapter1);

        statusspinner.setOnItemSelectedListener(this);

        //Geo Preference:
        geoNew = (TextView)findViewById(R.id.state_text_view);
        geoNew.setText(currUser.getGeopref());
        Spinner geospinner = (Spinner) findViewById(R.id.state_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.states, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        geospinner.setAdapter(adapter2);
        geospinner.setOnItemSelectedListener(this);

        //Work Authorization:
        workNew = (TextView)findViewById(R.id.work_auth_text_view);
        workNew.setText(currUser.getWorkAuth());
        Spinner workAspinner = (Spinner) findViewById(R.id.workauth_spinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.work_auth_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workAspinner.setAdapter(adapter3);
        workAspinner.setOnItemSelectedListener(this);


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

        ProfileEditBgTask bgTask = new ProfileEditBgTask(ctx);
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

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Spinner spinner = (Spinner) parent;
        switch(spinner.getId()) {
            case R.id.status_spinner:
                currUser.setStatus(spinner.getItemAtPosition(pos).toString());
                statusNew.setText(currUser.getStatus());
                break;
            case R.id.state_spinner:
                currUser.setGeoPref(spinner.getItemAtPosition(pos).toString());
                geoNew.setText(currUser.getGeopref());
                break;
            case R.id.workauth_spinner:
                currUser.setWorkAuth(spinner.getItemAtPosition(pos).toString());
                workNew.setText(currUser.getWorkAuth());
                break;
            default:
                break;
        }
    }


    public void onNothingSelected(AdapterView<?> parent) {

    }



}
