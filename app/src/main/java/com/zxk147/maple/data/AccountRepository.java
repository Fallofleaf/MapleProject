package com.zxk147.maple.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountRepository {
    private LiveData<List<Account>> allAccountLive;
    private LiveData<Account> queryById;
    private AccountDao accountDao;

    AccountRepository(Context context){
        AccounntDatabase accounntDatabase = AccounntDatabase.getDatabase(context.getApplicationContext());
        accountDao = accounntDatabase.getAccountDao();
        allAccountLive = accountDao.getAllAccount();

    }

    LiveData<List<Account>> getAllAccountLive(){return allAccountLive;}
    LiveData<Account> getQueryById(int id){return accountDao.getById(id);}

    void insertAccount(Account... accounts){new InsertAsyncTask(accountDao).execute(accounts);}
    void updateAccount(Account... accounts){new UpdateAsyncTask(accountDao).execute(accounts);}
    void deleteAccount(Account... accounts){new DeleteAsyncTask(accountDao).execute(accounts);}
    void deleteAllAccount(){new DeleteAllAsyncTask(accountDao).execute();}




    /**
     * 异步执行
     */
    static class InsertAsyncTask extends AsyncTask<Account,Void,Void>{
        private AccountDao accountDao;

        public InsertAsyncTask(AccountDao accountDao) { this.accountDao = accountDao; }
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.insertAccount(accounts);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Account,Void,Void>{
        private AccountDao accountDao;

        public UpdateAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.updateAccount(accounts);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Account,Void,Void>{
        private AccountDao accountDao;

        public DeleteAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.deleteAccount(accounts);
            return null;
        }
    }
    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private AccountDao accountDao;

        public DeleteAllAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accountDao.deleteAllAccount();
            return null;
        }
    }


}
