package com.game.a1520.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.game.a1520.AppConfig;
import com.game.a1520.model.GamesLog;

import java.util.List;

@Dao
public interface DAO {
    /**
     * add a new log to the games log table
     * @param log the new game log
     */
    @Insert
    public void addGameLog(GamesLog log);

    @Query("select * from GamesLog")
    public List<GamesLog> getAllLogs();

    @Query("select COUNT(:column_name) from GamesLog where winOrLost = :result_type")
    public int getResultCOunt(String column_name,String result_type);
}
