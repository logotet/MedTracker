package com.logotet.m.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.logotet.m.R;
import com.logotet.m.adapters.SubstanceAdapter;
import com.logotet.m.data.entities.Substance;
import com.logotet.m.databinding.FragmentSubstanceCategoryBinding;
import com.logotet.m.ui.viewmodels.SubstanceCategoryFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class SubstanceCategoryFragment extends Fragment implements SubstanceAdapter.SubstanceHolder.OnActionListener {

    private FragmentSubstanceCategoryBinding binding;
    private SubstanceCategoryFragmentViewModel viewModel;
    private static final String ARG_PARAM = "category";
    private String substanceCategory;
    private List<Substance> substanceList = new ArrayList<>();


    public static SubstanceCategoryFragment newInstance(String category) {
        SubstanceCategoryFragment fragment = new SubstanceCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            substanceCategory = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_substance_category, container, false);
        binding = DataBindingUtil.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SubstanceCategoryFragmentViewModel.class);

        SubstanceAdapter adapter = new SubstanceAdapter(substanceList, this);

        viewModel.getSubstancesByCategory(substanceCategory).observe(getViewLifecycleOwner(),
                substances -> adapter.updateData(substances));
        binding.recViewSubst.setAdapter(adapter);
        binding.recViewSubst.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDeleteClicked(Substance substance) {

    }

    @Override
    public void onOpenClicked(Substance substance) {

    }
}