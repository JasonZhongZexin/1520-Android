package com.game.a1520;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditUser extends AppCompatActivity {

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
    @BindView(R.id.edit_btn)
    public Button mEditBtn;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);
        mSp = getSharedPreferences(AppConfig.USERDETAIL_SHAREDPREFERENCE,MODE_PRIVATE);
        mName_et.setText(mSp.getString(AppConfig.USER_NAME,""));
        mEmail_et.setText(mSp.getString(AppConfig.USEREMAIL,""));
        mPhone_et.setText(mSp.getString(AppConfig.USER_PHONE,""));
        mDoB_et.setText(mSp.getString(AppConfig.USER_DOB,""));
        setEdittextEnable(false);
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
    }

    public void updateDoB(String dob){
        mDoB_et.setText(dob);
    }

    public void editAccount(View view) {
        setEdittextEnable(true);
        mEditBtn.setVisibility(View.GONE);
        mSaveBtn.setVisibility(View.VISIBLE);
    }

    public void saveAccount(View view) {
        attemptSaveUser();
    }

    /**
     * update the status of the edit text components
     * @param mode true: editable false:not editebale
     */
    private void setEdittextEnable(boolean mode){
        if(!mode)
            mDoB_et.setEnabled(false);
        else
            mDoB_et.setEnabled(true);
        mName_et.setInputType(mode ? InputType.TYPE_TEXT_VARIATION_PERSON_NAME:InputType.TYPE_NULL);
        mEmail_et.setInputType(mode ? InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:InputType.TYPE_NULL);
        mDoB_et.setInputType(mode ? InputType.TYPE_CLASS_DATETIME:InputType.TYPE_NULL);
        mPhone_et.setInputType(mode ? InputType.TYPE_CLASS_PHONE:InputType.TYPE_NULL);
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
            SharedPreferences.Editor editor = mSp.edit();
            editor.putString(AppConfig.USER_NAME,mName_et.getText().toString());
            editor.putString(AppConfig.USEREMAIL,mEmail_et.getText().toString());
            editor.putString(AppConfig.USER_DOB,mDoB_et.getText().toString());
            editor.putString(AppConfig.USER_PHONE,mPhone_et.getText().toString());
            editor.commit();
            setEdittextEnable(false);
            mEditBtn.setVisibility(View.VISIBLE);
            mSaveBtn.setVisibility(View.GONE);
            Toast.makeText(this,"Account has been updated!",Toast.LENGTH_SHORT).show();
        }
    }
}
