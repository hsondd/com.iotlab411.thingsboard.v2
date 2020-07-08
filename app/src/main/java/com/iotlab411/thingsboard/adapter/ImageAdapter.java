package com.iotlab411.thingsboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iotlab411.thingsboard.Model.ModelTemp;
import com.iotlab411.thingsboard.R;
import com.iotlab411.thingsboard.Utils.Utils;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    public ArrayList<ModelTemp.Image> imagList;
    private Context context;
    private ImageAdapter.OnItemClickListener onItemClickListener;

    public ImageAdapter(ArrayList<ModelTemp.Image> imagList, Context context, ImageAdapter.OnItemClickListener onItemClickListener) {
        this.imagList = imagList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temp, parent, false);
        return new ImageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyViewHolder holder, int position) {
        ModelTemp.Image image = imagList.get(position);
        holder.tvTime.setText("Time: " + Utils.convertMilliSecondsToFormattedDate(image.getTs()));
        holder.tvValue.setVisibility(View.GONE);
        holder.tvValue.setText("Link: " + image.getValue());
        Glide.with(context).load(image.getValue()).into(holder.imageViewAnh);
    }

    @Override
    public int getItemCount() {
        return imagList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvValue;
        ImageView imageViewAnh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvValue = itemView.findViewById(R.id.tv_value);
            imageViewAnh = itemView.findViewById(R.id.img_anh);
        }
    }

    public interface OnItemClickListener {
        void onCLickItem(int position);
    }
}
