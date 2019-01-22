package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JobPreciseActivity extends AppCompatActivity {

    TextView tvId, tvJobNum, tvClient, tvTool,
            tvModem, tvModemVer, tvCircHrs,
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
        tvCircHrs = (TextView)findViewById(R.id.tvCircHrs);
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
        String _id = intent.getStringExtra("_id");
        String jobNum = intent.getStringExtra("jobN");
        String client = intent.getStringExtra("client");
        String tool = intent.getStringExtra("tool");
        String modem = intent.getStringExtra("modem");
        String modemVer = intent.getStringExtra("modemVer");
        String circHrs = intent.getStringExtra("circHrs");
        String maxTmp = intent.getStringExtra("maxTmp");
        String eng1 = intent.getStringExtra("eng1");
        String eng2 = intent.getStringExtra("eng2");
        String eng1Arr = intent.getStringExtra("eng1Arr");
        String eng2Arr = intent.getStringExtra("eng2Arr");
        String eng1Lft = intent.getStringExtra("eng1Lft");
        String eng2Lft = intent.getStringExtra("eng2Lft");
        String container = intent.getStringExtra("container");
        String contArr = intent.getStringExtra("contArr");
        String contLft = intent.getStringExtra("contLft");
        String comments = intent.getStringExtra("comment");
        String rig = intent.getStringExtra("rig");
        String issues = intent.getStringExtra("issues");

        tvId.setText(_id);
        tvJobNum.setText(jobNum);
        tvClient.setText(client);
        tvTool.setText(tool);
        tvModem.setText(modem);
        tvModemVer.setText(modemVer);
        tvCircHrs.setText(circHrs);
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
