package com.zxk147.maple.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Account.class},version = 1,exportSchema = false)
public abstract class AccounntDatabase extends RoomDatabase {
    private static AccounntDatabase INSTANCE;
    static synchronized AccounntDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext()
                    ,AccounntDatabase.class
                    ,"account_database")
                    .addMigrations()
                    .build();
        }
        return INSTANCE;
    }
    public abstract AccountDao getAccountDao();
}
