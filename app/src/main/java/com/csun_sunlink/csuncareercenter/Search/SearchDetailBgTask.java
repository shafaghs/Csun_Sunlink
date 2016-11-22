package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
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
    private Context ctx;
    private View rootView;
    private String address,differenceDate;

    SearchDetailBgTask(Context ctx, View rootView) {
        this.ctx = ctx;
        this.rootView = rootView;
    }

    @Override
    protected String doInBackground(String... params) {
        final String searchDetailUrl = "http://10.0.2.2/CsunSunlink/searchDetail.php";
        String result, jobIdKey ;
        jobIdKey = params[0];
        address = params[1];
        differenceDate = params[2];
        try {
            URL url = new URL(searchDetailUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //send request
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("jobIdKey", "UTF-8") + "=" + URLEncoder.encode(jobIdKey, "UTF-8");
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
        String companyStreet, companyStreet1, companyCityName, companyZipcode, companyState, companyCountry, jobId, method;
        String jobTitle, jobDuties, jobSummary, essentialSkills, postedDate, companyName;
        TextView jobTitleTextView, companyNameTextView, positionTypeTextView, companyAddTextView, postedDateTextView, jobDesTextView, jobDutiesTextView, essentialSkillsTextView, desiredSkillsTextView;

        try {
            JSONObject jsonObj = new JSONObject(finalResult);
            JSONArray jsonArray = jsonObj.getJSONArray("server_res");
            jobTitleTextView = (TextView) rootView.findViewById(R.id.search_detail_job_title);
            companyNameTextView = (TextView) rootView.findViewById(R.id.search_detail_company_name);
            positionTypeTextView = (TextView) rootView.findViewById(R.id.search_detail_position_type);
            companyAddTextView = (TextView) rootView.findViewById(R.id.search_detail_company_add);
            postedDateTextView = (TextView) rootView.findViewById(R.id.search_detail_posted_date);
            jobDesTextView = (TextView) rootView.findViewById(R.id.search_detail_job_des_detail);
            jobDutiesTextView = (TextView) rootView.findViewById(R.id.search_detail_job_duty_detail);
            essentialSkillsTextView = (TextView) rootView.findViewById(R.id.search_detail_ess_detail);
            desiredSkillsTextView = (TextView) rootView.findViewById(R.id.search_detail_des_detail);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            jobTitleTextView.setText(jsonObject.getString("job_title"));
            companyNameTextView.setText(jsonObject.getString("company_name"));
            positionTypeTextView.setText("part time");
            companyAddTextView.setText(address);
            if(!differenceDate.equals("Today"))
                postedDateTextView.setText(differenceDate+"ays ago");
            else
                postedDateTextView.setText(differenceDate);
            jobDesTextView.setText(jsonObject.getString("job_summary"));
            jobDutiesTextView.setText(jsonObject.getString("job_duties"));
            essentialSkillsTextView.setText(jsonObject.getString("essential_skills"));
            desiredSkillsTextView.setText(jsonObject.getString("desired_skills"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
