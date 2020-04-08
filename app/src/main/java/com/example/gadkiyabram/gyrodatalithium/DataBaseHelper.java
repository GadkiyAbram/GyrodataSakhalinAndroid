package com.example.gadkiyabram.gyrodatalithium;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends PrefActivity{

    // sqlserver    1433    Equ     aleks   aleks

    private String IP_ADDR = null;
    private String IP_PORT = null;

    public static String LOG_TAG = "mylogs";
    static final String APP_SETTINGS = "U_SETTING";

    // Authenticate
    public static final String PATH_AUTHENTICATE = "/AuthServices/AuthService.svc/Authenticate";

    // Batteries
    public static final String PATH_SELECTED_BATTERIES = "/batteryservices/batteryservice.svc/GetSelectedBatteries";
    public static final String PATH_BATTERY_ADD = "/batteryservices/batteryservice.svc/AddBattery";

    // Tools
    public static final String PATH_SELECTED_ITEMS = "/toolservices/toolservice.svc/GetCustomItems";
    public static final String PATH_ITEM_ADD = "/toolservices/toolservice.svc/AddNewItem";

    // Jobs
    public static final String PATH_SELECTED_JOBS = "/jobservices/jobservice.svc/GetCustomJobData";
    public static final String PATH_JOB_ADD = "/jobservices/jobservice.svc/AddNewJob";
    public static final String PATH_JOB_INITIAL_DATA = "/JobServices/JobService.svc/GetAllDataForJobCreate";

    /*Columns Battery Inventory,
        LBatteries table
    */
    public static final String BATTERY_ID = "Id";
    public static final String BATTERY_BOX = "BoxNumber";
    public static final String BATTERY_CONDITION = "BatteryCondition";
    public static final String BATTERY_SERIALONE = "SerialOne";
    public static final String BATTERY_SERIALTWO = "SerialTwo";
    public static final String BATTERY_SERIALTHR = "SerialThr";
    public static final String BATTERY_CCD = "CCD";
    public static final String BATTERY_INVOICE = "Invoice";
    public static final String BATTERY_STATUS = "BatteryStatus";
    public static final String BATTERY_ARRIVED = "Arrived";
    public static final String BATTERY_CONTAINER = "Container";
    public static final String BATTERY_COMMENT = "Comment";

    /*Columns GWD Jobs List,
        GWDTracker table
    */

    public static final String JOB_ID = "Id";
    public static final String JOB_JOBNUMBER = "JobNumber";
    public static final String JOB_CLIENT = "ClientName";
    public static final String JOB_GDP = "GDP";
    public static final String JOB_MODEM = "Modem";
    public static final String JOB_MODEMVERSION = "ModemVersion";
    public static final String JOB_BULLPLUG = "Bullplug";
    public static final String JOB_CIRCULATION = "CirculationHours";
    public static final String JOB_BATTERY = "Battery";
    public static final String JOB_MAXTEMP = "MaxTemp";
    public static final String JOB_ENGINEERONE = "EngineerOne";
    public static final String JOB_ENGINEERTWO = "EngineerTwo";
    public static final String JOB_ENG_ONE_ARRIVED = "eng_one_arrived";
    public static final String JOB_ENG_TWO_ARRIVED = "eng_two_arrived";
    public static final String JOB_ENG_ONE_LEFT = "eng_one_left";
    public static final String JOB_ENG_TWO_LEFT = "eng_two_left";
    public static final String JOB_CONTAINER = "Container";
    public static final String JOB_CONT_ARRIVED = "ContainerArrived";
    public static final String JOB_CONT_LEFT = "ContainerLeft";
    public static final String JOB_RIG = "Rig";
    public static final String JOB_ISSUES = "Issues";
    public static final String JOB_COMMENT = "Comment";

    /*Columns GWD Tools List,
        Emp table
    */

    public static final String ITEM_ID = "Id";
    public static final String ITEM_ITEM = "Item";
    public static final String ITEM_ASSET = "Asset";
    public static final String ITEM_ARRIVED = "Arrived";
    public static final String ITEM_INVOICE = "Invoice";
    public static final String ITEM_CCD = "CCD";
    public static final String ITEM_CIRCULATION = "Circulation";
    public static final String ITEM_NAMERUS = "NameRus";
    public static final String ITEM_POSITION = "PositionCCD";
    public static final String ITEM_STATUS = "ItemStatus";
    public static final String ITEM_BOX = "Box";
    public static final String ITEM_CONTAINER = "Container";
    public static final String ITEM_COMMENT = "Comment";
    public static final String ITEM_ITEM_IMAGE = "ItemImage";

    // Leave as a Template
    static String connectString(String serverName,
                                String ipAddress,
                                String port,
                                String dbName){
        StringBuilder connBuilder = new StringBuilder();
        connBuilder.append("jdbc:jtds:")
                .append(serverName)
                .append("://")
                .append(ipAddress)
                .append(":")
                .append(port)
                .append(";databaseName=")
                .append(dbName)
                .append(";loginTimeout=10;socketTimeout=10");
        return connBuilder.toString();
    }

    public static String getConnectionString(Context context, String pathWorking){
        String finalUrl = null;

        SharedPreferences shPreferences = context.getSharedPreferences(DataBaseHelper.APP_SETTINGS, Context.MODE_PRIVATE);

        String url = shPreferences.getString(DataBaseHelper.URL, "");
        String port = shPreferences.getString(DataBaseHelper.PORT, "");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://")
                .append(url)
                .append(":")
                .append(port)
                .append(pathWorking);
        finalUrl = stringBuilder.toString();
        Log.d(DataBaseHelper.LOG_TAG, "FINAL URL ====== " + finalUrl);

        return finalUrl;
    }

    protected ArrayList<String> getSavedPreferences(Context context, SharedPreferences userPreferences){

        ArrayList<String> listPreferences = new ArrayList<>();
        userPreferences = context.getSharedPreferences(APP_SETTINGS, MODE_PRIVATE);
        String serverName = userPreferences.getString(PrefActivity.SERVER_NAME, "");
        String ipAddress = userPreferences.getString(PrefActivity.IP_ADDRESS, "");
        String port = userPreferences.getString(PrefActivity.PORT, "");
        String dbName = userPreferences.getString(PrefActivity.DATABASE_NAME, "");
        String login = userPreferences.getString(PrefActivity.USER_LOGIN, "");
        String pass = userPreferences.getString(PrefActivity.USER_PASSWORD, "");
        String connection = connectString(serverName, ipAddress, port, dbName);

        IP_ADDR = ipAddress;
        IP_PORT = port;

        listPreferences.add(connection);
        listPreferences.add(login);
        listPreferences.add(pass);
        listPreferences.add(IP_ADDR);
        listPreferences.add(IP_PORT);

        return listPreferences;
    }
}
