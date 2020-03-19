/*
Author: Jani Heinikoski | 0541122
Date: 15.3.2020
Version: 1.2
 */
package com.jhprog.menudemo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Integer> fontSize = new MutableLiveData<>();
    private MutableLiveData<Integer> fontColor = new MutableLiveData<>();
    private MutableLiveData<Boolean> textVisible = new MutableLiveData<>();
    private MutableLiveData<Boolean> textAllCaps = new MutableLiveData<>();
    private MutableLiveData<Boolean> textEditable = new MutableLiveData<>();
    private MutableLiveData<String> editText = new MutableLiveData<>();

    private void setEditText(String s) {
        editText.setValue(s);
    }

    public void setTextEditable(boolean editable) {
        this.textEditable.setValue(editable);
    }

    public void setTextAllCaps(boolean allCaps) {
        this.textAllCaps.setValue(allCaps);
    }

    public void setTextVisible(boolean checked) {
        this.textVisible.setValue(checked);
    }

    public void setFontColor(int fontColor) {
        this.fontColor.setValue(fontColor);
    }

    public void setFontSize(int fontSize) {
        this.fontSize.setValue(fontSize);
    }

    public LiveData<String> getEditText() {
        return editText;
    }

    public LiveData<Boolean> getTextEditable() {
        return textEditable;
    }

    public LiveData<Boolean> getTextAllCaps() {
        return textAllCaps;
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
