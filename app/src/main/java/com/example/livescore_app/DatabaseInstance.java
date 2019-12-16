package com.example.livescore_app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Match.class}, version = 1)
public abstract class DatabaseInstance extends RoomDatabase {
    private static DatabaseInstance instance = null;

    public abstract MatchDao matchDao();

    public static DatabaseInstance getDatabaseInstance(Context context) {
        if(instance == null)
            instance = Room
                    .databaseBuilder(context, DatabaseInstance.class, "my-db")
                    .allowMainThreadQueries()
                    .build();

        return instance;
    }
}
