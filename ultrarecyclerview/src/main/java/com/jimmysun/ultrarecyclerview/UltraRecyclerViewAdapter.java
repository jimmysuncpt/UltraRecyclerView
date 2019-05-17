package com.jimmysun.ultrarecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author SunQiang
 */
public class UltraRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private RecyclerView.Adapter<VH> mAdapter;
    private boolean mIsInfiniteLoop;

    UltraRecyclerViewAdapter(@NonNull RecyclerView.Adapter<VH> adapter) {
        mAdapter = adapter;
    }

    public boolean isInfiniteLoop() {
        return mIsInfiniteLoop;
    }

    public void setInfiniteLoop(boolean infiniteLoop) {
        mIsInfiniteLoop = infiniteLoop;
    }

    public int getRealCount() {
        return mAdapter.getItemCount();
    }

    private int getRealPosition(int position) {
        if (mIsInfiniteLoop && mAdapter != null && mAdapter.getItemCount() > 0) {
            return position % mAdapter.getItemCount();
        } else {
            return position;
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        mAdapter.onBindViewHolder(holder, getRealPosition(position));
    }

    @Override
    public int getItemCount() {
        if (mIsInfiniteLoop) {
            if (mAdapter.getItemCount() > 1) {
                return Integer.MAX_VALUE;
            } else {
                return mAdapter.getItemCount();
            }
        } else {
            return mAdapter.getItemCount();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapter.getItemViewType(getRealPosition(position));
    }

    @Override
    public long getItemId(int position) {
        return mAdapter.getItemId(getRealPosition(position));
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        mAdapter.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        mAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onViewRecycled(@NonNull VH holder) {
        super.onViewRecycled(holder);
        mAdapter.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull VH holder) {
        return mAdapter.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        super.onViewAttachedToWindow(holder);
        mAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        super.onViewDetachedFromWindow(holder);
        mAdapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
        mAdapter.setHasStableIds(hasStableIds);
    }
}
