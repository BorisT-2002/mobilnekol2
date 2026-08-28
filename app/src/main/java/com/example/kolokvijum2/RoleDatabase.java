package com.example.kolokvijum2;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Role.class}, version = 1)
public abstract class RoleDatabase extends RoomDatabase {
    public abstract RoleDao roleDao();

    private static RoleDatabase instance;

    public static synchronized RoleDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoleDatabase.class,
                    "role_database"
            ).build();
        }
        return instance;
    }
}
