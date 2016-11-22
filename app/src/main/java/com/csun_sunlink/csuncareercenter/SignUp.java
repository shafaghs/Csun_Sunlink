package com.csun_sunlink.csuncareercenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private EditText signUpEmail,signUpPassword;
    String signUpEmailText,signUpPasswordText,method="";
    private Context ctx;
    private View rootView;

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "my.csun.edu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        signUpEmail = (EditText) findViewById(R.id.sign_up_email);
        signUpPassword = (EditText) findViewById(R.id.sign_up_password);

        Button signUpSubmit = (Button) findViewById(R.id.sign_up_submit);

        signUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pattern = Pattern.compile(EMAIL_PATTERN);
                signUpEmailText = signUpEmail.getText().toString().trim();
                signUpPasswordText = signUpPassword.getText().toString().trim();
                if(signUpEmailText.equals("") && signUpPasswordText.equals("")){
                    signUpEmail.setError("You should fill email address");
                    signUpEmail.setBackgroundResource(R.drawable.error);
                    signUpPassword.setError("You should fill password");
                    signUpPassword.setBackgroundResource(R.drawable.error);
                }
                else if(signUpEmailText.equals("")){
                    signUpEmail.setError("You should fill email address");
                    signUpEmail.setBackgroundResource(R.drawable.error);
                }
                else if(signUpPasswordText.equals("")){
                    signUpPassword.setError("You should fill password");
                    signUpPassword.setBackgroundResource(R.drawable.error);
                }
                else if(!checkFormat(signUpEmailText)){
                    signUpEmail.setError("Your email format is not correct, it should be abc@my.csun.edu");
                    signUpEmail.setBackgroundResource(R.drawable.error);
                }
                else if (!signUpPasswordText.equals("123")){
                    signUpPassword.setError("Password is not correct, try again");
                    signUpPassword.setBackgroundResource(R.drawable.error);
                }
                else{
                    signUpEmail.setBackgroundResource(R.drawable.normal);
                    signUpPassword.setBackgroundResource(R.drawable.normal);
                    method = "signUp";
                    BgTask bgTask = new BgTask(ctx,rootView);
                    bgTask.execute(method,signUpEmailText,signUpPasswordText);
                }
            }
        });

    }

    boolean checkFormat(String emailText){
        matcher = pattern.matcher(emailText);
        return matcher.matches();
    }
}
