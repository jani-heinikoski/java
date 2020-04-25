package com.jhprog.dabank;

import androidx.fragment.app.Fragment;

public interface IFragmentOwner {
    void changeFragment(Fragment newFragment, boolean addToBackStack);
}
