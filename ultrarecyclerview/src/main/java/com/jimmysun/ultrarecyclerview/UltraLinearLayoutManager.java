package com.jimmysun.ultrarecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author SunQiang
 */
public class UltraLinearLayoutManager extends LinearLayoutManager {
    private LinearSmoothScroller mLinearSmoothScroller;

    public UltraLinearLayoutManager(Context context) {
        super(context);
        init(context);
    }

    public UltraLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        init(context);
    }

    public UltraLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mLinearSmoothScroller = new LinearSmoothScroller(context);
    }

    public LinearSmoothScroller getLinearSmoothScroller() {
        return mLinearSmoothScroller;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                       int position) {
        mLinearSmoothScroller.setTargetPosition(position);
        this.startSmoothScroll(mLinearSmoothScroller);
    }
}
