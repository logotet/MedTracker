package com.logotet.m.data.models;

import androidx.lifecycle.Observer;

import com.logotet.m.utils.DateUtils;
import com.logotet.m.utils.Utils;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataModelBuilder {
    private List<String> yearList = DateUtils.getYear(2021);
//    private List<ActiveDate> activeDates;
//
//    public void createSubstance() {
//        substance.setName(binding.edtNameInner.getText().toString());
//        substance.setCategory(radioButton.getText().toString());
//        substance.setDescription(binding.edtDescriptionInner.getText().toString());
//        substance.setIntakeForm(binding.spinnerDosageType.getSelectedItem().toString());
//        substance.setIntakeDays(binding.spinnerIntakePeriods.getSelectedItemPosition() + 1);
//        substance.setIntakeDaily(binding.spinnerTimesPerDay.getSelectedItemPosition() + 1);
////       TODO set error message for not picking a date and a dosage
////        substance.setStartDate(startDate.toString());
////        substance.setEndDate(endDate.toString());
////        substance.setDosagePerTake(Integer.parseInt(binding.edtDosage.getText().toString()));
//    }

    public List<DateAgendaModel> setDateAgenda(List<ActiveDate> activeDates) {
        List<DateAgendaModel> datesList = new ArrayList<>();
        for (int i = 0; i < yearList.size(); i++) {
            String date = yearList.get(i);
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
            int dayOfMonth = localDate.getDayOfMonth();
            int monthValue = localDate.getMonthValue();
            String dayOfWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            DateAgendaModel dateAgendaModel = new DateAgendaModel();
            dateAgendaModel.setDate(dayOfMonth);
            dateAgendaModel.setYear(Year.now().getValue());
            dateAgendaModel.setMonth(monthValue);
            dateAgendaModel.setWeekDay(dayOfWeek);

            String dateAgenda = dateAgendaModel.toString();
            List<HourPill> pillsForDay = new ArrayList<>();
            ActiveDate activeDate;
            for (int j = 0; j < activeDates.size(); j++) {
                activeDate = activeDates.get(j);
                if (activeDate.getDate().equals(dateAgenda)) {
                    List<String> hours = activeDate.getHours();
                    for (String h :
                            hours) {
                        HourPill hourPill = new HourPill(activeDate.getSubstanceName());
                        hourPill.setColor(activeDate.getColor());
                        hourPill.setTime(h);
                        hourPill.setColor(activeDate.getColor());
                        pillsForDay.add(hourPill);
                    }

                }
            }
            dateAgendaModel.setHourPills(pillsForDay);
            datesList.add(dateAgendaModel);
        }
        return datesList;
    }
}
