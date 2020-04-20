package com.zxk147.maple.editFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.zxk147.maple.R;
import com.zxk147.maple.data.Account;
import com.zxk147.maple.data.AccountViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment {

    private List<String> mData = new ArrayList<>();
    RecyclerView recyclerView;
    private CostRecyclerViewAdapter costRecyclerViewAdapter;
    private int kind;
    private static String TAG = "CostFragment";
    EditViewModel editViewModel;
    AccountViewModel accountViewModel;
    CalendarView calendarView;
    EditText editTextNote,editTextAmount;
    Button buttonConfirm;

    public CostFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cost, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

//        /**
//         * 在需要获取数据的地方，创建对象调取方法
//         */
//        EditActivity editActivity = new EditActivity();
//        editActivity.setKindListener(new EditActivity.onKindListener() {
//            @Override
//            public void OnKindListener(int kind) {
//                fkind = kind;
//                Log.e(TAG,"Kind is " + kind);
//            }
//        });

        editViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(EditViewModel.class);
        accountViewModel = new ViewModelProvider(getActivity()).get(AccountViewModel.class);

//        Bundle bundle = this.getArguments();
//        kind = -1;
//        if (bundle!=null){
//            kind = bundle.getInt("KIND");
//            Log.e(TAG,"Kind is " + kind);
//        }
        for (int i=0;i<6;i++){
            mData.add("dsd"+i);
        }
        editViewModel.getMyPosition().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                kind = integer;
            }
        });


        recyclerView = getActivity().findViewById(R.id.fragment_cost_recyclerview);
        costRecyclerViewAdapter = new CostRecyclerViewAdapter(mData,getContext(),kind);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(costRecyclerViewAdapter);
        costRecyclerViewAdapter.setOnItemListener(new CostRecyclerViewAdapter.OnItemListener() {
            @Override
            public void onClick(View view, final int position) {
                costRecyclerViewAdapter.setDefSelect(position);
                editViewModel.changeMyPosition(position);
            }
        });

    }

//    //接口回调实现fragment向activity传递数据
//    public interface putKindListener{
//        void PutKindListener(int position);
//    }
//    private putKindListener KindListener;
//    public void setKindListener(putKindListener putKindListener){
//        this.KindListener = putKindListener;
//    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        KindListener =(putKindListener) context;
//    }
}
