package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.chrisbanes.photoview.PhotoView;

public class ToolPreciseActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvId, tvToolItem, tvToolAsset, tvToolCircHrs, tvArrive,
            tvInvoice, tvCCD, tvPosCCD, tvNameRus,
            tvBoxDesc, tvContainer, tvComment, tvLocation;
    PhotoView phItemView;

    FloatingActionButton fabButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_details);

        fabButtonBack = (FloatingActionButton)findViewById(R.id.fabBackButton);
        fabButtonBack.setOnClickListener(this);

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
        phItemView = (PhotoView)findViewById(R.id.imageItemPhoto);

        Intent intent = getIntent();
        String _id = intent.getStringExtra(DataBaseHelper.ITEM_ID);
        String item = intent.getStringExtra(DataBaseHelper.ITEM_ITEM);
        String asset = intent.getStringExtra(DataBaseHelper.ITEM_ASSET);
        String circHrs = intent.getStringExtra(DataBaseHelper.ITEM_CIRCULATION);
        String arrived = intent.getStringExtra(DataBaseHelper.ITEM_ARRIVED);
        String invoice = intent.getStringExtra(DataBaseHelper.ITEM_INVOICE);
        String ccdNum = intent.getStringExtra(DataBaseHelper.ITEM_CCD);
        String nameRus = intent.getStringExtra(DataBaseHelper.ITEM_NAMERUS);
        String ccdPos = intent.getStringExtra(DataBaseHelper.ITEM_POSITION);
        String location = intent.getStringExtra(DataBaseHelper.ITEM_STATUS);
        String box = intent.getStringExtra(DataBaseHelper.ITEM_BOX);
        String container = intent.getStringExtra(DataBaseHelper.ITEM_CONTAINER);
        String comment = intent.getStringExtra(DataBaseHelper.ITEM_COMMENT);
        String itemImage = intent.getStringExtra(DataBaseHelper.ITEM_ITEM_IMAGE);

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

        if (itemImage != null){
            Bitmap decodedImage = prepareImage(itemImage);
            phItemView.setImageBitmap(decodedImage);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabBackButton:
                ToolPreciseActivity.this.finish();
                break;
            default:
                break;
        }
    }

    private Bitmap prepareImage(String imageRecievedInString){
        Bitmap decodedImageInBytes = null;
        byte[] imageInBytes = Base64.decode(imageRecievedInString, Base64.DEFAULT);
        decodedImageInBytes = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
        return decodedImageInBytes;
    }
}
