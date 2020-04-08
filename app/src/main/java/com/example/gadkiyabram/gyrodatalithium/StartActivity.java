package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "mylogs";

    ProgressBar pbHoriz;
    LinearLayout layout;
    Button btnSettings;
    SharedPreferences shPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        pbHoriz = (ProgressBar)findViewById(R.id.pbHoriz);
        pbHoriz.setProgressTintList(ColorStateList.valueOf(Color.RED));
        btnSettings = (Button)findViewById(R.id.toSettings);
        btnSettings.setOnClickListener(this);
        new InitializeSetting().execute();
//        new TestTokenConnection().execute("http://192.168.0.100:8081/TestServices/TestTokenService.svc/Test");
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(StartActivity.this, PrefActivity.class);
        startActivity(intent);
    }

//    public class TestTokenConnection extends AsyncTask<String, Integer, String> {
//
//        String status = null;
//
//        @Override
//        protected String doInBackground(String... connUrl) {
//            HttpURLConnection conn = null;
//            BufferedReader reader;
//
//            try{
//                int value = 0;
//                final URL url = new URL(connUrl[0]);
//                conn = (HttpURLConnection)url.openConnection();
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//                conn.setChunkedStreamingMode(0);
//                shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
//                String token = shPreferences.getString(DataBaseHelper.TOKEN, "");
//
//                // Pass token in HEADER!!!
//                conn.setRequestProperty("Content-type", "application/json; charset=utf-8");
//                conn.setRequestProperty("Token", token);
//                conn.setRequestMethod("POST");
//
//                Log.d(LOG_TAG, "TOKEN: " + token);
//
//                JSONObject objectToken = new JSONObject();
//
//                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
//                out.write(objectToken.toString().getBytes());
//                out.flush();
//                out.close();
//
//                int result = conn.getResponseCode();
//                Log.d(LOG_TAG, "" + result);
//                if (result == 200){
//
//                    InputStream in = new BufferedInputStream(conn.getInputStream());
//                    reader = new BufferedReader(new InputStreamReader(in));
//                    String line = null;
//                    status = reader.readLine();
//                    while (value < 100){
//                        value = value + 5;
//                        publishProgress(value);
//                        TimeUnit.MILLISECONDS.sleep(100);
//                    }
//                }
//            }catch (Exception ex){
//
//            }
//            Log.d(LOG_TAG, "" + status);
//            return status;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            pbHoriz.setProgress(values[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            Log.d(LOG_TAG, "" + result);
//            if (result == "true"){
//                Intent intent = new Intent(StartActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//            else{
//                Toast.makeText(getApplicationContext(), "Your token invalid, go to setting to request", Toast.LENGTH_SHORT).show();
//                btnSettings.setVisibility(View.VISIBLE);
//            }
//        }
//    }

    public class InitializeSetting extends AsyncTask<Void, Integer, Boolean>{

        boolean initialized = false;
        String fileName = "U_SETTING.xml";

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                int value = 0;
                File file = new File("/data/data/" + getPackageName() +  "/shared_prefs/" + fileName);
                if (file.exists()){
                    initialized = true;
                } else {

                }
                while (value < 100){
                    value = value + 5;
                    publishProgress(value);
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return initialized;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pbHoriz.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == true){
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(StartActivity.this, "File with settings not found", Toast.LENGTH_LONG).show();
            }
        }
    }
}