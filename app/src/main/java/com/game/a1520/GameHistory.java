package com.game.a1520;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.game.a1520.adapter.RecyclerView_Adapter;
import com.game.a1520.database.GamesLogDb;
import com.game.a1520.model.GamesLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameHistory extends AppCompatActivity {

    @BindView(R.id.logs_view)
    public RecyclerView logs_view;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView_Adapter adapter;
    private GamesLogDb gamesLogDb;
    private List<GamesLog> logs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);
        ButterKnife.bind(this);
        gamesLogDb = Room.databaseBuilder(this, GamesLogDb.class, AppConfig.DATABASENAME).allowMainThreadQueries().build();
        logs = gamesLogDb.dao().getAllLogs();
        adapter = new RecyclerView_Adapter(this,logs);
        layoutManager = new LinearLayoutManager(this);
        logs_view.setLayoutManager(layoutManager);
        logs_view.setAdapter(adapter);
        logs_view.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
