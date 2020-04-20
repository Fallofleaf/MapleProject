package com.zxk147.maple.editFragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditViewModel extends ViewModel {
    private MutableLiveData<Integer> MyPosition;
    public MutableLiveData<Integer> getMyPosition(){
        if (MyPosition ==null){
            MyPosition = new MutableLiveData<>();
            MyPosition.setValue(-1);
        }
        return MyPosition;
    }

    public void changeMyPosition(int position){
        MyPosition.setValue(position);
    }

    public int id = -1;
    public boolean type = false;
    public int kind = -1;
}
