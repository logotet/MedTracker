package com.logotet.m.utils;

import androidx.fragment.app.FragmentManager;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class Navigation {

    public static void setUpBtmNavView(BottomNavigationViewEx bottomNav) {
        bottomNav.enableItemShiftingMode(false);
        bottomNav.enableShiftingMode(false);
        bottomNav.setTextVisibility(false);
        bottomNav.enableAnimation(false);
    }

//    private void showDialogFragment() {
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        AddPillFragment fragment = new AddPillFragment();
//        fragment.setTargetFragment(MonthViewFragment.this, 300);
//        fragment.show(fm, "fragment_add_pill");
//    }


}
