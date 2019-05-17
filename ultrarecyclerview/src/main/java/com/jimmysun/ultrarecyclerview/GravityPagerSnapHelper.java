package com.jimmysun.ultrarecyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import java.util.Locale;

/**
 * The PagerSnapHelper which can set gravity.
 *
 * @author SunQiang
 * @since 2019-05-09
 */
public class GravityPagerSnapHelper extends PagerSnapHelper {

    private OrientationHelper verticalHelper;
    private OrientationHelper horizontalHelper;
    private int gravity;
    private int alignMargin;
    private boolean isRtl;
    private boolean snapLastItem;
    private OnSnapListener listener;
    private boolean snapping;
    private int lastSnappedPosition;
    private RecyclerView recyclerView;
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && snapping && listener != null) {
                if (lastSnappedPosition != RecyclerView.NO_POSITION) {
                    listener.onSnap(lastSnappedPosition);
                }
                snapping = false;
            }
        }
    };

    public GravityPagerSnapHelper(int gravity) {
        this(gravity, false);
    }

    public GravityPagerSnapHelper(int gravity, boolean enableSnapLastItem) {
        this(gravity, enableSnapLastItem, null);
    }

    public GravityPagerSnapHelper(int gravity, boolean enableSnapLastItem,
                                  @Nullable OnSnapListener onSnapListener) {
        if (gravity != Gravity.START && gravity != Gravity.END && gravity != Gravity.CENTER) {
            throw new IllegalArgumentException("Invalid gravity value. Use START | END | CENTER " +
                    "constants");
        }
        this.gravity = gravity;
        snapLastItem = enableSnapLastItem;
        listener = onSnapListener;
    }

    public void setAlignMargin(@Px int alignMargin) {
        this.alignMargin = alignMargin;
    }

    public void setOnSnapListener(OnSnapListener listener) {
        this.listener = listener;
    }

    /**
     * Enable snapping of the last item that's snappable.
     * The default value is false, because you can't see the last item completely
     * if this is enabled.
     *
     * @param snap true if you want to enable snapping of the last snappable item
     */
    public void enableLastItemSnap(boolean snap) {
        snapLastItem = snap;
    }

    public void smoothScrollToPosition(int position) {
        scrollTo(position, true);
    }

    public void scrollToPosition(int position) {
        scrollTo(position, false);
    }

    private void scrollTo(int position, boolean smooth) {
        if (recyclerView.getLayoutManager() != null) {
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder != null) {
                int[] distances = calculateDistanceToFinalSnap(recyclerView.getLayoutManager(),
                        viewHolder.itemView);
                if (smooth) {
                    recyclerView.smoothScrollBy(distances[0], distances[1]);
                } else {
                    recyclerView.scrollBy(distances[0], distances[1]);
                }
            } else {
                if (smooth) {
                    recyclerView.smoothScrollToPosition(position);
                } else {
                    recyclerView.scrollToPosition(position);
                }
            }
        }
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView)
            throws IllegalStateException {
        if (recyclerView != null && this.recyclerView != recyclerView) {
            if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager)
                    || recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                throw new IllegalStateException("GravityPagerSnapHelper needs a RecyclerView" +
                        " with a LinearLayoutManager");
            }
            recyclerView.setOnFlingListener(null);
            if (gravity == Gravity.START || gravity == Gravity.END) {
                isRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault())
                        == ViewCompat.LAYOUT_DIRECTION_RTL;
            }
            recyclerView.addOnScrollListener(scrollListener);
            this.recyclerView = recyclerView;
        }
        super.attachToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        int[] out = new int[2];

        if (!(layoutManager instanceof LinearLayoutManager)) {
            return out;
        }

        LinearLayoutManager lm = (LinearLayoutManager) layoutManager;

        if (lm.canScrollHorizontally()) {
            if ((isRtl && gravity == Gravity.END) || (!isRtl && gravity == Gravity.START)) {
                out[0] = distanceToStart(targetView, lm, getHorizontalHelper(lm));
            } else if (gravity == Gravity.CENTER) {
                out[0] = distanceToCenter(lm, targetView, getHorizontalHelper(lm));
            } else {
                out[0] = distanceToEnd(targetView, lm, getHorizontalHelper(lm));
            }
        }

        if (lm.canScrollVertically()) {
            if (gravity == Gravity.START) {
                out[1] = distanceToStart(targetView, lm, getVerticalHelper(lm));
            } else if (gravity == Gravity.CENTER) {
                out[1] = distanceToCenter(lm, targetView, getVerticalHelper(lm));
            } else { // END
                out[1] = distanceToEnd(targetView, lm, getVerticalHelper(lm));
            }
        }

        return out;
    }

    private int distanceToStart(View targetView, LinearLayoutManager lm,
                                @NonNull OrientationHelper helper) {
        int pos = recyclerView.getChildLayoutPosition(targetView);
        int distance;
        if ((pos == 0 && (!isRtl || lm.getReverseLayout())
                || pos == lm.getItemCount() - 1 && (isRtl || lm.getReverseLayout()))
                && !recyclerView.getClipToPadding()) {
            int childStart = helper.getDecoratedStart(targetView);
            if (childStart >= helper.getStartAfterPadding() / 2) {
                distance = childStart - helper.getStartAfterPadding();
            } else {
                distance = childStart;
            }
        } else {
            distance = helper.getDecoratedStart(targetView);
        }
        return distance - alignMargin;
    }

    private int distanceToCenter(@NonNull RecyclerView.LayoutManager layoutManager,
                                 @NonNull View targetView, OrientationHelper helper) {
        int childCenter =
                helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView) / 2;
        int containerCenter;
        if (layoutManager.getClipToPadding()) {
            containerCenter = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
        } else {
            containerCenter = helper.getEnd() / 2;
        }

        return childCenter - containerCenter;
    }

    private int distanceToEnd(View targetView, LinearLayoutManager lm,
                              @NonNull OrientationHelper helper) {
        int pos = recyclerView.getChildLayoutPosition(targetView);
        int distance;

        // The last position or the first position
        // (when there's a reverse layout or we're on RTL mode) must collapse to the padding edge.
        if ((pos == 0 && (isRtl || lm.getReverseLayout())
                || pos == lm.getItemCount() - 1 && (!isRtl || lm.getReverseLayout()))
                && !recyclerView.getClipToPadding()) {
            int childEnd = helper.getDecoratedEnd(targetView);
            if (childEnd >= helper.getEnd() - (helper.getEnd() - helper.getEndAfterPadding()) / 2) {
                distance = helper.getDecoratedEnd(targetView) - helper.getEnd();
            } else {
                distance = childEnd - helper.getEndAfterPadding();
            }
        } else {
            distance = helper.getDecoratedEnd(targetView) - helper.getEnd();
        }
        return distance + alignMargin;
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (verticalHelper == null) {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return verticalHelper;
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (horizontalHelper == null) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return horizontalHelper;
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return null;
        }
        View snapView = null;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        if (gravity == Gravity.CENTER) {
            if (layoutManager.canScrollVertically()) {
                snapView = findCenterView(layoutManager, getVerticalHelper(layoutManager));
            } else if (layoutManager.canScrollHorizontally()) {
                snapView = findCenterView(layoutManager, getHorizontalHelper(layoutManager));
            }
        } else if (gravity == Gravity.START) {
            if (layoutManager.canScrollVertically()) {
                snapView = findEdgeView(linearLayoutManager, getVerticalHelper(layoutManager),
                        true);
            } else if (layoutManager.canScrollHorizontally()) {
                snapView = findEdgeView(linearLayoutManager, getHorizontalHelper(layoutManager),
                        true);
            }
        } else if (gravity == Gravity.END) {
            if (layoutManager.canScrollVertically()) {
                snapView = findEdgeView(linearLayoutManager, getVerticalHelper(layoutManager),
                        false);
            } else if (layoutManager.canScrollHorizontally()) {
                snapView = findEdgeView(linearLayoutManager, getHorizontalHelper(layoutManager),
                        false);
            }
        }
        snapping = snapView != null;
        if (snapView != null) {
            lastSnappedPosition = recyclerView.getChildAdapterPosition(snapView);
        }
        return snapView;
    }

    /**
     * Returns the first view that we should snap to.
     *
     * @param lm     the recyclerview's layout manager
     * @param helper orientation helper to calculate view sizes
     * @return the first view in the LayoutManager to snap to
     */
    @Nullable
    private View findEdgeView(LinearLayoutManager lm, OrientationHelper helper, boolean start) {
        if (lm.getChildCount() == 0) {
            return null;
        }

        // If we're at the end of the list, we shouldn't snap
        // to avoid having the last item not completely visible.
        if (isAtEndOfList(lm) && !snapLastItem) {
            return null;
        }

        View edgeView = null;
        int distanceToEdge = Integer.MAX_VALUE;

        for (int i = 0; i < lm.getChildCount(); i++) {
            View currentView = lm.getChildAt(i);
            int currentViewDistance;
            if ((start && !isRtl) || (!start && isRtl)) {
                currentViewDistance = Math.abs(helper.getDecoratedStart(currentView));
            } else {
                currentViewDistance = Math.abs(helper.getDecoratedEnd(currentView)
                        - helper.getEnd());
            }
            if (currentViewDistance < distanceToEdge) {
                distanceToEdge = currentViewDistance;
                edgeView = currentView;
            }
        }
        return edgeView;
    }

    @Nullable
    private View findCenterView(RecyclerView.LayoutManager layoutManager,
                                OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        } else {
            View closestChild = null;
            int center;
            if (layoutManager.getClipToPadding()) {
                center = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
            } else {
                center = helper.getEnd() / 2;
            }

            int absClosest = Integer.MAX_VALUE;

            for (int i = 0; i < childCount; ++i) {
                View child = layoutManager.getChildAt(i);
                int childCenter =
                        helper.getDecoratedStart(child) + helper.getDecoratedMeasurement(child) / 2;
                int absDistance = Math.abs(childCenter - center);
                if (absDistance < absClosest) {
                    absClosest = absDistance;
                    closestChild = child;
                }
            }

            return closestChild;
        }
    }

    private boolean isAtEndOfList(LinearLayoutManager lm) {
        if ((!lm.getReverseLayout() && gravity == Gravity.START)
                || (lm.getReverseLayout() && gravity == Gravity.END)) {
            return lm.findLastCompletelyVisibleItemPosition() == lm.getItemCount() - 1;
        } else {
            return lm.findFirstCompletelyVisibleItemPosition() == 0;
        }
    }

    public interface OnSnapListener {
        void onSnap(int position);
    }
}