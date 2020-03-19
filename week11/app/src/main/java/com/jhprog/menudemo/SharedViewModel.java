/*
Author: Jani Heinikoski | 0541122
Date: 15.3.2020
Version: 1.1
 */
package com.jhprog.menudemo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Integer> fontSize = new MutableLiveData<>();
    private MutableLiveData<Integer> fontColor = new MutableLiveData<>();
    private MutableLiveData<Boolean> textVisible = new MutableLiveData<>();

    public void setTextVisible(boolean checked) {
        this.textVisible.setValue(checked);
    }

    public void setFontColor(int fontColor) {
        this.fontColor.setValue(fontColor);
    }

    public void setFontSize(int fontSize) {
        this.fontSize.setValue(fontSize);
    }

    public LiveData<Boolean> getTextVisible() {
        return textVisible;
    }

    public LiveData<Integer> getFontColor() {
        return fontColor;
    }

    public LiveData<Integer> getFontSize() {
        return fontSize;
    }

}
