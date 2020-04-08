package com.example.gadkiyabram.gyrodatalithium;

import android.content.ContentProvider;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrefActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "myLogs";

    TextView    tvUserToken;
    EditText    etIpAddress, etPort,
                etUserLogin, etUserPass;
    Button      btnAuthAcc, btnTestToken, btnSaveUrlPort;

    SharedPreferences   shPreferences;
    ProgressBar         pbWhileLoadingPrefs;

    FloatingActionButton fabButtonBack;

    String authURL = null;

    static final String APP_SETTINGS = "U_SETTING";
    static final String URL = "url";
    static final String PORT = "port";
    static final String TOKEN = "token";
    static final String SERVER_NAME = "server_name";
    static final String IP_ADDRESS = "ip_address";
    static final String DATABASE_NAME = "db_name";
    static final String USER_LOGIN = "user_login";
    static final String USER_PASSWORD = "user_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref_activity);

        fabButtonBack = (FloatingActionButton)findViewById(R.id.fabBackButton);
        fabButtonBack.setOnClickListener(this);

        pbWhileLoadingPrefs = (ProgressBar)findViewById(R.id.pbPrefTest);

        tvUserToken = (TextView)findViewById(R.id.tvUserToken);

        etIpAddress = (EditText)findViewById(R.id.etIp);
        etPort = (EditText)findViewById(R.id.etPort);

        etUserLogin = (EditText)findViewById(R.id.etUserLogin);
        etUserPass = (EditText)findViewById(R.id.etUserPassword);

        btnSaveUrlPort = (Button)findViewById(R.id.btnSaveUrlPort);
        btnSaveUrlPort.setOnClickListener(this);

        btnTestToken = (Button)findViewById(R.id.btnTestToken);
        btnTestToken.setOnClickListener(this);

        btnAuthAcc = (Button)findViewById(R.id.btnAuthAccount);
        btnAuthAcc.setOnClickListener(this);

        authURL = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_AUTHENTICATE);

        getUrlAndPort();
    }

    private void getUrlAndPort(){
        SharedPreferences shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
        String url = shPreferences.getString("url", "");
        String port = shPreferences.getString("port", "");
        etIpAddress.setText(url);
        etPort.setText(port);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.fabBackButton:
                PrefActivity.this.finish();
                break;
            case R.id.btnSaveUrlPort:
                String url = etIpAddress.getText().toString();
                String port = etPort.getText().toString();
                saveUrlAndPort(url, port);
                break;
            case R.id.btnTestToken:
//                new TestTokenConnection().execute("http://192.168.0.102:8081/TestServices/TestTokenService.svc/Test");
                break;
            case R.id.btnAuthAccount:
                new AuthorizeAccount().execute(authURL);
                break;
            default:
                break;
        }
    }

    void saveTokenUpdt(String token){
        Log.d(LOG_TAG, "TOKEN TO SAVE " + token);
        shPreferences = getSharedPreferences(APP_SETTINGS, 0);
        SharedPreferences.Editor editor = shPreferences.edit();
        String tokenFinal = token.substring(1, token.length() - 1);

        editor.putString(TOKEN, tokenFinal);
        editor.commit();

        Toast.makeText(this, "Token saved", Toast.LENGTH_SHORT).show();
    }
    void saveUrlAndPort(String url, String port){
        Log.d(LOG_TAG, "URL to save: " + url);
        Log.d(LOG_TAG, "PORT to save: " + port);
        shPreferences = getSharedPreferences(APP_SETTINGS, 0);
        SharedPreferences.Editor editor = shPreferences.edit();

        editor.putString(URL, url);
        editor.putString(PORT, port);
        editor.commit();
        Toast.makeText(this, "URL & PORT saved", Toast.LENGTH_SHORT).show();
    }

    public class AuthorizeAccount extends AsyncTask<String, Void, String> {

        String status = null;

        String username = etUserLogin.getText().toString();
        String password = etUserPass.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pbWhileLoadingPrefs.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            pbWhileLoadingPrefs.bringToFront();
            pbWhileLoadingPrefs.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... connUrl)
        {
            HttpURLConnection conn = null;
            BufferedReader reader;

            try{
                final URL url = new URL(connUrl[0]);
                Log.d(LOG_TAG, "URL: " + url);
                conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);

                conn.addRequestProperty("Content-type", "application/json; charset=utf-8");
                conn.setRequestMethod("POST");

                JSONObject authObject = new JSONObject();
                authObject.put("User", username);
                authObject.put("Password", password);

                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                out.write(authObject.toString().getBytes());
                out.flush();
                out.close();

                int result = conn.getResponseCode();
                Log.d(LOG_TAG, "" + result);
                if (result == 200){
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = reader.readLine();

                    Log.d(LOG_TAG, "" + line);
                    status = line.replace("\\", "");
                }
            }catch (Exception ex){

            }
            Log.d(LOG_TAG, "" + status);
            return status;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
            String tokenRecieved = null;

            if (result != null) {
                try {
                  tokenRecieved = result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (pbWhileLoadingPrefs != null && pbWhileLoadingPrefs.isShown() == true){
                pbWhileLoadingPrefs.setVisibility(View.GONE);
            }

            if (tokenRecieved != null){
                Log.d(LOG_TAG, "" + tokenRecieved);
                tvUserToken.setText(tokenRecieved);
                saveTokenUpdt(tokenRecieved);
                Toast.makeText(PrefActivity.this, "Token recieved", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(PrefActivity.this, "Token is null", Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "Token is null");
            }
        }
    }

    public class TestTokenConnection extends AsyncTask<String, Void, String> {

        String status = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pbWhileLoadingPrefs.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            pbWhileLoadingPrefs.bringToFront();
            pbWhileLoadingPrefs.setVisibility(View.VISIBLE);
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
                shPreferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE);
                String token = shPreferences.getString(TOKEN, "");

                // Pass token in HEADER!!!
                conn.setRequestProperty("Content-type", "application/json; charset=utf-8");
                conn.setRequestProperty("Token", token);
                conn.setRequestMethod("POST");

                Log.d(LOG_TAG, "TOKEN: " + token);

                JSONObject objectToken = new JSONObject();

                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                out.write(objectToken.toString().getBytes());
                out.flush();
                out.close();

                int result = conn.getResponseCode();
                Log.d(LOG_TAG, "" + result);
                if (result == 200){

                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    status = reader.readLine();
                }
            }catch (Exception ex){

            }
            Log.d(LOG_TAG, "" + status);
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
            if (pbWhileLoadingPrefs != null && pbWhileLoadingPrefs.isShown() == true){
                pbWhileLoadingPrefs.setVisibility(View.GONE);
            }
            Toast.makeText(PrefActivity.this, "" + result, Toast.LENGTH_SHORT).show();
        }
    }

//    void loadAccount(){
//        shPreferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE);
//        String serverName = shPreferences.getString(SERVER_NAME, "");
//        String ipAddress = shPreferences.getString(IP_ADDRESS, "");
//        String port = shPreferences.getString(PORT, "");
//        String dbName = shPreferences.getString(DATABASE_NAME, "");
//        String userLogin = shPreferences.getString(USER_LOGIN, "");
//        String userPass = shPreferences.getString(USER_PASSWORD, "");
//        etServerName.setText(serverName);
//        etIpAddress.setText(ipAddress);
//        etPort.setText(port);
//        etDbName.setText(dbName);
//        etUserLogin.setText(userLogin);
//        etUserPass.setText(userPass);
//        Toast.makeText(this, "Account loaded", Toast.LENGTH_SHORT).show();
//    }
}
