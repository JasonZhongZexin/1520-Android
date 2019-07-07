package com.game.a1520.adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.game.a1520.AppConfig;
import com.game.a1520.R;
import com.game.a1520.database.GamesLogDb;
import com.game.a1520.model.GamesLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.MyViewHolder> {
    private List<GamesLog> logs = new ArrayList<>();
    private Context context;

    public RecyclerView_Adapter(Context context,List<GamesLog> logs) {
        this.context = context;
        this.logs = logs;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_logs_view,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.gameDate_tv.setText("Game date:"+logs.get(i).getGamedate());
        myViewHolder.gameTime_tv.setText("Game time:"+logs.get(i).getGameTime());
        myViewHolder.opponent_name_tv.setText("Opponent:"+logs.get(i).getOpponentName());
        myViewHolder.winOrLost_tv.setText("Game result:"+logs.get(i).getWinOrLost());
    }


    @Override
    public int getItemCount() {
        return logs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView gameDate_tv;
        public TextView gameTime_tv;
        public TextView opponent_name_tv;
        public TextView winOrLost_tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gameDate_tv = itemView.findViewById(R.id.gameDate_tv);
            gameTime_tv = itemView.findViewById(R.id.gameTime_tv);
            opponent_name_tv = itemView.findViewById(R.id.opponent_name_tv);
            winOrLost_tv = itemView.findViewById(R.id.winOrLost_tv);
        }
    }
}
