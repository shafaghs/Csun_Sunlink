package com.csun_sunlink.csuncareercenter.Profile;

import android.content.Context;
import android.os.AsyncTask;
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

    @Override
    protected String doInBackground(String... params) {
        //PHP FILES

        final String personaleditUrl = "http://10.0.2.2/CsunSunlink/personalFragment.php";

        String result;
        searchKey = params[0];
        switch (searchKey) {
            case "editPersonalFragment":
                try {
                    URL url = new URL(personaleditUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("editpersonal", "UTF-8") + "=" + URLEncoder.encode(searchKey, "UTF-8");
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
            case "editPersonalFragment":
                try {

                    JSONObject jsonObj = new JSONObject(finalResult);
                    JSONArray jsonArray = jsonObj.getJSONArray("server_res");
                    int count = 0;

                    ProfileInfoAdapter itemAdapter;
                    itemAdapter = new ProfileInfoAdapter(ctx, R.layout.row_layout);

                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        //ID:
                        currUser.setUserID(jsonObject.getString("user_id"));

                        //Name:


                        //Email:
                        currUser.setEmail(jsonObject.getString("user_email"));
                        ProfileInfo newInfoEmail = new ProfileInfo("Email ", currUser.getEmail());
                        itemAdapter.add(newInfoEmail);

                        //Phone Number:
                        currUser.setPhone(jsonObject.getString("user_phone_number"));
                        ProfileInfo newInfoPhone = new ProfileInfo("Phone ", currUser.getPhone());
                        itemAdapter.add(newInfoPhone);

                        //Address: (REmineder later on should be changed for edit field each should be saved separetly in USER clas)


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

        }

    }


    public void setUser(UserPersonal newuser) {
        this.currUser = newuser;

    }

}
