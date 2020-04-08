package com.example.gadkiyabram.gyrodatalithium;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerJobAdapter extends RecyclerView.Adapter<RecyclerJobViewHolder> {
    final static String LOG_TAG = "mylogs";
    Context contex;
    ArrayList<JobModel> job;
    RVClickListener listener;

    public Context getContext() {
        return contex;
    }
        public RecyclerJobAdapter(Context context, ArrayList<JobModel> job, RVClickListener listener) {
        this.contex = context;
        this.job = job;
        this.listener = listener;
    }

//    public RecyclerJobAdapter(Context context, ArrayList<JobModel> job) {
//        this.contex = context;
//        this.job = job;
//    }

    @Override
    public RecyclerJobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobitem, parent, false);
        final RecyclerJobViewHolder holder = new RecyclerJobViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerJobViewHolder holder, int position) {
        holder.jobNumber.setText(job.get(position).getJobN());
        holder.client.setText(job.get(position).getClient());
        holder.tool.setText("GDP: " + job.get(position).getTool() + " & M: " + job.get(position).getModem());
        holder.circHrs.setText(job.get(position).getCircHrs());
        holder.clientLogo.setText(clientInfo(job.get(position).getClient()));
        GradientDrawable circleColor = (GradientDrawable)holder.clientLogo.getBackground();
        int colorClient = getClientColor(holder.client.getText().toString().trim());
        holder.clientLogo.setText(clientInfo(holder.client.getText().toString().trim()));
        circleColor.setColor(colorClient);
    }

    @Override
    public int getItemCount() {
        return job.size();
    }

    public int getClientColor(String client){
        int color = 0;
        if (client == null){
            color = R.color.defaultColor;
        }else{
            switch (client){
                case "Schlumberger D&M":
                    color = R.color.slbClient;
                    break;
                case "Halliburton":
                    color = R.color.halClient;
                    break;
            }
        }
        return ContextCompat.getColor(getContext(), color);
    }

    public String clientInfo(String client){
        String clientToSet = null;
        switch (client){
            case "Schlumberger D&M":
                clientToSet = "SLB";
                break;
            case "Halliburton":
                clientToSet = "HAL";
                break;
        }
        return clientToSet;
    }
}
