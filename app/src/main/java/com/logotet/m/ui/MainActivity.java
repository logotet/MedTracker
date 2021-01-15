package com.logotet.m.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.logotet.m.R;
import com.logotet.m.data.DatabaseClient;
import com.logotet.m.databinding.ActivityMainBinding;
import com.logotet.m.utils.Navigation;

public class MainActivity extends AppCompatActivity  {

    ActivityMainBinding binding;
    SubstanceListFragment substanceListFragment;
    DatabaseClient client;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        client = DatabaseClient.getInstance(this);
        Navigation.setUpBtmNavView(binding.bottomNav);
        setSupportActionBar(binding.toolbar);
        setUpBottomNavigation();
        openFragment(new SubstanceListFragment());
        setupNavigationDrawer();
        substanceListFragment = new SubstanceListFragment();

//        TODO: The current version of single FAB in the activity is not working properly due to inability to pass the data adn different ines sohuld be used
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment();
            }
        });

    }

    private void setupNavigationDrawer() {
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_month_view:
                        openFragment(new MonthViewFragment());
                        break;
                    case R.id.nav_day_view:
                        openFragment(new DayViewFragment());
                        break;
                    case R.id.nav_share:
                        Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_send:
                        Toast.makeText(getApplicationContext(), "Send", Toast.LENGTH_LONG).show();
                        break;
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setUpBottomNavigation() {
        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_calendar:
                        openFragment(new DayViewFragment());
                        break;
                    case R.id.navigation_pill:
                        openFragment(new SubstanceListFragment());
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ic_settings) {
            client.deleteAll();
            Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        {
            super.onBackPressed();
        }
    }

    private void showDialogFragment() {
        FragmentManager fm = getSupportFragmentManager();
        AddPillFragment fragment = new AddPillFragment();
//        fragment.setTargetFragment(this, 300);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
//        fragment.show(fm, "fragment_add_pill");
    }

//
//    private void showDialogFragment() {
//        FragmentManager fm = getSupportFragmentManager();
//        AddPillFragment fragment = new AddPillFragment();
////        fragment.show(fm, "fragment_add_pill");
//    }

//    @Override
//    public void onPillCreated(Substance substance) {
////        String data = substance.getName();
////        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
////        substanceListFragment.updateSubstance(substance);
//    }
}
