package com.example.gadkiyabram.gyrodatalithium;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecyclerBatteryViewHolder extends RecyclerView.ViewHolder{
    TextView boxNumber;
    TextView serNum1;
    TextView dOfManuf;
    TextView condition;

    public RecyclerBatteryViewHolder(View itemView){
        super(itemView);
        CardView cv = (CardView)itemView.findViewById(R.id.cardView);
        boxNumber = (TextView)itemView.findViewById(R.id.tvBoxN);
        serNum1 = (TextView)itemView.findViewById(R.id.tvSerialNumber1);
        dOfManuf= (TextView)itemView.findViewById(R.id.tvDateManuf);
        condition = (TextView)itemView.findViewById(R.id.tvCondition);
    }
}
