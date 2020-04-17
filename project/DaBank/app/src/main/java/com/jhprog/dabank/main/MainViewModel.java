package com.jhprog.dabank.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    private MutableLiveData<Account> clickedAccount = new MutableLiveData<>();

    private void loadAccounts() {
        DataManager dataManager = DataManager.getInstance();
        if (bank != null && customer != null) {
            accounts.addAll(dataManager.getCustomerAccounts(bank.getBank_id(), customer.getCust_id()));
        }
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

    public LiveData<Account> getClickedAccount() {
        return clickedAccount;
    }

    public void setClickedAccount(@NonNull Account clickedAccount) {
        this.clickedAccount.setValue(clickedAccount);
    }
}
