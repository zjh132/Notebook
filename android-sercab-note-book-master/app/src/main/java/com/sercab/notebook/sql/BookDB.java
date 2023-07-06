package com.sercab.notebook.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "book.db";
    private static final int DATABASE_VERSION = 1;

    public BookDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE book (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "author TEXT, name TEXT, book TEXT, time TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
