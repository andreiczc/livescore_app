package com.example.livescore_app;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Match.class, League.class}, version = 3)
public abstract class DatabaseInstance extends RoomDatabase {
    private static DatabaseInstance instance = null;

    public abstract DbDao dbDao();

    static final private Migration MIGRATION_1_3 = new Migration(1, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("create table 'leagues' ('leagueName' TEXT NOT NULL, PRIMARY KEY('leagueName'))");
            database.execSQL("alter table 'matches' add 'leagueName' TEXT");
        }
    };

    public static DatabaseInstance getDatabaseInstance(Context context) {
        if(instance == null)
            instance = Room
                    .databaseBuilder(context, DatabaseInstance.class, "my-db")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_3)
                    .build();

        return instance;
    }


}
