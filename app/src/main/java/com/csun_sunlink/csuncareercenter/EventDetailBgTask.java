package com.csun_sunlink.csuncareercenter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
 * Created by olgak on 12/8/16.
 */

public class EventDetailBgTask extends AsyncTask<String, Void, String> {
    private View rootView;
    private String address, differenceDate;

    EventDetailBgTask(Context ctx, View rootView) {
        this.rootView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        final String eventDetailUrl = "http://10.0.2.2/CsunSunlink/eventDetail.php";
        String result, jobIdKey, method, userId;
        method = params[0];
        switch (method) {
            case "showEventDetail":
                jobIdKey = params[1];
                userId = params[2];
                address = params[3];
                differenceDate = params[4];
                try {
                    URL url = new URL(eventDetailUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                            URLEncoder.encode("eventIdKey", "UTF-8") + "=" + URLEncoder.encode(jobIdKey, "UTF-8") + "&" +
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
            case "saveEvent":
                jobIdKey = params[1];
                userId = params[2];
                try {
                    URL url = new URL(eventDetailUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                            URLEncoder.encode("eventIdKey", "UTF-8") + "=" + URLEncoder.encode(jobIdKey, "UTF-8") + "&" +
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
                    URL url = new URL(eventDetailUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" +
                            URLEncoder.encode("eventIdKey", "UTF-8") + "=" + URLEncoder.encode(jobIdKey, "UTF-8") + "&" +
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
        }
        return null;
    }

    @Override
    protected void onPostExecute(String finalResult) {
        TextView eventTitle, eventLocation, date, eventInfo;
        JSONObject jsonObj = null;
        JSONArray jsonArray;
        JSONObject jsonObject = null;
        String answerMethod = "empty";
        Button saveEvent = (Button) rootView.findViewById(R.id.search_detail_save_button);
        try {
            jsonObj = new JSONObject(finalResult);
            jsonArray = jsonObj.getJSONArray("server_res");
            jsonObject = jsonArray.getJSONObject(0);
            answerMethod = jsonObject.getString("result_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch (answerMethod) {
            case "eventDetail":
                /*eventTitle = (TextView) rootView.findViewById(R.id.search_detail_job_title);
                eventLocation = (TextView) rootView.findViewById(R.id.search_detail_company_name);
                date= (TextView) rootView.findViewById(R.id.search_detail_position_type);*/
                eventInfo = (TextView) rootView.findViewById(R.id.search_detail_job_description);
                //ImageView imageView = (ImageView) rootView.findViewById(R.id.company_logo);
                try {

                    eventInfo.setText(jsonObject.getString("event_info"));

                    String savedEvent = jsonObject.getString("saved_event");
                    if (savedEvent.equals("alreadySaved")) {
                        saveEvent.setText("Unattend");
                    } else if (savedEvent.equals("notSavedBefore")) {
                        saveEvent.setText("Attend");
                    }
                    /*String encodedImage = jsonObject.getString("company_logo");
                    byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap companyLogo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setImageBitmap(companyLogo); */
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "deletedSuccessfully":;
                saveEvent.setText("Attend");
                break;
            case "savedSuccessfully":
                saveEvent.setText("Unattend");
                break;
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String[] spiltDate(String date) {
        String[] parts = date.split(" ");
        return parts;
    }


}
