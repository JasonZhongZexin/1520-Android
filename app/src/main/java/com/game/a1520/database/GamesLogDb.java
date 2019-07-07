package com.game.a1520.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.game.a1520.model.GamesLog;

@Database(entities = GamesLog.class, version = 1)
public abstract class GamesLogDb extends RoomDatabase {
    public abstract DAO dao();
}
