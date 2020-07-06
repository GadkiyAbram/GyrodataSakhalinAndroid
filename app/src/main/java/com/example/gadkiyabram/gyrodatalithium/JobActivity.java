package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JobActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences   shPreferences;

    EditText etJobSearch;
    RecyclerView jRecyclerView;
    LinearLayout linLayoutrecView;
    RecyclerView.LayoutManager mLayoutManager;
    public RecyclerJobAdapter jobAdapter;
    FloatingActionButton backToJobs;

    RadioButton rbJobNum, rbGDP, rbModem, rbBBP, rbBattery;
    String where;

    String jobURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_activity);

        // TODO - add second arg for job searching
        // RadioButtons configuration
        rbJobNum = (RadioButton)findViewById(R.id.radioJobButton);
        rbGDP = (RadioButton)findViewById(R.id.radioJobGDPButton);
        rbModem = (RadioButton)findViewById(R.id.radioJobModemButton);
        rbBBP = (RadioButton)findViewById(R.id.radioJobBPButton);
        rbBattery = (RadioButton)findViewById(R.id.radioJobBatteryButton);

        backToJobs = (FloatingActionButton)findViewById(R.id.fabBackButton);
        backToJobs.setOnClickListener(this);

        etJobSearch = (EditText)findViewById(R.id.editJobSearch);

        jRecyclerView = (RecyclerView)findViewById(R.id.jobRecyclerView);
        linLayoutrecView = (LinearLayout)findViewById(R.id.linLayoutrecyclerViewBattery);

        jRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        jRecyclerView.setLayoutManager(mLayoutManager);

        jobURL = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_SELECTED_JOBS);

        new JobActivity.RecieveJobs().execute(jobURL + "?what=" + "" + "&where=" + "");

        etJobSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (rbJobNum.isChecked()){ where = "Job Number";}
                        if (rbGDP.isChecked()) { where = "GDP Sections"; }
                        if (rbModem.isChecked()) {where = "Modem";}
                        if (rbBBP.isChecked()){ where = "Bullplug"; }
                        if (rbBattery.isChecked()){ where = "Battery"; }
                        new JobActivity.RecieveJobs().execute(jobURL + "?what=" + etJobSearch.getText() + "&where=" + where);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) { }
                }
        );
    }

    @Override
    public void onClick(View view) {
        JobActivity.this.finish();
    }

    public class RecieveJobs extends AsyncTask<String, Void, JSONArray> {

        String status = null;
        ArrayList<JobModel> jobList = new ArrayList<>();

        ProgressBar pbWhileLoadingJobs;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbWhileLoadingJobs = (ProgressBar)findViewById(R.id.pbWaiting);
            pbWhileLoadingJobs.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            pbWhileLoadingJobs.bringToFront();
            pbWhileLoadingJobs.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... connUrl)
        {
            shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
            String token = shPreferences.getString("token", "");
            if (connUrl.length < 1 || connUrl[0] == null){
                return null;
            }
            JSONArray jsonArrayResult = null;
            try {
                jsonArrayResult = QueryUtils.getData(connUrl[0], token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonArrayResult;
        }

        protected void onPostExecute(JSONArray data) {
            ArrayList<JobModel> jobList = new ArrayList<>();

            if (data != null) {
                try {

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = data.getJSONObject(i);

                        int _id = object.getInt(DataBaseHelper.JOB_ID);
                        String jobNum = object.getString(DataBaseHelper.JOB_JOBNUMBER);
                        String client = object.getString(DataBaseHelper.JOB_CLIENT);
                        String gdp = object.getString(DataBaseHelper.JOB_GDP);
                        String modem = object.getString(DataBaseHelper.JOB_MODEM);
                        String modemVersion = object.getString(DataBaseHelper.JOB_MODEMVERSION);
                        String bullplug = object.getString(DataBaseHelper.JOB_BULLPLUG);
                        String circulation = object.getString(DataBaseHelper.JOB_CIRCULATION);
                        String battery = object.getString(DataBaseHelper.JOB_BATTERY);
                        String maxTemp = object.getString(DataBaseHelper.JOB_MAXTEMP);
                        String engOne = object.getString(DataBaseHelper.JOB_ENGINEERONE);
                        String engTwo = object.getString(DataBaseHelper.JOB_ENGINEERTWO);
                        String engOneArrived = object.getString(DataBaseHelper.JOB_ENG_ONE_ARRIVED);
                        String engTwoArrived = object.getString(DataBaseHelper.JOB_ENG_TWO_ARRIVED);
                        String engOneLeft = object.getString(DataBaseHelper.JOB_ENG_ONE_LEFT);
                        String engTwoLeft = object.getString(DataBaseHelper.JOB_ENG_TWO_LEFT);
                        String container = object.getString(DataBaseHelper.JOB_CONTAINER);
                        String containerArrived = object.getString(DataBaseHelper.JOB_CONT_ARRIVED);
                        String containerLeft = object.getString(DataBaseHelper.JOB_CONT_LEFT);
                        String rig = object.getString(DataBaseHelper.JOB_RIG);
                        String issues = object.getString(DataBaseHelper.JOB_ISSUES);
                        String comment = object.getString(DataBaseHelper.JOB_COMMENT);

                        jobList.add(new JobModel(_id, jobNum, client, gdp, modem, modemVersion, bullplug, circulation, battery,
                                maxTemp, engOne, engTwo, engOneArrived, engTwoArrived, engOneLeft, engTwoLeft, container,
                                containerArrived, containerLeft, comment, rig, issues));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (data != null) {
                    if (pbWhileLoadingJobs != null && pbWhileLoadingJobs.isShown() == true) {
                        pbWhileLoadingJobs.setVisibility(View.GONE);
                    }

                    if (jobList != null) {
                        jobAdapter = new RecyclerJobAdapter(getApplicationContext(), jobList, new RVClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Log.d(DataBaseHelper.LOG_TAG, "selected: " + jobList.get(position).getJobN());

                                Intent intentJobDetails = new Intent(JobActivity.this, JobPreciseActivity.class);
                                intentJobDetails.putExtra(DataBaseHelper.JOB_ID, String.valueOf(jobList.get(position).get_id()));
                                intentJobDetails.putExtra(DataBaseHelper.JOB_JOBNUMBER, jobList.get(position).getJobN());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_CLIENT, jobList.get(position).getClient());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_GDP, jobList.get(position).getTool());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_MODEM, jobList.get(position).getModem());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_MODEMVERSION, jobList.get(position).getModemVer());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_BULLPLUG, jobList.get(position).getBullplug());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_CIRCULATION, jobList.get(position).getCircHrs());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_BATTERY, jobList.get(position).getBattery());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_MAXTEMP, jobList.get(position).getMaxTemp());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_ENGINEERONE, jobList.get(position).getSurvEng1());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_ENGINEERTWO, jobList.get(position).getSurvEng2());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_ENG_ONE_ARRIVED, jobList.get(position).getEngArr1());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_ENG_TWO_ARRIVED, jobList.get(position).getEngArr2());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_ENG_ONE_LEFT, jobList.get(position).getEngLft1());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_ENG_TWO_LEFT, jobList.get(position).getEngLft2());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_CONTAINER, jobList.get(position).getContainer());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_CONT_ARRIVED, jobList.get(position).getContArr());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_CONT_LEFT, jobList.get(position).getContLft());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_COMMENT, jobList.get(position).getComments());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_RIG, jobList.get(position).getRig());
                                intentJobDetails.putExtra(DataBaseHelper.JOB_ISSUES, jobList.get(position).getIssues());

                                startActivity(intentJobDetails);
                            }
                        });
                        jRecyclerView.setAdapter(jobAdapter);
                        jobAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}
