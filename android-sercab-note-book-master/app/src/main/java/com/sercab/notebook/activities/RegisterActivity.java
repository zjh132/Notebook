package com.sercab.notebook.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sercab.notebook.R;
import com.sercab.notebook.utils.AppUtils;
import com.sercab.notebook.utils.UserDBUtils;


public class RegisterActivity extends Activity implements View.OnClickListener {


    private Button back_login, register_this;
    private EditText username,password,password_again;
    private AppUtils appUtils;

    private UserDBUtils userDbUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        iniUI();
        appUtils = new AppUtils(this);
        userDbUtils = new UserDBUtils(this);

    }

    private void iniUI() {

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        password_again = findViewById(R.id.password_again);

        back_login = findViewById(R.id.back_login);
        back_login.setOnClickListener(this);
        register_this = findViewById(R.id.register_this);
        register_this.setOnClickListener(this);
    }

    private void registerUser(){
        if(!TextUtils.isEmpty(username.getText().toString())&&!TextUtils.isEmpty(password.getText().toString())&&!TextUtils.isEmpty(password_again.getText().toString())){
            if(password.getText().toString().equals(password_again.getText().toString())){
                if(userDbUtils.register(username.getText().toString(),password.getText().toString()).getCode().equals("200")){
                    appUtils.showToast("注册成功!");
                    appUtils.startActivity(this, LoginActivity.class);
                }else {
                    appUtils.showToast("注册失败");
                }
            }else {
                appUtils.showToast("您两次输入的密码不一致,请重新输入");
            }
        }else {
            appUtils.showToast("不可为空!");
        }
    }

    @Override
    public void onClick(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        int id = v.getId();
        if (id == R.id.back_login) {
            finish();
            appUtils.startActivity(this, LoginActivity.class);
        } else if (id == R.id.register_this) {
            registerUser();
        }
    }

}