package com.zxk147.maple.adapter;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zxk147.maple.R;
import com.zxk147.maple.data.Account;
import com.zxk147.maple.data.TypeAccount;

import java.util.ArrayList;
import java.util.List;

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.MyViewHolder> {

    List<Account> allAccount = new ArrayList<>();
    List<TypeAccount> typeAccounts = new ArrayList<>();

    public void getAllAccount(List<TypeAccount> allAccount) {
        this.typeAccounts = allAccount;
        Log.e("typeAccountmain",typeAccounts.size()+"sdddddds");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == 1){
            view = layoutInflater.inflate(R.layout.item_content,parent,false);
        }else {
            view = layoutInflater.inflate(R.layout.item_title,parent,false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TypeAccount account = typeAccounts.get(position);
        holder.itemView.setTag(R.id.tag_id,account.getId());
        Log.e("Adapter",account.getAmount());
        if (getItemViewType(position)==1){
            //设置Tag
            holder.itemView.setTag(R.id.tag_date,account.getDate());
            holder.itemView.setTag(R.id.tag_kind,account.getKind());
            holder.itemView.setTag(R.id.tag_amounnt,account.getAmount());
            holder.itemView.setTag(R.id.tag_note,account.getNote());
            holder.itemView.setTag(R.id.tag_type,account.isType());

            holder.itemContentKind.setText(String.valueOf(account.getKind()));
            holder.itemContentNote.setText(account.getNote());

            if (account.isType()) {
                holder.itemContentAmount.setText(account.getAmount());
            }else {
                holder.itemContentAmount.setText("-"+account.getAmount());
            }
        }else {
            String date = (String) DateFormat.format("yyyy年MM月dd日",account.getDate());
            holder.itemTitleDate.setText(date);
            if (account.getCost()==null||account.getCost().equals("0.0")){
                holder.itemTitleIncost.setText("收入:"+account.getIncome());
            }else if (account.getIncome()==null||account.getIncome().equals("0.0")){
                holder.itemTitleIncost.setText("支出:"+account.getCost());
            }else {
                holder.itemTitleIncost.setText("支出:"+account.getCost()+"    收入:"+account.getIncome());
            }

        }

    }

    @Override
    public int getItemCount() {
        Log.e("typeAccount",typeAccounts.size()+"ds");
        return typeAccounts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (typeAccounts.get(position).isTitle()){
            return 0;
        }else {
            return 1;
        }
//        return super.getItemViewType(position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemContentKind;
        TextView itemContentNote;
        TextView itemContentAmount;

        TextView itemTitleDate;
        TextView itemTitleIncost;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemContentKind = itemView.findViewById(R.id.item_content_kind);
            itemContentNote = itemView.findViewById(R.id.item_content_note);
            itemContentAmount = itemView.findViewById(R.id.item_content_amount);

            itemTitleDate = itemView.findViewById(R.id.item_title_date);
            itemTitleIncost = itemView.findViewById(R.id.item_title_in_cost);
        }
    }

}
