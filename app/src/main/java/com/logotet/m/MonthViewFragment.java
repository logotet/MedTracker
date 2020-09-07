package com.logotet.m;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.adapters.CalendarPageAdapter;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.utils.CalendarProperties;
import com.applandeo.materialcalendarview.utils.SelectedDay;
import com.logotet.m.data.DatabaseClient;
import com.logotet.m.databinding.FragmentMonthViewBinding;
import com.logotet.m.models.PillEvent;
import com.logotet.m.models.Substance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MonthViewFragment extends Fragment {

    FragmentMonthViewBinding binding;
    List<EventDay> eventDaysList = new ArrayList<>();
    PillEvent eventDay;
    OnCalendarEventCreatedListener onCalendarEventCreatedListener;
    DatabaseClient dbc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month_view, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbc = DatabaseClient.getInstance(getContext());

        binding.calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                if (eventDay != null) {
                    if (eventDay instanceof PillEvent) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        DayViewFragment fragment;
                        fragment = DayViewFragment.newInstance((PillEvent) eventDay);
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                }
            }
        });



    }



//    @Override
//    public void onPillCreated(Substance substance) {
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(substance.getStartDate());
//        String name = substance.getName();
//        List<Calendar> vs = new ArrayList<>();
//        vs.add(c);
//        CalendarProperties properties = new CalendarProperties(getContext());
//        properties.setSelectionColor(R.color.blue);
//        binding.calendarView.setHighlightedDays(vs);
//
//
//        eventDay = new PillEvent(c, R.drawable.ic_dot, name);
//        eventDaysList.add(eventDay);
//        binding.calendarView.setEvents(eventDaysList);
//        onCalendarEventCreatedListener.onCalendarEventCreated(substance);
//
//    }


    public interface OnCalendarEventCreatedListener {
        void onCalendarEventCreated(Substance substance);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onCalendarEventCreatedListener = (OnCalendarEventCreatedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onViewSelected");
        }
    }

    @Override
    public void onDetach() {
        onCalendarEventCreatedListener = null;
        super.onDetach();
    }
}
