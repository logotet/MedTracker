package com.logotet.m.ui;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.logotet.m.R;
import com.logotet.m.ui.dialogfragments.DateDialogFragment;
import com.logotet.m.ui.dialogfragments.TimeDialogFragment;

public class ScreenNavigator {

    public void openAddFragment(FragmentManager fm, Fragment targetFragment, String param) {
        AddPillFragment fragment = AddPillFragment.newInstance(param);
        openFragment(fm, targetFragment, fragment);
    }

    public void openDetailsFragment(FragmentManager fm, Fragment targetFragment, String param) {
        SubstanceDetailsFragment fragment = SubstanceDetailsFragment.newInstance(param);
        openFragment(fm, null, fragment);
    }

    private void openFragment(FragmentManager fm, Fragment targetFragment, Fragment fragment) {
        fragment.setTargetFragment(targetFragment, 300);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openTimeDialogFragment(FragmentManager fm, Fragment targetFragment, int viewId) {
        DialogFragment timePicker = TimeDialogFragment.newInstance(viewId);
        timePicker.setTargetFragment(targetFragment, 300);
        timePicker.show(fm, "time picker");
    }

    public void openDateDialogFragment(FragmentManager fm, Fragment targetFragment) {
        DialogFragment datePicker = new DateDialogFragment();
        datePicker.setTargetFragment(targetFragment, 300);
        datePicker.show(fm, "date picker");
    }
}
