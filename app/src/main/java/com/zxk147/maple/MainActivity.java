package com.zxk147.maple;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zxk147.maple.adapter.AccountItemDecoration;
import com.zxk147.maple.adapter.AccountRecyclerViewAdapter;
import com.zxk147.maple.adapter.OnRecyclerViewClickListener;
import com.zxk147.maple.data.Account;
import com.zxk147.maple.data.AccountViewModel;
import com.zxk147.maple.data.TypeAccount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.StatementEvent;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton floatingActionButton;
    TextView textViewYear,textViewMonthDay;
    RecyclerView recyclerView;
    RefreshLayout smartRefreshLayout;
    private AccountViewModel accountViewModel;
    private AccountRecyclerViewAdapter accountRecyclerViewAdapter;
    private String typeTest = "支出";

    TextView textViewIncomeTotal,textViewCostTotal;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        textViewDate = findViewById(R.id.mainDate);
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
//        //获取当前时间
//        Date dateMain = new Date(System.currentTimeMillis());
//        textViewDate.setText(simpleDateFormat.format(dateMain));
        textViewYear = findViewById(R.id.main_year);
        textViewMonthDay = findViewById(R.id.main_month_day);
        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        //获取系统时间
//        //小时
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        //分钟
//        int minute = calendar.get(Calendar.MINUTE);
//        //秒
//        int second = calendar.get(Calendar.SECOND);
        textViewYear.setText(year + "年");
        textViewMonthDay.setText(month+"月"+day+"日");

        textViewCostTotal = findViewById(R.id.costTotal);
        textViewIncomeTotal = findViewById(R.id.incomeTotal);


        //关联recyclerView和适配器
        recyclerView = findViewById(R.id.recyclerView);
        accountRecyclerViewAdapter = new AccountRecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(accountRecyclerViewAdapter);
        //使用ViewModel
        accountViewModel = new ViewModelProvider(this
                , new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AccountViewModel.class);
        accountViewModel.getAllAccount().observe(this
                , new Observer<List<Account>>() {
                    @Override
                    public void onChanged(final List<Account> accounts) {



                        List<Account>allAccount = accounts;
                        List<TypeAccount> typeAccounts = new ArrayList<>();
                        for (int i = 0;i<allAccount.size();i++){
                            if (i == 0&&accounts.size()!=0){
                                TypeAccount typeAccount = new TypeAccount(-1,
                                        allAccount.get(i).getDate(),
                                        allAccount.get(i).isType(),
                                        allAccount.get(i).getKind(),
                                        allAccount.get(i).getNote(),
                                        allAccount.get(i).getAmount(),
                                        true,
                                        null,
                                        null);
                                typeAccounts.add(typeAccount);
                                TypeAccount typeAccount1 = new TypeAccount(allAccount.get(i).getId(),
                                        allAccount.get(i).getDate(),
                                        allAccount.get(i).isType(),
                                        allAccount.get(i).getKind(),
                                        allAccount.get(i).getNote(),
                                        allAccount.get(i).getAmount(),
                                        false,
                                        null,
                                        null);
                                typeAccounts.add(typeAccount1);
                            }else if (!isSameDay(allAccount.get(i).getDate(),allAccount.get(i-1).getDate())){
                                TypeAccount typeAccount = new TypeAccount(-1,
                                        allAccount.get(i).getDate(),
                                        allAccount.get(i).isType(),
                                        allAccount.get(i).getKind(),
                                        allAccount.get(i).getNote(),
                                        allAccount.get(i).getAmount(),
                                        true,
                                        null,
                                        null);
                                typeAccounts.add(typeAccount);
                                TypeAccount typeAccount1 = new TypeAccount(allAccount.get(i).getId(),
                                        allAccount.get(i).getDate(),
                                        allAccount.get(i).isType(),
                                        allAccount.get(i).getKind(),
                                        allAccount.get(i).getNote(),
                                        allAccount.get(i).getAmount(),
                                        false,
                                        null,
                                        null);
                                typeAccounts.add(typeAccount1);
                            }else {
                                TypeAccount typeAccount = new TypeAccount(allAccount.get(i).getId(),
                                        allAccount.get(i).getDate(),
                                        allAccount.get(i).isType(),
                                        allAccount.get(i).getKind(),
                                        allAccount.get(i).getNote(),
                                        allAccount.get(i).getAmount(),
                                        false,
                                        null,
                                        null);
                                typeAccounts.add(typeAccount);
                            }
                        }
                        Log.e("alllisr",allAccount.size()+"ddd");
                        Log.e("mmmmmmm",typeAccounts.size()+"ddd");
                        float cost=0;
                        float income=0;
                        int m = 0;

                        if (typeAccounts.size()>0){
                        for (int i =0;i<typeAccounts.size()+1;i++){
                            if (i==typeAccounts.size()){
                                typeAccounts.get(i-m).setCost(String.valueOf(cost));
                                typeAccounts.get(i-m).setIncome(String.valueOf(income));
                            }else if (typeAccounts.get(i).isTitle()){
                                typeAccounts.get(i-m).setCost(String.valueOf(cost));
                                typeAccounts.get(i-m).setIncome(String.valueOf(income));
                                cost=0;
                                m = 1;
                            }else  {
                                if (typeAccounts.get(i).isType()){
                                    income += Float.parseFloat(typeAccounts.get(i).getAmount());
                                }else {
                                    cost += Float.parseFloat(typeAccounts.get(i).getAmount());
                                }
                                m +=1;
                            }
                        }
                        for (int i =0;i<typeAccounts.size();i++){
                            Log.e("type",typeAccounts.get(0).getAmount());
                        }
                        for (int i =0;i<allAccount.size();i++){
                            Log.e("mmmmmmmmmmmmmmmmmm",allAccount.get(0).getNote());
                        }
                        Log.e("sizeall",allAccount.size()+"ddd");
                        Log.e("sizeafter",typeAccounts.size()+"ddd");
                        }

                        accountRecyclerViewAdapter.getAllAccount(typeAccounts);
                        float costall =0;
                        float incomeall = 0;
                        for (int i =0;i<accounts.size();i++){
                            if (accounts.get(i).isType()){
                                incomeall += Float.parseFloat(accounts.get(i).getAmount());
                            }else {
                                costall += Float.parseFloat(accounts.get(i).getAmount());
                            }
                        }
                        textViewCostTotal.setText("总消费\n "+ costall);
                        textViewIncomeTotal.setText("总收入\n "+ incomeall);
//                        //当ItemDecoration已经添加了一个的时候，为了防止重复绘制，移除掉旧数据的，同时继续给新的数据设置Decoration，新的就变为了
//                        //0，如果数据再次改变刷新，那么就移除0.再添加
//                        if (recyclerView.getItemDecorationCount() != 0) {
//                            recyclerView.removeItemDecorationAt(0);
//                        }
//                        recyclerView.addItemDecoration(new AccountItemDecoration(MainActivity.this, new AccountItemDecoration.DecorationCallBack() {
//
//                            @Override
//                            public String getGroupDate(int position) {
//                                return accounts.get(position).getDate();
//                            }
//
//                            @Override
//                            public String getGroupFirstDate(int position) {
//                                return accounts.get(position).getDate();
//                            }
//
//                            @Override
//                            public boolean getGroupType(int position) {
//                                return accounts.get(position).isType();
//                            }
//
//                            @Override
//                            public String getGroupAmount(int position) {
//                                return accounts.get(position).getAmount();
//                            }
//                        }));

                        accountRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
//                for (int i = 1; i < 6; i++) {
//                    String string = "2020-4-"+i+" 12:12:12";
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = null;
//                    try {
//                        date = simpleDateFormat.parse(string);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    Account account = new Account(date.getTime(), false, 2, "谁知道", "23");
//                    accountViewModel.insertAccount(account);
//                }
            }
        });


        smartRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                smartRefreshLayout.finishRefresh();
            }
        });
//        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorAccent));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(MainActivity.this,EditActivity.class);
//                        startActivity(intent);
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                },5);
//            }
//        });


        recyclerView.addOnItemTouchListener(new OnRecyclerViewClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                final int id = (int) viewHolder.itemView.getTag(R.id.tag_id);
                if (id!=-1){
                    Intent intent = new Intent(MainActivity.this, EditPreActivity.class);
                    intent.putExtra("ID", id);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder) {
                final int id = (int) viewHolder.itemView.getTag(R.id.tag_id);
                if (id!=-1){
                    final long date = (long) viewHolder.itemView.getTag(R.id.tag_date);
                    final String amount = (String) viewHolder.itemView.getTag(R.id.tag_amounnt);
                    final boolean type = (boolean) viewHolder.itemView.getTag(R.id.tag_type);
                    final String note = (String) viewHolder.itemView.getTag(R.id.tag_note);
                    final int kind = (int) viewHolder.itemView.getTag(R.id.tag_kind);
                    Account account = new Account(date, type, kind, note, amount);
                    account.setId(id);
                    accountViewModel.deleteAccount(account);
                    if (type == true){
                        MainActivity.this.typeTest = "收入";
                    }
                    Toast.makeText(MainActivity.this,"删除了"+typeTest+date+kind+note+amount,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public boolean isSameDay(long date1,long date2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String da1 = simpleDateFormat.format(date1);
        String da2 = simpleDateFormat.format(date2);
        if (da1.equals(da2)){
            return true;
        }else {
            return false;
        }
    }
}
