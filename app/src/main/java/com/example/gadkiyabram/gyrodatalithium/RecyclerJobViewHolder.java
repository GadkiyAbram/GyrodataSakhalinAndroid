package com.example.gadkiyabram.gyrodatalithium;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecyclerJobViewHolder extends RecyclerView.ViewHolder {
    TextView jobNumber;
    TextView client;
    TextView tool;
    TextView clientLogo;
    TextView circHrs;

    public RecyclerJobViewHolder(View jobView){
        super(jobView);
        jobNumber = (TextView)jobView.findViewById(R.id.tvJobNumber);
        client = (TextView)jobView.findViewById(R.id.tvClient);
        tool = (TextView)jobView.findViewById(R.id.tvTool);
        clientLogo = (TextView)jobView.findViewById(R.id.tvClientLogo);
        circHrs = (TextView)jobView.findViewById(R.id.tvCircHrs);
    }
}
