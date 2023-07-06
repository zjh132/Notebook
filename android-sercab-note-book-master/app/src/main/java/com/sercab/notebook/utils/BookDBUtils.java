package com.sercab.notebook.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sercab.notebook.config.Result;
import com.sercab.notebook.entity.Book;
import com.sercab.notebook.sql.BookDB;

import java.util.ArrayList;
import java.util.List;

public class BookDBUtils {

    private BookDB bookDB;

    public BookDBUtils(Context context) {
        bookDB = new BookDB(context);
    }

    public Result insertBook(Book book) {

        SQLiteDatabase db = bookDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("author",book.getAuthor());
        values.put("name", book.getName());
        values.put("book", book.getBook());
        values.put("time", book.getTime());

        return Result.success(db.insert("book", null, values));
    }

    public Result updateBook(Book book) {

        SQLiteDatabase db = bookDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("author",book.getAuthor());
        values.put("name", book.getName());
        values.put("book", book.getBook());
        values.put("time", book.getTime());

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(book.getId())};

        return Result.success(db.update("book", values, selection, selectionArgs));
    }

    public Result deleteBook(int Id) {

        SQLiteDatabase db = bookDB.getWritableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(Id)};

        return Result.success(db.delete("book", selection, selectionArgs));
    }


    public List<Book> getBooksByAuthor(String author) {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = bookDB.getReadableDatabase();
        String[] columns = {"id", "author", "name", "book", "time"};
        String selection = "author=?";
        String[] selectionArgs = {author};
        Cursor cursor = db.query("book", columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow("author")));
                book.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                book.setBook(cursor.getString(cursor.getColumnIndexOrThrow("book")));
                book.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
                bookList.add(book);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            return null;
        }
        db.close();
        return bookList;
    }


    public List<Book> getBooksById(String id) {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = bookDB.getReadableDatabase();
        String[] columns = {"id", "author", "name", "book", "time"};
        String selection = "id=?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query("book", columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow("author")));
                book.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                book.setBook(cursor.getString(cursor.getColumnIndexOrThrow("book")));
                book.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
                bookList.add(book);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            return null;
        }
        db.close();
        return bookList;
    }


}

