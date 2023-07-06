package com.sercab.notebook.activities;

import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.sercab.notebook.R;
import com.sercab.notebook.adapter.rcBookListAdapter;
import com.sercab.notebook.entity.Book;
import com.sercab.notebook.utils.AppUtils;
import com.sercab.notebook.utils.BookDBUtils;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private AppUtils appUtils;
    private BookDBUtils bookDBUtils;

    private ListView bookList;

    private Button add,logout;

    private String data;

    private TextView tag,nowUser;

    private rcBookListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appUtils = new AppUtils(this);
        bookDBUtils = new BookDBUtils(this);

        bookList = findViewById(R.id.bookList);
        nowUser = findViewById(R.id.nowUser);
        tag = findViewById(R.id.tag);
        add = findViewById(R.id.add);
        add.setOnClickListener(this);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        data = JSONObject.parseObject(appUtils.readTextFile(appUtils.appFilePath("/key.json"))).getString("username");


        loadData();
        nowUser.setText("当前用户: " + data);

    }

    private void loadData(){
        List<Book> list = bookDBUtils.getBooksByAuthor(data);
        if (list != null) {
            listAdapter = new rcBookListAdapter(this, list, (position, book) -> {
                appUtils.vibrate(getApplicationContext(),20);
                appUtils.startActivityTakeString(this, UpdateNoteActivity.class,"Id",String.valueOf(book.getId()));
            });
            bookList.setAdapter(listAdapter);
            tag.setVisibility(View.INVISIBLE);
        }else {
            appUtils.showToast("您暂时没有笔记哦");
            tag.setVisibility(View.VISIBLE);
        }
    }


    private void userLogout(){
        JSONObject data = new JSONObject();
        data.put("username", "");
        data.put("password", "");
        appUtils.saveTextFile(appUtils.appFilePath("/key.json"), data.toString());
        finish();
        appUtils.startActivity(this,LoginActivity.class);
    }


    @Override
    public void onClick(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        int id = v.getId();
        if (id == R.id.add) {
            finish();
            appUtils.startActivity(this, NewNoteActivity.class);
        } else if (id == R.id.logout) {
            userLogout();
            appUtils.showToast("注销登录成功");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }


}

