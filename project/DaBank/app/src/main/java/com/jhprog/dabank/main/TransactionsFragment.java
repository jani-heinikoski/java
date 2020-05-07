package com.jhprog.dabank.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jhprog.dabank.ICardClickListener;
import com.jhprog.dabank.IFragmentOwner;
import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.data.NormalTransaction;
import com.jhprog.dabank.databinding.FragmentTransactionsBinding;

import java.util.ArrayList;
import java.util.Objects;

public final class TransactionsFragment extends Fragment implements ICardClickListener {

    private FragmentTransactionsBinding binding;
    private MainViewModel viewModel;
    private IFragmentOwner fragmentOwner;
    private TransactionRecyclerAdapter recyclerAdapter;
    private DataManager dataManager;
    private ArrayList<NormalTransaction> recyclerDataset;

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
        dataManager = DataManager.getInstance();
        recyclerDataset = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        binding.fragmentTransactionsTextviewAccountType.setText(viewModel.getClickedAccount().getValue().toString());
        // Set the fragment's subheader (account number)
        binding.fragmentTransactionsTextviewAccountNumber.setText((CharSequence) viewModel.getClickedAccount().getValue().getAcc_number());
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        binding.fragmentTransactionsRecyclerview.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.fragmentTransactionsRecyclerview.setLayoutManager(layoutManager);
        // specify an adapter and add it to the recyclerview
        recyclerAdapter = new TransactionRecyclerAdapter(
                recyclerDataset,
                this,
                getResources().getColor(android.R.color.holo_red_dark, null),
                getResources().getColor(android.R.color.holo_green_dark, null),
                viewModel.getClickedAccount(),
                getViewLifecycleOwner()
        );
        // When user clicks an account, get all NormalTransactions connected to it and notify the adapter of the change
        viewModel.getClickedAccount().observe(getViewLifecycleOwner(), new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                recyclerDataset.clear();
                recyclerDataset.addAll(
                        dataManager.getNormalTransactionsByAccNumber(account.getAcc_number())
                );
                recyclerAdapter.notifyDataSetChanged();
            }
        });
        binding.fragmentTransactionsRecyclerview.setAdapter(recyclerAdapter);
    }

    @Override
    public void onCardClick(int position) {
        viewModel.setClickedTransaction(recyclerDataset.get(position));
        fragmentOwner.changeFragment(new TransactionDetailFragment(), true);
    }
}
