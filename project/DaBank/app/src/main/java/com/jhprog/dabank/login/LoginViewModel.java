/*
 * Author: Jani Olavi Heinikoski
 * Date: 03.04.2020
 * Version: release
 * Sources:
 * https://developer.android.com/topic/libraries/architecture/viewmodel
 * https://developer.android.com/topic/libraries/architecture/livedata
 */
package com.jhprog.dabank.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.jhprog.dabank.data.Bank;

public class LoginViewModel extends ViewModel {

    private Bank bank = null;

    public Bank getBank() {
        return bank;
    }

    public void setBank(@NonNull Bank bank) {
        this.bank = bank;
    }

}
