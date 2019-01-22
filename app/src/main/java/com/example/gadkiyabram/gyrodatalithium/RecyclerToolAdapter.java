package com.example.gadkiyabram.gyrodatalithium;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerToolAdapter extends RecyclerView.Adapter<RecyclerToolViewHolder> {
    final static String LOG_TAG = "mylogs";
    Context contex;
    ArrayList<ToolDetails> tools;
    RVClickListener listener;

    public Context getContext() {
        return contex;
    }

    public RecyclerToolAdapter(Context context, ArrayList<ToolDetails> tools, RVClickListener listener) {
        this.contex = context;
        this.tools = tools;
        this.listener = listener;
    }

    @Override
    public RecyclerToolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toolitem, parent, false);
        final RecyclerToolViewHolder holder = new RecyclerToolViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerToolViewHolder holder, int position) {
        holder.toolItem.setText(tools.get(position).getItemName().trim());
        holder.toolAsset.setText(tools.get(position).getAsset().trim());
        holder.toolCircHrs.setText("C/h: " + String.valueOf(tools.get(position).getCircHrs()));
        holder.toolArrived.setText(tools.get(position).getArrived().trim());
        holder.toolInvoice.setText(tools.get(position).getInvoice().trim());
//        int colorClient = getClientColor(holder.client.getText().toString().trim());
    }

    @Override
    public int getItemCount() {
        return tools.size();
    }

}
