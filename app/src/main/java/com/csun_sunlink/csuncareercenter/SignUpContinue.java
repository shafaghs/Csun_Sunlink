package com.csun_sunlink.csuncareercenter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SignUpContinue extends AppCompatActivity {
    String signUpPasswordText,signUpConfirmPasswordText,signUpEmailText;
    TextView error;
    EditText signUpPassword,signUpConfirmPassword;
    private Context ctx;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_continue);

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        Bundle extras = getIntent().getExtras();

        TextView email = (TextView) findViewById(R.id.sign_up_con_email);
        if(extras != null){
            email.setText(extras.getString("EmailAddress"));
            signUpEmailText = extras.getString("EmailAddress");
        }

        Button submitButton = (Button) findViewById(R.id.sign_up_con_submit);
        signUpPassword = (EditText) findViewById(R.id.sign_up_con_password);
        signUpConfirmPassword = (EditText) findViewById(R.id.sign_up_con_confirm_password);
        error = (TextView) findViewById(R.id.sign_up_con_error);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpPasswordText = signUpPassword.getText().toString().trim();
                signUpConfirmPasswordText = signUpConfirmPassword.getText().toString().trim();
                if(signUpPasswordText.equals("") || signUpConfirmPasswordText.equals("")){
                    error.setText(R.string.Both_filled);
                    error.setTextColor(Color.RED);
                }else if(!signUpPasswordText.equals(signUpConfirmPasswordText)){
                    error.setText(R.string.same_value);
                }else{
                    String method = "register";
                    BgTask bgTask = new BgTask(ctx,rootView);
                    bgTask.execute(method,signUpEmailText,signUpPasswordText);
                }
            }
        });

    }
}
