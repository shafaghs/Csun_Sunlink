package com.csun_sunlink.csuncareercenter.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by bigmatt76 on 11/28/16.
 */

public class ProfileBgTask extends AsyncTask<String, Void, String> {
    private Context ctx;
    private View rootView;
    ListView listView;
    String searchKey;

    UserPersonal currUser = new UserPersonal();
        private String currID;


    //Strings for Personal:
    private String first = "null";
    private String last = "null";
    private String middle = "null";
    private String  name = "null";

    private String street1;
    private String street2;
    private String city;
    private String email = "null";
    private String phone = "null";
    private String address = "null";
    private String statusOfJobsSearch = "null";
    private String geoPreference = "null";
    private String workAuth= "null";

    //Strings for Academic:
    String major="null";
    String graduationYear = "null";
    String prevEdu = "null";
    String gpa ="null";
    String apType =" null";
    String degreeLevel = "null";

    //Strings for professional:
    String pStatement = "null";
    String experience = "null";
    String skills = "null";
    String projects = "null";

    ProfileBgTask(Context ctx, ListView rootView) {
        this.ctx = ctx;
        this.listView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        setUserID(pref.getString("user_id",""));
        //PHP FILEs
        final String personalUrl = "http://10.0.2.2/CsunSunlink/personalFragment.php";
        final String academicUrl = "http://10.0.2.2/CsunSunlink/academicFragment.php";
        final String professionalUrl = "http://10.0.2.2/CsunSunlink/professionalFragment.php";
        String result;
        searchKey = params[0];
        switch (searchKey) {
            case "academicFragment":
                try {
                    URL url = new URL(academicUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(this.currID, "UTF-8") + "&" +
                            URLEncoder.encode("academic", "UTF-8") + "=" + URLEncoder.encode(searchKey, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //get result
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((result = bufferedReader.readLine()) != null) {
                        stringBuilder.append(result).append("\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "personalFragment":
                try {
                    URL url = new URL(personalUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(this.currID, "UTF-8") + "&" +
                            URLEncoder.encode("personal", "UTF-8") + "=" + URLEncoder.encode(searchKey, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //get result
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((result = bufferedReader.readLine()) != null) {
                        stringBuilder.append(result).append("\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "professionalFragment":
                try {
                    URL url = new URL(professionalUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(this.currID, "UTF-8") + "&" +
                            URLEncoder.encode("professional", "UTF-8") + "=" + URLEncoder.encode(searchKey, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    //get result
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((result = bufferedReader.readLine()) != null) {
                        stringBuilder.append(result).append("\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
        return null;

    }

    @Override
    protected void onPostExecute(String finalResult) {

        switch (searchKey) {
            case "academicFragment":
                try {  JSONObject jsonObj = new JSONObject(finalResult);
                    JSONArray jsonArray = jsonObj.getJSONArray("server_res");
                    int count = 0;

                    ProfileInfoAdapter itemAdapter;
                    itemAdapter = new ProfileInfoAdapter(ctx, R.layout.row_layout);

                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        //Major:
                        this.major =jsonObject.getString("major");
                        if (this.major != "null"){
                            ProfileInfo newInfo = new ProfileInfo("Major", this.major);
                            itemAdapter.add(newInfo);
                        }
                        else {
                            ProfileInfo newInfo = new ProfileInfo("Major ", " ");
                            itemAdapter.add(newInfo);
                        }
                        //GPA:
                        if (jsonObject.getString("gpa") != "null") {
                            this.gpa = jsonObject.getString("gpa");
                            ProfileInfo newInfo = new ProfileInfo("GPA ", this.gpa);
                            itemAdapter.add(newInfo);
                        }
                        else {
                            ProfileInfo newInfo = new ProfileInfo("GPA ", " ");
                            itemAdapter.add(newInfo);
                        }

                        //Graduation Date:
                        if (jsonObject.getString("graduation_date") != "null") {
                            this.graduationYear = jsonObject.getString("graduation_date");
                            ProfileInfo newInfo = new ProfileInfo("Graduating in ", this.graduationYear);
                            itemAdapter.add(newInfo);
                        }
                        else {
                            ProfileInfo newInfo = new ProfileInfo("Graduating in ", " ");
                            itemAdapter.add(newInfo);
                        }

                        //Graduation Date:
                        if (jsonObject.getString("previous_education") != "null") {
                            this.prevEdu = jsonObject.getString("previous_education");
                            ProfileInfo newInfo = new ProfileInfo("Previous Education ", this.prevEdu);
                            itemAdapter.add(newInfo);
                        }
                        else {
                            ProfileInfo newInfo = new ProfileInfo("Previous Education ", " ");
                            itemAdapter.add(newInfo);
                        }

                        //Applicant Type:

                        if (jsonObject.getString("a_t_titles") != "null") {
                            this.apType = jsonObject.getString("a_t_titles");
                            ProfileInfo newInfo = new ProfileInfo("Applicant Type ", this.apType);
                            itemAdapter.add(newInfo);
                        }
                        else {
                            ProfileInfo newInfo = new ProfileInfo("Applicant Type ", " ");
                            itemAdapter.add(newInfo);
                        }
                        // Degree Level
                        if (jsonObject.getString("d_l_title") != "null") {
                            this.degreeLevel= jsonObject.getString("d_l_title");
                            ProfileInfo newInfo = new ProfileInfo("Degree Level ", this.degreeLevel);
                            itemAdapter.add(newInfo);
                        }
                        else {
                            ProfileInfo newInfo = new ProfileInfo("Degree Level ", " ");
                            itemAdapter.add(newInfo);
                        }

                        count++;
                    }
                    listView.setAdapter(itemAdapter);
                    setListViewHeightBasedOnItsChildren(listView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case "personalFragment":
                try {

                    JSONObject jsonObj = new JSONObject(finalResult);
                    JSONArray jsonArray = jsonObj.getJSONArray("server_res");
                    int count = 0;

                    ProfileInfoAdapter itemAdapter;
                    itemAdapter = new ProfileInfoAdapter(ctx, R.layout.row_layout);

                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        //ID:
                        //currUser.setUserID(jsonObject.getString("user_id"));

                        //Name:
                        currUser.setFirstName(jsonObject.getString("first_name"));
                        currUser.setLastName(jsonObject.getString("family_name"));
                        currUser.setMiddleName(jsonObject.getString("middle_name"));
                        this.name = constructNameString(currUser.getFirstName(), currUser.getLastName(), currUser.getMiddleName());
                        ProfileInfo newInfoName = new ProfileInfo("Name ", this.name);
                        itemAdapter.add(newInfoName);

                        //Email:
                        currUser.setEmail(jsonObject.getString("user_email"));
                        ProfileInfo newInfoEmail = new ProfileInfo("Email ", currUser.getEmail());
                        itemAdapter.add(newInfoEmail);

                        //Phone Number:
                        currUser.setPhone(jsonObject.getString("user_phone_number"));
                        ProfileInfo newInfoPhone = new ProfileInfo("Phone ", currUser.getPhone());
                        itemAdapter.add(newInfoPhone);

                        //Address: (REmineder later on should be changed for edit field each should be saved separetly in USER clas)
                        this.street1=jsonObject.getString("street");
                        this.street2=jsonObject.getString("street2");
                        this.city=jsonObject.getString("city_name");
                        currUser.setAddress(constructAddress(street1, street2, city));
                        ProfileInfo newInfoAddress = new ProfileInfo("Address ", currUser.getAddress());
                        itemAdapter.add(newInfoAddress);


                        //Status:
                        currUser.setStatus(jsonObject.getString("status_title"));
                        ProfileInfo newInfoStatus = new ProfileInfo("Status ", currUser.getStatus());
                        itemAdapter.add(newInfoStatus);

                        // Geo Preference
                        currUser.setGeoPref(jsonObject.getString("state_name"));
                        ProfileInfo newInfoGeoPref = new ProfileInfo("Geographic Preferences ", currUser.getGeopref());
                        itemAdapter.add(newInfoGeoPref);

                        //Work Authorization:
                        currUser.setWorkAuth(jsonObject.getString("w_a_title"));
                            ProfileInfo newInfo = new ProfileInfo("Work \nAuthorization ", currUser.getWorkAuth());
                            itemAdapter.add(newInfo);
                        count++;
                    }
                    listView.setAdapter(itemAdapter);
                    setListViewHeightBasedOnItsChildren(listView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "professionalFragment":
                try {
                 JSONObject jsonObj = new JSONObject(finalResult);
                    JSONArray jsonArray = jsonObj.getJSONArray("server_res");
                    int count = 0;

                    ProfileInfoAdapter itemAdapter;
                    itemAdapter = new ProfileInfoAdapter(ctx, R.layout.row_layout);


                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        //Personal Statement:
                        if (jsonObject.getString("statement") != "null"){
                            this.pStatement = jsonObject.getString("statement");
                            ProfileInfo newInfo = new ProfileInfo("Personal Statement", this.pStatement);
                            itemAdapter.add(newInfo);
                        }
                        else {
                            ProfileInfo newInfo = new ProfileInfo("Personal Statement ", " ");
                            itemAdapter.add(newInfo);
                        }
                        //Experience:
                        if (jsonObject.getString("experience") != "null") {
                            this.experience= jsonObject.getString("experience");
                            ProfileInfo newInfo = new ProfileInfo("Experience", this.experience);
                            itemAdapter.add(newInfo);
                        }
                        else {
                            ProfileInfo newInfo = new ProfileInfo("Experience ", " ");
                            itemAdapter.add(newInfo);
                        }

                        //Skills:
                        if (jsonObject.getString("skills") != "null") {
                            this.skills = jsonObject.getString("skills");
                            ProfileInfo newInfo = new ProfileInfo("Skills ", this.skills);
                            itemAdapter.add(newInfo);
                        }
                        else {
                            ProfileInfo newInfo = new ProfileInfo("Skills ", " ");
                            itemAdapter.add(newInfo);
                        }

                        //Projects:
                        if (jsonObject.getString("projects") != "null") {
                            this.projects = jsonObject.getString("projects");
                            ProfileInfo newInfo = new ProfileInfo("Projects ", this.projects);
                            itemAdapter.add(newInfo);
                        }
                        else {
                            ProfileInfo newInfo = new ProfileInfo("Projects ", " ");
                            itemAdapter.add(newInfo);
                        }

                        count++;
                    }
                    listView.setAdapter(itemAdapter);
                    setListViewHeightBasedOnItsChildren(listView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            }

    }

    public String constructNameString(String f, String l, String m) {
        String fullName = "null";
        if (f != "null")
            fullName = f;
        if ( m != "null") {
            if (fullName == "null")
                fullName = m;
            else
                fullName += " " + m;

        }
        if (l != "null") {
            if(fullName == "null")
                fullName = l;
            else {
                fullName += " " + l;
            }
        }
         return fullName;
    }

    public String constructAddress(String s1,String s2, String c) {
        String address = "";
        if( s1 != "null")
            address += s1;
        if (s2 != "null")
            address += "\n" + s2;
        if (c != "null")
            address += "\n" + c;
        return address;
    }

    //Method to make sure all information is shown for each adapter
    public static void setListViewHeightBasedOnItsChildren(ListView listView) {

        if (listView.getAdapter() == null) {
            // pre-condition adaptershould not be null
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            View listItem = listView.getAdapter().getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //set layout params for listview
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listView.getAdapter()
                .getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    //Methods to return UserPersonalinfo:
    public UserPersonal getUserPersonal(){
        return this.currUser;
    }

    public void setUserID(String newID) {
        this.currID = newID;
    }
}
