package com.jhprog.dabank.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.Bank;
import com.jhprog.dabank.data.Customer;
import com.jhprog.dabank.data.DataManager;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private ArrayList<Account> accounts = new ArrayList<>();
    private Bank bank = null;
    private Customer customer = null;

    private void loadAccounts() {
        // TODO get the accounts from DataManager where bank_id matches this.bank and cust_id matches this.customer
        DataManager dataManager = DataManager.getInstance();
    }

    public ArrayList<Account> getAccounts() {
        if (accounts.size() == 0) {
            loadAccounts();
        }
        return accounts;
    }

    public Bank getBank() {
        return this.bank;
    }

    public void setBank(@NonNull Bank bank) {
        this.bank = bank;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(@NonNull Customer customer) {
        this.customer = customer;
    }
}
