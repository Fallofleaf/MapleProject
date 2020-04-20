package com.zxk147.maple;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxk147.maple.data.Account;
import com.zxk147.maple.data.AccountDao;
import com.zxk147.maple.data.AccountViewModel;
import com.zxk147.maple.editFragment.EditViewModel;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class EditPreActivity extends AppCompatActivity {

    TextView textPreKind,textPreType,textPreNote,textPreAmount,textPreDate;
    Button buttonBack,buttonEdit,buttonDelete;
    AccountViewModel accountViewModel;
    private int id;

    EditViewModel editViewModel;

    private List<String> costData = new ArrayList<>();
    private List<String> incomeData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pre);

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

        Intent intent =getIntent();
        id = intent.getIntExtra("ID",-1);
//        editViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(EditViewModel.class);
//        id = editViewModel.id;
        accountViewModel = new ViewModelProvider(this
                ,new ViewModelProvider
                .AndroidViewModelFactory(getApplication()))
                .get(AccountViewModel.class);

        textPreKind = findViewById(R.id.pre_kind);
        textPreType = findViewById(R.id.pre_type);
        textPreAmount = findViewById(R.id.pre_amount);
        textPreDate = findViewById(R.id.pre_date);
        textPreNote = findViewById(R.id.pre_note);

        buttonBack = findViewById(R.id.pre_back);
        buttonEdit = findViewById(R.id.pre_edit);
        buttonDelete = findViewById(R.id.pre_delete);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        accountViewModel.getQueryById(id).observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if (account!=null){
                    if (!account.isType()){
                        textPreKind.setText(costData.get(account.getKind()));
                    }else {
                        textPreKind.setText(incomeData.get(account.getKind()));
                    }
                    textPreAmount.setText(account.getAmount());
                    String date = (String) DateFormat.format("yyyy年MM月dd日",account.getDate());
                    textPreDate.setText(date);
                    textPreNote.setText(account.getNote());
                    if (account.isType()){
                        textPreType.setText("收入");
                    }else {
                        textPreType.setText("支出");
                    }
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = new Account(0,false,1,null,null);
                account.setId(id);
                accountViewModel.deleteAccount(account);
                finish();
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPreActivity.this,EditActivity.class);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });
    }
}
