package com.jimmysun.ultrarecyclerview;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Indicator for UltraRecyclerView
 *
 * @author SunQiang
 * @since 2019-05-17
 */
public class UltraRecyclerViewIndicator extends LinearLayout {
    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 2;
    public static final int DEFAULT_MARGIN = 5;
    public static final int SELECTED_COLOR = 0xccffffff;
    public static final int DEFAULT_COLOR = 0x4d868e9e;

    private View[] mViews;
    private int mSelectedWidth, mDefaultWidth, mHeight, mMargin;
    private int mSelectedColor, mDefaultColor;

    private OnAttachStateChangeListener mOnAttachStateChangeListener;
    private RecyclerView.OnScrollListener mOnScrollListener;

    public UltraRecyclerViewIndicator(Context context) {
        super(context);
        init(context);
    }

    public UltraRecyclerViewIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UltraRecyclerViewIndicator(Context context, @Nullable AttributeSet attrs,
                                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        mSelectedWidth = Utils.dip2px(context, DEFAULT_WIDTH);
        mDefaultWidth = Utils.dip2px(context, DEFAULT_WIDTH);
        mHeight = Utils.dip2px(context, DEFAULT_HEIGHT);
        mMargin = Utils.dip2px(context, DEFAULT_MARGIN);
        mSelectedColor = SELECTED_COLOR;
        mDefaultColor = DEFAULT_COLOR;
        mOnAttachStateChangeListener = new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                onPositionChanged(0);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        };
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && recyclerView instanceof UltraRecyclerView) {
                    onPositionChanged(((UltraRecyclerView) recyclerView).getCurrentPosition());
                }
            }
        };
    }

    public void setSelectedWidth(@Px int selectedWidth) {
        if (selectedWidth >= 0) {
            mSelectedWidth = selectedWidth;
        }
    }

    public void setDefaultWidth(@Px int defaultWidth) {
        if (defaultWidth >= 0) {
            mDefaultWidth = defaultWidth;
        }
    }

    public void setHeight(@Px int height) {
        if (height >= 0) {
            mHeight = height;
        }
    }

    public void setMargin(@Px int margin) {
        if (margin >= 0) {
            mMargin = margin;
        }
    }

    public void setSelectedColor(@ColorInt int selectedColor) {
        mSelectedColor = selectedColor;
    }

    public void setDefaultColor(@ColorInt int defaultColor) {
        mDefaultColor = defaultColor;
    }

    public void attachToRecyclerView(UltraRecyclerView recyclerView) {
        if (recyclerView == null || recyclerView.getRealCount() <= 0) {
            return;
        }
        initViews(recyclerView.getRealCount());
        addListeners(recyclerView);
    }

    private void initViews(int count) {
        removeAllViews();
        if (count > 1) {
            mViews = new View[count];
            for (int i = 0; i < count; i++) {
                mViews[i] = new View(getContext());
                LayoutParams layoutParams;
                if (i == 0) {
                    layoutParams = new LayoutParams(mSelectedWidth, mHeight);
                    mViews[i].setBackgroundColor(mSelectedColor);
                } else {
                    layoutParams = new LayoutParams(mDefaultWidth, mHeight);
                    mViews[i].setBackgroundColor(mDefaultColor);
                }
                if (i < count - 1) {
                    layoutParams.rightMargin = mMargin;
                }
                addView(mViews[i], layoutParams);
            }
        }
    }

    private void addListeners(UltraRecyclerView recyclerView) {
        recyclerView.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
        recyclerView.addOnScrollListener(mOnScrollListener);
    }

    public void detachFromRecyclerView(RecyclerView recyclerView) {
        if (recyclerView != null) {
            removeListeners(recyclerView);
        }
    }

    private void removeListeners(RecyclerView recyclerView) {
        recyclerView.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);
        recyclerView.removeOnScrollListener(mOnScrollListener);
    }

    private void onPositionChanged(int position) {
        if (mViews != null && position >= 0) {
            for (int i = 0; i < mViews.length; i++) {
                ViewGroup.LayoutParams layoutParams = mViews[i].getLayoutParams();
                if (i == (position % mViews.length)) {
                    layoutParams.width = mSelectedWidth;
                    mViews[i].setBackgroundColor(mSelectedColor);
                } else {
                    layoutParams.width = mDefaultWidth;
                    mViews[i].setBackgroundColor(mDefaultColor);
                }
            }
            requestLayout();
        }
    }
}
