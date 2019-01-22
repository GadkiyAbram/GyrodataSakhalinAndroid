package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class InitialActivity extends AppCompatActivity implements View.OnClickListener{

    private final String LOG_TAG = "mylogs";

    Button btnClose, btnSettings;

    Toast toast;

    ProgressBar pbHoriz;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

//        btnClose = (Button)findViewById(R.id.btnCloseApp);
        btnSettings = (Button)findViewById(R.id.btnGoToSettings);
        btnSettings.setOnClickListener(this);

        pbHoriz = (ProgressBar)findViewById(R.id.pbHoriz);
        pbHoriz.setProgressTintList(ColorStateList.valueOf(Color.RED));
        new InitializeSetting().execute();
    }

    @Override
    public void onClick(View view) {
        Intent intentSelection;
        switch (view.getId()){
            case R.id.btnGoToSettings:
                intentSelection = new Intent(InitialActivity.this, PrefActivity.class);
                startActivity(intentSelection);
                break;
        }
    }


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
                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                startActivity(intent);
            }else {
//                btnClose.setVisibility(View.VISIBLE);
                btnSettings.setVisibility(View.VISIBLE);
                toast = Toast.makeText(getApplicationContext(), "No settings", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0,100);
                toast.show();
            }
        }
    }
}