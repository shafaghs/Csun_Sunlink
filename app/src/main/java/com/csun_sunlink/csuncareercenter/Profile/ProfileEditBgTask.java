package com.csun_sunlink.csuncareercenter.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.csun_sunlink.csuncareercenter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import static com.csun_sunlink.csuncareercenter.Profile.ProfileBgTask.setListViewHeightBasedOnItsChildren;

/**
 * Created by olgak on 11/30/16.
 */

public class ProfileEditBgTask extends AsyncTask<String, Void, String> {

    private Context ctx;
    private View rootView;
    ListView listView;
    String searchKey;
    UserPersonal currUser;
    UserProfessional pfUser;

    ProfileEditBgTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        //PHP FILES

        final String personaleditUrl = "http://10.0.2.2/CsunSunlink/editPersonalFragment.php";
        final String professionaleditUrl = "http://10.0.2.2/CsunSunlink/editProfessional.php";

        String result;
        searchKey = params[0];
        switch (searchKey) {
            case "editPersonalFragment":

                try {

                    //Get new ids for the personal info:
                    URL url = new URL(personaleditUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(currUser.getID(), "UTF-8") + "&" +
                                    URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(currUser.getFirstName(), "UTF-8") + "&" +
                                    URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(currUser.getLastName(), "UTF-8") + "&" +
                                    URLEncoder.encode("middleName", "UTF-8") + "=" + URLEncoder.encode(currUser.getMiddleName(), "UTF-8") + "&" +
                                    URLEncoder.encode("phone","UTF-8") + "= " + URLEncoder.encode(currUser.getPhone(), "UTF-8");
                            //URLEncoder.encode("waTitle","UTF-8") + "= " + URLEncoder.encode(currUser.getWorkAuth(), "UTF-8");
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

            case "editProfessional":

                try {

                    //Get new ids for the personal info:
                    URL url = new URL(professionaleditUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(pfUser.getID(), "UTF-8") + "&" +
                                    URLEncoder.encode("ps", "UTF-8") + "=" + URLEncoder.encode(pfUser.getPS(), "UTF-8") + "&" +
                                    URLEncoder.encode("exp", "UTF-8") + "=" + URLEncoder.encode(pfUser.getEXP(), "UTF-8") + "&" +
                                    URLEncoder.encode("skills", "UTF-8") + "=" + URLEncoder.encode(pfUser.getSkills(), "UTF-8") + "&" +
                                    URLEncoder.encode("projects","UTF-8") + "= " + URLEncoder.encode(pfUser.getProjects(), "UTF-8");
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
        }
        return null;
    }

    @Override
    protected void onPostExecute(String finalResult) {

    }


    public void setUser(UserPersonal newUser) {
        this.currUser = newUser;

    }
    public void setProfessional(UserProfessional newUser) {
        this.pfUser = newUser;

    }





}
