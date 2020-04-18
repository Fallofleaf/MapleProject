package com.zxk147.maple;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zxk147.maple.View.NumKeyBoardView;
import com.zxk147.maple.data.Account;
import com.zxk147.maple.data.AccountViewModel;
import com.zxk147.maple.editFragment.DialogDate;

import java.util.zip.Inflater;

public class EditActivity extends AppCompatActivity {

    EditText editTextType,editTextAmount,editTextKind,editTextDate,editTextNote;
    Button buttonConfirm;
    NumKeyBoardView numKeyBoardView;
    TextView textViewDate,textViewCancle,textViewConfirm;
    DatePicker datePicker;
    AccountViewModel accountViewModel;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        editTextType = findViewById(R.id.editTextType);
        editTextNote = findViewById(R.id.editTextNote);
        editTextAmount = findViewById(R.id.editTextAmuont);
        editTextKind = findViewById(R.id.editTextKind);
        editTextDate = findViewById(R.id.editTextDate);

        buttonConfirm = findViewById(R.id.edit_confirm);

        accountViewModel = new ViewModelProvider(this
                ,new ViewModelProvider
                .AndroidViewModelFactory(getApplication()))
                .get(AccountViewModel.class);

        final Intent intent = getIntent();
        id = intent.getIntExtra("ID",-1);
        if (id!=-1){
            accountViewModel.getQueryById(id).observe(this, new Observer<Account>() {
                @Override
                public void onChanged(Account account) {
                    editTextDate.setText(account.getDate());
                    editTextNote.setText(account.getNote());
                    editTextKind.setText(String.valueOf(account.getKind()));
                    editTextAmount.setText(account.getAmount());
                    if (account.isType()){
                        editTextType.setText("收入");
                    }else {
                        editTextType.setText("支出");
                    }

                }
            });
            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean type = true;
                    if (editTextType.toString().equals("支出")){
                        type = false;
                    }
                    int kind = Integer.parseInt(editTextKind.getText().toString());
                    Account account = new Account(editTextDate.getText().toString(),type,kind,editTextNote.getText().toString(),editTextAmount.getText().toString());
                    account.setId(id);
                    accountViewModel.updateAccount(account);
                    finish();
                }
            });
        }else {
            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean type = true;
                    if (editTextType.toString().equals("支出")){
                        type = false;
                    }
                    int kind = Integer.parseInt(editTextKind.getText().toString());
                    Account account = new Account(editTextDate.getText().toString(),type,kind,editTextNote.getText().toString(),editTextAmount.getText().toString());
                    accountViewModel.insertAccount(account);
                    finish();
                }
            });
        }



    }
}
