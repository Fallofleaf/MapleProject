package com.zxk147.maple.editFragment;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxk147.maple.EditActivity;
import com.zxk147.maple.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment {

    private List<String> mData = new ArrayList<>();
    RecyclerView recyclerView;
    private CostRecyclerViewAdapter costRecyclerViewAdapter;
    private int kind;
    private static String TAG = "CostFragment";




    public CostFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cost, container, false);


        Bundle bundle = this.getArguments();
        kind = -1;
        if (bundle!=null){
            kind = bundle.getInt("KIND");
        }
        Log.e(TAG,"Kind is " + kind);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


//        for (int i=0;i<1;i++){
//            mData.add("dsd"+i);
//        }
        recyclerView = getActivity().findViewById(R.id.fragment_cost_recyclerview);
        costRecyclerViewAdapter = new CostRecyclerViewAdapter(mData,getContext(),kind);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(costRecyclerViewAdapter);
        costRecyclerViewAdapter.setOnItemListener(new CostRecyclerViewAdapter.OnItemListener() {
            @Override
            public void onClick(View view, int position) {
                costRecyclerViewAdapter.setDefSelect(position);
            }
        });

    }

}
