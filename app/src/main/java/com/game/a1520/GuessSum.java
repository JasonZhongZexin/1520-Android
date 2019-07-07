package com.game.a1520;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.game.a1520.model.Opponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuessSum extends AppCompatActivity {
    @BindView(R.id.opponentID_tv)
    public TextView opponentID_tv;
    @BindView(R.id.opponentName_tv)
    public TextView opponentName_tv;
    @BindView(R.id.opponentCountry_tv)
    public TextView opponentCountry_tv;
    private Opponent opponent;
    private String user_hands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_sum);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        opponent = (Opponent) intent.getSerializableExtra("opponent");
        user_hands = intent.getStringExtra("user_hands");
        updateOpponentInfo(opponent.getID(),opponent.getName(),opponent.getCountry());
    }

    private void updateOpponentInfo(int id, String name, String country){
        opponentID_tv.setText(Integer.toString(id));
        opponentName_tv.setText(name);
        opponentCountry_tv.setText(country);
    }

    public void choose0(View view) {
        submitGuessResult(0);
    }

    public void choose5(View view) {
        submitGuessResult(5);
    }

    public void choose10(View view) {
        submitGuessResult(10);
    }

    public void choose15(View view) {
        submitGuessResult(15);
    }

    public void choose20(View view) {
        submitGuessResult(20);
    }

    public void submitGuessResult(int guessResult){
        Intent intent = new Intent(this,FinalResult.class);
        intent.putExtra("opponent",opponent);
        intent.putExtra("guess_reuslt",guessResult);
        intent.putExtra("user_hands",user_hands);
        startActivity(intent);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            return true;
        }else{
            return super.dispatchKeyEvent(event);
        }
    }
}
