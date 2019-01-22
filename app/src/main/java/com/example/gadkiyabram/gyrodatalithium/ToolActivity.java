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

public class ToolActivity extends AppCompatActivity {

    private final String LOG_TAG = "mylogs";
    ArrayList<ToolDetails> tool;

    RecyclerView tRecyclerView;
    LinearLayout linLayoutrecView;
    RecyclerView.LayoutManager mLayoutManager;
    public RecyclerToolAdapter toolAdapter;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_activity);

        tRecyclerView = (RecyclerView)findViewById(R.id.toolRecyclerView);
        linLayoutrecView = (LinearLayout)findViewById(R.id.linLayoutrecyclerViewTool);

        tRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        tRecyclerView.setLayoutManager(mLayoutManager);

        tool = new ArrayList<>();
        tRecyclerView.setAdapter(toolAdapter);

        new ToolActivity.RecieveTools().execute();
    }

    public class RecieveTools extends AsyncTask<Void, Void, ArrayList<ToolDetails>> {
        DataBaseHelper dbHelper = new DataBaseHelper();

        String MSSQL_DB = dbHelper.getSavedPreferences(getApplicationContext(), pref).get(0);
        String MSSQL_LOGIN = dbHelper.getSavedPreferences(getApplicationContext(), pref).get(1);
        String MSSQL_PASS = dbHelper.getSavedPreferences(getApplicationContext(), pref).get(2);
        String query = "select * from Emp order by Item";
        ArrayList<ToolDetails> toolList = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        @Override
        protected ArrayList<ToolDetails> doInBackground(Void... voids) {

            try {
                con = DriverManager.getConnection(MSSQL_DB, MSSQL_LOGIN, MSSQL_PASS);
                if (con != null){
                    Log.d(LOG_TAG, "Connection established");
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        Log.d(LOG_TAG, "" + rs.getString("Item"));

                        int _id = rs.getInt(DataBaseHelper.COL_T_ID);
                        String item = rs.getString(DataBaseHelper.COL_T_1);
                        String asset = rs.getString(DataBaseHelper.COL_T_2);
                        String arrived = rs.getString(DataBaseHelper.COL_T_3);
                        String invoice = rs.getString(DataBaseHelper.COL_T_4);
                        String ccd = rs.getString(DataBaseHelper.COL_T_5);
                        String nameRus = rs.getString(DataBaseHelper.COL_T_6);
                        String posCCD = rs.getString(DataBaseHelper.COL_T_7);
                        String status = rs.getString(DataBaseHelper.COL_T_8);
                        String box = rs.getString(DataBaseHelper.COL_T_9);
                        String container = rs.getString(DataBaseHelper.COL_T_10);
                        String comment = rs.getString(DataBaseHelper.COL_T_11);
                        float circHrs = rs.getFloat(DataBaseHelper.COL_T_12);
                        toolList.add(new ToolDetails(_id, item, asset, arrived, circHrs, invoice,
                                ccd, nameRus, posCCD, status, box, container, comment));
                    }
                }
            } catch (SQLException sqlEx) {
//                sqlEx.printStackTrace();
                toolList = null;
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
            return toolList;
        }

        @Override
        protected void onPostExecute(ArrayList<ToolDetails> data) {
            if (data != null){
                toolAdapter = new RecyclerToolAdapter(getApplicationContext(), data, new RVClickListener() {
                    @Override
                    public void onItemClick(View v, int postition) {
                        Log.d(LOG_TAG, "selected: " + toolList.get(postition).getItemName());

                        Intent intentToolDetails = new Intent(ToolActivity.this, ToolPreciseActivity.class);
                        intentToolDetails.putExtra("_id", String.valueOf(toolList.get(postition).get_id()));
                        intentToolDetails.putExtra("item", toolList.get(postition).getItemName());
                        intentToolDetails.putExtra("asset", toolList.get(postition).getAsset());
                        intentToolDetails.putExtra("circHrs", String.valueOf(toolList.get(postition).getCircHrs()));
                        intentToolDetails.putExtra("arrived", toolList.get(postition).getArrived());
                        intentToolDetails.putExtra("invoice", toolList.get(postition).getInvoice());
                        intentToolDetails.putExtra("ccdNum", toolList.get(postition).getCcdNum());
                        intentToolDetails.putExtra("nameRus", toolList.get(postition).getNameRus());
                        intentToolDetails.putExtra("ccdPos", toolList.get(postition).getPositionCCD());
                        intentToolDetails.putExtra("location", toolList.get(postition).getLocation());
                        intentToolDetails.putExtra("boxDesc", toolList.get(postition).getBoxDesc());
                        intentToolDetails.putExtra("container", toolList.get(postition).getContainer());
                        intentToolDetails.putExtra("comment", toolList.get(postition).getComment());

                        startActivity(intentToolDetails);
                    }
                });
                tRecyclerView.setAdapter(toolAdapter);
                toolAdapter.notifyDataSetChanged();
            }else {
                setContentView(R.layout.layout_no_connection);
            }
        }
    }
}
