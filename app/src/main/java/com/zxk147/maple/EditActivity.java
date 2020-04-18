package com.zxk147.maple;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zxk147.maple.View.NumKeyBoardView;
import com.zxk147.maple.editFragment.DialogDate;

public class EditActivity extends AppCompatActivity {

    EditText editText;
    NumKeyBoardView numKeyBoardView;
    TextView textViewDate,textViewCancle,textViewConfirm;
    DatePicker datePicker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        textViewDate = findViewById(R.id.edit_date);
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }




}
