package com.zxk147.maple;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zxk147.maple.View.NumKeyBoardView;
import com.zxk147.maple.data.Account;
import com.zxk147.maple.data.AccountViewModel;
import com.zxk147.maple.editFragment.CostFragment;
import com.zxk147.maple.editFragment.IncomeFragment;

import java.util.Calendar;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private static String TAG= "EditActivity";

    EditText editTextType,editTextAmount,editTextKind,editTextNote;
    Button buttonConfirm;
    CalendarView calendarView;
    AccountViewModel accountViewModel;
    int id;
    TabLayout tableLayout;
    ViewPager2 viewPager2;
    TabItem tabItem1,tabItem2;

    Bundle bundle;

    private long mCalendar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        editTextNote = findViewById(R.id.editTextNote);
        editTextAmount = findViewById(R.id.editTextAmuont);
        editTextKind = findViewById(R.id.editTextKind);
        buttonConfirm = findViewById(R.id.edit_confirm);
        tableLayout = findViewById(R.id.tabLayout);
        tabItem1 = findViewById(R.id.asd);
        tabItem2 = findViewById(R.id.ds);
        viewPager2 = findViewById(R.id.viewpager_edit);

        calendarView = findViewById(R.id.edit_calendarView);

        accountViewModel = new ViewModelProvider(this
                ,new ViewModelProvider
                .AndroidViewModelFactory(getApplication()))
                .get(AccountViewModel.class);

        final Intent intent = getIntent();
        id = intent.getIntExtra("ID",-1);
        if (id!=-1){
            accountViewModel.getQueryById(id).observe(this, new Observer<Account>() {
                @Override
                public void onChanged(final Account account) {
                    calendarView.setDate(account.getDate());
                    String date = (String) DateFormat.format("yyyy年MM月dd日",account.getDate());
                    editTextNote.setText(account.getNote());
                    editTextKind.setText(String.valueOf(account.getKind()));
                    editTextAmount.setText(account.getAmount());
                    calendarView.setDate(account.getDate());


                    bundle = new Bundle();
                    bundle.putInt("KIND",account.getKind());
                    Log.e(TAG,"此时的bundle是："+bundle);

//                    if (account.isType()){
//                        editTextType.setText("收入");
//                    }else {
//                        editTextType.setText("支出");
//                    }

                    //利用延时操作，通过自身特点滑动到特定位置
                    if (account.isType()){
                        tableLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tableLayout.getTabAt(1).select();
                            }
                        },10);
                    }

                }
            });
            Calendar calendar = Calendar.getInstance();
            mCalendar = calendar.getTime().getTime();
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year,month,dayOfMonth);
                    mCalendar = calendar.getTime().getTime();
                }
            });
            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentItem = viewPager2.getCurrentItem();
                    boolean type = true;
                    Log.e("sdsdddddddddddddd",currentItem+"ds");
                    if (currentItem==0){
                        type = false;
                    }

                    int kind = Integer.parseInt(editTextKind.getText().toString());
                    Account account = new Account(mCalendar,type,kind,editTextNote.getText().toString(),editTextAmount.getText().toString());
                    account.setId(id);
                    accountViewModel.updateAccount(account);
                    finish();
                }
            });
        }else {
            Calendar calendar = Calendar.getInstance();
            mCalendar = calendar.getTime().getTime();
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year,month,dayOfMonth);
                    mCalendar = calendar.getTime().getTime();
                }
            });
            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentItem = viewPager2.getCurrentItem();
                    boolean type = true;
                    Log.e("sdsdddddddddddddd",currentItem+"ds");
                    if (currentItem==0){
                        type = false;
                    }
                    int kind = Integer.parseInt(editTextKind.getText().toString());
                    Account account = new Account(mCalendar,type,kind,editTextNote.getText().toString(),editTextAmount.getText().toString());
                    accountViewModel.insertAccount(account);
                    finish();
                }
            });
        }
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) {
                    Fragment fragment = new CostFragment();
                    Log.e(TAG,"传入fragment前的bundle是："+bundle);
                    fragment.setArguments(bundle);
                    return fragment;
                }else {
                    return new IncomeFragment();
                }
            }
            @Override
            public int getItemCount() {
                return 2;
            }
        });

        new TabLayoutMediator(tableLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Log.e("position",position+"sdd");
                if (position == 0) {
                    tab.setText("支出");
                }else {
                    tab.setText("收入");
                }
            }
        }).attach();



    }
}
