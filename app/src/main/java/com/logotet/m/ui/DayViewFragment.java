package com.logotet.m.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.logotet.m.R;
import com.logotet.m.adapters.DateAgendaAdapter;
import com.logotet.m.data.DatabaseClient;
import com.logotet.m.databinding.FragmentDayViewBinding;
import com.logotet.m.data.models.ActiveDate;
import com.logotet.m.data.models.HourPill;
import com.logotet.m.data.models.PillEvent;
import com.logotet.m.data.models.DateAgendaModel;
import com.logotet.m.data.models.Substance;
import com.logotet.m.utils.DateUtils;
import com.logotet.m.utils.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DayViewFragment extends Fragment {

    FragmentDayViewBinding binding;
    PillEvent eventDay;
    List<DateAgendaModel> datesList = new ArrayList<>();
    //    List<ActiveDate> activeDates = new ArrayList<>();
    DatabaseClient client = DatabaseClient.getInstance(getContext());
    private List<ActiveDate> activeDates;

    public static DayViewFragment newInstance(PillEvent pillEvent) {
        DayViewFragment fragment = new DayViewFragment();
        Bundle args = new Bundle();
        args.putParcelable("event_day", pillEvent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventDay = getArguments().getParcelable("event_day");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        List<String> yearList = DateUtils.getYear(2020);
        activeDates = client.getAllDates();
        for (int i = 0; i < yearList.size(); i++) {
            String date = yearList.get(i);
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
            int dayOfMonth = localDate.getDayOfMonth();
            int montValue = localDate.getMonthValue();
            String dayOfWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            DateAgendaModel dateAgendaModel = new DateAgendaModel();
            dateAgendaModel.setDate(dayOfMonth);
            dateAgendaModel.setYear(2020);
            dateAgendaModel.setMonth(montValue);
            dateAgendaModel.setWeekDay(dayOfWeek);

            String s = dateAgendaModel.toString();
//            activeDates = client.getDatesByValue(s);
            List<HourPill> pillsForDay = new ArrayList<>();
            for (int j = 0; j < activeDates.size(); j++) {
                ActiveDate activeDate = activeDates.get(j);
                if (activeDate.getDate().equals(s)) {
                    HourPill hourPill = new HourPill(activeDate.getSubstanceName());
                    Substance substance = client.getSubstanceByName(activeDate.getSubstanceName());
                    int color = Utils.getColor(substance.getCategory());
                    hourPill.setColor(color);
                    pillsForDay.add(hourPill);
                }
            }
            dateAgendaModel.setHourPills(pillsForDay);
            datesList.add(dateAgendaModel);
        }


        DateAgendaAdapter dateAgendaAdapter = new DateAgendaAdapter(datesList);
        binding.recViewDates.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recViewDates.setAdapter(dateAgendaAdapter);
        LocalDate today = LocalDate.now();
        int position = today.getDayOfYear();
        binding.recViewDates.scrollToPosition(position - 1);



        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment();
            }
        });
    }

    private void showDialogFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AddPillFragment fragment = new AddPillFragment();
        fragment.setTargetFragment(DayViewFragment.this, 300);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
//        fragment.show(fm, "fragment_add_pill");
    }

    public void updateData(Substance substance) {
        binding.txtData.setText(substance.getName());
    }
}
