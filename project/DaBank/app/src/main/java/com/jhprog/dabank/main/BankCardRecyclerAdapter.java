package com.jhprog.dabank.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.BankCard;
import com.jhprog.dabank.data.DataManager;

import java.util.ArrayList;
import java.util.Locale;

public final class BankCardRecyclerAdapter extends RecyclerView.Adapter<BankCardRecyclerAdapter.CardViewHolder> {

    private ArrayList<BankCard> dataSet;

    public final class CardViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCardNumber, textViewCardBalance;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCardNumber = (TextView) itemView.findViewById(R.id.cardview_bank_card_number);
            textViewCardBalance = (TextView) itemView.findViewById(R.id.cardview_bank_card_balance);
        }

    }

    public BankCardRecyclerAdapter(@NonNull ArrayList<BankCard> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bank_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        if (position != RecyclerView.NO_POSITION) {
            DataManager dataManager = DataManager.getInstance();
            Account owner = dataManager.getAccountByID(dataSet.get(position).getOwnerAccountId());
            String balance;

            if (owner != null) {
                if (owner.getAcc_balance() > 0) {
                    balance = String.format(
                            Locale.getDefault(),
                            "+%.2f",
                            owner.getAcc_balance()
                    );
                } else {
                    balance = String.format(
                            Locale.getDefault(),
                            "-%.2f",
                            owner.getAcc_balance()
                    );
                }
            }

            holder.textViewCardNumber.setText(
                    dataSet.get(position).getCardNumber()
            );
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
