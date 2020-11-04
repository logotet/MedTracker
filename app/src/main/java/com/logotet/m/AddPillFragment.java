package com.logotet.m;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.logotet.m.data.DatabaseClient;
import com.logotet.m.databinding.FragmentAddPillBinding;
import com.logotet.m.models.ActiveDate;
import com.logotet.m.models.Substance;
import com.logotet.m.timeutils.DateDialogFragment;
import com.logotet.m.timeutils.TimeDialogFragment;
import com.logotet.m.utils.AppConstants;
import com.logotet.m.utils.DateUtils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;


public class AddPillFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private FragmentAddPillBinding binding;
    private Substance substance = new Substance();
    private DatabaseClient databaseClient = DatabaseClient.getInstance(getContext());
    private String name;
    private ActiveDate activeDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean startDateButton;
    private boolean endDateButton;
    RadioButton radioButton;


//    @Override
//    public void onResume() {
//        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
//        super.onResume();
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_DayNight_DialogWhenLarge);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_pill, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                substance.setName(s.toString());
                name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        binding.radioMedication.setChecked(true);
        binding.radioGroupCategories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(checkedId);
            }
        });

        setSpinner(R.array.medication_category, binding.spinnerCategory, AppConstants.PROMPT_CATEGORY);
        setSpinner(R.array.times_to_take, binding.spinnerIntake, AppConstants.PROMPT_INTAKE);
        setSpinner(R.array.times_per_day, binding.spinnerTimesPerDay, AppConstants.PROMPT_INTAKE_DAILY);
        setSpinner(R.array.dosage_per_take, binding.spinnerDosage, AppConstants.PROMPT_INTAKE_DOSAGE);
        setSpinner(R.array.dosage_type, binding.spinnerDosageType, AppConstants.PROMPT_FORM);

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

        LocalDateTime localDateTime = LocalDateTime.now();
        String dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(localDateTime);

        binding.txtStartDate.setText(dateFormat);
        binding.txtEndDate.setText(dateFormat);

        binding.txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openDateDialogFragment();
                startDateButton = true;
                endDateButton = false;
            }
        });

        binding.txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openDateDialogFragment();
                startDateButton = false;
                endDateButton = true;
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSubstance();
                databaseClient.insertSubstance(substance);
                OnPillCreatedListener listener = (OnPillCreatedListener) getTargetFragment();
                listener.onPillCreated(substance);
                getDialog().dismiss();
            }
        });

    }

    private void setSubstance() {
        substance.setName(name);
        substance.setCategory(binding.spinnerCategory.getSelectedItem().toString());
        substance.setCategory(radioButton.getText().toString());
        substance.setDescription(binding.edtDescription.getText().toString());
        substance.setIntakeForm(binding.spinnerDosageType.getSelectedItem().toString());
        substance.setIntakeDays(binding.spinnerIntake.getSelectedItemPosition() + 1);
        substance.setIntakeDaily(binding.spinnerTimesPerDay.getSelectedItemPosition() + 1);
        substance.setDosagePerTake(Integer.parseInt(binding.spinnerDosage.getSelectedItem().toString()) + 1);

    }

    private void setUpDailyIntakeHours(final TextView view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openTimeDialogFragment();
            }
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

    private void setSpinner(int list, Spinner spinner, String prompt) {
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(getContext(), list,
                android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategory);
        spinner.setPrompt(prompt);

    }

    private void openTimeDialogFragment() {
        DialogFragment timePicker = new TimeDialogFragment();
        timePicker.setTargetFragment(AddPillFragment.this, 300);
        timePicker.show(getActivity().getSupportFragmentManager(), "time picker");
    }

    private void openDateDialogFragment() {
        DialogFragment datePicker = new DateDialogFragment();
        datePicker.setTargetFragment(AddPillFragment.this, 300);
        datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        binding.txtTime1.setText(i + ":" + i1);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        LocalDate chosenDate = LocalDate.of(i, i1 + 1, i2);
        String dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(chosenDate);
        if (startDateButton) {
            startDate = chosenDate;
//            binding.txtStartDate.setText(i2 + "/" + (i1 + 1) + "/" + i);
            binding.txtStartDate.setText(dateFormat);

        } else if (endDateButton) {
            endDate = chosenDate;
            binding.txtEndDate.setText(dateFormat);
        }

        if (substance.getName() != null) {
            if (startDate != null && endDate != null) {
                List<String> dates = DateUtils.getDatesBetweenStartAndEnd(startDate, endDate);
                for (String dateValue : dates) {
                    activeDate = new ActiveDate();
                    activeDate.setSubstanceName(substance.getName());
                    activeDate.setDate(dateValue);
                    databaseClient.insertActiveDate(activeDate);
                }
            }
        } else {
            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }


    public interface OnPillCreatedListener {
        void onPillCreated(Substance substance);
    }

}
