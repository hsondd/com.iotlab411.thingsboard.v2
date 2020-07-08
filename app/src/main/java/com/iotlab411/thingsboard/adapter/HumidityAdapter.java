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

public class HumidityAdapter extends RecyclerView.Adapter<HumidityAdapter.MyViewHolder> {
    public ArrayList<ModelTemp.Humidity> humList;
    private Context context;
    private HumidityAdapter.OnItemClickListener onItemClickListener;

    public HumidityAdapter(ArrayList<ModelTemp.Humidity> humList, Context context, HumidityAdapter.OnItemClickListener onItemClickListener) {
        this.humList = humList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HumidityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temp, parent, false);
        return new HumidityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HumidityAdapter.MyViewHolder holder, int position) {
        ModelTemp.Humidity humidity = humList.get(position);
        holder.tvTime.setText("Time: " + Utils.convertMilliSecondsToFormattedDate(humidity.getTs()));
        holder.tvValue.setText("Value: " + humidity.getValue() + "%");
        holder.imgAnh.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return humList.size();
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
