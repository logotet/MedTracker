package com.logotet.m.ui;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.logotet.m.R;
import com.logotet.m.data.DatabaseClient;
import com.logotet.m.databinding.FragmentAddPillBinding;
import com.logotet.m.data.entities.ActiveDate;
import com.logotet.m.data.entities.Substance;
import com.logotet.m.utils.AppConstants;
import com.logotet.m.utils.DateUtils;
import com.logotet.m.utils.Utils;
import com.logotet.m.utils.ViewManager;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;


public class AddPillFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final String ARG_PARAM = "name";
    private FragmentAddPillBinding binding;
    private Substance substance = new Substance();
    private DatabaseClient databaseClient = DatabaseClient.getInstance(getContext());
    private String name = "";
    private int dosagePerTake = 1;
    private ActiveDate activeDate;
    private List<ActiveDate> activeDateList = new ArrayList<>();
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isStartDateButton;
    private boolean isEndDateButton;
    private ScreenNavigator screenNavigator;
    private ViewManager viewManager;
    private Context context;
    private List<String> hours = new ArrayList<>();
    private String substanceName;

    private RadioButton radioButton;
    private TextView pressedTimeView;
    private TextView[] hourViews;



    public static AddPillFragment newInstance(String name) {
        AddPillFragment fragment = new AddPillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, name);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            substanceName = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_pill, container, false);
        binding = DataBindingUtil.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        screenNavigator = new ScreenNavigator();
        viewManager = new ViewManager();
        context = getContext();

        binding.radioGroupCategories.check(binding.radioMedication.getId());
        radioButton = binding.radioMedication;
        binding.radioGroupCategories.setOnCheckedChangeListener((group, checkedId) -> radioButton = view.findViewById(checkedId));

        viewManager.setSpinner(context, R.array.times_to_take, binding.spinnerIntakePeriods, AppConstants.PROMPT_INTAKE);
        viewManager.setSpinner(context, R.array.times_per_day, binding.spinnerTimesPerDay, AppConstants.PROMPT_INTAKE_DAILY);
        hourViews = new TextView[]{
                binding.txtTime1, binding.txtTime2, binding.txtTime3,binding.txtTime4,binding.txtTime5,binding.txtTime6,
        };
        viewManager.setSpinner(context, R.array.dosage_type, binding.spinnerDosageType, AppConstants.PROMPT_FORM);

        binding.spinnerTimesPerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setTimersVisibility(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        setUpDailyIntakeHours(binding.txtTime1);
        setUpDailyIntakeHours(binding.txtTime2);
        setUpDailyIntakeHours(binding.txtTime3);
        setUpDailyIntakeHours(binding.txtTime4);
        setUpDailyIntakeHours(binding.txtTime5);
        setUpDailyIntakeHours(binding.txtTime6);

        binding.txtStartDate.setText(DateUtils.getCurrentDay());
        binding.txtEndDate.setText(DateUtils.getCurrentDay());
        binding.txtStartDate.setOnClickListener(view1 -> {
            screenNavigator.openDateDialogFragment(
                    getActivity().getSupportFragmentManager(),
                    AddPillFragment.this
            );
            isStartDateButton = true;
            isEndDateButton = false;
        });

        binding.txtEndDate.setOnClickListener(view2 -> {
            screenNavigator.openDateDialogFragment(
                    getActivity().getSupportFragmentManager(),
                    AddPillFragment.this
            );
            isStartDateButton = false;
            isEndDateButton = true;
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.ok_cancel_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ok:
                if (isNameFilled()) {
                    setSubstance();
                    setActiveDates();
//                    databaseClient.insertActiveDate(activeDate);
                    databaseClient.insertSubstance(substance);
                    databaseClient.insertActiveDates(activeDateList.stream().toArray(ActiveDate[]::new));
                    OnPillCreatedListener listener = (OnPillCreatedListener) getTargetFragment();
                    listener.onPillCreated(substance);
                    getParentFragmentManager().popBackStack();
                }
                break;
            case R.id.menu_cancel:
                getParentFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNameFilled() {
        if (viewManager.isViewEmpty(binding.edtNameInner)) {
            binding.edtNameInner.setError("Add name");
            return false;
        }
        return true;
    }

    private void setSubstance() {
        substance.setName(binding.edtNameInner.getText().toString());
        substance.setCategory(radioButton.getText().toString());
        substance.setDescription(binding.edtDescriptionInner.getText().toString());
        substance.setIntakeForm(binding.spinnerDosageType.getSelectedItem().toString());
        substance.setIntakeDays(binding.spinnerIntakePeriods.getSelectedItemPosition() + 1);
        substance.setIntakeDaily(binding.spinnerTimesPerDay.getSelectedItemPosition() + 1);
        if (viewManager.isViewEmpty(binding.edtDosage)) {
            substance.setDosagePerTake(dosagePerTake);
        } else {
            substance.setDosagePerTake(Integer.parseInt(binding.edtDosage.getText().toString()));
        }
        substance.setStartDate(binding.txtStartDate.getText().toString());
        substance.setEndDate(binding.txtEndDate.getText().toString());
    }

    private void setActiveDates() {
        if (startDate == null){
            startDate = DateUtils.dateOfString(DateUtils.getCurrentDay());
        }
        if (endDate == null){
            endDate = DateUtils.dateOfString(DateUtils.getCurrentDay());
        }
        List<String> dates = DateUtils.getDatesBetweenStartAndEnd(startDate, endDate);

        hours = viewManager.getListOfHours(hourViews);
        for (String dateValue : dates) {
            activeDate = new ActiveDate();
            activeDate.setSubstanceName(substance.getName());
            int color = Utils.getColor(substance.getCategory());
            activeDate.setColor(color);
            activeDate.setDate(dateValue);
            for (String hour :
                    hours) {
                activeDate.setHour(hour);
                activeDateList.add(activeDate);
//                databaseClient.insertActiveDate(activeDate);
            }
            }
    }

    private void setUpDailyIntakeHours(final TextView view) {
        view.setOnClickListener(timeView -> {
            pressedTimeView = (TextView) timeView;
            screenNavigator.openTimeDialogFragment(
                    AddPillFragment.this.getActivity().getSupportFragmentManager(),
                    AddPillFragment.this, view.getId()
            );
        });

    }

    private void setTimersVisibility(int i) {
        switch (i) {
            case 5:
                binding.txtTime6.setVisibility(View.VISIBLE);
                binding.txtTime5.setVisibility(View.VISIBLE);
                binding.txtTime4.setVisibility(View.VISIBLE);
                binding.txtTime3.setVisibility(View.VISIBLE);
                binding.txtTime2.setVisibility(View.VISIBLE);
                break;
            case 4:
                binding.txtTime6.setVisibility(View.GONE);
                binding.txtTime5.setVisibility(View.VISIBLE);
                binding.txtTime4.setVisibility(View.VISIBLE);
                binding.txtTime3.setVisibility(View.VISIBLE);
                binding.txtTime2.setVisibility(View.VISIBLE);
                break;
            case 3:
                binding.txtTime6.setVisibility(View.GONE);
                binding.txtTime5.setVisibility(View.GONE);
                binding.txtTime4.setVisibility(View.VISIBLE);
                binding.txtTime3.setVisibility(View.VISIBLE);
                binding.txtTime2.setVisibility(View.VISIBLE);
                break;
            case 2:
                binding.txtTime6.setVisibility(View.GONE);
                binding.txtTime5.setVisibility(View.GONE);
                binding.txtTime4.setVisibility(View.GONE);
                binding.txtTime3.setVisibility(View.VISIBLE);
                binding.txtTime2.setVisibility(View.VISIBLE);
                break;
            case 1:
                binding.txtTime6.setVisibility(View.GONE);
                binding.txtTime5.setVisibility(View.GONE);
                binding.txtTime4.setVisibility(View.GONE);
                binding.txtTime3.setVisibility(View.GONE);
                binding.txtTime2.setVisibility(View.VISIBLE);
                break;
            default:
                binding.txtTime6.setVisibility(View.GONE);
                binding.txtTime5.setVisibility(View.GONE);
                binding.txtTime4.setVisibility(View.GONE);
                binding.txtTime3.setVisibility(View.GONE);
                binding.txtTime2.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String hm = DateUtils.timeToString(i) + ":" +DateUtils.timeToString(i1);
        hours.add(hm);
        pressedTimeView.setText(hm);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        LocalDate chosenDate = LocalDate.of(i, i1 + 1, i2);
        String dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(chosenDate);
        setDateViews(chosenDate, dateFormat);
    }

    private void setDateViews(LocalDate chosenDate, String dateFormat) {
        if (isStartDateButton) {
            startDate = chosenDate;
            binding.txtStartDate.setText(dateFormat);
        } else if (isEndDateButton) {
            endDate = chosenDate;
            binding.txtEndDate.setText(dateFormat);
        }
    }

    public interface OnPillCreatedListener {
        void onPillCreated(Substance substance);
    }

}
