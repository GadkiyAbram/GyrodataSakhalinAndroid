package com.example.gadkiyabram.gyrodatalithium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class ToolActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView tRecyclerView;
    LinearLayout linLayoutrecView;
    RecyclerView.LayoutManager mLayoutManager;
    public RecyclerToolAdapter toolAdapter;
    EditText etItemSearch;
    // TODO - pass it to Helper file (create or update)
    SharedPreferences shPreferences;

    RadioButton rbItem, rbAsset, rbCcd, rbInvoice;
    String where = null;

    FloatingActionButton fabButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_activity);

        tRecyclerView = (RecyclerView)findViewById(R.id.toolRecyclerView);
        linLayoutrecView = (LinearLayout)findViewById(R.id.linLayoutrecyclerViewTool);
        etItemSearch = (EditText)findViewById(R.id.editItemSearch);

        tRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        tRecyclerView.setLayoutManager(mLayoutManager);

        fabButtonBack = (FloatingActionButton)findViewById(R.id.fabBackButton);
        fabButtonBack.setOnClickListener(this);

        rbItem = (RadioButton)findViewById(R.id.radioItemItemButton);
        rbAsset = (RadioButton)findViewById(R.id.radioItemAssetButton);
        rbCcd = (RadioButton)findViewById(R.id.radioItemCCDButton);
        rbInvoice = (RadioButton)findViewById(R.id.radioItemInvoiceButton);

        String urlFromSP = DataBaseHelper.getConnectionString(this, DataBaseHelper.PATH_SELECTED_ITEMS);

//        new ToolActivity.RecieveTools().execute("http://192.168.0.102:8081/toolservices/toolservice.svc/GetCustomItems?what=" + "" + "&where=" + where);
//        new ToolActivity.RecieveTools().execute(finalUrl + DataBaseHelper.PATH_SELECTED_ITEMS + "?what=" + "" + "&where=" + where);
        new ToolActivity.RecieveTools().execute(urlFromSP + "?what=" + "" + "&where=" + where);

        etItemSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (rbItem.isChecked()){ where = "Item";}
                if (rbAsset.isChecked()) { where = "Asset"; }
                if (rbCcd.isChecked()) {where = "CCD";}
                if (rbInvoice.isChecked()){ where = "Invoice"; }
                new ToolActivity.RecieveTools().execute(urlFromSP + "?what=" + etItemSearch.getText() + "&where=" + where);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabBackButton:
                ToolActivity.this.finish();
                break;
            default:
                break;
        }
    }

    public class RecieveTools extends AsyncTask<String, Void, JSONArray> {

        ProgressBar pbWhileLoadingTools;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbWhileLoadingTools = (ProgressBar)findViewById(R.id.pbWaiting);
            pbWhileLoadingTools.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            pbWhileLoadingTools.bringToFront();
            pbWhileLoadingTools.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... connUrl)
        {
            shPreferences = getSharedPreferences(DataBaseHelper.APP_SETTINGS, MODE_PRIVATE);
            String token = shPreferences.getString("token", "");
            if (connUrl.length < 1 || connUrl[0] == null){
                return null;
            }
            JSONArray jsonBatteriesArrayResult = null;
            try {
                jsonBatteriesArrayResult = QueryUtils.getData(connUrl[0], token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonBatteriesArrayResult;
        }

        protected void onPostExecute(JSONArray data) {
            ArrayList<ToolModel> toolList = new ArrayList<>();

            if (data != null) {
                try {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = data.getJSONObject(i);
                        int id = object.getInt(DataBaseHelper.ITEM_ID);
                        String item = object.getString(DataBaseHelper.ITEM_ITEM);
                        String asset = object.getString(DataBaseHelper.ITEM_ASSET);
                        String arrived = object.getString(DataBaseHelper.ITEM_ARRIVED);
                        String invoice = object.getString(DataBaseHelper.ITEM_INVOICE);
                        String ccd = object.getString(DataBaseHelper.ITEM_CCD);
                        float circulation = Float.parseFloat(object.getString(DataBaseHelper.ITEM_CIRCULATION));
                        String nameRus = object.getString(DataBaseHelper.ITEM_NAMERUS);
                        String positionCcd = object.getString(DataBaseHelper.ITEM_POSITION);
                        String itemStatus = object.getString(DataBaseHelper.ITEM_STATUS);
                        String box = object.getString(DataBaseHelper.ITEM_BOX);
                        String container = object.getString(DataBaseHelper.ITEM_CONTAINER);
                        String comment = object.getString(DataBaseHelper.ITEM_COMMENT);
                        String itemImage = object.getString(DataBaseHelper.ITEM_ITEM_IMAGE);
                        String itemCreated = object.getString(DataBaseHelper.RECORD_UPDATED);
                        String itemUpdated = object.getString(DataBaseHelper.RECORD_UPDATED);

                        toolList.add(new ToolModel(id, item, asset, arrived, circulation, invoice, ccd, nameRus,
                                positionCcd, itemStatus, box, container, comment, itemImage, itemCreated , itemUpdated));
                        Log.d(DataBaseHelper.LOG_TAG, "Image: " + itemImage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (toolList != null){

                    if (pbWhileLoadingTools != null && pbWhileLoadingTools.isShown() == true){
                        pbWhileLoadingTools.setVisibility(View.GONE);
                    }

                    toolAdapter = new RecyclerToolAdapter(getApplicationContext(), toolList, new RVClickListener() {
                        @Override
                        public void onItemClick(View v, int postition) {
                            Log.d(DataBaseHelper.LOG_TAG, "selected: " + toolList.get(postition).getItemName());

                            Intent intentToolDetails = new Intent(ToolActivity.this, ToolPreciseActivity.class);
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_ID, String.valueOf(toolList.get(postition).get_id()));
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_ITEM, toolList.get(postition).getItemName());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_ASSET, toolList.get(postition).getAsset());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_CIRCULATION, String.valueOf(toolList.get(postition).getCircHrs()));
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_ARRIVED, toolList.get(postition).getArrived());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_INVOICE, toolList.get(postition).getInvoice());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_CCD, toolList.get(postition).getCcdNum());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_NAMERUS, toolList.get(postition).getNameRus());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_POSITION, toolList.get(postition).getPositionCCD());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_STATUS, toolList.get(postition).getLocation());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_BOX, toolList.get(postition).getBoxDesc());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_CONTAINER, toolList.get(postition).getContainer());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_COMMENT, toolList.get(postition).getComment());
                            intentToolDetails.putExtra(DataBaseHelper.ITEM_ITEM_IMAGE, toolList.get(postition).getItemImage());
                            Log.d(DataBaseHelper.LOG_TAG, "Image: " + toolList.get(postition).getItemImage());
                            Log.d(DataBaseHelper.LOG_TAG, "Comment: " + toolList.get(postition).getComment());

                            startActivity(intentToolDetails);
                        }
                    });
                    tRecyclerView.setAdapter(toolAdapter);
                    toolAdapter.notifyDataSetChanged();
                }else {
                    setContentView(R.layout.layout_no_connection);
                }
            }
        }
    }
}
