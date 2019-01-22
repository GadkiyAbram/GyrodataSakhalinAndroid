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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "mylogs";
    static final String APP_SETTINGS = "U_SETTING";

    FloatingActionButton fabMain, fabTool, fabJob, fabBatt;
    Animation fabOpen, fabClose, fabClockwise, fabAntiClockwise;
    LinearLayout layTool, layJob, layBatt, layWithFab;

    LinearLayout lBatteries, lJobLog, lTools, lSettings;
    SharedPreferences preferences;
    String serverName, ipAddress, port, dbName, login, pass;
    String connection;
    ArrayList<String> conLogPass;


    TestConnection tc;
    RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //concerning floating menu layout
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

        fabMain.setOnClickListener(this);


        conLogPass = getPreferences(preferences);

        tc = new TestConnection();
        tc.execute();
    }

    protected ArrayList<String> getPreferences(SharedPreferences userPreferences){
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

                    Log.d(LOG_TAG, "Invisible");
                }else {
                    layJob.startAnimation(fabOpen);
                    layTool.startAnimation(fabOpen);
                    layBatt.startAnimation(fabOpen);
                    fabMain.startAnimation(fabClockwise);

                    layTool.setVisibility(View.VISIBLE);
                    layJob.setVisibility(View.VISIBLE);
                    layBatt.setVisibility(View.VISIBLE);

                    Log.d(LOG_TAG, "Visible");
                }
                break;
            case R.id.fab_tool:
                Log.d(LOG_TAG, "Tool pressed");
            case R.id.fab_battery:
                intentSelection = new Intent(MainActivity.this, NewBatteryActivity.class);
                startActivity(intentSelection);
                break;
        }
    }

    public class TestConnection extends AsyncTask<Void, Void, ArrayList<ConnectionDetails>> {

        DataBaseHelper dbHelper = new DataBaseHelper();

        ArrayList<ConnectionDetails> cd = new ArrayList<>();

        String conString = null;
        String userLogin = null;
        String userPass = null;
        String ipAddr = null;
        int ipPort;
        Connection con = null;

        boolean result = false;
        String login = null;

        Socket socket;

        ProgressBar pbWhileLoadingSetting;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbWhileLoadingSetting = (ProgressBar)findViewById(R.id.pbTest);
            pbWhileLoadingSetting.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            pbWhileLoadingSetting.bringToFront();
            pbWhileLoadingSetting.setVisibility(View.VISIBLE);
        }


        @Override
        protected ArrayList<ConnectionDetails> doInBackground(Void... voids) {
            conString = dbHelper.getSavedPreferences(getApplicationContext(), preferences).get(0);
            userLogin = dbHelper.getSavedPreferences(getApplicationContext(), preferences).get(1);
            userPass = dbHelper.getSavedPreferences(getApplicationContext(), preferences).get(2);
            ipAddr = dbHelper.getSavedPreferences(getApplicationContext(), preferences).get(3);
            ipPort = Integer.valueOf(dbHelper.getSavedPreferences(getApplicationContext(), preferences).get(4));

            try{
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ipAddr, ipPort), 10000);
                if (socket.isConnected() == true){
                    Log.d(LOG_TAG, "Socket available");
                    try {
                        con = DriverManager.getConnection(conString, userLogin, userPass);
                        Log.d(LOG_TAG, "Connected");
                        if (con != null){
                            result = true;
                            login = userLogin;
                            cd.add(new ConnectionDetails(result, login));
                        }
                    }catch (SQLException e) {
                        e.printStackTrace();
                        Log.d(LOG_TAG, "Invalid credentials");
                    } finally {
                        //close connection
                        if (con != null){
                            try {
                                con.close();
                            } catch (SQLException se) { /*can't do anything */ }
                        }
                    }

                }
            }catch (SocketTimeoutException stoEx){
                Log.d(LOG_TAG, "Socket is not available");
            }catch (IOException ioEx){
                ioEx.printStackTrace();
            }finally {
                try {
                    if (socket != null){
                        Log.d(LOG_TAG, "Closing socket...");
                        socket.close();
                        Log.d(LOG_TAG, "Socket closed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return cd;
        }

        @Override
        protected void onPostExecute(ArrayList<ConnectionDetails> cd) {
            if (pbWhileLoadingSetting != null && pbWhileLoadingSetting.isShown() == true){
                pbWhileLoadingSetting.setVisibility(View.GONE);
            }
            if (!cd.isEmpty()){
                if (cd.get(0).iswConnected() == true){
//                    tvTestConnection.setText("Welcome, Mr. " + cd.get(0).getLogin());
                }
            }else {
                showAlertDialog();
            }
        }
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("No connection with DB")
                .setMessage(R.string.checkPref)
                .setNegativeButton(R.string.notNow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(R.string.toPreferences, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intentPref = new Intent(MainActivity.this, PrefActivity.class);
                        startActivity(intentPref);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
