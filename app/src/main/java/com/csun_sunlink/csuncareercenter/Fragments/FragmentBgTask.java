package com.csun_sunlink.csuncareercenter.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.csun_sunlink.csuncareercenter.R;
import com.csun_sunlink.csuncareercenter.Search.ItemAdapter;
import com.csun_sunlink.csuncareercenter.Search.ItemInfo;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FragmentBgTask extends AsyncTask<String, Void, String> {
    private Context ctx;
    private View rootView;
    ListView listView;
    String searchKey;

    FragmentBgTask(Context ctx, ListView rootView) {
        this.ctx = ctx;
        this.listView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        final String searchUrl = "http://10.0.2.2/CsunSunlink/jobListing.php";
        final String eventUrl = "http://10.0.2.2/CsunSunlink/eventListing.php";
        String result;
        searchKey = params[0];
        switch (searchKey) {
            case "jobListing":
                try {
                    URL url = new URL(searchUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //send request
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("jobTitle", "UTF-8") + "=" + URLEncoder.encode(searchKey, "UTF-8");
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
            case "eventListing":
                try {
                URL url = new URL(eventUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //send request
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("eventTitle", "UTF-8") + "=" + URLEncoder.encode(searchKey, "UTF-8");
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
            case "jobListing":
                try {
                    String companyStreet, companyStreet1, companyCityName, companyZipcode, companyState, companyCountry, jobId;
                    String jobTitle, postedDate, companyName, differenceDate;

                    JSONObject jsonObj = new JSONObject(finalResult);
                    JSONArray jsonArray = jsonObj.getJSONArray("server_res");
                    int count = 0;
                    ItemAdapter itemAdapter;
                    itemAdapter = new ItemAdapter(ctx, R.layout.row_layout);
                    //listView = (ListView) rootView.findViewById(R.id.job_listing_fragment);
                    listView.setAdapter(itemAdapter);
                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        jobTitle = jsonObject.getString("job_title");

                        postedDate = jsonObject.getString("posted_date").trim();
                        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy");
                        java.util.Date d = null;
                        java.util.Date d1 = null;
                        Calendar cal = Calendar.getInstance();
                        try {
                            d = dfDate.parse(postedDate);
                            d1 = dfDate.parse(dfDate.format(cal.getTime()));
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
                        if (diffInDays == 0)
                            differenceDate = "Today";
                        else
                            differenceDate = Integer.toString(diffInDays) + " d";

                        companyName = jsonObject.getString("company_name");
                        companyCityName = jsonObject.getString("city_name");
                        companyState = jsonObject.getString("state_name");
                        companyCountry = jsonObject.getString("country_name");
                        jobId = jsonObject.getString("job_id");
                        StringBuilder address = new StringBuilder();
                        address.append(companyCityName).append(",");
                        if (!companyState.equals("null")) {
                            address.append(companyState).append(",");

                        }
                        address.append("\n").append(companyCountry).append(".");
                        ItemInfo itemInfo = new ItemInfo(jobId, jobTitle, companyName, differenceDate, address.toString());
                        itemAdapter.add(itemInfo);
                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case "eventListing":
                try {
                    String eventId, eventDate, eventLocation, eventTitle, eventInfo;

                    JSONObject jsonObj = new JSONObject(finalResult);
                    JSONArray jsonArray = jsonObj.getJSONArray("server_res");
                    int count = 0;
                    EventAdapter itemAdapter;
                    itemAdapter = new EventAdapter(ctx, R.layout.row_layout);
                    //listView = (ListView) rootView.findViewById(R.id.job_listing_fragment);
                    listView.setAdapter(itemAdapter);
                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        eventTitle = jsonObject.getString("event_title");
                        eventId = jsonObject.getString("event_id");
                        eventInfo = jsonObject.getString("event_info");
                        eventLocation = jsonObject.getString("event_location");
                        eventDate = jsonObject.getString("event_start").trim();
                        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy");
                        java.util.Date d = null;
                        java.util.Date d1 = null;
                        Calendar cal = Calendar.getInstance();
                        try {
                            d = dfDate.parse(eventDate);
                            d1 = dfDate.parse(dfDate.format(cal.getTime()));
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        //Missing a method thats chekcing if user saved event:
                        //Also need to impmemtn order

                        EventInfo eventI = new EventInfo(eventId, eventTitle, eventDate, eventInfo, eventLocation);
                        itemAdapter.add(eventI);
                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }

    }
}
