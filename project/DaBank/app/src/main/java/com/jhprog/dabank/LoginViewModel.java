/*
 * Author: Jani Olavi Heinikoski
 * Date: 03.04.2020
 * Version: alpha
 * Sources:
 * https://developer.android.com/topic/libraries/architecture/viewmodel
 */
package com.jhprog.dabank;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Integer> b_id = new MutableLiveData<>();

    public LiveData<Integer> getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id.setValue(b_id);
    }
}
