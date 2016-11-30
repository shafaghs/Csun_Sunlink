package com.csun_sunlink.csuncareercenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignIn extends AppCompatActivity {
    private EditText signInUserName, signInPassword;
    private Context ctx;
    private View rootView;
    private String method,signInUserNameString, signInPasswordString;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        rootView = findViewById(android.R.id.content);
        ctx = this.getApplicationContext();

        signIn = (Button) findViewById(R.id.signin_signin);
        signInUserName = (EditText) findViewById(R.id.signin_email);
        signInPassword = (EditText) findViewById(R.id.signin_password);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(signInUserName) || isEmpty(signInPassword)) {
                    if (isEmpty(signInUserName)) {
                        signInUserName.setError("You should fill email address");
                        signInUserName.setBackgroundResource(R.drawable.error);
                    }
                    if (isEmpty(signInPassword)) {
                        signInPassword.setError("You should fill password");
                        signInPassword.setBackgroundResource(R.drawable.error);
                    }
                } else {
                    method = "logIn";
                    signInUserNameString = signInUserName.getText().toString().trim();
                    signInPasswordString = signInPassword.getText().toString().trim();
                    BgTask bgTask = new BgTask(ctx,rootView);
                    bgTask.execute(method,signInUserNameString,signInPasswordString);
                }
            }
        });
    }

    public boolean isEmpty(EditText text) {
        return text.getText().toString().trim().length() == 0;
    }
}
