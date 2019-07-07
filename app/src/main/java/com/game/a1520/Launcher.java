package com.game.a1520;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Launcher extends AppCompatActivity {

    @BindView(R.id.register_btn)
    public Button mRegister_btn;
    @BindView(R.id.start_btn)
    public Button mStart_btn;
    @BindView(R.id.history_btn)
    public Button mHistory_btn;
    @BindView(R.id.edit_account_btn)
    public Button mEditUser_btn;
    @BindView(R.id.result_statistical_btn)
    public Button mResult_statistical_btn;
    SoundPoolUtils soundPoolUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        soundPoolUtils = SoundPoolUtils.getInstnce(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isRegisted()){
            mRegister_btn.setVisibility(View.GONE);
            mStart_btn.setVisibility(View.VISIBLE);
            mHistory_btn.setVisibility(View.VISIBLE);
            mEditUser_btn.setVisibility(View.VISIBLE);
            mResult_statistical_btn.setVisibility(View.VISIBLE);
        }else{
            mRegister_btn.setVisibility(View.VISIBLE);
            mStart_btn.setVisibility(View.GONE);
            mHistory_btn.setVisibility(View.GONE);
            mEditUser_btn.setVisibility(View.GONE);
            mResult_statistical_btn.setVisibility(View.GONE);
        }
        mEditUser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EditUser.class);
                startActivity(intent);
            }
        });
        mStart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        mHistory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(v.getContext(),GameHistory.class);
                startActivity(intent);
            }
        });
        mResult_statistical_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),StatisticsResult.class);
                startActivity(intent);
            }
        });
        soundPoolUtils.stop();
        soundPoolUtils.play("bgm",0.3f,1,-1 );
    }

    private boolean isRegisted(){
        SharedPreferences sp = getSharedPreferences(AppConfig.USERDETAIL_SHAREDPREFERENCE,MODE_PRIVATE);
        String email = sp.getString(AppConfig.USEREMAIL,"");
        if(email.equals(""))
            return false;
        return true;
    }

    public void createUser(View view) {
        Intent intent = new Intent(this,Registration.class);
        startActivity(intent);
    }

/*
    @Override
    protected void onStop() {
        soundPoolUtils.stop();
        super.onStop();
    }
*/

    @Override
    protected void onDestroy() {
        soundPoolUtils.stop();
        super.onDestroy();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK||keyCode ==KeyEvent.KEYCODE_HOME){
            soundPoolUtils.stop();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
