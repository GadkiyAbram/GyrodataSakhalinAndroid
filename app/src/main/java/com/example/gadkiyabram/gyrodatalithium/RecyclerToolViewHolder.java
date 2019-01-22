package com.example.gadkiyabram.gyrodatalithium;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecyclerToolViewHolder extends RecyclerView.ViewHolder {
    TextView toolItem;
    TextView toolAsset;
    TextView toolCircHrs;
    TextView toolArrived;
    TextView toolInvoice;

    public RecyclerToolViewHolder(View jobView){
        super(jobView);
        toolItem = (TextView)jobView.findViewById(R.id.tvToolItem);
        toolAsset = (TextView)jobView.findViewById(R.id.tvToolAsset);
        toolCircHrs = (TextView)jobView.findViewById(R.id.tvToolCircHrs);
        toolArrived = (TextView)jobView.findViewById(R.id.tvArrived);
        toolInvoice = (TextView)jobView.findViewById(R.id.tvInvoice);
    }
}
