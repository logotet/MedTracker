package com.logotet.m.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.m.R;
import com.logotet.m.adapters.models.DateAgendaModel;
import com.logotet.m.adapters.models.HourPill;

import java.util.ArrayList;
import java.util.List;

public class DateAgendaAdapter extends RecyclerView.Adapter<DateAgendaAdapter.SimpleDateHolder> {

    private List<DateAgendaModel> dates;
    private List<HourPill> pillsForToday = new ArrayList<>();
    private HourAdapter.HourHolder.OnHourClickedListener onHourClickedListener;

    public DateAgendaAdapter(List<DateAgendaModel> dates, HourAdapter.HourHolder.OnHourClickedListener listener) {
        this.dates = dates;
        onHourClickedListener = listener;
    }

    public void updateDate(List<DateAgendaModel> dates){
        this.dates.clear();
        this.dates = dates;
        notifyDataSetChanged();
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
        pillsForToday = model.getHourPills();

        HourAdapter hourAdapter = new HourAdapter(pillsForToday, onHourClickedListener);
        holder.recyclerViewHours.setAdapter(hourAdapter);
        holder.recyclerViewHours.setLayoutManager(new LinearLayoutManager(holder.recyclerViewHours.getContext()));
        holder.recyclerViewHours.addItemDecoration(new DividerItemDecoration(holder.recyclerViewHours.getContext(),
                LinearLayoutManager.VERTICAL));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class SimpleDateHolder extends RecyclerView.ViewHolder {

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
