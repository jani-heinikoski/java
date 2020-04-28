package com.jhprog.dabank.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.jhprog.dabank.ICardClickListener;
import com.jhprog.dabank.R;
import com.jhprog.dabank.data.NormalTransaction;

import java.util.ArrayList;
import java.util.Locale;

public final class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.CardViewHolder> {

    private ArrayList<NormalTransaction> transactions;
    private ICardClickListener onCardClickListener;
    private int redTextColor, greenTextColor;
    private String accNumber;

    // Provide a reference to the views for each data item
    public final class CardViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDate, textViewOtherAccountName, textViewAmount;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.cardview_transaction_date);
            textViewOtherAccountName = itemView.findViewById(R.id.cardview_transaction_other_account_name);
            textViewAmount = itemView.findViewById(R.id.cardview_transaction_amount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        onCardClickListener.onCardClick(pos);
                    }
                }
            });
        }
    }

    public TransactionRecyclerAdapter(@NonNull ArrayList<NormalTransaction> transactions,
                                      @NonNull ICardClickListener onCardClickListener,
                                      int redTextColor,
                                      int greenTextColor,
                                      final LiveData<String> accountNumber) {
        this.transactions = transactions;
        this.onCardClickListener = onCardClickListener;
        this.redTextColor = redTextColor;
        this.greenTextColor = greenTextColor;
        accountNumber.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (accNumber != null) {
                    accNumber = s;
                }
            }
        });
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_transaction, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        if (position != RecyclerView.NO_POSITION) {
            // todo remove test below
            holder.textViewDate.setText(
                    String.format("%s/%s", transactions.get(position).getTrans_date().split("/")[0],
                            transactions.get(position).getTrans_date().split("/")[1])
            );
            /*
            String otherAccName, amount;
            double transAmount;
            NormalTransaction tr = transactions.get(position);
            // Set date textview
            holder.textViewDate.setText(tr.getTrans_date());
            // Set other account's name (if name > 16 replace with 13 first chars and ...)
            otherAccName = tr.getTrans_to_acc_number();

            if (otherAccName.length() > 16) {
                otherAccName = otherAccName.substring(0, 12) + "...";
            }

            holder.textViewOtherAccountName.setText(otherAccName);
            // Set transaction amount, if gotten money set green text else red
            transAmount = tr.getTrans_amount();

            if (transAmount > 0) {
                holder.textViewAmount.setTextColor(greenTextColor);
                amount = String.format(
                        Locale.getDefault(),
                        "+%.2f", transAmount
                );
            } else {
                holder.textViewAmount.setTextColor(redTextColor);
                amount = String.format(
                        Locale.getDefault(),
                        "-%.2f", transAmount
                );
            }
            // TODO: 28.4.2020 this logic is faulty as fuck
            holder.textViewAmount.setText(amount);
             */
        }
    }

    @Override
    public int getItemCount() {
        if (transactions != null) {
            return transactions.size();
        } else {
            return 0;
        }
    }

}
