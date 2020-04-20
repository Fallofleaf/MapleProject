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
    private List<String> costData = new ArrayList<>();
    private List<String> incomeData = new ArrayList<>();


    public void getAllAccount(List<TypeAccount> allAccount) {
        this.typeAccounts = allAccount;
        Log.e("typeAccountmain",typeAccounts.size()+"sdddddds");
        costData.add("餐饮");
        costData.add("购物");
        costData.add("日用");
        costData.add("交通");
        costData.add("蔬菜");
        costData.add("水果");
        costData.add("零食");
        costData.add("运动");
        costData.add("娱乐");
        costData.add("通讯");
        costData.add("服饰");
        costData.add("美容");
        costData.add("住房");
        costData.add("居家");
        costData.add("孩子");
        costData.add("长辈");
        costData.add("社交");
        costData.add("旅行");
        costData.add("烟酒");
        costData.add("数码");
        costData.add("汽车");
        costData.add("医疗");
        costData.add("书籍");
        costData.add("学习");
        costData.add("宠物");
        costData.add("礼金");
        costData.add("礼物");
        costData.add("办公");
        costData.add("维修");
        costData.add("捐赠");
        costData.add("彩票");
        costData.add("亲友");
        costData.add("快递");
        costData.add("其他");
        incomeData.add("工资");
        incomeData.add("兼职");
        incomeData.add("理财");
        incomeData.add("礼金");
        incomeData.add("其他");
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
            if (!account.isType()){
                holder.itemContentKind.setText(costData.get(account.getKind()));
            }else {
                holder.itemContentKind.setText(incomeData.get(account.getKind()));
            }

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
