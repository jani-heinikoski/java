package com.jhprog.dabank.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.jhprog.dabank.ICardClickListener;
import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.NormalTransaction;
import com.jhprog.dabank.data.Transaction;

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
                                      final LiveData<Account> clickedAccount,
                                      final LifecycleOwner lifecycleOwner) {
        this.transactions = transactions;
        this.onCardClickListener = onCardClickListener;
        this.redTextColor = redTextColor;
        this.greenTextColor = greenTextColor;
        clickedAccount.observe(lifecycleOwner, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                accNumber = account.getAcc_number();
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
            NormalTransaction transaction = transactions.get(position);
            if (transaction != null) {
                char firstChar;
                // Set date
                holder.textViewDate.setText(
                        transaction.getTrans_date().substring(5)
                );
                // Transaction is income if the accountNumber matches transaction.to_acc_number
                // except for withdraws.
                if (transaction.getTrans_type() == Transaction.TYPE_WITHDRAW) {
                    holder.textViewAmount.setTextColor(redTextColor);
                    firstChar = '-';
                } else {
                    if (transaction.getTrans_to_acc_number().equals(accNumber)) {
                        holder.textViewAmount.setTextColor(greenTextColor);
                        firstChar = '+';
                    } else {
                        holder.textViewAmount.setTextColor(redTextColor);
                        firstChar = '-';
                    }
                }

                String amountAsString = String.format(
                        Locale.getDefault(),
                        "%c%.2f€",
                        firstChar,
                        transaction.getTrans_amount()
                );

                holder.textViewAmount.setText(amountAsString);
                holder.textViewOtherAccountName.setText(transaction.getTrans_payee_name());
            }
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
