package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class BatteryActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fabButtonBack;

    RecyclerView mRecyclerView;
    LinearLayout linLayoutrecView;
    RecyclerView.LayoutManager mLayoutManager;
    public RecyclerBatteryAdapter myAdapter;
    EditText etBatterySearch;

    RadioButton rbSerialOne, rbStatus, rbCCD, rbInvoice;
    String where = null;
    String battURL = null;

    SharedPreferences   shPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_activity);

        rbSerialOne = (RadioButton)findViewById(R.id.radioBatterySerialButton);
        rbStatus = (RadioButton)findViewById(R.id.radioBatteryStatusButton);
        rbCCD = (RadioButton)findViewById(R.id.radioBatteryCCDButton);
        rbInvoice = (RadioButton)findViewById(R.id.radioBatteryInvoiceButton);

        // configuring EditText
        etBatterySearch = (EditText)findViewById(R.id.editBatterySearch);

        mRecyclerView = (RecyclerView)findViewById(R.id.batteryRecyclerView);
        linLayoutrecView = (LinearLayout)findViewById(R.id.linLayoutrecyclerView);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        fabButtonBack = (FloatingActionButton)findViewById(R.id.fabBackButton);
        fabButtonBack.setOnClickListener(this);

        battURL = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_SELECTED_BATTERIES);

        new RecieveBatteries().execute(battURL + "?what=" + "" + "&where=" + "");

        etBatterySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (rbSerialOne.isChecked()){ where = "Serial 1";}
                if (rbStatus.isChecked()) { where = "Status"; }
                if (rbCCD.isChecked()) {where = "CCD";}
                if (rbInvoice.isChecked()){ where = "Invoice"; }
//                new RecieveBatteries().execute("http://192.168.0.100:8081/batteryservices/batteryservice.svc/GetSelectedBatteries?what=" + etBatterySearch.getText() + "&where=" + where);
                new RecieveBatteries().execute(battURL + "?what=" + etBatterySearch.getText() + "&where=" + where);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabBackButton:
                BatteryActivity.this.finish();
                break;
            default:
                break;
        }
    }

    public class RecieveBatteries extends AsyncTask<String, Void, JSONArray> {

        ProgressBar pbWhileLoadingBatteries;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbWhileLoadingBatteries = (ProgressBar)findViewById(R.id.pbWaiting);
            pbWhileLoadingBatteries.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            pbWhileLoadingBatteries.bringToFront();
            pbWhileLoadingBatteries.setVisibility(View.VISIBLE);
        }

        protected JSONArray doInBackground(String... connUrl)
        {
            shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
            String token = shPreferences.getString("token", "");
            if (connUrl.length < 1 || connUrl[0] == null){
                return null;
            }
            JSONArray jsonBatteriesArrayResult = null;
            try {
                jsonBatteriesArrayResult = QueryUtils.getData(connUrl[0], token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonBatteriesArrayResult;
        }

        protected void onPostExecute(JSONArray batteriesData){
            ArrayList<BatteryModel> batteryList = new ArrayList<>();

            if (batteriesData != null){

                try {
                    for (int i = 0; i < batteriesData.length(); i++) {
                        JSONObject object = batteriesData.getJSONObject(i);
                        int _id = object.getInt(DataBaseHelper.BATTERY_ID);
                        String boxNum = object.getString(DataBaseHelper.BATTERY_BOX);
                        String condition = object.getString(DataBaseHelper.BATTERY_CONDITION);
                        String serNum1 = object.getString(DataBaseHelper.BATTERY_SERIALONE);
                        String serNum2 = object.getString(DataBaseHelper.BATTERY_SERIALTWO);
                        String serNum3 = object.getString(DataBaseHelper.BATTERY_SERIALTHR);
                        String CCD = object.getString(DataBaseHelper.BATTERY_CCD);
                        String invoice = object.getString(DataBaseHelper.BATTERY_INVOICE);
                        String status = object.getString(DataBaseHelper.BATTERY_STATUS);
                        String date = object.getString(DataBaseHelper.BATTERY_ARRIVED);
                        String container = object.getString(DataBaseHelper.BATTERY_CONTAINER);
                        String comment = object.getString(DataBaseHelper.BATTERY_COMMENT);

                        batteryList.add(new BatteryModel(_id, boxNum, condition, serNum1, serNum2, serNum3, CCD, invoice,
                                date, status, container, comment));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (batteryList != null){
                    if (pbWhileLoadingBatteries != null && pbWhileLoadingBatteries.isShown() == true){
                        pbWhileLoadingBatteries.setVisibility(View.GONE);
                    }
                    myAdapter = new RecyclerBatteryAdapter(getApplicationContext(), batteryList, new RVClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            Log.d(DataBaseHelper.LOG_TAG, "selected id: " + batteryList.get(position).get_id() + " ### "
                                    + batteryList.get(position).getSerNum1());

                            Intent intentBatteryDetails = new Intent(BatteryActivity.this, BatteryPreciseActivity.class);
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_ID, String.valueOf(batteryList.get(position).get_id()));
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_BOX, batteryList.get(position).getBoxN());
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_CONDITION, batteryList.get(position).getCondition());
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_SERIALONE, batteryList.get(position).getSerNum1());
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_SERIALTWO, batteryList.get(position).getSerNum2());
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_SERIALTHR, batteryList.get(position).getSerNum3());
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_CCD, batteryList.get(position).getCCD());
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_INVOICE, batteryList.get(position).getInvoice());
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_ARRIVED, batteryList.get(position).getDate());
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_STATUS, batteryList.get(position).getStatus());
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_CONTAINER, batteryList.get(position).getContainer());
                            intentBatteryDetails.putExtra(DataBaseHelper.BATTERY_COMMENT, batteryList.get(position).getComment());

                            startActivity(intentBatteryDetails);
                        }
                    });
                    mRecyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }

            }
            else {
                Log.d(DataBaseHelper.LOG_TAG, "Array is null");
            }
        }
    }
}
