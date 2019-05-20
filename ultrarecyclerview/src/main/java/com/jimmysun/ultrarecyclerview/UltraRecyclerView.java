package com.jimmysun.ultrarecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

import java.lang.reflect.Field;

/**
 * The Ultra RecyclerView
 *
 * @author SunQiang
 * @since 2019-04-16
 */
public class UltraRecyclerView extends RecyclerView {
    private static final int AUTO_SCROLL_DURATION = 5000;

    private UltraRecyclerViewAdapter mRecyclerViewAdapter;
    // pager snap
    private GravityPagerSnapHelper mPagerSnapHelper;
    private UltraLinearSmoothScroller mSmoothScroller;
    private int mAlignGravity = Gravity.NO_GRAVITY;
    private int mAlignMargin;
    // infinite loop
    private boolean mInfiniteLoop;
    // auto scroll
    private int mAutoScrollStartDelay;
    private int mAutoScrollDuration;
    private boolean mStopAutoScroll;
    private Runnable mScrollRunnable;
    private int mAutoScrollSpeed;
    private Interpolator mInterpolator;

    public UltraRecyclerView(Context context) {
        super(context);
        init(context);
        setOrientation(HORIZONTAL);
    }

    public UltraRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        init(context, attrs);
    }

    public UltraRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        init(context, attrs);
    }

    private void init(Context context) {
        mSmoothScroller = new UltraLinearSmoothScroller(context);
        mInterpolator = new Interpolator() {
            @Override
            public float getInterpolation(float t) {
                --t;
                return t * t * t * t * t + 1.0F;
            }
        };
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.UltraRecyclerView);
        setOrientation(typedArray.getInt(R.styleable.UltraRecyclerView_orientation, HORIZONTAL));
        int gravity = typedArray.getInt(R.styleable.UltraRecyclerView_alignGravity,
                Gravity.NO_GRAVITY);
        int margin = typedArray.getDimensionPixelSize(R.styleable.UltraRecyclerView_alignMargin, 0);
        setPagerSnap(gravity, margin);
        setInfiniteLoop(typedArray.getBoolean(R.styleable.UltraRecyclerView_infiniteLoop, false));
        typedArray.recycle();
    }

    /**
     * Sets the scroll orientation.
     *
     * @param orientation One of {@link #HORIZONTAL}, or {@link #VERTICAL}.
     */
    public void setOrientation(int orientation) {
        switch (orientation) {
            case HORIZONTAL:
                setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false));
                break;
            case VERTICAL:
                setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false));
                break;
            default:
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mRecyclerViewAdapter = buildUltraRecyclerViewAdapter(adapter);
        mRecyclerViewAdapter.setInfiniteLoop(mInfiniteLoop);
        super.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        mRecyclerViewAdapter = buildUltraRecyclerViewAdapter(adapter);
        super.swapAdapter(mRecyclerViewAdapter, removeAndRecycleExistingViews);
    }

    private UltraRecyclerViewAdapter buildUltraRecyclerViewAdapter(Adapter<?> adapter) {
        if (adapter instanceof UltraRecyclerViewAdapter) {
            return (UltraRecyclerViewAdapter) adapter;
        } else {
            return new UltraRecyclerViewAdapter<>(adapter);
        }
    }

    /**
     * Returns whether this view enables pager snap.
     *
     * @return True if this view enables pager snap, or false otherwise.
     */
    public boolean isPagerSnap() {
        return mAlignGravity == Gravity.START || mAlignGravity == Gravity.CENTER
                || mAlignGravity == Gravity.END;
    }

    /**
     * Enables or disables pager snap and sets align gravity.
     *
     * @param gravity The align gravity. Should be one of {@link Gravity#CENTER},
     *                {@link Gravity#START}, {@link Gravity#END}, or {@link Gravity#NO_GRAVITY}
     *                to disable pager snap.
     */
    public void setPagerSnap(int gravity) {
        setPagerSnap(gravity, 0);
    }

    /**
     * Enables or disables pager snap, and sets align gravity and margin.
     *
     * @param gravity     The align gravity. Should be one of {@link Gravity#CENTER},
     *                    {@link Gravity#START}, {@link Gravity#END}, or {@link Gravity#NO_GRAVITY}.
     * @param alignMargin The align margin in px, only valid when the gravity is
     *                    {@link Gravity#START} or {@link Gravity#END}.
     */
    public void setPagerSnap(int gravity, @Px int alignMargin) {
        mAlignGravity = gravity;
        mAlignMargin = alignMargin;
        if (mPagerSnapHelper != null) {
            mPagerSnapHelper.attachToRecyclerView(null);
        }
        if (isPagerSnap()) {
            mPagerSnapHelper = new GravityPagerSnapHelper(mAlignGravity);
            mPagerSnapHelper.setAlignMargin(mAlignMargin);
            mPagerSnapHelper.attachToRecyclerView(this);
            mSmoothScroller.setGravity(mAlignGravity);
            mSmoothScroller.setMargin(mAlignMargin);
        }
    }

    /**
     * Register a callback to be invoked when this view enables pager snap and changes position.
     *
     * @param onSnapListener The callback that will run.
     */
    public void setOnSnapListener(GravityPagerSnapHelper.OnSnapListener onSnapListener) {
        if (mPagerSnapHelper != null) {
            mPagerSnapHelper.setOnSnapListener(onSnapListener);
        }
    }

    /**
     * Returns whether the items are infinite loop.
     *
     * @return True if the items are infinite loop, or false otherwise.
     */
    public boolean isInfiniteLoop() {
        return mRecyclerViewAdapter != null && mRecyclerViewAdapter.isInfiniteLoop();
    }

    /**
     * Enables or disables the items infinite loop.
     *
     * @param infiniteLoop True to enable the items infinite loop, or false otherwise.
     */
    public void setInfiniteLoop(boolean infiniteLoop) {
        mInfiniteLoop = infiniteLoop;
        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.setInfiniteLoop(infiniteLoop);
        }
    }

    /**
     * Starts auto scrolling, and the duration is default.
     */
    public void startAutoScroll() {
        startAutoScroll(AUTO_SCROLL_DURATION);
    }

    /**
     * Starts auto scrolling, and the duration is specified.
     *
     * @param duration The duration in milliseconds.
     */
    public void startAutoScroll(int duration) {
        startAutoScrollDelayed(0, duration);
    }

    /**
     * Starts auto scrolling, the start delay-time is specified, and the duration is default.
     *
     * @param delay The start delay-time in milliseconds.
     */
    public void startAutoScrollDelayed(int delay) {
        startAutoScrollDelayed(delay, AUTO_SCROLL_DURATION);
    }

    /**
     * Starts auto scrolling, the duration and start delay-time are specified.
     *
     * @param delay    The duration in milliseconds.
     * @param duration The start delay-time in milliseconds.
     */
    public void startAutoScrollDelayed(int delay, int duration) {
        stopAutoScroll();
        if (mRecyclerViewAdapter != null && mRecyclerViewAdapter.getItemCount() > 1) {
            mAutoScrollStartDelay = delay < 0 ? 0 : delay;
            mAutoScrollDuration = duration < 0 ? AUTO_SCROLL_DURATION : duration;
            mScrollRunnable = new Runnable() {
                @Override
                public void run() {
                    if (!mStopAutoScroll && mRecyclerViewAdapter != null && getLayoutManager() != null) {
                        mSmoothScroller.setTargetPosition((getCurrentPosition() + 1) % mRecyclerViewAdapter.getItemCount());
                        if (mAutoScrollSpeed > 0) {
                            setScrollSpeed(mAutoScrollSpeed, mInterpolator);
                        } else {
                            setScrollSpeed(-1, mInterpolator);
                        }
                        getLayoutManager().startSmoothScroll(mSmoothScroller);
                        removeCallbacks(this);
                        postDelayed(this, mAutoScrollDuration);
                    }
                }
            };
            postDelayed(mScrollRunnable, mAutoScrollStartDelay + mAutoScrollDuration);
        }
    }

    /**
     * Restarts auto scrolling.
     */
    public void restartAutoScroll() {
        if (isAutoScroll()) {
            removeCallbacks(mScrollRunnable);
            postDelayed(mScrollRunnable, mAutoScrollStartDelay + mAutoScrollDuration);
        }
    }

    /**
     * Stops auto scrolling.
     */
    public void stopAutoScroll() {
        removeCallbacks(mScrollRunnable);
        mScrollRunnable = null;
    }

    /**
     * Sets auto scrolling speed.
     *
     * @param duration The duration in milliseconds when scrolling. Sets negative number to
     *                 restore default.
     */
    public void setAutoScrollSpeed(int duration) {
        mAutoScrollSpeed = duration;
    }

    /**
     * Sets auto scrolling speed and interpolator.
     *
     * @param duration     The duration in milliseconds when scrolling. Sets negative number to
     *                     restore default.
     * @param interpolator The animation interpolator.
     */
    public void setAutoScrollSpeed(int duration, Interpolator interpolator) {
        mAutoScrollSpeed = duration;
        mInterpolator = interpolator;
    }

    private void setScrollSpeed(int duration, Interpolator interpolator) {
        try {
            Field mViewFlinger = RecyclerView.class.getDeclaredField("mViewFlinger");
            mViewFlinger.setAccessible(true);
            Class viewFlingerClass = Class.forName("android.support.v7.widget" +
                    ".RecyclerView$ViewFlinger");
            Field mScroller = viewFlingerClass.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Field mInterpolator = viewFlingerClass.getDeclaredField("mInterpolator");
            mInterpolator.setAccessible(true);
            Field mDecelerateInterpolator = LinearSmoothScroller.class.getDeclaredField(
                    "mDecelerateInterpolator");
            mDecelerateInterpolator.setAccessible(true);
            mInterpolator.set(mViewFlinger.get(this),
                    mDecelerateInterpolator.get(mSmoothScroller));
            if (duration >= 0) {
                UltraOverScroller overScroller = new UltraOverScroller(getContext(), interpolator);
                overScroller.setScrollDuration(duration);
                mScroller.set(mViewFlinger.get(this), overScroller);
            } else {
                OverScroller overScroller = new OverScroller(getContext(), interpolator);
                mScroller.set(mViewFlinger.get(this), overScroller);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns whether this view is auto scrolled.
     *
     * @return True if this view is auto scrolled, or false otherwise.
     */
    public boolean isAutoScroll() {
        return mScrollRunnable != null;
    }

    /**
     * Returns current position.
     *
     * @return Current position.
     */
    public int getCurrentPosition() {
        int position = -1;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            if (mAlignGravity == Gravity.END) {
                position = layoutManager.findLastCompletelyVisibleItemPosition();
                if (position < 0) {
                    if (mAlignMargin > 0) {
                        position = layoutManager.findLastVisibleItemPosition() - 1;
                    } else {
                        position = layoutManager.findLastVisibleItemPosition();
                    }
                }
            } else {
                position = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (position < 0) {
                    if (mAlignMargin > 0) {
                        position = layoutManager.findFirstVisibleItemPosition() + 1;
                    } else {
                        position = layoutManager.findFirstVisibleItemPosition();
                    }
                }
            }
        }
        return position;
    }

    /**
     * Returns real count.
     *
     * @return Real count.
     */
    public int getRealCount() {
        if (mRecyclerViewAdapter != null) {
            return mRecyclerViewAdapter.getRealCount();
        } else {
            return 0;
        }
    }

    /**
     * Refresh adapter.
     */
    public void refresh() {
        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStopAutoScroll = true;
                if (isAutoScroll()) {
                    removeCallbacks(mScrollRunnable);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mStopAutoScroll = false;
                if (isAutoScroll()) {
                    removeCallbacks(mScrollRunnable);
                    postDelayed(mScrollRunnable, mAutoScrollDuration);
                }
                break;
            default:
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInfiniteLoop()) {
            int position =
                    Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % mRecyclerViewAdapter.getRealCount());
            scrollToPosition(position);
            if (isPagerSnap() && getLayoutManager() != null) {
                mSmoothScroller.setTargetPosition(position);
                setScrollSpeed(0, null);
                getLayoutManager().startSmoothScroll(mSmoothScroller);
            }
        }
        restartAutoScroll();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (isAutoScroll()) {
            removeCallbacks(mScrollRunnable);
        }
        super.onDetachedFromWindow();
    }
}
