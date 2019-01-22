package com.example.gadkiyabram.gyrodatalithium;

import android.content.ContentProvider;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrefActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "myLogs";

    EditText etServerName, etIpAddress, etPort,
                etDbName, etUserLogin, etUserPass;
    Button btnSaveAcc, btnTestConn, btnLoadAcc;

    SharedPreferences shPreferences;

    static final String APP_SETTINGS = "U_SETTING";
    static final String SERVER_NAME = "server_name";
    static final String IP_ADDRESS = "ip_address";
    static final String PORT = "port";
    static final String DATABASE_NAME = "db_name";
    static final String USER_LOGIN = "user_login";
    static final String USER_PASSWORD = "user_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref_activity);

        etServerName = (EditText)findViewById(R.id.etServerName);
        etIpAddress = (EditText)findViewById(R.id.etIp);
        etPort = (EditText)findViewById(R.id.etPort);
        etDbName = (EditText)findViewById(R.id.etDbName);
        etUserLogin = (EditText)findViewById(R.id.etUserLogin);
        etUserPass = (EditText)findViewById(R.id.etUserPassword);

        btnSaveAcc = (Button)findViewById(R.id.btnSaveAccount);
        btnSaveAcc.setOnClickListener(this);

        btnTestConn = (Button)findViewById(R.id.btnTestConn);
        btnTestConn.setOnClickListener(this);

        btnLoadAcc = (Button)findViewById(R.id.btnLoadAccount);
        btnLoadAcc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnSaveAccount:
                saveAccount();
                break;
            case R.id.btnTestConn:
                new TestConnection().execute();
                break;
            case R.id.btnLoadAccount:
                loadAccount();
                break;
            default:
                break;
        }
    }

    void saveAccount(){
        shPreferences = getSharedPreferences(APP_SETTINGS, 0);
        SharedPreferences.Editor editor = shPreferences.edit();
        editor.putString(SERVER_NAME, etServerName.getText().toString());
        editor.putString(IP_ADDRESS, etIpAddress.getText().toString());
        editor.putString(PORT, etPort.getText().toString());
        editor.putString(DATABASE_NAME, etDbName.getText().toString());
        editor.putString(USER_LOGIN, etUserLogin.getText().toString());
        editor.putString(USER_PASSWORD, etUserPass.getText().toString());
        editor.commit();
        Toast.makeText(this, "Account saved", Toast.LENGTH_SHORT).show();
    }

    public class TestConnection extends AsyncTask<Void, Void, Boolean> {

        DataBaseHelper dbHelper = new DataBaseHelper();

        String conString = null;
        String userLogin = null;
        String userPass = null;
        Connection con = null;
        boolean result = false;

        @Override
        protected Boolean doInBackground(Void... voids) {
            conString = dbHelper.getSavedPreferences(getApplicationContext(), shPreferences).get(0);
            userLogin = dbHelper.getSavedPreferences(getApplicationContext(), shPreferences).get(1);
            userPass = dbHelper.getSavedPreferences(getApplicationContext(), shPreferences).get(2);
            try {
                con = DriverManager.getConnection(conString, userLogin, userPass);
                if (con == null){
                    result = false;
                }else {
                    result = true;
                }

            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } finally {
                //close connection
                try {
                    if (con != null){
                        con.close();
                    }
                } catch (SQLException se) { /*can't do anything */ }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == true){
                btnTestConn.setText("Connected");
            }
        }
    }

    void loadAccount(){
        shPreferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE);
        String serverName = shPreferences.getString(SERVER_NAME, "");
        String ipAddress = shPreferences.getString(IP_ADDRESS, "");
        String port = shPreferences.getString(PORT, "");
        String dbName = shPreferences.getString(DATABASE_NAME, "");
        String userLogin = shPreferences.getString(USER_LOGIN, "");
        String userPass = shPreferences.getString(USER_PASSWORD, "");
        etServerName.setText(serverName);
        etIpAddress.setText(ipAddress);
        etPort.setText(port);
        etDbName.setText(dbName);
        etUserLogin.setText(userLogin);
        etUserPass.setText(userPass);
        Toast.makeText(this, "Account loaded", Toast.LENGTH_SHORT).show();
    }
}
