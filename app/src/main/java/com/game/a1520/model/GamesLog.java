package com.game.a1520.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.game.a1520.AppConfig;

@Entity(tableName = AppConfig.DB_TBALE_NAME)
public class GamesLog {
    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo(name=AppConfig.GAMEDATE_COLUMN)
    private String gamedate;
    @ColumnInfo(name=AppConfig.GAMETIME_COLUMN)
    private String gameTime;
    @ColumnInfo(name=AppConfig.OPPONENT_NAME_COULUMN)
    private String opponentName;
    @ColumnInfo(name=AppConfig.WIN_OR_LOST_COULUMN)
    private String winOrLost;

    public String getGamedate() {
        return gamedate;
    }

    @NonNull
    public void setId(int id) {
        this.id = id;
    }

    public GamesLog(String gamedate, String gameTime, String opponentName, String winOrLost) {
        this.gamedate = gamedate;
        this.gameTime = gameTime;
        this.opponentName = opponentName;
        this.winOrLost = winOrLost;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getGameTime() {
        return gameTime;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public String getWinOrLost() {
        return winOrLost;
    }

    public void setGamedate(String gamedate) {
        this.gamedate = gamedate;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public void setWinOrLost(String winOrLost) {
        this.winOrLost = winOrLost;
    }
}
