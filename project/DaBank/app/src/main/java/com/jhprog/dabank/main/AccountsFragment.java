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
import com.jhprog.dabank.databinding.FragmentAccountsBinding;

import java.util.Objects;

public final class AccountsFragment extends Fragment
        implements AccountRecyclerAdapter.OnCardClickListener, AccountRecyclerAdapter.OnSettingsClickListener {

    private MainViewModel viewModel;
    private FragmentAccountsBinding binding;
    private IFragmentOwner fragmentOwner;
    private AccountRecyclerAdapter accountRecyclerAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentOwner) {
            fragmentOwner = (IFragmentOwner) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get reference to the MainViewModel in order to handle clickedAccount
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        binding.fragmentAccountsRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.fragmentAccountsRecyclerView.setLayoutManager(layoutManager);
        // specify an adapter and add it to the recyclerview
        accountRecyclerAdapter = new AccountRecyclerAdapter(viewModel.getAccounts(), this, this);
        binding.fragmentAccountsRecyclerView.setAdapter(accountRecyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        accountRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCardClick(int position) {
        viewModel.setClickedAccount(viewModel.getAccounts().get(position));
        fragmentOwner.changeFragment(new TransactionsFragment(), true);
    }

    @Override
    public void onSettingsClick(int position) {
        viewModel.setClickedAccount(viewModel.getAccounts().get(position));
        fragmentOwner.changeFragment(new AccountSettingsFragment(), true);
    }
}

// TODO: 28.4.2020 IOnCardClick --> root of proj. 
