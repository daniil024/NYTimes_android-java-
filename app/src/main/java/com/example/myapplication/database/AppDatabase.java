package com.example.myapplication.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.myapplication.database.daos.NewsDao;
import com.example.myapplication.database.entites.NewsEntity;

@Database(entities = {NewsEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase database;
    private static final String DATABASE_NAME = "NewsDb.db";

    public AppDatabase() {
    }

    public abstract NewsDao newsDao();

    public static AppDatabase getInstance(Context context) {
        if (database == null)
            synchronized (AppDatabase.class) {
                if (database == null) {
                    database = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }

        return database;
    }
}
