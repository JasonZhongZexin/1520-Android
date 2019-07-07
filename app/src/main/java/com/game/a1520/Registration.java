package com.game.a1520;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Registration extends AppCompatActivity {

    @BindView(R.id.name_et)
    public EditText mName_et;
    @BindView(R.id.email_et)
    public EditText mEmail_et;
    @BindView(R.id.date_of_birth_title)
    public EditText mDoB_et;
    @BindView(R.id.phone_et)
    public EditText mPhone_et;
    private AlertDialog mDialog;
    @BindView(R.id.save_btn)
    public Button mSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        mDoB_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
                final View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_date_picker,null);
                Button confirmBtn = dialogView.findViewById(R.id.dialog_ConfirmBtn);
                Button cancelBtn = dialogView.findViewById(R.id.dialog_CancelBtn);
                final DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dob = String.format("%04d",datePicker.getYear())+String.format("%02d",datePicker.getMonth())+String.format("%02d",datePicker.getDayOfMonth());
                        updateDoB(dob);
                        mDialog.dismiss();
                    }
                });
                mBuilder.setView(dialogView);
                mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validFields()){
                    attemptSaveUser();
                }
            }
        });
    }

    public void updateDoB(String dob){
        mDoB_et.setText(dob);
    }

    public Boolean isEmpty(EditText editText_view){
        return TextUtils.isEmpty(editText_view.getText().toString());
    }

    public Boolean validFields(){
        if(isEmpty(mName_et)){
            mName_et.setError(getApplicationContext().getString(R.string.name_empty_error));
            mName_et.requestFocus();
            return false;
        }else if(isEmpty(mEmail_et)){
            mEmail_et.setError(getApplicationContext().getString(R.string.email_empty_error));
            mEmail_et.requestFocus();
            return false;
        }else if(isEmpty(mDoB_et)){
            mDoB_et.setError(getApplicationContext().getString(R.string.dob_empty_error));
            mDoB_et.requestFocus();
            return false;
        }else if(isEmpty(mPhone_et)){
            mPhone_et.setError(getApplicationContext().getString(R.string.phone_empty_error));
            mPhone_et.requestFocus();
            return false;
        }
        return true;
    }

    public void resetError(){
        mName_et.setError(null);
        mEmail_et.setError(null);
        mPhone_et.setError(null);
        mDoB_et.setError(null);
    }

    public void attemptSaveUser(){
        resetError();
        if(validFields()){
            SharedPreferences sp = getSharedPreferences(AppConfig.USERDETAIL_SHAREDPREFERENCE,MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(AppConfig.USER_NAME,mName_et.getText().toString());
            editor.putString(AppConfig.USEREMAIL,mEmail_et.getText().toString());
            editor.putString(AppConfig.USER_DOB,mDoB_et.getText().toString());
            editor.putString(AppConfig.USER_PHONE,mPhone_et.getText().toString());
            editor.commit();
            this.finish();
        }
    }
}
