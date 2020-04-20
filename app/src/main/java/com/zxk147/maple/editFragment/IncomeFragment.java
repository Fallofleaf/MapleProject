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

import com.zxk147.maple.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    EditViewModel editViewModel;
    private List<String> mData = new ArrayList<>();
    RecyclerView recyclerView;
    private CostRecyclerViewAdapter costRecyclerViewAdapter;
    private int kind;
    private static String TAG = "IncomeFragment";

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_income, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();


        editViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(EditViewModel.class);
        for (int i=0;i<6;i++){
            mData.add("dsd"+i);
        }
        editViewModel.getMyPosition().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                kind = integer;
            }
        });


        recyclerView = getActivity().findViewById(R.id.fragment_income_recyclerview);
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
}
