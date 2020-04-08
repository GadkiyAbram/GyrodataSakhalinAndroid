package com.example.gadkiyabram.gyrodatalithium;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddJobActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton confirmJobInsert;

    ArrayList<String> errorJob;

    Spinner spCLients, spGdp, spModems, spBbp, spBatteries, spEngOne, spEngTwo;
    EditText edJobNumber, edModemVer, edJobCircul, edJobMaxTemp, edJobEngOneArr, edJobEngTwoArr,
            edJobEngOneLeft, edJobEngTwoLeft, edJobContainer, edJobContArr, edJobContLeft, edJobRig,
            edJobComment;
    public RadioButton rbIssues, rbNoIssues;
    SharedPreferences shPreferences;
    ProgressBar pbJob;
    String addJobURL = null;
    String jobInitData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_add);

        confirmJobInsert = (FloatingActionButton)findViewById(R.id.fabAddJob);
        confirmJobInsert.setOnClickListener(this);

        pbJob = (ProgressBar)findViewById(R.id.pbHandlingJob);

        spCLients = (Spinner)findViewById(R.id.spinnerClient);
        spGdp = (Spinner)findViewById(R.id.spinnerGDP);
        spModems = (Spinner)findViewById(R.id.spinnerModem);
        spBbp = (Spinner)findViewById(R.id.spinnerBullplug);
        spBatteries = (Spinner)findViewById(R.id.spinnerBattery);
        spEngOne = (Spinner)findViewById(R.id.spinnerEngOne);
        spEngTwo = (Spinner)findViewById(R.id.spinnerEngTwo);

        edJobNumber = (EditText)findViewById(R.id.edJobJobNumber);
        edModemVer = (EditText)findViewById(R.id.edJobModemVer);
        edJobCircul = (EditText)findViewById(R.id.edJobCirculation);
        edJobMaxTemp = (EditText)findViewById(R.id.edJobMaxTemp);
        edJobEngOneArr = (EditText)findViewById(R.id.edJobEngOneArrived);
        edJobEngTwoArr = (EditText)findViewById(R.id.edJobEngTwoArrived);
        edJobEngOneLeft = (EditText)findViewById(R.id.edJobEngOneLeft);
        edJobEngTwoLeft = (EditText)findViewById(R.id.edJobEngTwoLeft);
        edJobContainer = (EditText)findViewById(R.id.edJobContainer);
        edJobContArr = (EditText)findViewById(R.id.edJobContArrived);
        edJobContLeft = (EditText)findViewById(R.id.edJobContLeft);
        edJobComment = (EditText)findViewById(R.id.edJobComments);
        edJobRig = (EditText)findViewById(R.id.edJobRig);

        rbIssues = (RadioButton)findViewById(R.id.rbNoIssues);
        rbNoIssues = (RadioButton)findViewById(R.id.rbYesIssues);

        addJobURL = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_JOB_ADD);
        jobInitData = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_JOB_INITIAL_DATA);

        SwipeRefreshLayout refreshAddJob = (SwipeRefreshLayout) findViewById(R.id.swipeAddJob);

        refreshAddJob.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetJobData().execute(jobInitData);
                edJobNumber.setText("");
                edModemVer.setText("");
                edJobCircul.setText("0");
                edJobEngOneArr.setText("");
                edJobEngTwoArr.setText("");
                edJobEngOneLeft.setText("");
                edJobEngTwoLeft.setText("");
                edJobContainer.setText("");
                edJobContArr.setText("");
                edJobContLeft.setText("");
                edJobMaxTemp.setText("");
                edJobRig.setText("");
                refreshAddJob.setRefreshing(false);
            }
        });

        new GetJobData().execute(jobInitData);
    }

    @Override
    public void onClick(View view) {
        new AddJob().execute(addJobURL);
    }

    public class GetJobData extends AsyncTask<String, Void, JSONArray> {

        JSONArray jsonResponse = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbJob.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            pbJob.bringToFront();
            pbJob.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... connUrl) {

            try{
                shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
                String token = shPreferences.getString("token", "");

                jsonResponse = QueryUtils.getData(connUrl[0], token);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        protected void onPostExecute(JSONArray jResultArray){

            if (pbJob != null && pbJob.isShown() == true) {
                pbJob.setVisibility(View.GONE);
            }

            if (jResultArray != null){
                try{
                    // Clients parsing
                    ArrayList<String> clients = new ArrayList<String>();
                    JSONArray jClients = jResultArray.getJSONArray(0);
                    for (int i = 0; i < jClients.length(); i++){
                        clients.add(jClients.getString(i));
                    }
                    ArrayAdapter<String> adapter = null;
                    adapter = new ArrayAdapter<String>(AddJobActivity.this, R.layout.spinner_clients, clients);
                    spCLients.setAdapter(adapter);

                    // GDP parsing
                    ArrayList<String> gdps = new ArrayList<String>();
                    JSONArray jGdp = jResultArray.getJSONArray(1);
                    for (int i = 0; i < jGdp.length(); i++){
                        gdps.add(jGdp.getString(i));
                    }
                    adapter = new ArrayAdapter<String>(AddJobActivity.this, R.layout.spinner_items, gdps);
                    spGdp.setAdapter(adapter);

                    // Modems parsing
                    ArrayList<String> modems = new ArrayList<String>();
                    JSONArray jModem = jResultArray.getJSONArray(2);
                    for (int i = 0; i < jModem.length(); i++){
                        modems.add(jModem.getString(i));
                    }
                    adapter = new ArrayAdapter<String>(AddJobActivity.this, R.layout.spinner_items, modems);
                    spModems.setAdapter(adapter);

                    // BBPs parsing
                    ArrayList<String> bbps = new ArrayList<String>();
                    JSONArray jBbp = jResultArray.getJSONArray(3);
                    for (int i = 0; i < jBbp.length(); i++){
                        bbps.add(jBbp.getString(i));
                    }
                    adapter = new ArrayAdapter<String>(AddJobActivity.this, R.layout.spinner_items, bbps);
                    spBbp.setAdapter(adapter);

                    // Engineers parsing
                    ArrayList<String> engineers = new ArrayList<String>();
                    JSONArray jEngineers = jResultArray.getJSONArray(4);
                    for (int i = 0; i < jEngineers.length(); i++){
                        engineers.add(jEngineers.getString(i));
                    }
                    adapter = new ArrayAdapter<String>(AddJobActivity.this, R.layout.spinner_items, engineers);
                    spEngOne.setAdapter(adapter);
                    spEngTwo.setAdapter(adapter);

                    // Batteries parsing
                    ArrayList<String> batteries = new ArrayList<String>();
                    JSONArray jBatteries = jResultArray.getJSONArray(5);
                    for (int i = 0; i < jBatteries.length(); i++){
                        batteries.add(jBatteries.getString(i));
                    }
                    adapter = new ArrayAdapter<String>(AddJobActivity.this, R.layout.spinner_items, batteries);
                    spBatteries.setAdapter(adapter);


                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public class AddJob extends AsyncTask<String, Void, String> {

        String id_response = null;

        String jobNumber = edJobNumber.getText().toString();
        String client = spCLients.getSelectedItem().toString();
        String gdp = spGdp.getSelectedItem().toString();
        String modem = spModems.getSelectedItem().toString();
        String modVer = edModemVer.getText().toString();
        String bbp = spBbp.getSelectedItem().toString();
        float circHours = Float.parseFloat(edJobCircul.getText().toString());
        String battery = spBatteries.getSelectedItem().toString();
        String maxTemp = edJobMaxTemp.getText().toString();
        String engOne = spEngOne.getSelectedItem().toString();
        String engTwo = spEngTwo.getSelectedItem().toString();
        String engOneArr = edJobEngOneArr.getText().toString();
        String engTwoArr = edJobEngTwoArr.getText().toString();
        String engOneLeft = edJobEngOneLeft.getText().toString();
        String engTwoLeft = edJobEngTwoLeft.getText().toString();
        String container = edJobContainer.getText().toString();
        String contArrived = edJobContArr.getText().toString();
        String contLeft = edJobContLeft.getText().toString();
        String rig = edJobRig.getText().toString();

        String issues = null;
        String comment = edJobComment.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbJob.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            pbJob.bringToFront();
            pbJob.setVisibility(View.VISIBLE);

            if (rbIssues.isChecked()) { issues = "Yes"; } else { issues = "No"; }
        }

        @Override
        protected String doInBackground(String... connUrl) {

            // TODO - complete alert dialog with errors

            try {
                shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
                String token = shPreferences.getString("token", "");

                JSONObject jobObject = new JSONObject();
                jobObject.put("JobNumber", jobNumber);
                jobObject.put("ClientName", client);
                jobObject.put("GDP", gdp);
                jobObject.put("Modem", modem);
                jobObject.put("ModemVersion", modVer);
                jobObject.put("Bullplug", bbp);
                jobObject.put("CirculationHours", circHours);
                jobObject.put("Battery", battery);
                jobObject.put("MaxTemp", maxTemp);
                jobObject.put("EngineerOne", engOne);
                jobObject.put("EngineerTwo", engTwo);
                jobObject.put("eng_one_arrived", engOneArr);
                jobObject.put("eng_two_arrived", engTwoArr);
                jobObject.put("eng_one_left", engOneLeft);
                jobObject.put("eng_two_left", engTwoLeft);
                jobObject.put("Container", container);
                jobObject.put("ContainerArrived", contArrived);
                jobObject.put("ContainerLeft", contLeft);
                jobObject.put("Rig", rig);
                jobObject.put("Issues", issues);
                jobObject.put("Comment", comment);

                id_response = QueryUtils.saveData(jobObject, connUrl[0], token);

                Log.d(DataBaseHelper.LOG_TAG, "JobObject: " + jobObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return id_response;
        }

        protected void onPostExecute(String id_response) {

            if (pbJob != null && pbJob.isShown() == true) {
                pbJob.setVisibility(View.GONE);
            }
            Log.d(DataBaseHelper.LOG_TAG, "RESULT: " + id_response);
            if (id_response != null) {
                Toast.makeText(AddJobActivity.this, "Job " + jobNumber + " saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddJobActivity.this, "Unable to save", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // TODO - complete adding job validation
    private boolean validateJob(){
        boolean output = true;
        errorJob = new ArrayList<>();

        if (edJobNumber.getText() == null){
            output = false;
            errorJob.add("Job Number is empty");
        }

        if (edJobContainer.getText() == null){
            output = false;
            errorJob.add("Container is empty");
        }


        return output;
    }
}
