package com.csun_sunlink.csuncareercenter.Fragments;

import android.content.Context;
import android.os.AsyncTask;
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
    private ListView listView;
    private String searchKey;

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
            JSONObject jsonObj;
            JSONArray jsonArray = null;
            JSONObject jsonObject;
            String method = "empty";
            try {
                jsonObj = new JSONObject(finalResult);
                jsonArray = jsonObj.getJSONArray("server_res");
                jsonObject = jsonArray.getJSONObject(0);
                method = jsonObject.getString("result_type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            switch (method) {
                case "jobListing":
                    try {
                        String companyCityName, companyState, companyCountry, jobId;
                        String jobTitle, postedDate, companyName, differenceDate, companyId;
                        int count = 0;
                        ItemAdapter itemAdapter;
                        itemAdapter = new ItemAdapter(ctx, R.layout.row_layout);
                        //listView = (ListView) rootView.findViewById(R.id.job_listing_fragment);
                        listView.setAdapter(itemAdapter);
                        while (count < jsonArray.length()) {
                            jsonObject = jsonArray.getJSONObject(count);
                            jobTitle = jsonObject.getString("job_title");
                            companyId = jsonObject.getString("company_id");
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
                                differenceDate = Integer.toString(diffInDays) + " d ago";

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
                            ItemInfo itemInfo = new ItemInfo(jobId, jobTitle, companyName, differenceDate, address.toString(), companyId);
                            itemAdapter.add(itemInfo);
                            count++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case "eventListing":
                    try {
                        String eventId, eventTitle, eventLocation, eventType, dateStart, eventInfo;
                        JSONObject jsonObj1 = new JSONObject(finalResult);
                        jsonArray = jsonObj1.getJSONArray("server_res");
                        int count = 0;
                        EventAdapter itemAdapter;
                        itemAdapter = new EventAdapter(ctx, R.layout.row_layout);
                        //listView = (ListView) rootView.findViewById(R.id.job_listing_fragment);
                        listView.setAdapter(itemAdapter);
                        while (count < jsonArray.length()) {
                            jsonObject = jsonArray.getJSONObject(count);
                            eventId = jsonObject.getString("event_id");
                            eventTitle = jsonObject.getString("event_title");
                            eventLocation = jsonObject.getString("event_location");
                            eventType = jsonObject.getString("event_type");
                            eventInfo = jsonObject.getString("event_info");
                            dateStart = jsonObject.getString("date_start").trim();
                            SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy");
                       /* java.util.Date d = null;
                        java.util.Date d1 = null;
                        Calendar cal = Calendar.getInstance();
                        try {
                            d = dfDate.parse(dateStart);
                            d1 = dfDate.parse(dfDate.format(cal.getTime()));
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        } */

                            //Missing a method thats chekcing if user saved event:
                            //Also need to impment order

                            EventInfo eventI = new EventInfo(eventId, eventTitle, dateStart, eventInfo, eventLocation);
                            itemAdapter.add(eventI);
                            count++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

        }

}
