package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.widget.ListView;
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
import com.csun_sunlink.csuncareercenter.R;

class SearchBgTask extends AsyncTask<String, Void, String> {
    private Context ctx;
    private View rootView;

    SearchBgTask(Context ctx, View rootView) {
        this.ctx = ctx;
        this.rootView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        final String searchUrl = "http://10.0.2.2/CsunSunlink/search.php";
        String searchKey, result;
        searchKey = params[0];
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
        return null;
    }

    @Override
    protected void onPostExecute(String finalResult) {
        String companyStreet, companyStreet1, companyCityName, companyZipcode, companyState, companyCountry, jobId;
        String jobTitle, postedDate, companyName,companyId;
        String differenceDate;
        ListView listView;
        try {
            JSONObject jsonObj = new JSONObject(finalResult);
            JSONArray jsonArray = jsonObj.getJSONArray("server_res");
            int count = 0;
            ItemAdapter itemAdapter;
            itemAdapter = new ItemAdapter(ctx, R.layout.row_layout);
            listView = (ListView) rootView.findViewById(R.id.search_result_list);
            listView.setAdapter(itemAdapter);
            while (count < jsonArray.length()) {
                JSONObject jsonObject = jsonArray.getJSONObject(count);
                jobTitle = jsonObject.getString("job_title");

                postedDate = jsonObject.getString("posted_date").trim();
                SimpleDateFormat dfDate  = new SimpleDateFormat("MM/dd/yyyy");
                java.util.Date d = null;
                java.util.Date d1 = null;
                Calendar cal = Calendar.getInstance();
                try {
                    d = dfDate.parse(postedDate);
                    d1 = dfDate.parse(dfDate.format(cal.getTime()));
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                assert d1 != null;
                int diffInDays = (int) ((d1.getTime() - d.getTime())/ (1000 * 60 * 60 * 24));
                if (diffInDays == 0)
                    differenceDate = "Today";
                else
                    differenceDate = Integer.toString(diffInDays)+" d ago";

                companyName = jsonObject.getString("company_name");
               // companyStreet = jsonObject.getString("company_street");
               // companyStreet1 = jsonObject.getString("company_street1");
                companyCityName = jsonObject.getString("city_name");
              //  companyZipcode = jsonObject.getString("zipcode");
                companyState = jsonObject.getString("state_name");
                companyCountry = jsonObject.getString("country_name");
                jobId = jsonObject.getString("job_id");
                companyId = jsonObject.getString("company_id");
                StringBuilder address = new StringBuilder();
               /* address.append(companyStreet).append(",");
                if (!companyStreet1.equals("null")) {
                    address.append(companyStreet1).append(",");
                }*/
                address.append(companyCityName).append(",");
                if (!companyState.equals("null")) {
                    address.append(companyState).append(",");

                }
                address.append(companyCountry).append(",");
               // address.append(companyZipcode).append(".");

                String encodedImage= jsonObject.getString("company_logo");
                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap companyLogo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                ItemInfo itemInfo = new ItemInfo(jobId, jobTitle, companyName, differenceDate,
                                    address.toString(),companyId, companyLogo);
                itemAdapter.add(itemInfo);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
