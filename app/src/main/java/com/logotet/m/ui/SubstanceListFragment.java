package com.logotet.m.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.logotet.m.R;
import com.logotet.m.adapters.SubstanceAdapter;
import com.logotet.m.data.DatabaseClient;
import com.logotet.m.databinding.FragmentSubstanceListBinding;
import com.logotet.m.data.entities.Substance;
import com.logotet.m.ui.viewmodels.SubstanceListFragmentViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SubstanceListFragment extends Fragment implements AddPillFragment.OnPillCreatedListener, SubstanceAdapter.SubstanceHolder.OnActionListener {

    private FragmentSubstanceListBinding binding;
    private List<Substance> substanceList = new ArrayList<>();
    @Inject
    DatabaseClient client;
    private ScreenNavigator screenNavigator;
    private SubstanceListFragmentViewModel viewModel;


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

        viewModel = new ViewModelProvider(this).get(SubstanceListFragmentViewModel.class);

        SubstanceAdapter substanceAdapter = new SubstanceAdapter(substanceList, this);
        binding.recViewSubst.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recViewSubst.setAdapter(substanceAdapter);

        viewModel.getAllSubstances().observe(getViewLifecycleOwner(), new Observer<List<Substance>>() {
            @Override
            public void onChanged(List<Substance> substances) {
                Collections.reverse(substances);
                substanceAdapter.updateData(substances);
                checkIfListEmpty(substances);
            }
        });

        screenNavigator = new ScreenNavigator();

        binding.fabAdd.setOnClickListener(v -> screenNavigator.openAddFragment(
                getActivity().getSupportFragmentManager(), SubstanceListFragment.this, null));
    }

    private void checkIfListEmpty(List<Substance> substances) {
        if (substances.size() > 0) {
            binding.recViewSubst.setVisibility(View.VISIBLE);
            binding.txtAddPills.setVisibility(View.GONE);
        } else {
            binding.recViewSubst.setVisibility(View.GONE);
            binding.txtAddPills.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onPillCreated(Substance substance) {
        substanceList.add(0, substance);
        binding.recViewSubst.setVisibility(View.VISIBLE);
        binding.txtAddPills.setVisibility(View.GONE);
        binding.recViewSubst.getAdapter().notifyItemInserted(0);
        binding.recViewSubst.scrollToPosition(0);

//        Dummy data
        Toast.makeText(getContext(), substance.toString(), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDeleteClicked(Substance substance) {
        viewModel.deleteSubstance(substance.getName());
    }

    @Override
    public void onOpenClicked(Substance substance) {
        screenNavigator.openDetailsFragment(
                getActivity().getSupportFragmentManager(), SubstanceListFragment.this, substance.getName());
    }
}
