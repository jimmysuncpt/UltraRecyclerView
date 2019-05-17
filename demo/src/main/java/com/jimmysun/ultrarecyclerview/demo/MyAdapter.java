package com.jimmysun.ultrarecyclerview.demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jimmysun.ultrarecyclerview.R;
import com.jimmysun.ultrarecyclerview.Utils;

/**
 * @author SunQiang
 * @since 2019-04-29
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final int[] colors = new int[]{
            0xffff0000, 0xff00ff00, 0xff0000ff, 0xffffff00, 0xffff00ff, 0xff00ffff
    };

    private int mOrientation;

    public MyAdapter(int orientation) {
        mOrientation = orientation;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item,
                viewGroup, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        ViewGroup.LayoutParams layoutParams = myViewHolder.itemView.getLayoutParams();
        if (mOrientation == RecyclerView.VERTICAL) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = Utils.dip2px(myViewHolder.itemView.getContext(), 520);
        } else {
            layoutParams.width = Utils.dip2px(myViewHolder.itemView.getContext(), 260);
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        myViewHolder.itemView.setBackgroundColor(colors[position]);
        myViewHolder.mTextView.setText("Real position: " + position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_item);
        }
    }
}
