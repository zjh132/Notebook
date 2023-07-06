package com.sercab.notebook.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sercab.notebook.R;
import com.sercab.notebook.entity.Book;
import com.sercab.notebook.utils.AppUtils;
import com.sercab.notebook.utils.BookDBUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UpdateNoteActivity extends Activity implements View.OnClickListener {

    private AppUtils appUtils;

    private final String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));

    private BookDBUtils bookDBUtils;

    private Button give, delete, back;

    private EditText notebook, name_book;

    private TextView numWords, editTime, updateName;

    private String data, bookID;
    private AlertDialog deleteDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        iniUI();
        appUtils = new AppUtils(this);
        bookDBUtils = new BookDBUtils(this);

        data = JSONObject.parseObject(appUtils.readTextFile(appUtils.appFilePath("/key.json"))).getString("username");
        bookID = getIntent().getStringExtra("Id");
        List<Book> bookList = bookDBUtils.getBooksById(bookID);
        updateName.setText("正在修改: " + bookList.get(0).getName());
        name_book.setText(bookList.get(0).getName());
        notebook.setText(bookList.get(0).getBook());

    }


    private void iniUI() {

        give = findViewById(R.id.give);
        give.setOnClickListener(this);
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        numWords = findViewById(R.id.numWords);
        editTime = findViewById(R.id.editTime);
        updateName = findViewById(R.id.updateName);

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
                runOnUiThread(() -> numWords.setText("总字数: " + s.length()));

            }
        });


    }


    private void updateText() {
        Book book = new Book();
        book.setId(Integer.valueOf(bookID));
        book.setAuthor(data);
        book.setName(name_book.getText().toString());
        book.setBook(notebook.getText().toString());
        book.setTime(editTime.getText().toString());
        bookDBUtils.updateBook(book);
        finish();
        appUtils.startActivity(this, MainActivity.class);
    }


    public void showDeleteDialog() {
        LinearLayout Item = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_delete, null);
        Button yes = Item.findViewById(R.id.yes);
        Button not = Item.findViewById(R.id.not);
        yes.setOnClickListener(v -> {
            appUtils.vibrate(this,20);
            bookDBUtils.deleteBook(Integer.parseInt(bookID));
            appUtils.showToast("删除成功");
            finish();

        });
        not.setOnClickListener(v -> {
            appUtils.vibrate(this,20);
            deleteDialog.cancel();
        });

        deleteDialog = new AlertDialog.Builder(this).setView(Item).create();
        deleteDialog.show();
    }


    @Override
    public void onClick(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        int id = v.getId();
        if (id == R.id.delete) {
            showDeleteDialog();
        } else if (id == R.id.back) {
            finish();
            appUtils.startActivity(this, MainActivity.class);
        } else if (id == R.id.give) {
            updateText();
            appUtils.showToast("修改成功");
        }
    }


}