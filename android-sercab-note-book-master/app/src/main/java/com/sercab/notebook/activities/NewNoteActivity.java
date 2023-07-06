package com.sercab.notebook.activities;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sercab.notebook.R;
import com.sercab.notebook.entity.Book;
import com.sercab.notebook.utils.AppUtils;
import com.sercab.notebook.utils.BookDBUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNoteActivity extends Activity implements View.OnClickListener {

    private AppUtils appUtils;

    private final String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));

    private BookDBUtils bookDBUtils;

    private Button give,close,back;

    private EditText notebook,name_book;

    private TextView numWords,editTime;

    private String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        iniUI();

        appUtils = new AppUtils(this);
        bookDBUtils = new BookDBUtils(this);
        data = JSONObject.parseObject(appUtils.readTextFile(appUtils.appFilePath("/key.json"))).getString("username");
    }


    private void iniUI() {

        give = findViewById(R.id.give);
        give.setOnClickListener(this);
        close = findViewById(R.id.close);
        close.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        numWords = findViewById(R.id.numWords);
        editTime = findViewById(R.id.editTime);

        editTime.setText(nowTime);

        name_book = findViewById(R.id.name_book);
        name_book.setHint("此处输入标题");
        notebook = findViewById(R.id.notebook);
        notebook.setHint("点击此处输入内容");
        notebook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                runOnUiThread(()->numWords.setText("总字数: " + s.length()));

            }
        });


    }

    private void insertText(){
        Book book = new Book();
        book.setAuthor(data);
        book.setName(name_book.getText().toString());
        book.setBook(notebook.getText().toString());
        book.setTime(editTime.getText().toString());
        bookDBUtils.insertBook(book);
        finish();
        appUtils.startActivity(this, MainActivity.class);
    }


    @Override
    public void onClick(View v) {

        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        int id = v.getId();
        if (id == R.id.close) {
            notebook.setText(null);
        } else if (id == R.id.back) {
            finish();
            appUtils.startActivity(this, MainActivity.class);
        }else if (id == R.id.give) {
            insertText();
            appUtils.showToast("保存成功");
        }
    }


}