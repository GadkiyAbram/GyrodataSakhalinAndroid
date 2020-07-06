package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.github.chrisbanes.photoview.PhotoView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddToolActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerGWD;

    FloatingActionButton confirmItemInsert, takeImageScreenShot;
    EditText itemAsset;
    EditText itemArrived;
    EditText itemInvoice;
    EditText itemCCD;
    EditText itemNameRus;
    EditText itemPosition;
    EditText itemStatus;
    EditText itemBox;
    EditText itemContainer;
    EditText itemComments;
    PhotoView itemScreenShot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_add);

        // TODO - Customize SpinnerGWD text color etc..
        spinnerGWD = (Spinner)findViewById(R.id.spinnerGWD);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.GWDEquipment, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGWD.setAdapter(adapter);

        itemAsset = (EditText)findViewById(R.id.edItemAsset);
        itemArrived = (EditText)findViewById(R.id.edAItemArrived);
        itemInvoice = (EditText)findViewById(R.id.edItemInvoice);
        itemCCD = (EditText)findViewById(R.id.edItemCCD);
        itemNameRus = (EditText)findViewById(R.id.edItemNameRussian);
        itemPosition = (EditText)findViewById(R.id.edItemPosition);
        itemStatus = (EditText)findViewById(R.id.edItemStatus);
        itemBox = (EditText)findViewById(R.id.edItemBox);
        itemContainer = (EditText)findViewById(R.id.edItemContainer);
        itemComments = (EditText)findViewById(R.id.edItemComments);

        itemScreenShot = (PhotoView) findViewById(R.id.imageItemPhoto);

        confirmItemInsert = (FloatingActionButton)findViewById(R.id.fabAddItem);
        confirmItemInsert.setOnClickListener(this);

        takeImageScreenShot = (FloatingActionButton)findViewById(R.id.fabAddItemImage);
        takeImageScreenShot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAddItem:
                String addItemURL = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_ITEM_ADD);
                new AddItem().execute(addItemURL);
                break;
            case R.id.fabAddItemImage:
                Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (imageTakeIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(imageTakeIntent, 101);
                }
                break;
        }
//        String addItemURL = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_ITEM_ADD);
//        new AddItem().execute(addItemURL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            itemScreenShot.setImageBitmap(imageBitmap);
        }
    }

    private String prepareImage(Bitmap itemImage){
        String preparedImage = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        itemImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        preparedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return preparedImage;
    }

    public class AddItem extends AsyncTask<String, Void, String> {

        String id_response = null;

        String ItemItem = spinnerGWD.getSelectedItem().toString();
        String ItemAsset = itemAsset.getText().toString();
        String ItemArrived = itemArrived.getText().toString();
        String ItemInvoice = itemInvoice.getText().toString();
        String ItemCCD = itemCCD.getText().toString();
        String ItemNameRus = itemNameRus.getText().toString();
        String ItemPosition = itemPosition.getText().toString();
        String ItemStatus = itemStatus.getText().toString();
        String ItemBox = itemBox.getText().toString();
        String ItemContainer = itemContainer.getText().toString();
        String ItemComments = itemComments.getText().toString();
        String ItemImage = prepareImage(((BitmapDrawable)itemScreenShot.getDrawable()).getBitmap());

        ProgressBar pbWhileAddingItems;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbWhileAddingItems = (ProgressBar)findViewById(R.id.pbAddingItemTest);
            pbWhileAddingItems.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            pbWhileAddingItems.bringToFront();
            pbWhileAddingItems.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... connUrl) {

            try{
                SharedPreferences shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
                String token = shPreferences.getString("token", "");

                JSONObject toolObject = new JSONObject();
                toolObject.put("Item", ItemItem);
                toolObject.put("Asset", ItemAsset);
                toolObject.put("Arrived", ItemArrived);
                toolObject.put("Invoice", ItemInvoice);
                toolObject.put("CCD", ItemCCD);
                toolObject.put("NameRus", ItemNameRus);
                toolObject.put("PositionCCD", ItemPosition);
                toolObject.put("ItemStatus", ItemStatus);
                toolObject.put("Box", ItemBox);
                toolObject.put("Container", ItemContainer);
                toolObject.put("Comment", ItemComments);
                toolObject.put("ItemImage", ItemImage);

                id_response = QueryUtils.saveData(toolObject, connUrl[0], token);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return id_response;
        }

        protected void onPostExecute(String id_response){

            if (pbWhileAddingItems != null && pbWhileAddingItems.isShown() == true){
                pbWhileAddingItems.setVisibility(View.GONE);
            }

            if (id_response != null){

                Toast.makeText(AddToolActivity.this, "" + ItemItem + " " + ItemAsset + " saved", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(AddToolActivity.this, "Unable to save", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
