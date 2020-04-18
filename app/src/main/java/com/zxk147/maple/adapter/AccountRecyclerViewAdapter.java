package com.zxk147.maple.adapter;

import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zxk147.maple.R;
import com.zxk147.maple.data.Account;
import com.zxk147.maple.data.AccountViewModel;

import java.util.ArrayList;
import java.util.List;

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.MyViewHolder> {

    List<Account> allAccount = new ArrayList<>();
    public void getAllAccount(List<Account> allAccount) {
        this.allAccount = allAccount;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_content,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Account account = allAccount.get(position);
        holder.itemView.setTag(account.getId());
        holder.itemContentKind.setText(String.valueOf(account.getKind()));
        holder.itemContentNote.setText(account.getNote());
        if (account.isType()) {
            holder.itemContentAmount.setText(account.getAmount());
        }else {
            holder.itemContentAmount.setText("-"+account.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return allAccount.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemContentKind;
        TextView itemContentNote;
        TextView itemContentAmount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemContentKind = itemView.findViewById(R.id.item_content_kind);
            itemContentNote = itemView.findViewById(R.id.item_content_note);
            itemContentAmount = itemView.findViewById(R.id.item_content_amount);
        }
    }

}
