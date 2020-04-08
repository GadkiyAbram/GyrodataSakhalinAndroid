package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JobPreciseActivity extends AppCompatActivity {

    TextView tvId, tvJobNum, tvClient, tvTool,
            tvModem, tvModemVer, tvBbp, tvCircHrs, tvBattery,
            tvMaxTemp, tvSurvEng1, tvSurvEng2,
            tvEngArr1, tvEngArr2, tvEngLft1,
            tvEngLft2, tvContainer, tvContArr,
            tvContLft, tvComments, tvRig, tvIssues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details);

        tvId = (TextView)findViewById(R.id.tvId);
        tvJobNum = (TextView)findViewById(R.id.tvJobNumber);
        tvClient = (TextView)findViewById(R.id.tvClient);
        tvTool = (TextView)findViewById(R.id.tvTool);
        tvModem = (TextView)findViewById(R.id.tvModem);
        tvModemVer = (TextView)findViewById(R.id.tvModemVersion);
        tvBbp = (TextView)findViewById(R.id.tvBbp);
        tvCircHrs = (TextView)findViewById(R.id.tvCircHrs);
        tvBattery = (TextView)findViewById(R.id.tvBattery);
        tvMaxTemp = (TextView)findViewById(R.id.tvMaxTmp);
        tvSurvEng1 = (TextView)findViewById(R.id.tvSurvEng1);
        tvSurvEng2 = (TextView)findViewById(R.id.tvSurvEng2);
        tvEngArr1 = (TextView)findViewById(R.id.tvEng1Arr);
        tvEngArr2 = (TextView)findViewById(R.id.tvEng2Arr);
        tvEngLft1 = (TextView)findViewById(R.id.tvEng1Lft);
        tvEngLft2 = (TextView)findViewById(R.id.tvEng2Lft);
        tvContainer = (TextView)findViewById(R.id.tvContainer);
        tvContArr = (TextView)findViewById(R.id.tvContainerArr);
        tvContLft = (TextView)findViewById(R.id.tvContainerLft);
        tvComments = (TextView)findViewById(R.id.tvComments);
        tvRig = (TextView)findViewById(R.id.tvRig);
        tvIssues = (TextView)findViewById(R.id.tvIssues);

        Intent intent = getIntent();
        String _id = intent.getStringExtra(DataBaseHelper.JOB_ID);
        String jobNum = intent.getStringExtra(DataBaseHelper.JOB_JOBNUMBER);
        String client = intent.getStringExtra(DataBaseHelper.JOB_CLIENT);
        String tool = intent.getStringExtra(DataBaseHelper.JOB_GDP);
        String modem = intent.getStringExtra(DataBaseHelper.JOB_MODEM);
        String modemVer = intent.getStringExtra(DataBaseHelper.JOB_MODEMVERSION);
        String bullplug = intent.getStringExtra(DataBaseHelper.JOB_BULLPLUG);
        String circHrs = intent.getStringExtra(DataBaseHelper.JOB_CIRCULATION);
        String battery = intent.getStringExtra(DataBaseHelper.JOB_BATTERY);
        String maxTmp = intent.getStringExtra(DataBaseHelper.JOB_MAXTEMP);
        String eng1 = intent.getStringExtra(DataBaseHelper.JOB_ENGINEERONE);
        String eng2 = intent.getStringExtra(DataBaseHelper.JOB_ENGINEERTWO);
        String eng1Arr = intent.getStringExtra(DataBaseHelper.JOB_ENG_ONE_ARRIVED);
        String eng2Arr = intent.getStringExtra(DataBaseHelper.JOB_ENG_TWO_ARRIVED);
        String eng1Lft = intent.getStringExtra(DataBaseHelper.JOB_ENG_ONE_LEFT);
        String eng2Lft = intent.getStringExtra(DataBaseHelper.JOB_ENG_TWO_LEFT);
        String container = intent.getStringExtra(DataBaseHelper.JOB_CONTAINER);
        String contArr = intent.getStringExtra(DataBaseHelper.JOB_CONT_ARRIVED);
        String contLft = intent.getStringExtra(DataBaseHelper.JOB_CONT_LEFT);
        String comments = intent.getStringExtra(DataBaseHelper.JOB_COMMENT);
        String rig = intent.getStringExtra(DataBaseHelper.JOB_RIG);
        String issues = intent.getStringExtra(DataBaseHelper.JOB_ISSUES);

        tvId.setText(_id);
        tvJobNum.setText(jobNum);
        tvClient.setText(client);
        tvTool.setText(tool);
        tvModem.setText(modem);
        tvBbp.setText(bullplug);
        tvModemVer.setText(modemVer);
        tvCircHrs.setText(circHrs);
        tvBattery.setText(battery);
        tvMaxTemp.setText(maxTmp);
        tvSurvEng1.setText(eng1);
        tvSurvEng2.setText(eng2);
        tvEngArr1.setText(eng1Arr);
        tvEngArr2.setText(eng2Arr);
        tvEngLft1.setText(eng1Lft);
        tvEngLft2.setText(eng2Lft);
        tvContainer.setText(container);
        tvContArr.setText(contArr);
        tvContLft.setText(contLft);
        tvComments.setText(comments);
        tvRig.setText(rig);
        tvIssues.setText(issues);
    }
}
