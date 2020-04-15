package com.jhprog.dabank.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jhprog.dabank.data.Account;
import com.jhprog.dabank.data.Bank;
import com.jhprog.dabank.data.Customer;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Account>> accounts = new MutableLiveData<>();
    private MutableLiveData<Bank> bank = new MutableLiveData<>();
    private MutableLiveData<Customer> customer = new MutableLiveData<>();

    public LiveData<ArrayList<Account>> getAccounts() {
        if (accounts.getValue() == null || accounts.getValue().size() == 0) {
            loadAccounts();
        }
        return accounts;
    }

    private void loadAccounts() {
        // TODO get the accounts from DataManager where bank_id matches this.bank and cust_id matches this.customer
    }

    public LiveData<Bank> getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        if (bank != null) {
            this.bank.setValue(bank);
        }
    }

    public LiveData<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer.setValue(customer);
    }
}
