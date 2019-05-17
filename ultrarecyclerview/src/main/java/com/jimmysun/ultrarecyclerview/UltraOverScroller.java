package com.jimmysun.ultrarecyclerview;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

/**
 * @author SunQiang
 */
public class UltraOverScroller extends OverScroller {
    private int mDuration = -1;

    public UltraOverScroller(Context context) {
        this(context, null);
    }

    public UltraOverScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        if (mDuration > 0) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        } else {
            super.startScroll(startX, startY, dx, dy, duration);
        }
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        if (mDuration > 0) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        } else {
            super.startScroll(startX, startY, dx, dy);
        }
    }

    /**
     * Sets the scroll speed.
     *
     * @param duration The default duration to scroll.
     */
    public void setScrollDuration(int duration) {
        mDuration = duration;
    }
}
