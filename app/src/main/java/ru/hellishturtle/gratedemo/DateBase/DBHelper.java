package ru.hellishturtle.gratedemo.DateBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    final String LOG_TAG = "myLogs";

    private static final String DATABASE_NAME = "ratesDB";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "date,"
                + "sour,"
                + "usd,"
                + "eur,"
                + "gbp" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}