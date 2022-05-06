package com.example.notebook.utils;
import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class utils {
    public static final String TABLE_NAME = "Note";
    public static final String ID_FIELD = "id";
    public static final String CONTENT_FIELD = "content";
    public static final String NOTETIME_FIELD = "notetime";

    public static String getTime(){
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("MM-dd hh:mm");
        return dateFormat.format(new Date());


    }
}
