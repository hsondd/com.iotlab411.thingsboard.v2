package com.iotlab411.thingsboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iotlab411.thingsboard.Model.ModelTemp;
import com.iotlab411.thingsboard.R;
import com.iotlab411.thingsboard.Utils.Utils;

import java.util.ArrayList;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.MyViewHolder> {
    public ArrayList<ModelTemp.Temperature> tempList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public TempAdapter(ArrayList<ModelTemp.Temperature> tempList, Context context, OnItemClickListener onItemClickListener) {
        this.tempList = tempList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temp, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelTemp.Temperature temperature = tempList.get(position);
        holder.tvTime.setText("Time: " + Utils.convertMilliSecondsToFormattedDate(temperature.getTs()));
        holder.tvValue.setText("Value: " + temperature.getValue() + "°C");
        holder.imgAnh.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvValue;
        ImageView imgAnh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvValue = itemView.findViewById(R.id.tv_value);
            imgAnh = itemView.findViewById(R.id.img_anh);
        }
    }

    public interface OnItemClickListener {
        void onCLickItem(int position);
    }
}
