package com.zxk147.maple.editFragment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zxk147.maple.R;

import java.util.List;

public class CostRecyclerViewAdapter extends RecyclerView.Adapter<CostRecyclerViewAdapter.MyViewHolder> {
    private List<String> kindData;
    private Context context;
    private int itemChoosed = -1;//默认值
    private OnItemListener onItemListener;

    public CostRecyclerViewAdapter(List<String> data, Context context,int itemChoosed) {
        kindData = data;
        this.context = context;
        this.itemChoosed = itemChoosed;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    //获取点击的位置
    public void setDefSelect(int position) {
        this.itemChoosed = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cost_fragment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.mTextView.setText(kindData.get(position));

        if (itemChoosed != -1) {
            /*点的位置跟点击的textview位置一样设置点击后的不同样式*/
            if (itemChoosed == position) {
                /*设置选中的样式*/
                holder.mTextView.setBackground(context.getResources().getDrawable(R.drawable.select_true_button));
                holder.mTextView.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
               /*其他的变为未选择状态
                 *设置未选中的样式
                */
                holder.mTextView.setBackground(context.getResources().getDrawable(R.drawable.select_false_button));
                holder.mTextView.setTextColor(Color.parseColor("#000000"));
            }
        }

    }
    @Override
    public int getItemCount() {
        return kindData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_cost_kind);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemListener != null) {
                        onItemListener.onClick(v, getLayoutPosition());
                    }
                }
            });
        }
    }
    //设置点击事件
    public interface OnItemListener {
        void onClick(View view, int position);
    }
}
