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

    /*Columns Battery Inventory,
        LBatteries table
    */
    public static final String DATABASE_NAME = "Equ";
    public static final String TABLE_NAME = "LBatteries";
    public static final String COL_ID = "_id";
    public static final String COL_1 = "boxN";
    public static final String COL_2 = "condition";
    public static final String COL_3 = "serNum1";
    public static final String COL_4 = "serNum2";
    public static final String COL_5 = "serNum3";
    public static final String COL_6 = "Date";
    public static final String COL_7 = "Status";
    public static final String COL_8 = "Comment";

    /*Columns GWD Jobs List,
        GWDTracker table
    */

//    public static final String DATABASE_JOBS_NAME = "GWDTracker";
//    public static final String TABLE_JOBS_NAME = "LBatteries";
    public static final String TABLE_JOBS_NAME = "LBatteries";
    public static final String COL_J_ID = "id";
    public static final String COL_J_1 = "JobN";
    public static final String COL_J_2 = "Client";
    public static final String COL_J_3 = "Tool";
    public static final String COL_J_4 = "Modem";
    public static final String COL_J_5 = "ModemVer";
    public static final String COL_J_6 = "CircHrs";
    public static final String COL_J_7 = "MaxTemp";
    public static final String COL_J_8 = "SurveyEng1";
    public static final String COL_J_9 = "SurveyEng2";
    public static final String COL_J_10 = "Eng1arr";
    public static final String COL_J_11 = "Eng2arr";
    public static final String COL_J_12 = "Eng1left";
    public static final String COL_J_13 = "Eng2left";
    public static final String COL_J_14 = "Container";
    public static final String COL_J_15 = "ContArr";
    public static final String COL_J_16 = "ContLeft";
    public static final String COL_J_17 = "Comments";
    public static final String COL_J_18 = "Rig";
    public static final String COL_J_19 = "Issues";

    /*Columns GWD Tools List,
        Emp table
    */

    public static final String DATABASE_TOOLS_NAME = "GWDTracker";
    public static final String TABLE_TOOLS_NAME = "Emp";
    public static final String COL_T_ID = "id";
    public static final String COL_T_1 = "Item";
    public static final String COL_T_2 = "Asset";
    public static final String COL_T_3 = "Arrived";
    public static final String COL_T_4 = "Invoice";
    public static final String COL_T_5 = "CCD";
    public static final String COL_T_6 = "NameRus";
    public static final String COL_T_7 = "PositionCCD";
    public static final String COL_T_8 = "Status";
    public static final String COL_T_9 = "Box";
    public static final String COL_T_10 = "Container";
    public static final String COL_T_11 = "Comment";
    public static final String COL_T_12 = "circHrs";

//    AsyncGetAll getAll = new AsyncGetAll();

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
