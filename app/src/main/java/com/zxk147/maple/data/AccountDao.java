package com.zxk147.maple.data;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDao {
    @Insert
    void insertAccount(Account... accounts);
    @Update
    void updateAccount(Account... accounts);
    @Delete
    void deleteAccount(Account... accounts);
    @Query("SELECT * FROM Account ORDER BY date DESC")
    LiveData<List<Account>> getAllAccount();
    @Query("DELETE FROM Account")
    void deleteAllAccount();
    @Query("SELECT * FROM Account WHERE id = :myId")
    LiveData<Account> getById(int myId);

}
