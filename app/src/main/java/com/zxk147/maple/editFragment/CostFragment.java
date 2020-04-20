package com.zxk147.maple.editFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zxk147.maple.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment {

    private static String TAG = "CostFragment";
    private RecyclerView recyclerView;
    private EditViewModel editViewModel;
    private List<String> costData = new ArrayList<>();
    private CostRecyclerViewAdapter costRecyclerViewAdapter;
    private int kind;
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
        editViewModel.getMyPosition().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                kind =integer;
            }
        });
//        calendarView = getActivity().findViewById(R.id.cost_calendarview);
//        editTextAmount = getActivity().findViewById(R.id.edit_cost_amount);
//        editTextNote = getActivity().findViewById(R.id.edit_cost_note);

//        Bundle bundle = this.getArguments();
//        kind = -1;
//        if (bundle!=null){
//            kind = bundle.getInt("KIND");
//            Log.e(TAG,"Kind is " + kind);
//        }
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


        recyclerView = getActivity().findViewById(R.id.fragment_cost_recyclerview);
        costRecyclerViewAdapter = new CostRecyclerViewAdapter(costData, getContext(), kind);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
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
