/*
 * Author: Jani Olavi Heinikoski
 * Date: 07.04.2020
 * Version: alpha
 * Sources:
 * -
 * */
package com.jhprog.dabank.utility;

import com.jhprog.dabank.R;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public final class AnimationProvider {

    private static Animation onClickAnimation =
            AnimationUtils.loadAnimation(DaBank.getAppContext(), R.anim.scale_down_animation);

    // This class should not be initialized
    private AnimationProvider() {
    }

    public static Animation getOnClickAnimation() {
        onClickAnimation.setDuration(100);
        onClickAnimation.setRepeatCount(1);
        onClickAnimation.setRepeatMode(Animation.REVERSE);
        return onClickAnimation;
    }

}
