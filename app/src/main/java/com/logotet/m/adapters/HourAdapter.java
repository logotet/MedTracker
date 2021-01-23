package com.logotet.m.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.m.R;
import com.logotet.m.data.models.HourPill;
import com.logotet.m.utils.AppConstants;

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
        holder.txtHourTake.setText(hourPill.getTime());
        holder.categoryColor.setBackgroundResource(hourPill.getColor());
        holder.setHourPillItem(hourPill);

    }

    @Override
    public int getItemCount() {
        return hourPills.size();
    }

    public static class HourHolder extends RecyclerView.ViewHolder {

        HourPill hourPillItem;
        TextView categoryColor, txtPillName, txtHourTake;
        CheckBox checkBox;

        public HourHolder(@NonNull View itemView, final OnHourClickedListener listener) {
            super(itemView);
            txtPillName = itemView.findViewById(R.id.txt_pill_name_hour);
            categoryColor = itemView.findViewById(R.id.color_substance);
            txtHourTake = itemView.findViewById(R.id.txt_hour_take);
            checkBox = itemView.findViewById(R.id.check_box);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        txtPillName.setEnabled(false);
                        txtHourTake.setEnabled(false);
                        categoryColor.setBackgroundResource(AppConstants.COLOR_GREY);
                        txtPillName.setPaintFlags(txtPillName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }else {
                        txtPillName.setEnabled(true);
                        txtHourTake.setEnabled(true);
                        categoryColor.setBackgroundResource(hourPillItem.getColor());
                        txtPillName.setPaintFlags(txtPillName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                }
            });
            itemView.setOnClickListener(v -> listener.onHourClicked(hourPillItem));
        }

        public void setHourPillItem(HourPill hourPillItem) {
            this.hourPillItem = hourPillItem;
        }

        public interface OnHourClickedListener {
            void onHourClicked(HourPill hourPillItem);
            void onCheckClicked(TextView view);
        }
    }
}
