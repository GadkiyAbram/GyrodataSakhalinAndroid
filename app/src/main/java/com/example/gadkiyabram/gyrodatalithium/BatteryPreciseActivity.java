package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class BatteryPreciseActivity extends AppCompatActivity implements View.OnClickListener {

    TextView    batSerialOne, batSerialTwo, batSerialThr,
                batCondition, batCCD, batInvoice, batArrived,
                batLocation, batComment, batBoxN;

    FloatingActionButton fabButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_details);

        fabButtonBack = (FloatingActionButton)findViewById(R.id.fabBackButton);
        fabButtonBack.setOnClickListener(this);

        batSerialOne = (TextView)findViewById(R.id.tvSerial1);
        batSerialTwo = (TextView)findViewById(R.id.tvSerNum2);
        batSerialThr = (TextView)findViewById(R.id.tvSerNum3);

        batCondition = (TextView)findViewById(R.id.tvBatteryCondition);
        batCCD = (TextView)findViewById(R.id.tvBatteryCCD);
        batInvoice = (TextView)findViewById(R.id.tvBatteryInvoice);
        batArrived = (TextView)findViewById(R.id.tvManuf);
        batLocation = (TextView)findViewById(R.id.tvLocation);
        batComment = (TextView)findViewById(R.id.tvBatteryComments);
        batBoxN = (TextView)findViewById(R.id.tvBatteryBox);

        Intent intent = getIntent();
        String id = intent.getStringExtra(DataBaseHelper.BATTERY_ID);
        String serialOne = intent.getStringExtra(DataBaseHelper.BATTERY_SERIALONE);
        String serialTwo = intent.getStringExtra(DataBaseHelper.BATTERY_SERIALTWO);
        String serialThr = intent.getStringExtra(DataBaseHelper.BATTERY_SERIALTHR);
        String condition = intent.getStringExtra(DataBaseHelper.BATTERY_CONDITION);
        String ccd = intent.getStringExtra(DataBaseHelper.BATTERY_CCD);
        String invoice = intent.getStringExtra(DataBaseHelper.BATTERY_INVOICE);
        String arrived = intent.getStringExtra(DataBaseHelper.BATTERY_ARRIVED);
        String location = intent.getStringExtra(DataBaseHelper.BATTERY_STATUS);
        String container = intent.getStringExtra(DataBaseHelper.BATTERY_CONTAINER);
        String comment = intent.getStringExtra(DataBaseHelper.BATTERY_COMMENT);
        String boxN = intent.getStringExtra(DataBaseHelper.BATTERY_BOX);

        batBoxN.setText("Box: " + boxN);
        batSerialOne.setText(serialOne);
        batSerialTwo.setText(serialTwo);
        batSerialThr.setText(serialThr);
        batCondition.setText(condition);
        batCCD.setText(ccd);
        batInvoice.setText(invoice);
        batArrived.setText(arrived);
        batLocation.setText(location + "/" + container);
        batComment.setText(comment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabBackButton:
                BatteryPreciseActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
