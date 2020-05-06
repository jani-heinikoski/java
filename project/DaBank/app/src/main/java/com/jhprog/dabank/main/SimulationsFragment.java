package com.jhprog.dabank.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jhprog.dabank.IFragmentOwner;
import com.jhprog.dabank.databinding.FragmentSimulationsBinding;
import com.jhprog.dabank.utility.AnimationProvider;

public final class SimulationsFragment extends Fragment {

    private FragmentSimulationsBinding binding;
    private IFragmentOwner fragmentOwner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentOwner) {
            fragmentOwner = (IFragmentOwner) context;
        } else {
            System.err.println("Host Activity must implement IFragmentOwner");
            System.exit(-1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSimulationsBinding.inflate(inflater, container, false);
        initButtons();
        return binding.getRoot();
    }

    private void initButtons() {

        binding.fragmentSimulationsButtonAtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentSimulationsButtonAtm.startAnimation(AnimationProvider.getOnClickAnimation());
                fragmentOwner.changeFragment(new AtmSimulationFragment(), true);
            }
        });

        binding.fragmentSimulationsButtonCardPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentSimulationsButtonCardPayments.startAnimation(AnimationProvider.getOnClickAnimation());
                // TODO: 06/05/2020 this
            }
        });

    }
}
