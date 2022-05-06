package com.example.notebook.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.notebook.RecordActivity;
import com.example.notebook.bean.NotepadBean;
import com.example.notebook.utils.utils;

import java.util.ArrayList;
import java.util.List;


public class MyDBHelper extends SQLiteOpenHelper {


    private static final String name = "notebook.db";
    private static final int version = 1;

    public MyDBHelper(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("mydbhelper", "oncreate succeed!");
        db.execSQL("CREATE TABLE " + utils.TABLE_NAME + "(" + utils.ID_FIELD + " integer PRIMARY KEY," + utils.CONTENT_FIELD + " text," + utils.NOTETIME_FIELD + " text);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public List<NotepadBean> query() {
        List<NotepadBean> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = getReadableDatabase().query(utils.TABLE_NAME, null,
                null, null, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
//                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
//                @SuppressLint("Range") String notetime = cursor.getString(cursor.getColumnIndex("notetime"));
//                list.add(new NotepadBean(id, content, notetime));
//            } while (cursor.moveToNext());
//        }
        if (cursor != null && cursor.moveToLast()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
                @SuppressLint("Range") String notetime = cursor.getString(cursor.getColumnIndex("notetime"));
                list.add(new NotepadBean(id, content, notetime));
            } while (cursor.moveToPrevious());
        }
        return list;
    }

    public boolean add(NotepadBean bean) {
        ContentValues values = new ContentValues();
        values.put(utils.CONTENT_FIELD, bean.getNotepadContent());
        values.put(utils.NOTETIME_FIELD, bean.getNotepadTime());
        long result = getWritableDatabase().insert(utils.TABLE_NAME, null, values);
        // Toast.makeText(RecordActivity.getContext(), "result="+result, Toast.LENGTH_SHORT).show();
        Log.d("cao", String.valueOf(result));
        return result >= 0;
    }

    public boolean delete(String id) {
        int result = getWritableDatabase().delete(utils.TABLE_NAME, "id=?", new String[]{id});
        return result > 0;
    }

    public boolean update(NotepadBean bean) {
        ContentValues values = new ContentValues();
        values.put(utils.CONTENT_FIELD, bean.getNotepadContent());
        values.put(utils.NOTETIME_FIELD, bean.getNotepadTime());
        int result = getWritableDatabase().update(utils.TABLE_NAME, values, "id=?", new String[]{String.valueOf(bean.getId())});
        return result > 0;
    }

}
