/*
 * Author: Jani Olavi Heinikoski
 * Date: 03.04.2020
 * Version: alpha
 * Sources:
 * https://developer.android.com/topic/libraries/architecture/viewmodel
 * https://developer.android.com/topic/libraries/architecture/livedata
 */
package com.jhprog.dabank.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jhprog.dabank.data.Bank;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Bank> bank = new MutableLiveData<>();

    @NonNull
    public LiveData<Bank> getBank() {
        // This should NOT be null, since it is initialized in ChooseBankFragment
        if (bank == null) {
            bank = new MutableLiveData<>();
            bank.setValue(new Bank(1, "Error"));
        }
        return bank;
    }

    public void setBank(@NonNull Bank bank) {
        this.bank.setValue(bank);
    }

}
