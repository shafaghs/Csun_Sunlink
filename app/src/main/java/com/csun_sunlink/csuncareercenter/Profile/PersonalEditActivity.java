package com.csun_sunlink.csuncareercenter.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csun_sunlink.csuncareercenter.HomePage;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currUser = (UserPersonal) getIntent().getSerializableExtra("user");
        setContentView(R.layout.profile_edit_fragment_layout);

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






}
