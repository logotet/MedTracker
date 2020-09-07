package com.logotet.m.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.m.R;
import com.logotet.m.models.ActiveDate;
import com.logotet.m.models.HourPill;
import com.logotet.m.models.DateAgendaModel;

import java.util.ArrayList;
import java.util.List;

public class DateAgendaAdapter extends RecyclerView.Adapter<DateAgendaAdapter.SimpleDateHolder> {

    List<DateAgendaModel> dates;
    List<HourPill> pillstForDay = new ArrayList<>();



    public DateAgendaAdapter(List<DateAgendaModel> dates) {
        this.dates = dates;
    }

    @NonNull
    @Override
    public SimpleDateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_view, parent, false);
        return new SimpleDateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleDateHolder holder, int position) {
        DateAgendaModel model;
        model = dates.get(position);
        holder.txtDate.setText(String.valueOf(model.getDate()));
        holder.txtWeekDay.setText(model.getWeekDay());



//        hourPillList = model.initDummyHours();

        HourAdapter hourAdapter = new HourAdapter(model.getHourPills(), new HourAdapter.HourHolder.OnHourClickedListener() {
            @Override
            public void onHourClicked(HourPill hourItem) {
                Toast.makeText(holder.recyclerViewHours.getContext(), hourItem.getName(), Toast.LENGTH_LONG).show();
            }
        });
        holder.recyclerViewHours.setAdapter(hourAdapter);
        holder.recyclerViewHours.setLayoutManager(new LinearLayoutManager(holder.recyclerViewHours.getContext()));
    }


    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class SimpleDateHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerViewHours;
        TextView txtDate, txtWeekDay;

        public SimpleDateHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtSimpleDate);
            txtWeekDay = itemView.findViewById(R.id.txtSimpleWeekDay);
            recyclerViewHours = itemView.findViewById(R.id.rec_view_hours);
        }
    }
}
