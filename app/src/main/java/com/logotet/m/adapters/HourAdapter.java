package com.logotet.m.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.m.R;
import com.logotet.m.data.models.HourPill;

import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourHolder> {

    List<HourPill> hourPills;
    HourHolder.OnHourClickedListener listener;

    public HourAdapter(List<HourPill> hourPills, HourHolder.OnHourClickedListener listener) {
        this.hourPills = hourPills;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_view, parent, false);
        return new HourHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HourHolder holder, int position) {
        HourPill hourPill;
        hourPill = hourPills.get(position);
        holder.txtPillName.setText(hourPill.getName());
//        holder.pillTypeColor.setBackgroundColor(hourPill.getColor());
        holder.pillTypeColor.setBackgroundResource(hourPill.getColor());
        holder.setHourPillItem(hourPill);
    }

    @Override
    public int getItemCount() {
        return hourPills.size();
    }

    public static class HourHolder extends RecyclerView.ViewHolder {

        HourPill hourPillItem;
        TextView pillTypeColor, txtPillName, txtHourTake;

        public HourHolder(@NonNull View itemView, final OnHourClickedListener listener) {
            super(itemView);
            txtPillName = itemView.findViewById(R.id.txt_pill_name_hour);
            pillTypeColor = itemView.findViewById(R.id.color_substance);
            txtHourTake = itemView.findViewById(R.id.txt_hour_take);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onHourClicked(hourPillItem);
                }
            });
        }

        public void setHourPillItem(HourPill hourPillItem){
            this.hourPillItem = hourPillItem;
        }

        public interface OnHourClickedListener{
            void onHourClicked(HourPill hourPillItem);
        }
    }
}
