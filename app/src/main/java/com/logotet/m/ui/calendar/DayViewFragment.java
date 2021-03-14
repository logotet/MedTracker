package com.logotet.m.ui.calendar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.logotet.m.R;
import com.logotet.m.adapters.DateAgendaAdapter;
import com.logotet.m.adapters.HourAdapter;
import com.logotet.m.data.DatabaseClient;
import com.logotet.m.data.entities.DataModelBuilder;
import com.logotet.m.adapters.models.HourPill;
import com.logotet.m.databinding.FragmentDayViewBinding;
import com.logotet.m.data.entities.ActiveDate;
import com.logotet.m.adapters.models.DateAgendaModel;
import com.logotet.m.data.entities.Substance;
import com.logotet.m.ui.AddPillFragment;
import com.logotet.m.ui.ScreenNavigator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DayViewFragment extends Fragment implements AddPillFragment.OnPillCreatedListener, HourAdapter.HourHolder.OnHourClickedListener {

    private FragmentDayViewBinding binding;
//    private PillEvent eventDay;
    private List<DateAgendaModel> datesList = new ArrayList<>();
    //    List<ActiveDate> activeDates = new ArrayList<>();
    private DatabaseClient databaseClient = DatabaseClient.getInstance(getContext());
    private List<ActiveDate> dates = new ArrayList<>();
    private ScreenNavigator screenNavigator;
    private DataModelBuilder modelBuilder;

//    public static DayViewFragment newInstance(PillEvent pillEvent) {
//        DayViewFragment fragment = new DayViewFragment();
//        Bundle args = new Bundle();
//        args.putParcelable("event_day", pillEvent);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            eventDay = getArguments().getParcelable("event_day");
//        }
//    }
//
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
        modelBuilder = new DataModelBuilder();
        screenNavigator = new ScreenNavigator();
//        activeDates = databaseClient.getAllDates();

        datesList = modelBuilder.setDateAgenda(dates);

        DateAgendaAdapter dateAgendaAdapter = new DateAgendaAdapter(datesList, this);
        binding.recViewDates.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recViewDates.setAdapter(dateAgendaAdapter);
        binding.recViewDates.addItemDecoration(new DividerItemDecoration(binding.recViewDates.getContext(),
                LinearLayoutManager.VERTICAL));

        databaseClient.getAllDates().observe(getViewLifecycleOwner(), activeDates ->
                dateAgendaAdapter.updateDate(modelBuilder.setDateAgenda(activeDates)));

        LocalDate today = LocalDate.now();
        int position = today.getDayOfYear();
        binding.recViewDates.scrollToPosition(position - 1);

        binding.fabAdd.setOnClickListener(v -> screenNavigator.openAddFragment(
                getActivity().getSupportFragmentManager(), DayViewFragment.this, null));
    }

    public void updateData(Substance substance) {
        binding.txtData.setText(substance.getName());
    }

    @Override
    public void onPillCreated(Substance substance) {

    }

    @Override
    public void onHourClicked(HourPill hourPillItem) {
        Toast.makeText(getContext(), hourPillItem.getName(), Toast.LENGTH_LONG).show();
        screenNavigator.openDetailsFragment(getActivity().getSupportFragmentManager(),
                null, hourPillItem.getName());
    }

    @Override
    public void onCheckClicked(TextView view) {
//        view.setEnabled(false);
//        view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }


}
