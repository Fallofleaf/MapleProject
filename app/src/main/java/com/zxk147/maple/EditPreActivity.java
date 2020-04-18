package com.zxk147.maple;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxk147.maple.data.Account;
import com.zxk147.maple.data.AccountDao;
import com.zxk147.maple.data.AccountViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditPreActivity extends AppCompatActivity {

    TextView textPreKind,textPreType,textPreNote,textPreAmount,textPreDate;
    Button buttonBack,buttonEdit,buttonDelete;
    AccountViewModel accountViewModel;
    private int id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pre);

        Intent intent =getIntent();
        id = intent.getIntExtra("ID",-1);

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
                    textPreKind.setText(String.valueOf(account.getKind()));
                    textPreAmount.setText(account.getAmount());
                    textPreDate.setText(account.getDate());
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
                Account account = new Account(null,false,1,null,null);
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
