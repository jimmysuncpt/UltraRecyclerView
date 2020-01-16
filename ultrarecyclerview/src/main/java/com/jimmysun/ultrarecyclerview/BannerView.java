package com.jimmysun.ultrarecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.Px;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

/**
 * Banner View
 *
 * @author SunQiang
 * @since 2019-05-17
 */
public class BannerView extends FrameLayout {
    private static final int INDICATOR_BOTTOM_MARGIN = 8;

    private UltraRecyclerView mUltraRecyclerView;
    private UltraRecyclerViewIndicator mIndicator;

    public BannerView(Context context) {
        super(context);
        init(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        init(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        init(context, attrs);
    }

    private void init(Context context) {
        mUltraRecyclerView = new UltraRecyclerView(context);
        addView(mUltraRecyclerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mIndicator = new UltraRecyclerViewIndicator(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        layoutParams.bottomMargin = Utils.dip2px(context, INDICATOR_BOTTOM_MARGIN);
        addView(mIndicator, layoutParams);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        int gravity = typedArray.getInt(R.styleable.BannerView_alignGravity, Gravity.NO_GRAVITY);
        int margin = typedArray.getDimensionPixelSize(R.styleable.BannerView_alignMargin, 0);
        setPagerSnap(gravity, margin);
        setInfiniteLoop(typedArray.getBoolean(R.styleable.BannerView_infiniteLoop, false));
        setIndicatorVisibility(typedArray.getInt(R.styleable.BannerView_indicatorVisibility,
                VISIBLE));
        setIndicatorBottomMargin(typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorBottomMargin, Utils.dip2px(context, INDICATOR_BOTTOM_MARGIN)));
        setIndicatorSelectedWidth(typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorSelectedWidth, -1));
        setIndicatorDefaultWidth(typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorDefaultWidth, -1));
        setIndicatorHeight(typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorHeight, -1));
        setIndicatorMargin(typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorMargin, -1));
        setIndicatorSelectedColor(typedArray.getColor(R.styleable.BannerView_indicatorSelectedColor, UltraRecyclerViewIndicator.SELECTED_COLOR));
        setIndicatorDefaultColor(typedArray.getColor(R.styleable.BannerView_indicatorDefaultColor
                , UltraRecyclerViewIndicator.DEFAULT_COLOR));
        typedArray.recycle();
    }

    /**
     * Sets RecyclerView Adapter.
     *
     * @param adapter RecyclerView Adapter.
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mUltraRecyclerView.setAdapter(adapter);
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
        mUltraRecyclerView.setPagerSnap(gravity, alignMargin);
    }

    /**
     * Register a callback to be invoked when this view enables pager snap and changes position.
     *
     * @param onSnapListener The callback that will run.
     */
    public void setOnSnapListener(GravityPagerSnapHelper.OnSnapListener onSnapListener) {
        mUltraRecyclerView.setOnSnapListener(onSnapListener);
    }

    /**
     * Returns whether the items are infinite loop.
     *
     * @return True if the items are infinite loop, or false otherwise.
     */
    public boolean isInfiniteLoop() {
        return mUltraRecyclerView.isInfiniteLoop();
    }

    /**
     * Enables or disables the items infinite loop.
     *
     * @param infiniteLoop True to enable the items infinite loop, or false otherwise.
     */
    public void setInfiniteLoop(boolean infiniteLoop) {
        mUltraRecyclerView.setInfiniteLoop(infiniteLoop);
    }

    /**
     * Starts auto scrolling, the duration is default.
     */
    public void startAutoScroll() {
        mUltraRecyclerView.startAutoScroll();
    }

    /**
     * Starts auto scrolling, the duration is specified.
     *
     * @param duration The duration in milliseconds.
     */
    public void startAutoScroll(int duration) {
        mUltraRecyclerView.startAutoScroll(duration);
    }

    /**
     * Starts auto scrolling, the start delay-time is specified, and the duration is default.
     *
     * @param delay The start delay-time in milliseconds.
     */
    public void startAutoScrollDelayed(int delay) {
        mUltraRecyclerView.startAutoScrollDelayed(delay);
    }

    /**
     * Starts auto scrolling, the duration and start delay-time are specified.
     *
     * @param delay    The duration in milliseconds.
     * @param duration The start delay-time in milliseconds.
     */
    public void startAutoScrollDelayed(int delay, int duration) {
        mUltraRecyclerView.startAutoScrollDelayed(delay, duration);
    }

    /**
     * Restarts auto scrolling.
     */
    public void restartAutoScroll() {
        mUltraRecyclerView.restartAutoScroll();
    }

    /**
     * Stops auto scrolling.
     */
    public void stopAutoScroll() {
        mUltraRecyclerView.stopAutoScroll();
    }

    /**
     * Sets auto scrolling speed.
     *
     * @param duration The duration in milliseconds when scrolling. Sets negative number to
     *                 restore default.
     */
    public void setAutoScrollSpeed(int duration) {
        mUltraRecyclerView.setAutoScrollSpeed(duration);
    }

    /**
     * Sets auto scrolling speed and interpolator.
     *
     * @param duration     The duration in milliseconds when scrolling. Sets negative number to
     *                     restore default.
     * @param interpolator The animation interpolator.
     */
    public void setAutoScrollSpeed(int duration, Interpolator interpolator) {
        mUltraRecyclerView.setAutoScrollSpeed(duration, interpolator);
    }

    /**
     * Returns whether this view is auto scrolled.
     *
     * @return True if this view is auto scrolled, or false otherwise.
     */
    public boolean isAutoScroll() {
        return mUltraRecyclerView.isAutoScroll();
    }

    /**
     * Returns current position.
     *
     * @return Current position.
     */
    public int getCurrentPosition() {
        return mUltraRecyclerView.getCurrentPosition();
    }

    /**
     * Returns real count.
     *
     * @return Real count.
     */
    public int getRealCount() {
        return mUltraRecyclerView.getRealCount();
    }

    /**
     * Refresh adapter.
     */
    public void refresh() {
        mUltraRecyclerView.refresh();
    }

    /**
     * Sets visibility of the indicator.
     *
     * @param visibility Visibility of the indicator.
     */
    public void setIndicatorVisibility(int visibility) {
        mIndicator.setVisibility(visibility);
    }

    /**
     * Sets bottom margin of the indicator.
     *
     * @param margin Bottom margin of the indicator in px.
     */
    public void setIndicatorBottomMargin(@Px int margin) {
        LayoutParams layoutParams = (LayoutParams) mIndicator.getLayoutParams();
        layoutParams.bottomMargin = margin;
        mIndicator.requestLayout();
    }

    /**
     * Sets selected width of the indicator.
     *
     * @param selectedWidth Selected width of the indicator in px.
     */
    public void setIndicatorSelectedWidth(@Px int selectedWidth) {
        mIndicator.setSelectedWidth(selectedWidth);
    }

    /**
     * Sets default width of the indicator.
     *
     * @param defaultWidth Default width of the indicator in px.
     */
    public void setIndicatorDefaultWidth(@Px int defaultWidth) {
        mIndicator.setDefaultWidth(defaultWidth);
    }

    /**
     * Sets height of the indicator.
     *
     * @param height Height of the indicator in px.
     */
    public void setIndicatorHeight(@Px int height) {
        mIndicator.setHeight(height);
    }

    /**
     * Sets inner margin of the indicator.
     *
     * @param margin Inner margin of the indicator in px.
     */
    public void setIndicatorMargin(@Px int margin) {
        mIndicator.setMargin(margin);
    }

    /**
     * Sets selected color of the indicator.
     *
     * @param selectedColor Selected color of the indicator.
     */
    public void setIndicatorSelectedColor(@ColorInt int selectedColor) {
        mIndicator.setSelectedColor(selectedColor);
    }

    /**
     * Sets default color of the indicator.
     *
     * @param defaultColor Default color of the indicator.
     */
    public void setIndicatorDefaultColor(@ColorInt int defaultColor) {
        mIndicator.setDefaultColor(defaultColor);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mIndicator.attachToRecyclerView(mUltraRecyclerView);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIndicator.detachFromRecyclerView(mUltraRecyclerView);
    }
}
