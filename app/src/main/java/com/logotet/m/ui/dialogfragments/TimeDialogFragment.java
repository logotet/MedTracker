package com.logotet.m.ui.dialogfragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.logotet.m.ui.AddPillFragment;

import java.util.Calendar;

public class TimeDialogFragment extends DialogFragment {

    private static String ARG_TEXT_VIEW;

    public static TimeDialogFragment newInstance(int viewId) {
        TimeDialogFragment fragment = new TimeDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TEXT_VIEW, viewId);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getTargetFragment(), hour, minute, DateFormat.is24HourFormat(getActivity()));
    }



}
