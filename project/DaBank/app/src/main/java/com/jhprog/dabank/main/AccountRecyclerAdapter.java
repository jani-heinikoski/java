package com.jhprog.dabank.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jhprog.dabank.ICardClickListener;
import com.jhprog.dabank.R;
import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.CurrentAccount;
import com.jhprog.dabank.data.FixedTermAccount;
import com.jhprog.dabank.data.SavingsAccount;

import java.util.ArrayList;
import java.util.Locale;

public final class AccountRecyclerAdapter extends RecyclerView.Adapter<AccountRecyclerAdapter.CardViewHolder> {

    private ArrayList<Account> accounts;
    private ICardClickListener onCardClickListener;
    private ISettingsClickListener onSettingsClickListener;
    private IBankCardsClickListener onBankCardClickListener;

    // Provide a reference to the views for each data item
    public final class CardViewHolder extends RecyclerView.ViewHolder {

        TextView textViewType, textViewBalance, textViewAccountNumber, textViewCreditLimit, textViewWithdrawLimit, textViewDueDate;
        Button btnAccountSettings, btnAccountBankCards;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = (TextView) itemView.findViewById(R.id.cardview_account_type);
            textViewBalance = (TextView) itemView.findViewById(R.id.cardview_account_balance);
            textViewAccountNumber = (TextView) itemView.findViewById(R.id.cardview_account_number);
            textViewCreditLimit = (TextView) itemView.findViewById(R.id.cardview_account_credit_limit);
            textViewWithdrawLimit = (TextView) itemView.findViewById(R.id.cardview_account_withdraw_limit);
            textViewDueDate = (TextView) itemView.findViewById(R.id.cardview_account_due_date);
            btnAccountSettings = (Button) itemView.findViewById(R.id.cardview_account_settings);
            btnAccountBankCards = (Button) itemView.findViewById(R.id.cardview_account_bank_cards);

            btnAccountBankCards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        onBankCardClickListener.onBankCardsClick(pos);
                    }
                }
            });

            btnAccountSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        onSettingsClickListener.onSettingsClick(pos);
                    }
                }
            });

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

    public AccountRecyclerAdapter(@NonNull ArrayList<Account> accounts,
                                  @NonNull ICardClickListener onCardClickListener,
                                  @NonNull ISettingsClickListener onSettingsClickListener,
                                  @NonNull IBankCardsClickListener onBankCardClickListener) {
        this.accounts = accounts;
        this.onCardClickListener = onCardClickListener;
        this.onSettingsClickListener = onSettingsClickListener;
        this.onBankCardClickListener = onBankCardClickListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_account, parent, false);
        return new CardViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        if (position != RecyclerView.NO_POSITION) {

            Account acc = accounts.get(position);
            holder.textViewAccountNumber.setText(acc.getAcc_number());
            holder.textViewBalance.setText(
                    String.format(Locale.getDefault(),"Balance: %.2f", acc.getAcc_balance()));

            if (acc instanceof CurrentAccount) {
                holder.textViewType.setText(R.string.current_account_as_string);
                // If it has credit limit, show it, else don't.
                if (((CurrentAccount) acc).getAcc_creditlimit() > 0) {
                    holder.textViewCreditLimit.setVisibility(View.VISIBLE);
                    holder.textViewCreditLimit.setText(
                            String.format(Locale.getDefault(),"Credit limit: %.2f", ((CurrentAccount) acc).getAcc_creditlimit())
                    );
                } else {
                    holder.textViewCreditLimit.setVisibility(View.GONE);
                }

            } else if (acc instanceof SavingsAccount) {
                holder.textViewType.setText(R.string.savings_account_as_string);
                holder.textViewWithdrawLimit.setVisibility(View.VISIBLE);
                holder.textViewWithdrawLimit.setText(
                        String.format(Locale.getDefault(), "Withdraws left this year: %d", ((SavingsAccount) acc).getAcc_withdrawlimit())
                );
            } else if (acc instanceof FixedTermAccount) {
                holder.textViewType.setText(R.string.fixed_term_account_as_string);
                holder.textViewDueDate.setVisibility(View.VISIBLE);
                holder.textViewDueDate.setText(
                        String.format(Locale.getDefault(), "Due date: %s", ((FixedTermAccount) acc).getAcc_due_date())
                );
            }
        }
    }

    // Return the size of accounts (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (accounts != null) {
            return accounts.size();
        } else {
            return 0;
        }
    }

    public interface ISettingsClickListener {
        void onSettingsClick(int position);
    }

    public interface IBankCardsClickListener {
        void onBankCardsClick(int position);
    }

}
