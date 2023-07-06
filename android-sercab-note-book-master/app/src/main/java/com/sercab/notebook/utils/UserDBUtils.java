package com.sercab.notebook.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sercab.notebook.config.Result;
import com.sercab.notebook.sql.UserDB;

public class UserDBUtils {

    private UserDB userDB;

    public UserDBUtils(Context context) {
        userDB = new UserDB(context);
    }

    public Result login(String account, String password) {
        SQLiteDatabase db = userDB.getReadableDatabase();

        String[] projection = {"id"};
        String selection = "account = ? AND password = ?";
        String[] selectionArgs = {account, password};

        Cursor cursor = db.query(
                "user",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean loggedIn = cursor.getCount() > 0;
        cursor.close();
        return Result.success(loggedIn);
    }


    public Result register(String account, String password) {
        // 检查账号是否已存在
        if ((Boolean) isAccountExists(account).getData()) {
            return Result.error();  // 账号已存在，注册失败
        }

        SQLiteDatabase db = userDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("account", account);
        values.put("password", password);

        long newRowId = db.insert("user", null, values);

        return Result.success(newRowId != -1);
    }

    private Result isAccountExists(String account) {
        SQLiteDatabase db = userDB.getReadableDatabase();

        String[] projection = {"account"};
        String selection = "account = ?";
        String[] selectionArgs = {account};

        Cursor cursor = db.query(
                "user",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        return Result.success(exists);
    }

}

