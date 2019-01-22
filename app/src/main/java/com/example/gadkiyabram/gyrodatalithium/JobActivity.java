package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.sql.*;
import java.util.ArrayList;

public class JobActivity extends AppCompatActivity {
    private final String LOG_TAG = "mylogs";
    ArrayList<JobDetails> job;

    RecyclerView jRecyclerView;
    LinearLayout linLayoutrecView;
    RecyclerView.LayoutManager mLayoutManager;
    public RecyclerJobAdapter jobAdapter;

    SharedPreferences pref;
    static final String APP_SETTINGS = "U_SETTING";
    String serverName, ipAddress, port, dbName, login, pass;
    String connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_activity);

        jRecyclerView = (RecyclerView)findViewById(R.id.jobRecyclerView);
        linLayoutrecView = (LinearLayout)findViewById(R.id.linLayoutrecyclerViewBattery);

        jRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        jRecyclerView.setLayoutManager(mLayoutManager);

        job = new ArrayList<>();
        jRecyclerView.setAdapter(jobAdapter);

        new RecieveJobs().execute();

    }

    protected ArrayList<String> getSavedPreferences(SharedPreferences userPreferences) {
        ArrayList<String> listPreferences = new ArrayList<>();
        userPreferences = getSharedPreferences(APP_SETTINGS, 0);
        serverName = userPreferences.getString(PrefActivity.SERVER_NAME, "");
        ipAddress = userPreferences.getString(PrefActivity.IP_ADDRESS, "");
        port = userPreferences.getString(PrefActivity.PORT, "");
        dbName = userPreferences.getString(PrefActivity.DATABASE_NAME, "");
        login = userPreferences.getString(PrefActivity.USER_LOGIN, "");
        pass = userPreferences.getString(PrefActivity.USER_PASSWORD, "");
        connection = DataBaseHelper.connectString(serverName, ipAddress, port, dbName);
        listPreferences.add(connection);
        listPreferences.add(login);
        listPreferences.add(pass);
        return listPreferences;
    }

    public class RecieveJobs extends AsyncTask<Void, Void, ArrayList<JobDetails>> {

        DataBaseHelper dbHelper = new DataBaseHelper();

        String MSSQL_DB = dbHelper.getSavedPreferences(getApplicationContext(), pref).get(0);
        String MSSQL_LOGIN = dbHelper.getSavedPreferences(getApplicationContext(), pref).get(1);
        String MSSQL_PASS= dbHelper.getSavedPreferences(getApplicationContext(), pref).get(2);

        String query = "select * from GWDTracker";
//        String query = "select * from GWDTracker where Tool='G0211H'";             //tool = 'G0211H'
        ArrayList<JobDetails> jobList = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        @Override
        protected ArrayList<JobDetails> doInBackground(Void... voids) {

            try {
                con = DriverManager.getConnection(MSSQL_DB, MSSQL_LOGIN, MSSQL_PASS);
                if (con != null){
                    Log.d(LOG_TAG, "Connection established");
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        Log.d(LOG_TAG, "" + rs.getString("jobN"));

                        int _id = rs.getInt(DataBaseHelper.COL_J_ID);
                        String jobN = rs.getString(DataBaseHelper.COL_J_1);
                        String client = rs.getString(DataBaseHelper.COL_J_2);
                        String tool = rs.getString(DataBaseHelper.COL_J_3);
                        String modem = rs.getString(DataBaseHelper.COL_J_4);
                        String modemVer = rs.getString(DataBaseHelper.COL_J_5);
                        String circHrs = rs.getString(DataBaseHelper.COL_J_6);
                        String maxTmp = rs.getString(DataBaseHelper.COL_J_7);
                        String sEng1 = rs.getString(DataBaseHelper.COL_J_8);
                        String sEng2 = rs.getString(DataBaseHelper.COL_J_9);
                        String sEng1Arr = rs.getString(DataBaseHelper.COL_J_10);
                        String sEng2Arr = rs.getString(DataBaseHelper.COL_J_11);
                        String sEng1Lft = rs.getString(DataBaseHelper.COL_J_12);
                        String sEng2Lft = rs.getString(DataBaseHelper.COL_J_13);
                        String container = rs.getString(DataBaseHelper.COL_J_14);
                        String containerArr = rs.getString(DataBaseHelper.COL_J_15);
                        String containerLft = rs.getString(DataBaseHelper.COL_J_16);
                        String comments = rs.getString(DataBaseHelper.COL_J_17);
                        String rig = rs.getString(DataBaseHelper.COL_J_18);
                        String issues = rs.getString(DataBaseHelper.COL_J_19);
                        jobList.add(new JobDetails(_id, jobN, client, tool, modem, modemVer, circHrs, maxTmp,
                                sEng1, sEng2, sEng1Arr, sEng2Arr, sEng1Lft, sEng2Lft, container,
                                containerArr, containerLft, comments, rig, issues));
                    }
                }
            } catch (SQLException sqlEx) {
//                sqlEx.printStackTrace();
                jobList = null;
            } finally {
                if (con != null){
                    //close connection ,stmt and resultset here
                    try {
                        con.close();
                    } catch (SQLException se) { /*can't do anything */ }
                    try {
                        stmt.close();
                    } catch (SQLException se) { /*can't do anything */ }
                    try {
                        rs.close();
                    } catch (SQLException se) { /*can't do anything */ }
                }
            }
            return jobList;
        }

        @Override
        protected void onPostExecute(ArrayList<JobDetails> data) {
            if (data != null){
                jobAdapter = new RecyclerJobAdapter(getApplicationContext(), data, new RVClickListener() {
                    @Override
                    public void onItemClick(View v, int postition) {
                        Log.d(LOG_TAG, "selected: " + jobList.get(postition).getJobN());

                        Intent intentJobDetails = new Intent(JobActivity.this, JobPreciseActivity.class);
                        intentJobDetails.putExtra("_id", jobList.get(postition).get_id());
                        intentJobDetails.putExtra("jobN", jobList.get(postition).getJobN());
                        intentJobDetails.putExtra("client", jobList.get(postition).getClient());
                        intentJobDetails.putExtra("tool", jobList.get(postition).getTool());
                        intentJobDetails.putExtra("modem", jobList.get(postition).getModem());
                        intentJobDetails.putExtra("modemVer", jobList.get(postition).getModemVer());
                        intentJobDetails.putExtra("circHrs", jobList.get(postition).getCircHrs());
                        intentJobDetails.putExtra("maxTmp", jobList.get(postition).getMaxTemp());
                        intentJobDetails.putExtra("eng1", jobList.get(postition).getSurvEng1());
                        intentJobDetails.putExtra("eng2", jobList.get(postition).getSurvEng2());
                        intentJobDetails.putExtra("eng1Arr", jobList.get(postition).getEngArr1());
                        intentJobDetails.putExtra("eng2Arr", jobList.get(postition).getEngArr2());
                        intentJobDetails.putExtra("eng1Lft", jobList.get(postition).getEngLft1());
                        intentJobDetails.putExtra("eng2Lft", jobList.get(postition).getEngLft2());
                        intentJobDetails.putExtra("container", jobList.get(postition).getContainer());
                        intentJobDetails.putExtra("contArr", jobList.get(postition).getContArr());
                        intentJobDetails.putExtra("contLft", jobList.get(postition).getContLft());
                        intentJobDetails.putExtra("comment", jobList.get(postition).getComments());
                        intentJobDetails.putExtra("rig", jobList.get(postition).getRig());
                        intentJobDetails.putExtra("issues", jobList.get(postition).getIssues());

                        startActivity(intentJobDetails);
                    }
                });
                jRecyclerView.setAdapter(jobAdapter);
                jobAdapter.notifyDataSetChanged();
            }else {
                setContentView(R.layout.layout_no_connection);
            }
        }
    }


}
