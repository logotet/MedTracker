package com.logotet.m.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.logotet.m.R;
import com.logotet.m.data.entities.Substance;
import com.logotet.m.databinding.FragmentSubstanceDetailsBinding;
import com.logotet.m.ui.viewmodels.SubstanceDetailsFragmentViewModel;

public class SubstanceDetailsFragment extends Fragment {

    private static final String ARG_PARAM = "name";
    private String substanceName;
    private FragmentSubstanceDetailsBinding binding;
    private SubstanceDetailsFragmentViewModel viewModel;

    public static SubstanceDetailsFragment newInstance(String name) {
        SubstanceDetailsFragment fragment = new SubstanceDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, name);
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_substance_details, container, false);
        binding = DataBindingUtil.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SubstanceDetailsFragmentViewModel.class);

        viewModel.getSubstance(substanceName).observe(getViewLifecycleOwner(), new Observer<Substance>() {
            @Override
            public void onChanged(Substance substance) {
                binding.txtInfo.setText(substance.toString());
            }
        });
    }
}
