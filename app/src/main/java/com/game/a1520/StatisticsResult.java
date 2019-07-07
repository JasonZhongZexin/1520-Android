package com.game.a1520;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.game.a1520.customUI.BarChartView;
import com.game.a1520.database.GamesLogDb;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsResult extends AppCompatActivity {

    private ArrayList<Double> data_total = new ArrayList<Double>();
    private ArrayList<String> data_title = new ArrayList<String>();
    private GamesLogDb gamesLogDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_result);
        View parentLayout = findViewById(R.id.parentLayout);
        gamesLogDb = Room.databaseBuilder(this, GamesLogDb.class, AppConfig.DATABASENAME).allowMainThreadQueries().build();
        int winCount = gamesLogDb.dao().getResultCOunt(AppConfig.WIN_OR_LOST_COULUMN,"Win");
        int lostCount = gamesLogDb.dao().getResultCOunt(AppConfig.WIN_OR_LOST_COULUMN,"Lost");
        data_total.add((double)winCount);
        data_total.add((double)lostCount);
        data_title.add("Win");
        data_title.add("Lost");
        BarChartView barChartView = new BarChartView(this, data_total, data_title);
        Log.d("count win:",""+winCount);
        Log.d("count lost:",""+lostCount);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        barChartView.setLayoutParams(lp);
        ((ViewGroup)parentLayout).addView(barChartView,lp);
    }

    @Override
    protected void onStart() {
        SoundPoolUtils.getInstnce(this).stop();
        SoundPoolUtils.getInstnce(this).play("bgm",0.3f,1,-1 );
        super.onStart();
    }

    @Override
    protected void onResume() {
        SoundPoolUtils.getInstnce(this).stop();
        SoundPoolUtils.getInstnce(this).play("bgm",0.3f,1,-1 );
        super.onResume();
    }

    @Override
    protected void onRestart() {
        SoundPoolUtils.getInstnce(this).stop();
        SoundPoolUtils.getInstnce(this).play("bgm",0.3f,1,-1 );
        super.onRestart();
    }
}
