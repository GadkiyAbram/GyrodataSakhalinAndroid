package com.example.gadkiyabram.gyrodatalithium;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.gadkiyabram.gyrodatalithium.DataBaseHelper.LOG_TAG;
import static com.example.gadkiyabram.gyrodatalithium.DataBaseHelper.TOKEN;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fabMain, fabTool, fabJob, fabBatt;
    Animation fabOpen, fabClose, fabClockwise, fabAntiClockwise;
    LinearLayout layTool, layJob, layBatt, layWithFab;

    LinearLayout lBatteries, lJobLog, lTools, lSettings;
    SharedPreferences shPreferences;
    String serverName, ipAddress, port, dbName, login, pass;
    String connection;

    ProgressBar progressBar;

    TextView tvItemsCreatedAt, tvTotalItems,
            tvBatteriesCreatedAt, tvTotalBatteries,
            tvJobsCreatedAt, tvTotalJobs;

    RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // floating menu layout
        fabMain = (FloatingActionButton)findViewById(R.id.fab_main);
        fabTool = (FloatingActionButton)findViewById(R.id.fab_tool);
        fabJob = (FloatingActionButton)findViewById(R.id.fab_job);
        fabBatt = (FloatingActionButton)findViewById(R.id.fab_battery);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        layTool = (LinearLayout)findViewById(R.id.layout_tool);
        layJob = (LinearLayout)findViewById(R.id.layout_job);
        layBatt = (LinearLayout)findViewById(R.id.layout_battery);
        layWithFab = (LinearLayout)findViewById(R.id.layoutWithFab);

        layTool.setVisibility(View.GONE);
        layJob.setVisibility(View.GONE);
        layBatt.setVisibility(View.GONE);

        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);

        lBatteries = (LinearLayout)findViewById(R.id.layoutBatteries);
        lJobLog = (LinearLayout)findViewById(R.id.layoutJobLog);
        lTools = (LinearLayout)findViewById(R.id.layoutTools);
        lSettings = (LinearLayout)findViewById(R.id.layoutSettings);

        lBatteries.setOnClickListener(this);
        lJobLog.setOnClickListener(this);
        lTools.setOnClickListener(this);
        lSettings.setOnClickListener(this);

        fabTool.setOnClickListener(this);
        fabBatt.setOnClickListener(this);
        fabJob.setOnClickListener(this);

        fabMain.setOnClickListener(this);

        // TextViews for Items
        tvItemsCreatedAt = (TextView)findViewById(R.id.tools_updated);
        tvTotalItems = (TextView)findViewById(R.id.items_total);

        // TextViews for Batteries
        tvBatteriesCreatedAt = (TextView)findViewById(R.id.batteries_updated);
        tvTotalBatteries = (TextView)findViewById(R.id.batteries_total);

        // TextViews for Batteries
        tvJobsCreatedAt = (TextView)findViewById(R.id.jobs_updated);
        tvTotalJobs = (TextView)findViewById(R.id.jobs_total);

        String urlFromSP = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_SELECTED_ITEMS);

//        new TestTokenConnection().execute("http://192.168.0.100:8081/TestServices/TestTokenService.svc/Test");
    }

    protected ArrayList<String> getPreferences(SharedPreferences userPreferences){
        ArrayList<String> listPreferences = new ArrayList<>();
        userPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, 0);
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

    @Override
    public void onClick(View view){
        Intent intentSelection;
        switch (view.getId()){
            case R.id.layoutBatteries:
                intentSelection = new Intent(MainActivity.this, BatteryActivity.class);
                startActivity(intentSelection);
                break;
            case R.id.layoutJobLog:
                intentSelection = new Intent(MainActivity.this, JobActivity.class);
                startActivity(intentSelection);
                break;
            case R.id.layoutTools:
                intentSelection = new Intent(MainActivity.this, ToolActivity.class);
                startActivity(intentSelection);
                break;
            case R.id.layoutSettings:
                intentSelection = new Intent(MainActivity.this, PrefActivity.class);
                startActivity(intentSelection);
                break;
            case R.id.fab_main:
                if(layTool.getVisibility() == View.VISIBLE
                        && layJob.getVisibility() == View.VISIBLE
                        && layBatt.getVisibility() == View.VISIBLE){
                    layJob.startAnimation(fabClose);
                    layTool.startAnimation(fabClose);
                    layBatt.startAnimation(fabClose);
                    fabMain.startAnimation(fabAntiClockwise);

                    layTool.setVisibility(View.GONE);
                    layJob.setVisibility(View.GONE);
                    layBatt.setVisibility(View.GONE);
                }else {
                    layJob.startAnimation(fabOpen);
                    layTool.startAnimation(fabOpen);
                    layBatt.startAnimation(fabOpen);
                    fabMain.startAnimation(fabClockwise);

                    layTool.setVisibility(View.VISIBLE);
                    layJob.setVisibility(View.VISIBLE);
                    layBatt.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.fab_tool:
                intentSelection = new Intent(MainActivity.this, AddToolActivity.class);
                startActivity(intentSelection);
                break;
            case R.id.fab_battery:
                intentSelection = new Intent(MainActivity.this, AddBatteryActivity.class);
                startActivity(intentSelection);
                break;
            case R.id.fab_job:
                intentSelection = new Intent(MainActivity.this, AddJobActivity.class);
                startActivity(intentSelection);
                break;
        }
    }

    public class TestTokenConnection extends AsyncTask<String, Void, String> {

        String status = null;

        ProgressBar pbWhileTestingToken;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pbWhileTestingToken = (ProgressBar)findViewById(R.id.pbTestToken);
//            pbWhileTestingToken.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
//            pbWhileTestingToken.bringToFront();
//            pbWhileTestingToken.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn = null;
            BufferedReader reader;

            try{
                final URL url = new URL(connUrl[0]);
                conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);
                shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
                String token = shPreferences.getString(TOKEN, "");

                // Pass token in HEADER!!!
                conn.setRequestProperty("Content-type", "application/json; charset=utf-8");
                conn.setRequestProperty("Token", token);
                conn.setRequestMethod("POST");

                Log.d(DataBaseHelper.LOG_TAG, "TOKEN: " + token);

                JSONObject objectToken = new JSONObject();

                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                out.write(objectToken.toString().getBytes());
                out.flush();
                out.close();

                int result = conn.getResponseCode();
                Log.d(DataBaseHelper.LOG_TAG, "" + result);
                if (result == 200){

                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    status = reader.readLine();
                }
            }catch (Exception ex){

            }
            Log.d(DataBaseHelper.LOG_TAG, "" + status);
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(DataBaseHelper.LOG_TAG, "RESULT: " + result);
            super.onPostExecute(result);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "MainActivity: onResume()");
        // TODO - refactor urls

//        String urlItems = "http://192.168.0.102:8081/toolServices/toolService.svc/GetCustomItems?what=&where=";
//        String urlBatteries = "http://192.168.0.102:8081/batteryservices/batteryservice.svc/GetSelectedBatteries?what=&where=";
//        String urlJobs = "http://192.168.0.102:8081/jobservices/jobservice.svc/GetCustomJobData?what=&where=";
        String urlItems = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_SELECTED_ITEMS) + DataBaseHelper.PATH_QUERY;
        String urlBatteries = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_SELECTED_BATTERIES) + DataBaseHelper.PATH_QUERY;
        String urlJobs = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_SELECTED_JOBS) + DataBaseHelper.PATH_QUERY;
//        new RecieveTools().execute("http://192.168.0.102:8081/toolServices/toolService.svc/GetCustomItems?what=" + "" + "&where=");
        new RecieveTools().execute(urlItems, urlBatteries, urlJobs);
    }

    @Override
    public void onDestroy(){
        shPreferences = this.getSharedPreferences(DataBaseHelper.APP_SETTINGS,MODE_PRIVATE);
        shPreferences.edit().clear().commit();
        Log.d(LOG_TAG, "Preferences cleared");
        super.onDestroy();
    }

    // TODO - REFACTOR THIS

    public class RecieveTools extends AsyncTask<String, Void, Map<String, JSONArray>> {

        Map<String, JSONArray> initialData = new HashMap<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar)findViewById(R.id.pbWaiting);
            progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            progressBar.bringToFront();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Map<String, JSONArray> doInBackground(String... connUrl)
        {
            shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
            String token = shPreferences.getString("token", "");
            if (connUrl.length < 1 || connUrl[0] == null || connUrl[1] == null || connUrl[2] == null){
                return null;
            }
            JSONArray jsonItemsDataArrayResult = null;
            JSONArray jsonBatteriesDataArrayResult = null;
            JSONArray jsonJobsDataArrayResult = null;
            try {
                jsonItemsDataArrayResult = QueryUtils.getData(connUrl[0], token);
                initialData.put("Items", jsonItemsDataArrayResult);
                jsonBatteriesDataArrayResult = QueryUtils.getData(connUrl[1], token);
                initialData.put("Batteries", jsonBatteriesDataArrayResult);
                jsonJobsDataArrayResult = QueryUtils.getData(connUrl[2], token);
                initialData.put("Jobs", jsonJobsDataArrayResult);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return initialData;
        }

        protected void onPostExecute(Map<String, JSONArray> data) {
            ArrayList<ToolModel> toolList = new ArrayList<>();
            ArrayList<BatteryModel> batteryList = new ArrayList<>();
            ArrayList<JobModel> jobList = new ArrayList<>();

            JSONArray itemsInitial = data.get("Items");
            JSONArray batteriesInitial = data.get("Batteries");
            JSONArray jobsInitial = data.get("Jobs");

            // Parsing Items info
            if (itemsInitial != null) {
                try {
                    for (int i = 0; i < itemsInitial.length(); i++) {
                        JSONObject object = itemsInitial.getJSONObject(i);
                        int id = object.getInt(DataBaseHelper.RECORD_ID);
                        //remove if issues
                        String itemCreated = object.getString(DataBaseHelper.RECORD_UPDATED);
                        String itemUpdated = object.getString(DataBaseHelper.RECORD_UPDATED);
                        Log.d(LOG_TAG, "Items updated: " + itemUpdated);

                        toolList.add(new ToolModel(id, itemCreated, itemUpdated));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (toolList != null){

                    if (progressBar != null && progressBar.isShown() == true){
                        progressBar.setVisibility(View.GONE);
                    }

                    // Getting last record in toolList
                    String itemsUpdated = toolList.get(toolList.size() - 1).getItemUpdated();
                    String itemsInDB = String.valueOf(toolList.size());
                    Log.d(LOG_TAG, "Items updated: " + itemsUpdated);
                    Log.d(LOG_TAG, "Items total: " + itemsInDB);

                    // Setting values above to TVs
                    tvItemsCreatedAt.setText(itemsUpdated);
                    tvTotalItems.setText(itemsInDB);
                }else {
                    setContentView(R.layout.layout_no_connection);
                }
            }

            // Parsing Batteries info
            if (batteriesInitial != null) {
                try {
                    for (int i = 0; i < batteriesInitial.length(); i++) {
                        JSONObject object = batteriesInitial.getJSONObject(i);
                        int id = object.getInt(DataBaseHelper.RECORD_ID);
                        //remove if issues
                        String batteryCreated = object.getString(DataBaseHelper.RECORD_CREATED);
                        String batteryUpdated = object.getString(DataBaseHelper.RECORD_UPDATED);
                        Log.d(LOG_TAG, "Batteries updated: " + batteryUpdated);

                        batteryList.add(new BatteryModel(id, batteryCreated, batteryUpdated));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (toolList != null){

                    if (progressBar != null && progressBar.isShown() == true){
                        progressBar.setVisibility(View.GONE);
                    }

                    // Getting last record in toolList
                    String batteriesUpdated = batteryList.get(batteryList.size() - 1).getBatteryUpdated();
                    String batteriesInDB = String.valueOf(batteryList.size());
                    Log.d(LOG_TAG, "Batteries updated: " + batteriesUpdated);
                    Log.d(LOG_TAG, "Batteries total: " + batteriesInDB);

                    // Setting values above to TVs
                    tvBatteriesCreatedAt.setText(batteriesUpdated);
                    tvTotalBatteries.setText(batteriesInDB);

                }else {
                    setContentView(R.layout.layout_no_connection);
                }
            }

            // Parsing Jobs info
            if (jobsInitial != null) {
                try {
                    for (int i = 0; i < jobsInitial.length(); i++) {
                        JSONObject object = jobsInitial.getJSONObject(i);
                        int id = object.getInt(DataBaseHelper.RECORD_ID);
                        //remove if issues
                        String jobCreated = object.getString(DataBaseHelper.RECORD_UPDATED);
                        String jobUpdated = object.getString(DataBaseHelper.RECORD_UPDATED);
                        Log.d(LOG_TAG, "Batteries updated: " + jobUpdated);

                        jobList.add(new JobModel(id, jobCreated, jobUpdated));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (toolList != null){

                    if (progressBar != null && progressBar.isShown() == true){
                        progressBar.setVisibility(View.GONE);
                    }

                    // Getting last record in toolList
                    String jobsUpdated = jobList.get(jobList.size() - 1).getJobUpdated();
                    String jobsInDB = String.valueOf(jobList.size());
                    Log.d(LOG_TAG, "Batteries updated: " + jobsUpdated);
                    Log.d(LOG_TAG, "Batteries total: " + jobsInDB);

                    // Setting values above to TVs
                    tvJobsCreatedAt.setText(jobsUpdated);
                    tvTotalJobs.setText(jobsInDB);

                }else {
                    setContentView(R.layout.layout_no_connection);
                }
            }

        }
    }

//    public class TestConnection extends AsyncTask<Void, Void, ArrayList<ConnectionDetails>> {
//
//        DataBaseHelper dbHelper = new DataBaseHelper();
//
//        ArrayList<ConnectionDetails> cd = new ArrayList<>();
//
//        String conString = null;
//        String userLogin = null;
//        String userPass = null;
//        String ipAddr = null;
//        int ipPort;
//        Connection con = null;
//
//        boolean result = false;
//        String login = null;
//
//        Socket socket;
//
//        ProgressBar pbWhileLoadingSetting;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pbWhileLoadingSetting = (ProgressBar)findViewById(R.id.pbTest);
//            pbWhileLoadingSetting.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
//            pbWhileLoadingSetting.bringToFront();
//            pbWhileLoadingSetting.setVisibility(View.VISIBLE);
//        }
//
//
//        @Override
//        protected ArrayList<ConnectionDetails> doInBackground(Void... voids) {
//            conString = dbHelper.getSavedPreferences(getApplicationContext(), preferences).get(0);
//            userLogin = dbHelper.getSavedPreferences(getApplicationContext(), preferences).get(1);
//            userPass = dbHelper.getSavedPreferences(getApplicationContext(), preferences).get(2);
//            ipAddr = dbHelper.getSavedPreferences(getApplicationContext(), preferences).get(3);
//            ipPort = Integer.valueOf(dbHelper.getSavedPreferences(getApplicationContext(), preferences).get(4));
//
//            try{
//                Socket socket = new Socket();
//                socket.connect(new InetSocketAddress(ipAddr, ipPort), 10000);
//                if (socket.isConnected() == true){
//                    Log.d(LOG_TAG, "Socket available");
//                    try {
//                        con = DriverManager.getConnection(conString, userLogin, userPass);
//                        Log.d(LOG_TAG, "Connected");
//                        if (con != null){
//                            result = true;
//                            login = userLogin;
//                            cd.add(new ConnectionDetails(result, login));
//                        }
//                    }catch (SQLException e) {
//                        e.printStackTrace();
//                        Log.d(LOG_TAG, "Invalid credentials");
//                    } finally {
//                        //close connection
//                        if (con != null){
//                            try {
//                                con.close();
//                            } catch (SQLException se) { /*can't do anything */ }
//                        }
//                    }
//
//                }
//            }catch (SocketTimeoutException stoEx){
//                Log.d(LOG_TAG, "Socket is not available");
//            }catch (IOException ioEx){
//                ioEx.printStackTrace();
//            }finally {
//                try {
//                    if (socket != null){
//                        Log.d(LOG_TAG, "Closing socket...");
//                        socket.close();
//                        Log.d(LOG_TAG, "Socket closed");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return cd;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<ConnectionDetails> cd) {
//            if (pbWhileLoadingSetting != null && pbWhileLoadingSetting.isShown() == true){
//                pbWhileLoadingSetting.setVisibility(View.GONE);
//            }
//            if (!cd.isEmpty()){
//                if (cd.get(0).iswConnected() == true){
////                    tvTestConnection.setText("Welcome, Mr. " + cd.get(0).getLogin());
//                }
//            }else {
//                showAlertDialog();
//            }
//        }
//    }
//
//    public void showAlertDialog(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle("No connection with DB")
//                .setMessage(R.string.checkPref)
//                .setNegativeButton(R.string.notNow, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                })
//                .setPositiveButton(R.string.toPreferences, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intentPref = new Intent(MainActivity.this, PrefActivity.class);
//                        startActivity(intentPref);
//                    }
//                });
//
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
}
