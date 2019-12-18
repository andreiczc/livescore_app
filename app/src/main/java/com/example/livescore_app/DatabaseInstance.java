package com.example.livescore_app;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Match.class, League.class}, version = 4)
public abstract class DatabaseInstance extends RoomDatabase {
    private static DatabaseInstance instance = null;

    public abstract DbDao dbDao();

    public static DatabaseInstance getDatabaseInstance(Context context) {
        if (instance == null)
            instance = Room
                    .databaseBuilder(context, DatabaseInstance.class, "my-db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        return instance;
    }


}
