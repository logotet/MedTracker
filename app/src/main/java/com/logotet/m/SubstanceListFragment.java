package com.logotet.m;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.logotet.m.adapters.SubstanceAdapter;
import com.logotet.m.adapters.WrapContentLinearLayoutManager;
import com.logotet.m.data.DatabaseClient;
import com.logotet.m.databinding.FragmentSubstanceListBinding;
import com.logotet.m.models.ActiveDate;
import com.logotet.m.models.Substance;
import com.logotet.m.models.SubstanceAndDates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SubstanceListFragment extends Fragment implements AddPillFragment.OnPillCreatedListener {

    FragmentSubstanceListBinding binding;
    Substance substance;
    private List<Substance> substanceList = new ArrayList<>();
    DatabaseClient client = DatabaseClient.getInstance(getContext());



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_substance_list, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e("SubstanceListFragment", "ZANZIBAR");
        checkIfListEmpty(client);


//        DUMMMY METHOD
//       List<SubstanceAndDates> substanceAndDates = client.getAllSubstanceAndDates();
//       substanceAndDates.size();
//       SubstanceAndDates sad = substanceAndDates.get(0);
//       Substance substance = sad.getSubstance();
//       String name = substance.getName();
//       String date = sad.getDates().get(0).toString();
//       Toast.makeText(getContext(), name + " " , Toast.LENGTH_LONG).show();

//        DUMMY METHOD 2
//        String s = "20200523";
//      List<ActiveDate>  activeDates = client.getDatesByValue(s);
//               Toast.makeText(getContext(), activeDates.size() + " " , Toast.LENGTH_LONG).show();




        SubstanceAdapter substanceAdapter = new SubstanceAdapter(substanceList);
        binding.recViewSubst.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recViewSubst.setAdapter(substanceAdapter);

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment();
            }
        });
    }

    private void checkIfListEmpty(DatabaseClient client) {
        if(client.getAllSubstances().size() > 0) {
            substanceList = client.getAllSubstances();
            binding.recViewSubst.setVisibility(View.VISIBLE);
            binding.txtAddPills.setVisibility(View.GONE);
        }else {
            binding.recViewSubst.setVisibility(View.GONE);
            binding.txtAddPills.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onPillCreated(Substance substance) {
        substanceList.add(0, substance);
//        checkIfListEmpty(client);
//TODO: Put the visibillty stuff in an if statement
        binding.recViewSubst.setVisibility(View.VISIBLE);
        binding.txtAddPills.setVisibility(View.GONE);
        binding.recViewSubst.getAdapter().notifyItemInserted(0);
        binding.recViewSubst.scrollToPosition(0);
    }

    private void showDialogFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AddPillFragment fragment = new AddPillFragment();
        fragment.setTargetFragment(SubstanceListFragment.this, 300);
        fragment.show(fm, "fragment_add_pill");
    }
}
