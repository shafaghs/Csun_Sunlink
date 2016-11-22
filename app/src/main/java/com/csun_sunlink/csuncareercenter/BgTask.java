package com.csun_sunlink.csuncareercenter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import android.content.SharedPreferences;


public class BgTask extends AsyncTask<String, Void, String> {

    private Context ctx;
    private View rootView;
    String userName="",userEmail="",userPass="";
    //IPV4:192.168.0.4

    private final String loginUrl = "http://10.0.2.2/CsunSunlink/login.php";
    private final String signUpUrl = "http://10.0.2.2/CsunSunlink/signUp.php";
    private final String registerUrl = "http://10.0.2.2/CsunSunlink/register.php";

    BgTask(Context ctx, View rootView) {
        this.ctx = ctx;
        this.rootView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        String method="", response = "", line,emailAdd="";
        method = params[0];
        switch (method) {
            case "signUp":
                userName = params[1];
                userPass = params[2];
                try {
                    URL url = new URL(signUpUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
                            URLEncoder.encode("userPass", "UTF-8") + "=" + URLEncoder.encode(userPass, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream Is = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Is, "iso-8859-1"));
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    Is.close();
                    return response;
                } catch (IOException e) {
                    Log.i("error", e.toString());
                }
                break;
            case "register":
                userEmail = params[1];
                userPass = params[2];
                try {
                    URL url = new URL(registerUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String date = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
                    String data = URLEncoder.encode("userEmail", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8") + "&" +
                            URLEncoder.encode("userPass", "UTF-8") + "=" + URLEncoder.encode(userPass, "UTF-8")+ "&" +
                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
                    Log.i("data",userEmail+","+userPass+","+date);
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream Is = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Is, "iso-8859-1"));
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    Is.close();
                    Log.i("error", response);
                    return response;
                } catch (IOException e) {
                    Log.i("error", e.toString());
                }
                break;
            case "logIn":
                userName = params[1];
                userPass = params[2];
                try {
                    URL url = new URL(loginUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
                            URLEncoder.encode("userPass", "UTF-8") + "=" + URLEncoder.encode(userPass, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream Is = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Is, "iso-8859-1"));
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    Is.close();
                    Log.i("response",response);
                    return response;
                } catch (IOException e) {
                    Log.i("response",e.toString());
                }
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();
        switch (result) {
            case "redundantUser":
                EditText signUpEmailError = (EditText) rootView.findViewById(R.id.sign_up_email);
                signUpEmailError.setError("This email address is already registered, use sign in page");
                break;
            case "validEmail":
                intent = new Intent(ctx, SignUpContinue.class);
                intent.putExtra("EmailAddress", userName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                break;
            case "registeredSuccessfully":
                intent = new Intent(ctx, HomePage.class);
                intent.putExtra("EmailAddress", userEmail);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                break;
            case "invalidUser":
                TextView errorText = (TextView) rootView.findViewById(R.id.signin_error);
                errorText.setText(R.string.invalid_userPass);
                break;
            case "validUser":
                intent = new Intent(ctx, HomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                editor.putString("user_email", userName);
                editor.apply();
                ctx.startActivity(intent);
                break;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
    }
}
