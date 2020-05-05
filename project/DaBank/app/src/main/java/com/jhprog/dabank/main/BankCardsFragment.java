package com.jhprog.dabank.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jhprog.dabank.data.BankCard;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.databinding.FragmentBankCardsBinding;

import java.util.ArrayList;
import java.util.Objects;

public final class BankCardsFragment extends Fragment {

    private MainViewModel viewModel;
    private FragmentBankCardsBinding binding;
    private BankCardRecyclerAdapter recyclerAdapter;
    private ArrayList<BankCard> recyclerDataSet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerDataSet = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBankCardsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get reference to the MainViewModel in order to handle clickedAccount
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        recyclerDataSet.clear();
        recyclerDataSet.addAll(DataManager.getInstance().getBankCardsByOwner(
                Objects.requireNonNull(viewModel.getClickedAccount().getValue()).getAcc_id())
        );
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        binding.fragmentBankCardsRecyclerview.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.fragmentBankCardsRecyclerview.setLayoutManager(layoutManager);
        // specify an adapter and add it to the recyclerview
        recyclerAdapter = new BankCardRecyclerAdapter(recyclerDataSet);
        binding.fragmentBankCardsRecyclerview.setAdapter(recyclerAdapter);
    }
}
