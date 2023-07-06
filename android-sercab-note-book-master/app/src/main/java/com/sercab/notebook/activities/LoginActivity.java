package com.sercab.notebook.activities;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.sercab.notebook.R;
import com.sercab.notebook.utils.AppUtils;
import com.sercab.notebook.utils.UserDBUtils;


public class LoginActivity extends Activity implements View.OnClickListener {

    private Button login, register;
    private AppUtils appUtils;
    private UserDBUtils userDbUtils;

    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniUI();
        appUtils = new AppUtils(this);
        userDbUtils = new UserDBUtils(this);

        tokenLogin();

    }

    private void iniUI() {

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);

    }

    private void tokenLogin(){
        if(appUtils.isFileExists(appUtils.appFilePath("/key.json"))) {
            JSONObject jsonObject = JSONObject.parseObject(appUtils.readTextFile(appUtils.appFilePath("/key.json")));
            String token_username = jsonObject.getString("username");
            String token_password = jsonObject.getString("password");
            Boolean Yes = (Boolean) userDbUtils.login(token_username, token_password).getData();
            if (Yes) {
                appUtils.showToast("登录成功");
                appUtils.startActivity(this, MainActivity.class);
            } else {
                appUtils.showToast("令牌失效,请重新登录!");
            }
        }else {
            appUtils.showToast("令牌失效,请重新登录!");
        }
    }

    private void loginUser() {
        Boolean Yes = (Boolean) userDbUtils.login(username.getText().toString(), password.getText().toString()).getData();
        if (!TextUtils.isEmpty(username.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {
            if (Yes) {
                appUtils.showToast("登录成功");
                JSONObject data = new JSONObject();
                data.put("username", username.getText().toString());
                data.put("password", password.getText().toString());
                appUtils.saveTextFile(appUtils.appFilePath("/key.json"), data.toString());
                appUtils.startActivity(this, MainActivity.class);
            } else {
                appUtils.showToast("登录失败,请检查用户名或密码是否正确!");
            }
        }
    }


    @Override
    public void onClick(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        int id = v.getId();
        if (id == R.id.login) {
            loginUser();
        } else if (id == R.id.register) {
            appUtils.startActivity(this, RegisterActivity.class);
        }
    }
}