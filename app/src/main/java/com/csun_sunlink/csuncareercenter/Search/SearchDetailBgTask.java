package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

class SearchDetailBgTask extends AsyncTask<String, Void, String> {
    private View rootView;
    private String address, differenceDate;

    SearchDetailBgTask(View rootView) {
        this.rootView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        final String searchDetailUrl = "http://10.0.2.2/CsunSunlink/searchDetail.php";
        String result, jobIdKey, method, userId;
        method = params[0];
        switch (method) {
            case "saveJob":
                jobIdKey = params[1];
                userId = params[2];
                try {
                    URL url = new URL(searchDetailUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                            URLEncoder.encode("jobIdKey", "UTF-8") + "=" + URLEncoder.encode(jobIdKey, "UTF-8") + "&" +
                            URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");
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
            case "unSaveJob":
                jobIdKey = params[1];
                userId = params[2];
                try {
                    URL url = new URL(searchDetailUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                            URLEncoder.encode("jobIdKey", "UTF-8") + "=" + URLEncoder.encode(jobIdKey, "UTF-8") + "&" +
                            URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");
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
            case "showJobDetail":
                jobIdKey = params[1];
                address = params[2];
                differenceDate = params[3];
                userId = params[4];
                try {
                    URL url = new URL(searchDetailUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                            URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") + "&" +
                            URLEncoder.encode("jobIdKey", "UTF-8") + "=" + URLEncoder.encode(jobIdKey, "UTF-8");
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
    protected void onPostExecute(String result) {
        JSONObject jsonObj;
        JSONObject jsonObject = null;
        JSONArray jsonArray;
        //JSONArray jobDuty;
        String method = "empty";
        Button saveButton = (Button) rootView.findViewById(R.id.search_detail_save_button);

        try {
            jsonObj = new JSONObject(result);
            jsonArray = jsonObj.getJSONArray("server_res");
            jsonObject = jsonArray.getJSONObject(0);
            method = jsonObject.getString("result_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (method) {
            case "savedSuccessfully":
                saveButton.setText(R.string.unsave_job);
                break;
            case "deletedSuccessfully":
                saveButton.setText(R.string.save_job);
                break;
            case "jobDetail":
                TextView jobTitleTextView, companyNameTextView, positionTypeTextView, companyAddTextView,
                        postedDateTextView, jobDesTextView, jobDutiesTextView, essentialSkillsTextView, desiredSkillsTextView;
                try {
                    jobTitleTextView = (TextView) rootView.findViewById(R.id.search_detail_job_title);
                    companyNameTextView = (TextView) rootView.findViewById(R.id.search_detail_company_name);
                    positionTypeTextView = (TextView) rootView.findViewById(R.id.search_detail_position_type);
                    companyAddTextView = (TextView) rootView.findViewById(R.id.search_detail_company_add);
                    postedDateTextView = (TextView) rootView.findViewById(R.id.search_detail_posted_date);
                    jobDesTextView = (TextView) rootView.findViewById(R.id.search_detail_job_des_detail);
                    jobDutiesTextView = (TextView) rootView.findViewById(R.id.search_detail_job_duty_detail);
                    essentialSkillsTextView = (TextView) rootView.findViewById(R.id.search_detail_ess_detail);
                    desiredSkillsTextView = (TextView) rootView.findViewById(R.id.search_detail_des_detail);
                    ImageView imageView = (ImageView) rootView.findViewById(R.id.company_logo);
                    if (jsonObject != null) {
                        jobTitleTextView.setText(jsonObject.getString("job_title"));
                        companyNameTextView.setText(jsonObject.getString("company_name"));
                        positionTypeTextView.setText("part time");
                        companyAddTextView.setText(address);
                        postedDateTextView.setText(differenceDate);
                        jobDesTextView.setText(jsonObject.getString("job_summary"));
                        int count = 0;
                        StringBuilder jobDutyStringBuilder= new StringBuilder();
                       String jobDutyS = jsonObject.getString("job_duties");
                        JSONArray jobDuty=new JSONArray(jobDutyS);
                        while (count < jobDuty.length()) {
                            JSONObject jobDutyObject = jobDuty.getJSONObject(count);
                            jobDutyStringBuilder.append("&#8226;").append(jobDutyObject.getString("job_duty_items"));
                            count++;
                        }

                        jobDutiesTextView.setText(jobDutyStringBuilder);
                        essentialSkillsTextView.setText(jsonObject.getString("essential_skills"));
                        desiredSkillsTextView.setText(jsonObject.getString("desired_skills"));
                        if (jsonObject.getString("saved_job").equals("notSavedBefore"))
                            saveButton.setText(R.string.save_job);
                        else if (jsonObject.getString("saved_job").equals("alreadySaved"))
                            saveButton.setText(R.string.unsave_job);
                        String encodedImage = jsonObject.getString("company_logo");
                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                        Bitmap companyLogo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(companyLogo);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
