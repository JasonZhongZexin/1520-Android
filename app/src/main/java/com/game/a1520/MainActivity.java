package com.game.a1520;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.game.a1520.model.NetworkConnection;
import com.game.a1520.model.Opponent;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.opponentID_tv)
    public TextView opponentID_tv;
    @BindView(R.id.opponentName_tv)
    public TextView opponentName_tv;
    @BindView(R.id.opponentCountry_tv)
    public TextView opponentCountry_tv;
    @BindView(R.id.opponent_info_layout)
    public LinearLayout opponent_info_layout;
    @BindView(R.id.progress_circular)
    public ProgressBar progressBar;
    @BindView(R.id.user_choose_hands_layout)
    public LinearLayout user_choose_hands_layout;
    private Opponent opponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        attemptToLoadOpponentInfo();
    }

    private void attemptToLoadOpponentInfo(){
        NetworkConnection.getOpponentFromServer(AppConfig.FIND_OPPONENT, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAlertDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                Gson gson = new Gson();
                opponent= gson.fromJson(res,Opponent.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateOpponentInfo(opponent.getID(),opponent.getName(),opponent.getCountry());
                        progressBar.setVisibility(GONE);
                        opponent_info_layout.setVisibility(View.VISIBLE);
                        user_choose_hands_layout.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    private void showAlertDialog(){
        AlertDialog.Builder bulider = new AlertDialog.Builder(MainActivity.this);
        bulider.setTitle(R.string.network_connection_error_title);
        bulider.setMessage(R.string.network_connection_error_message);
        bulider.setCancelable(false);
        bulider.setPositiveButton(R.string.retry_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                attemptToLoadOpponentInfo();
                dialog.dismiss();
            }
        });
        bulider.setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                exitActivity();
            }
        });
        AlertDialog dialog = bulider.create();
        dialog.show();
    }

    private void exitActivity(){
        this.finish();
    }

    private void updateOpponentInfo(int id, String name, String country){
        opponentID_tv.setText(Integer.toString(id));
        opponentName_tv.setText(name);
        opponentCountry_tv.setText(country);
    }

    public void choose_5_0(View view) {
        setUser_choose_hands("5_0");
    }

    public void choose_0_0(View view) {
        setUser_choose_hands("0_0");
    }

    public void choose_0_5(View view) {
        setUser_choose_hands("0_5");
    }

    public void choose_5_5(View view) {
        setUser_choose_hands("5_5");
    }

    public void setUser_choose_hands(String hands){
        Intent intent = new Intent(this,GuessSum.class);
        intent.putExtra("user_hands",hands);
        intent.putExtra("opponent", opponent);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            Intent intent = new Intent(this,Launcher.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            Intent intent = new Intent(this,Launcher.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
