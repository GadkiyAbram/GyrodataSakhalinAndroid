package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ToolPreciseActivity extends AppCompatActivity {

    TextView tvId, tvToolItem, tvToolAsset, tvToolCircHrs, tvArrive,
            tvInvoice, tvCCD, tvPosCCD, tvNameRus,
            tvBoxDesc, tvContainer, tvComment, tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_details);

        tvId = (TextView)findViewById(R.id.tvId);
        tvToolItem = (TextView)findViewById(R.id.tvToolItem);
        tvToolAsset = (TextView)findViewById(R.id.tvToolAsset);
        tvToolCircHrs = (TextView)findViewById(R.id.tvCircHours);
        tvArrive = (TextView)findViewById(R.id.tvArrived);
        tvInvoice = (TextView)findViewById(R.id.tvInvoice);
        tvCCD = (TextView)findViewById(R.id.tvCCD);
        tvPosCCD = (TextView)findViewById(R.id.tvPosCCD);
        tvNameRus = (TextView)findViewById(R.id.tvNameRus);
        tvBoxDesc = (TextView)findViewById(R.id.tvBoxDesc);
        tvContainer = (TextView)findViewById(R.id.tvContainer);
        tvLocation = (TextView)findViewById(R.id.tvLocation);
        tvComment = (TextView)findViewById(R.id.tvComments);


        Intent intent = getIntent();
        String _id = intent.getStringExtra("_id");
        String item = intent.getStringExtra("item");
        String asset = intent.getStringExtra("asset");
        String circHrs = intent.getStringExtra("circHrs");
        String arrived = intent.getStringExtra("arrived");
        String invoice = intent.getStringExtra("invoice");
        String ccdNum = intent.getStringExtra("ccdNum");
        String nameRus = intent.getStringExtra("nameRus");
        String ccdPos = intent.getStringExtra("ccdPos");
        String location = intent.getStringExtra("location");
        String box = intent.getStringExtra("boxDesc");
        String container = intent.getStringExtra("container");
        String comment = intent.getStringExtra("comment");

        tvId.setText("ID: " + _id);
        tvToolItem.setText(item.trim());
        tvToolAsset.setText(asset.trim());
        tvToolCircHrs.setText(circHrs);
        tvArrive.setText(arrived.trim());
        tvInvoice.setText(invoice.trim());
        tvCCD.setText(ccdNum.trim());
        tvPosCCD.setText(ccdPos.trim());
        tvNameRus.setText(nameRus.trim());
        tvBoxDesc.setText(box.trim());
        tvLocation.setText(location.trim());
        tvContainer.setText(container.trim());
        tvComment.setText(comment.trim());
    }
}
