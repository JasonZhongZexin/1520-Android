package com.game.a1520;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.game.a1520.model.NetworkConnection;
import com.game.a1520.model.Opponent;
import com.game.a1520.model.OpponentHands;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.view.View.GONE;

public class FinalResult extends AppCompatActivity {

    private Opponent opponent;
    private String user_hands;
    @BindView(R.id.your_left_hand)
    public ImageView user_left_hand;
    @BindView(R.id.your_right_hand)
    public ImageView user_right_hand;
    @BindView(R.id.opponent_left_hand)
    public ImageView opponent_left_hand;
    @BindView(R.id.opponent_right_hand)
    public ImageView opponent_right_hand;
    @BindView(R.id.opponent_choose_hand_tv_title)
    public TextView opponent_choose_hand_tv_title;
    @BindView(R.id.final_result_compare_layout)
    public LinearLayout final_result_compare_layout;
    @BindView(R.id.progress_circular)
    public ProgressBar progressBar;
    private OpponentHands opponentHands;
    private int final_sum;
    private int guess_reuslt;
    private Context context;
    private int round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        opponent = (Opponent) intent.getSerializableExtra("opponent");
        user_hands = intent.getStringExtra("user_hands");
        round = intent.getIntExtra("round",-1);
        guess_reuslt = intent.getIntExtra("guess_reuslt",-1);
        attemptToLoadOpponentHnads();
        context = this;
    }

    public void attemptToLoadOpponentHnads(){
        NetworkConnection.getOpponentFromServer(AppConfig.GET_OPPONENT_HANDS + opponent.getID(), new Callback() {
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
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Log.d("JSON Hands Result",res);
                Gson gson = new Gson();
                opponentHands= gson.fromJson(res,OpponentHands.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(GONE);
                        setFinal_result_compare_layout();
                        final_result_compare_layout.setVisibility(View.VISIBLE);
                        Log.d("guess_resutl",isGuessTrue()+"");
                        compareGuessAndResult();
                    }
                });
            }
        });
    }

    public void compareGuessAndResult(){
        if(isGuessTrue()){
            //user's guess round
            if(round==0||(round%2==0&&round>0)){
                AlertDialog dialog = getResultDialog("Congratulation","You win!","Try again","Back to menu");
                dialog.show();
            }else{
                AlertDialog dialog = getResultDialog("Oppps",opponent.getName()+"'s guess match."+"You lose!","Try again","Back to menu");
                dialog.show();
            }
        }else{
            if(round==0||(round%2==0&&round>0)){
                showLoseDialog("Oppps","You lose!");
            }else{
                showLoseDialog("Oppps",opponent.getName()+"'s guess doesn't match.");
            }
        }
    }

    public void showLoseDialog(String title,String message){
        final AlertDialog dialog = getResultDialog(title,message,null,null);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("opponent",opponent);
                round = round+1;
                intent.putExtra("round",round);
                Log.d("set round id:",round+"");
                startActivity(intent);
            }
        },3000);
    }

    public AlertDialog getResultDialog(String title,String message,String positiveBtn,String negativeButton){
        AlertDialog.Builder bulider = new AlertDialog.Builder(FinalResult.this);
        bulider.setTitle(title);
        bulider.setMessage(message);
        bulider.setCancelable(false);
        if(positiveBtn!=null && negativeButton!=null){
            bulider.setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    }
                    Intent intent = new Intent(context,MainActivity.class);
                    startActivity(intent);
                }
            });
            bulider.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    }
                    Intent intent = new Intent(context,Launcher.class);
                    startActivity(intent);
                }
            });
        }
        AlertDialog dialog = bulider.create();
        return dialog;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            return true;
        }else{
            return super.dispatchKeyEvent(event);
        }
    }

    public void setFinal_result_compare_layout(){
        opponent_choose_hand_tv_title.setText(opponent.getName()+"'s hands");
        setHandsImage(user_left_hand,user_right_hand,user_hands);
        setHandsImage(opponent_left_hand,opponent_right_hand,opponentHands.getLeft()+"_"+opponentHands.getRight());
    }

    public void setHandsImage(ImageView leftView,ImageView rightView,String hands){
        switch(hands){
            case "0_5":
                leftView.setImageDrawable(getResources().getDrawable(R.drawable.zero_down));
                rightView.setImageDrawable(getResources().getDrawable(R.drawable.five));
                break;
            case "0_0":
                leftView.setImageDrawable(getResources().getDrawable(R.drawable.zero_down));
                rightView.setImageDrawable(getResources().getDrawable(R.drawable.zero_down));
                break;
            case "5_0":
                rightView.setImageDrawable(getResources().getDrawable(R.drawable.zero_down));
                leftView.setImageDrawable(getResources().getDrawable(R.drawable.five));
                break;
            case "5_5":
                rightView.setImageDrawable(getResources().getDrawable(R.drawable.five));
                leftView.setImageDrawable(getResources().getDrawable(R.drawable.five));
                break;
        }
    }

    public void countSum(){
        switch(user_hands){
            case "0_5":
                final_sum=5+opponentHands.getRight()+opponentHands.getLeft();
                break;
            case "0_0":
                final_sum=0+opponentHands.getRight()+opponentHands.getLeft();
                break;
            case "5_0":
                final_sum=5+opponentHands.getRight()+opponentHands.getLeft();
                break;
            case "5_5":
                final_sum=10+opponentHands.getRight()+opponentHands.getLeft();
                break;
        }
    }

    /**
     * check if the guess number same as the final result
     * @return
     */
    public Boolean isGuessTrue(){
        countSum();
        if(guess_reuslt==-1)
            guess_reuslt = opponentHands.getGuess();
        Log.d("opponenetGuess",guess_reuslt+"");
        return final_sum == guess_reuslt;
    }

    private void showAlertDialog(){
        AlertDialog.Builder bulider = new AlertDialog.Builder(FinalResult.this);
        bulider.setTitle(R.string.network_connection_error_title);
        bulider.setMessage(R.string.network_connection_error_message1);
        bulider.setCancelable(false);
        bulider.setPositiveButton(R.string.retry_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                attemptToLoadOpponentHnads();
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
}
