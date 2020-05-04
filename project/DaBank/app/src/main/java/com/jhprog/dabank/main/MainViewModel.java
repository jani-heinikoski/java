/*
 * Author: Jani Olavi Heinikoski
 * Date (last update): 04.05.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.Bank;
import com.jhprog.dabank.data.Customer;
import com.jhprog.dabank.data.DataManager;
import com.jhprog.dabank.data.NormalTransaction;


import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private ArrayList<Account> accounts = new ArrayList<>();
    private Bank bank = null;
    private Customer customer = null;
    private MutableLiveData<Account> clickedAccount = new MutableLiveData<>();
    private MutableLiveData<NormalTransaction> clickedTransaction = new MutableLiveData<>();

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

    public LiveData<NormalTransaction> getClickedTransaction() {
        return clickedTransaction;
    }

    public void setClickedTransaction(@NonNull NormalTransaction clickedTransaction) {
        this.clickedTransaction.setValue(clickedTransaction);
    }
}
