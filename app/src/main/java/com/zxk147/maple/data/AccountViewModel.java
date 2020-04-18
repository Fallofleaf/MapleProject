package com.zxk147.maple.data;


import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private AccountRepository accountRepository;
    public AccountViewModel(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
    }



    public LiveData<List<Account>> getAllAccount(){return accountRepository.getAllAccountLive();}

    public LiveData<Account> getQueryById(int id){return accountRepository.getQueryById(id);}

    public void insertAccount(Account... accounts){accountRepository.insertAccount(accounts);}
    public void updateAccount(Account... accounts){accountRepository.updateAccount(accounts);}
    public void deleteAccount(Account... accounts){accountRepository.deleteAccount(accounts);}
    public void deleteAllAccount(Account... accounts){accountRepository.deleteAllAccount();}
}
