package com.jimmysun.ultrarecyclerview;

import android.content.Context;
import android.support.annotation.Px;
import android.support.v7.widget.LinearSmoothScroller;
import android.view.Gravity;
import android.view.View;

/**
 * LinearSmoothScroller which can snap to start, end, or center.
 *
 * @author SunQiang
 * @since 2019-05-14
 */
public class UltraLinearSmoothScroller extends LinearSmoothScroller {
    public static final int SNAP_TO_CENTER = 2;

    private int mGravity;
    private int mMargin;

    public UltraLinearSmoothScroller(Context context) {
        super(context);
    }

    /**
     * Sets the snap gravity.
     *
     * @param gravity The align gravity. Should be one of {@link Gravity#CENTER},
     *                {@link Gravity#START}, {@link Gravity#END}, or {@link Gravity#NO_GRAVITY}.
     */
    public void setGravity(int gravity) {
        mGravity = gravity;
    }

    /**
     * Sets the align margin.
     *
     * @param margin The align margin in px, only valid when the gravity is {@link Gravity#START}
     *               or {@link Gravity#END}.
     */
    public void setMargin(@Px int margin) {
        mMargin = margin;
    }

    @Override
    protected int getHorizontalSnapPreference() {
        switch (mGravity) {
            case Gravity.START:
                return SNAP_TO_START;
            case Gravity.END:
                return SNAP_TO_END;
            case Gravity.CENTER:
                return SNAP_TO_CENTER;
            default:
                return super.getHorizontalSnapPreference();
        }
    }

    @Override
    protected int getVerticalSnapPreference() {
        switch (mGravity) {
            case Gravity.START:
                return SNAP_TO_START;
            case Gravity.END:
                return SNAP_TO_END;
            case Gravity.CENTER:
                return SNAP_TO_CENTER;
            default:
                return super.getVerticalSnapPreference();
        }
    }

    @Override
    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd,
                                int snapPreference) {
        if (snapPreference == SNAP_TO_CENTER) {
            return ((boxStart + boxEnd) - (viewStart + viewEnd)) / 2;
        } else {
            return super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, snapPreference);
        }
    }

    @Override
    public int calculateDxToMakeVisible(View view, int snapPreference) {
        int dx = super.calculateDxToMakeVisible(view, snapPreference);
        switch (mGravity) {
            case Gravity.START:
                return dx + mMargin;
            case Gravity.END:
                return dx - mMargin;
            default:
                return dx;
        }
    }

    @Override
    public int calculateDyToMakeVisible(View view, int snapPreference) {
        int dy = super.calculateDyToMakeVisible(view, snapPreference);
        switch (mGravity) {
            case Gravity.START:
                return dy + mMargin;
            case Gravity.END:
                return dy - mMargin;
            default:
                return dy;
        }
    }
}
