package com.csun_sunlink.csuncareercenter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class BgTask extends AsyncTask<String, Void, String> {
    private Context ctx;
    private View rootView;
    private String userEmail,userPass,firstName,familyName,middleName;

    BgTask(Context ctx, View rootView) {
        this.ctx = ctx;
        this.rootView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        final String signUpUrl = "http://10.0.2.2/CsunSunlink/signUp.php";
        final String registerUrl = "http://10.0.2.2/CsunSunlink/register.php";
        final String loginUrl = "http://10.0.2.2/CsunSunlink/login.php";
        String method;
        String result;

        method = params[0];
        switch (method) {
            case "signUp":
                userEmail = params[1];
                userPass = params[2];
                try {
                    URL url = new URL(signUpUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("userEmail", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((result = bufferedReader.readLine()) != null) {
                        stringBuilder.append(result).append("\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    urlConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (IOException e) {
                    Log.i("error", e.toString());
                }
                break;
            case "register":
                userEmail = params[1];
                userPass = params[2];
                firstName = params[3];
                familyName = params[4];
                middleName = params[5];
                try {
                    URL url = new URL(registerUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String date = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
                    String data = URLEncoder.encode("userEmail", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8") + "&" +
                            URLEncoder.encode("userPass", "UTF-8") + "=" + URLEncoder.encode(userPass, "UTF-8") + "&" +
                            URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") + "&" +
                            URLEncoder.encode("familyName", "UTF-8") + "=" + URLEncoder.encode(familyName, "UTF-8") + "&" +
                            URLEncoder.encode("middleName", "UTF-8") + "=" + URLEncoder.encode(middleName, "UTF-8") + "&" +
                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((result = bufferedReader.readLine()) != null) {
                        stringBuilder.append(result).append("\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    urlConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (IOException e) {
                    Log.i("error", e.toString());
                }
                break;
            case "logIn":
                userEmail = params[1];
                userPass = params[2];
                try {
                    URL url = new URL(loginUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8") + "&" +
                            URLEncoder.encode("userPass", "UTF-8") + "=" + URLEncoder.encode(userPass, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((result = bufferedReader.readLine()) != null) {
                        stringBuilder.append(result).append("\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    urlConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (IOException e) {
                    Log.i("response", e.toString());
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
        String method = "empty", firstName, familyName,email,userId;
        JSONObject jsonObj;
        JSONObject jsonObject=null;
        JSONArray jsonArray;
        try {
            jsonObj = new JSONObject(result);
            jsonArray = jsonObj.getJSONArray("server_res");
            jsonObject = jsonArray.getJSONObject(0);
            method = jsonObject.getString("result_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (method) {
            case "redundantUser":
                TextView signUpEmailError = (TextView) rootView.findViewById(R.id.sign_up_email_error);
                signUpEmailError.setVisibility(View.VISIBLE);
                signUpEmailError.setText(R.string.redundant_email);
                break;
            case "validEmail":
                intent = new Intent(ctx, SignUpContinue.class);
                intent.putExtra("EmailAddress", userEmail);
                intent.putExtra("Password",userPass);
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
                TextView errorText = (TextView) rootView.findViewById(R.id.sign_in_error);
                errorText.setVisibility(View.VISIBLE);
                errorText.setText(R.string.invalid_userPass);
                break;
            case "validUser":
                intent = new Intent(ctx, HomePage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    firstName = jsonObject.getString("first_name");
                    familyName = jsonObject.getString("family_name");
                    email = jsonObject.getString("user_email");
                    userId = jsonObject.getString("user_id") ;
                    editor.putString("user_email", email);
                    editor.putString("first_name", firstName);
                    editor.putString("family_name", familyName);
                    editor.putString("user_id", userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.apply();
                ctx.startActivity(intent);
                break;
            case "empty":
                Log.w("invalid", "invalid query");
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
