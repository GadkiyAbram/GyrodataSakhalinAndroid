package com.example.gadkiyabram.gyrodatalithium;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerBatteryAdapter extends RecyclerView.Adapter<RecyclerBatteryViewHolder> {
    final static String LOG_TAG = "mylogs";
    Context contex;
    ArrayList<BatteryModel> battery;
    RVClickListener listener;

    public Context getContext() {
        return contex;
    }

    public RecyclerBatteryAdapter(Context context, ArrayList<BatteryModel> battery, RVClickListener listener) {
        this.contex = context;
        this.battery = battery;
        this.listener = listener;
    }
//    public RecyclerBatteryAdapter(Context context, ArrayList<BatteryModel> battery) {
//        this.contex = context;
//        this.battery = battery;
//    }

    @Override
    public RecyclerBatteryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batteryitem, parent, false);
        final RecyclerBatteryViewHolder holder = new RecyclerBatteryViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerBatteryViewHolder holder, int position) {
        holder.boxNumber.setText("Pelicase: " + battery.get(position).getBoxN());
        holder.serNum1.setText(battery.get(position).getSerNum1());
        holder.dOfManuf.setText("Arrived: " + battery.get(position).getDate());
        holder.condition.setText(battery.get(position).getCondition());
        GradientDrawable circleColor = (GradientDrawable) holder.condition.getBackground();
        int colorCondition = getConditionColor(holder.condition.getText().toString().trim());
        circleColor.setColor(colorCondition);
    }

    @Override
    public int getItemCount() {
        return battery.size();
    }

    public int getConditionColor(String condition){
        int color = 0;
        if (condition == null){
            color = R.color.defaultColor;
        }else{
            switch (condition){
                case "New":
                    color = R.color.newBattColor;
                    break;
                case "Used":
                    color = R.color.usedBattColor;
                    break;
            }
        }
        return ContextCompat.getColor(getContext(), color);
    }
}
