package com.jhprog.dabank.main;

import androidx.fragment.app.Fragment;

public interface IFragmentOwner {
    void changeFragment(Fragment newFragment, boolean addToBackStack);
}
