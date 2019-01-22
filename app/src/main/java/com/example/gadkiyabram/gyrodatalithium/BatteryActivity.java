package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;

import java.sql.*;
import java.util.ArrayList;

public class BatteryActivity extends AppCompatActivity {
    private final String LOG_TAG = "mylogs";
    ArrayList<BatteryDetails> battery;

    RecyclerView mRecyclerView;
    LinearLayout linLayoutrecView;
    RecyclerView.LayoutManager mLayoutManager;
    public RecyclerBatteryAdapter myAdapter;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_activity);

        mRecyclerView = (RecyclerView)findViewById(R.id.batteryRecyclerView);
        linLayoutrecView = (LinearLayout)findViewById(R.id.linLayoutrecyclerView);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        battery = new ArrayList<>();
        mRecyclerView.setAdapter(myAdapter);

        new RecieveBatteries().execute();
    }

    public class RecieveBatteries extends AsyncTask<Void, Void, ArrayList<BatteryDetails>> {

        DataBaseHelper dbHelper = new DataBaseHelper();
        String MSSQL_DB = dbHelper.getSavedPreferences(getApplicationContext(), pref).get(0);
        String MSSQL_LOGIN = dbHelper.getSavedPreferences(getApplicationContext(), pref).get(1);
        String MSSQL_PASS = dbHelper.getSavedPreferences(getApplicationContext(), pref).get(2);
        String query = "select * from LBatteries order by condition";
//        String query = "select * from LBatteries where condition='used'";             //condition=used
//        String query = "select * from LBatteries where condition='new'";              //condition=new
//        String query = "select * from LBatteries where serNum1='S1-0525-0062'";         //serNum1 = S10525-0062
        ArrayList<BatteryDetails> batteryList = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        @Override
        protected ArrayList<BatteryDetails> doInBackground(Void... voids){

            try{
                con = DriverManager.getConnection(MSSQL_DB, MSSQL_LOGIN,MSSQL_PASS);
                if (con != null){
                    Log.d(LOG_TAG, "Connection established");
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        Log.d(LOG_TAG, "" + rs.getString("serNum1"));
                        int _id = rs.getInt("ID");
                        int boxNum = rs.getInt("boxN");
                        String condition = rs.getString(1);
                        String serNum1 = rs.getString(2);
                        String serNum2 = rs.getString(3);
                        String serNum3 = rs.getString(4);
                        String date = rs.getString(5);
                        String status = rs.getString(6);
                        String comment = rs.getString(7);
                        batteryList.add(new BatteryDetails(_id, boxNum, serNum1, serNum2, serNum3,
                                date, status, condition,comment));
                        }
                }
            }catch (SQLException sqlEx) {
//                sqlEx.printStackTrace();
                batteryList = null;
            }finally {
                //close connection ,stmt and resultset here
                if (con != null){
                    try { con.close(); } catch(SQLException se) { /*can't do anything */ }
                    try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
                    try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
                }
            }
            return batteryList;
        }

        @Override
        protected void onPostExecute(ArrayList<BatteryDetails> data){
            if (data != null){
                myAdapter = new RecyclerBatteryAdapter(getApplicationContext(), data, new RVClickListener() {
                    @Override
                    public void onItemClick(View v, int postition) {
                        Log.d(LOG_TAG, "selected: " + data.get(postition).getSerNum1());
                    }
                });
                mRecyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }else {
                Log.d(LOG_TAG, "Array is null");
                setContentView(R.layout.layout_no_connection);
                Button btn = (Button)findViewById(R.id.btnNoConnection);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }
    }
}
