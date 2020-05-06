package com.jhprog.dabank.main;

import android.content.Context;
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

import com.jhprog.dabank.IFragmentOwner;
import com.jhprog.dabank.data.BankCard;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.databinding.FragmentBankCardsBinding;
import com.jhprog.dabank.utility.AnimationProvider;

import java.util.ArrayList;
import java.util.Objects;

public final class BankCardsFragment extends Fragment implements AccountRecyclerAdapter.IBankCardsClickListener {

    private MainViewModel viewModel;
    private FragmentBankCardsBinding binding;
    private BankCardRecyclerAdapter recyclerAdapter;
    private ArrayList<BankCard> recyclerDataSet;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerDataSet = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerDataSet.clear();
        recyclerDataSet.addAll(DataManager.getInstance().getBankCardsByOwner(
                Objects.requireNonNull(viewModel.getClickedAccount().getValue()).getAcc_id())
        );
        recyclerAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBankCardsBinding.inflate(inflater, container, false);
        binding.fragmentBankCardsAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fragmentBankCardsAddCard.startAnimation(AnimationProvider.getOnClickAnimation());
                fragmentOwner.changeFragment(new AddBankCardFragment(), true);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get reference to the MainViewModel in order to handle clickedAccount
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        binding.fragmentBankCardsRecyclerview.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.fragmentBankCardsRecyclerview.setLayoutManager(layoutManager);
        // specify an adapter and add it to the recyclerview
        recyclerAdapter = new BankCardRecyclerAdapter(recyclerDataSet, this);
        binding.fragmentBankCardsRecyclerview.setAdapter(recyclerAdapter);
    }

    @Override
    public void onBankCardsClick(int position) {
        viewModel.setClickedBankCard(recyclerDataSet.get(position));
        fragmentOwner.changeFragment(new BankCardSettingsFragment(), true);
    }
}
