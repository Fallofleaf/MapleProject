package com.zxk147.maple;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

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
import com.zxk147.maple.data.Account;
import com.zxk147.maple.data.AccountViewModel;
import com.zxk147.maple.editFragment.CostFragment;
import com.zxk147.maple.editFragment.EditViewModel;
import com.zxk147.maple.editFragment.IncomeFragment;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity{

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

    int editKind=-1;

    EditViewModel editViewModel;


    Fragment incomeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //初始化fragment和bundle
        final CostFragment costFragment = new CostFragment();

        incomeFragment = new IncomeFragment();
        bundle = new Bundle();

        editTextNote = findViewById(R.id.editTextNote);
        editTextAmount = findViewById(R.id.editTextAmuont);
        editTextKind = findViewById(R.id.editTextKind);
        buttonConfirm = findViewById(R.id.edit_confirm);


        editViewModel = new ViewModelProvider(this
                ,new ViewModelProvider
                .AndroidViewModelFactory(getApplication()))
                .get(EditViewModel.class);
        editViewModel.getMyPosition().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                editKind=integer;
            }
        });


        calendarView = findViewById(R.id.edit_calendarView);

        accountViewModel = new ViewModelProvider(this
                ,new ViewModelProvider
                .AndroidViewModelFactory(getApplication()))
                .get(AccountViewModel.class);
        tableLayout = findViewById(R.id.tabLayout);
        tabItem1 = findViewById(R.id.asd);
        tabItem2 = findViewById(R.id.ds);
        viewPager2 = findViewById(R.id.viewpager_edit);



        final Intent intent = getIntent();
        id = intent.getIntExtra("ID",-1);
        id = editViewModel.id;
        if (id!=-1){
            accountViewModel.getQueryById(id).observe(this, new Observer<Account>() {
                @Override
                public void onChanged(final Account account) {
                    calendarView.setDate(account.getDate());
                    mCalendar = account.getDate();
//                    String date = (String) DateFormat.format("yyyy年MM月dd日",account.getDate());
                    editTextNote.setText(account.getNote());
                    editTextKind.setText(String.valueOf(account.getKind()));
                    editTextAmount.setText(account.getAmount());
                    calendarView.setDate(account.getDate());

//                    /**
//                     * 在合适的位置调用接口里面的方法,传递数据
//                     */
//                    editKind = account.getKind();
                    editViewModel.changeMyPosition(account.getKind());
                    Log.e(TAG,"此时的kind是："+account.getKind());

                    viewPager2.setAdapter(new FragmentStateAdapter(EditActivity.this) {
                        @NonNull
                        @Override
                        public Fragment createFragment(int position) {
                            if (position == 0) {
                                return costFragment;
                            }else {
                                return incomeFragment;
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
//            Calendar calendar = Calendar.getInstance();
//            mCalendar = calendar.getTime().getTime();
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
//                    costFragment.setKindListener(new CostFragment.putKindListener() {
//                        @Override
//                        public void PutKindListener(int position) {
//                            editKind = position;
//                        }
//                    });
                    int kind = Integer.parseInt(editTextKind.getText().toString());
                    Account account = new Account(mCalendar,type,editKind,editTextNote.getText().toString(),editTextAmount.getText().toString());
                    account.setId(id);
                    accountViewModel.updateAccount(account);
                    finish();
                }
            });
        }else {

            viewPager2.setAdapter(new FragmentStateAdapter(EditActivity.this) {
                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    if (position == 0) {
                        return costFragment;
                    }else {
                        return incomeFragment;
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
                    editViewModel.getMyPosition().observe(EditActivity.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            editKind = integer;
                        }
                    });
                    int kind = Integer.parseInt(editTextKind.getText().toString());
                    Account account = new Account(mCalendar,type,editKind,editTextNote.getText().toString(),editTextAmount.getText().toString());
                    accountViewModel.insertAccount(account);
                    finish();
                }
            });
        }



//        /**
//         * 在合适的位置调用接口里面的方法,传递数据
//         */
//        Log.e(TAG,"sdfdsf："+onKindListener);
//
//        if (onKindListener!=null){
//
//            onKindListener.OnKindListener(editKind);
//        }

        /**
         * 添加Fragment
         */


    }


//    /**
//     * 定义一个接口
//     */
//    public interface onKindListener{
//        void OnKindListener(int kind);
//    }
//
//    /**
//     * 定义变量存储数据
//     */
//
//    private onKindListener onKindListener;
//    /**
//     * 提供公共方法,并且初始化接口类型的数据
//     */
//    public void setKindListener(onKindListener onKindListener){
//        this.onKindListener = onKindListener;
//    }
}
