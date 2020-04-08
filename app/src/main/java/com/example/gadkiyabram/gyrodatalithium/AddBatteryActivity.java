package com.example.gadkiyabram.gyrodatalithium;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AddBatteryActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton confirmBatteryInsert;
    EditText boxNumber;
    EditText condition;
    EditText serialOne;
    EditText serialTwo;
    EditText serialThr;
    EditText ccd;
    EditText invoice;
    EditText arrived;
    EditText location;
    EditText container;
    EditText comments;

    ArrayList<String> errorBatteryList;

    FloatingActionButton fabButtonBack;

    String addBattURL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_add);

        confirmBatteryInsert = (FloatingActionButton)findViewById(R.id.fabAddBattery);
        confirmBatteryInsert.setOnClickListener(this);

        // Battery Fields
        boxNumber = (EditText)findViewById(R.id.edBox);
        condition = (EditText)findViewById(R.id.edCondition);
        serialOne = (EditText)findViewById(R.id.edSerial1);
        serialTwo = (EditText)findViewById(R.id.edSerial2);
        serialThr = (EditText)findViewById(R.id.edSerial3);
        ccd = (EditText)findViewById(R.id.edCCD);
        invoice = (EditText)findViewById(R.id.edInvoice);
        arrived = (EditText)findViewById(R.id.edArrived);
        location = (EditText)findViewById(R.id.edLocation);
        container = (EditText)findViewById(R.id.edContainer);
        comments = (EditText)findViewById(R.id.edComments);

        fabButtonBack = (FloatingActionButton)findViewById(R.id.fabBackButton);
        fabButtonBack.setOnClickListener(this);

        addBattURL = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_BATTERY_ADD);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabBackButton:
                AddBatteryActivity.this.finish();
                break;
            case R.id.fabAddBattery:
                new AddBattery().execute(addBattURL);
                break;
        }
    }

    public class AddBattery extends AsyncTask<String, Void, String>{

        String id_recieved = null;

        String BoxNumber = boxNumber.getText().toString();
        String Condition = condition.getText().toString();
        String SerialOne = serialOne.getText().toString();
        String SerialTwo = serialTwo.getText().toString();
        String SerialThree = serialThr.getText().toString();
        String CCD = ccd.getText().toString();
        String Invoice = invoice.getText().toString();
        String Arrived = arrived.getText().toString();
        String Location = location.getText().toString();
        String Container = container.getText().toString();
        String Comments = comments.getText().toString();

        ProgressBar pbWhileAddingBatteries;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbWhileAddingBatteries = (ProgressBar)findViewById(R.id.pbAddingBatteryTest);
            pbWhileAddingBatteries.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            pbWhileAddingBatteries.bringToFront();
            pbWhileAddingBatteries.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... connUrl) {

            try{

                SharedPreferences shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
                String token = shPreferences.getString("token", "");

                JSONObject batteryObject = new JSONObject();
                batteryObject.put("BoxNumber", BoxNumber);
                batteryObject.put("BatteryCondition", Condition);
                batteryObject.put("SerialOne", SerialOne);
                batteryObject.put("SerialTwo", SerialTwo);
                batteryObject.put("SerialThr", SerialThree);
                batteryObject.put("CCD", CCD);
                batteryObject.put("Invoice", Invoice);
                batteryObject.put("Arrived", Arrived);
                batteryObject.put("BatteryStatus", Location);
                batteryObject.put("Container", Container);
                batteryObject.put("Comment", Comments);

                id_recieved = QueryUtils.saveData(batteryObject, connUrl[0], token);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return id_recieved;
        }

        protected void onPostExecute(String id_recieved){

            Log.d(DataBaseHelper.LOG_TAG, "id_recieved : " + id_recieved );
            if (pbWhileAddingBatteries != null && pbWhileAddingBatteries.isShown() == true){
                pbWhileAddingBatteries.setVisibility(View.GONE);
            }

            if (id_recieved != null){
                Toast.makeText(AddBatteryActivity.this, "Battery " + SerialOne + " saved", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(AddBatteryActivity.this, "Unable to save", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean ValidateBattery(){
        errorBatteryList = new ArrayList<>();
        boolean output = true;

        String BoxNumber = boxNumber.getText().toString();
        String SerialOne = serialOne.getText().toString();
        String SerialTwo = serialTwo.getText().toString();
        String SerialThree = serialThr.getText().toString();
        String CCD = ccd.getText().toString();
        String Invoice = invoice.getText().toString();
        String Arrived = arrived.getText().toString();
        String Location = location.getText().toString();
        String Container = container.getText().toString();
        String Comments = comments.getText().toString();

        if (!BoxNumber.isEmpty()){
            if (BoxNumber.length() > 3){
                output = false;
                errorBatteryList.add("Box Number too long (<= 3)");
            }
        }
        return output;
    }
}
