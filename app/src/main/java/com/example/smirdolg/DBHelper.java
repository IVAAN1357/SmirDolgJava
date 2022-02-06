package com.example.smirdolg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "debtsDb";
    public static final String TABLE_DEBTS = "debts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SUM = "sum";
    public static final String KEY_DATE = "date";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_DEBTS + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_SUM + " integer," + KEY_DATE + " text" +")");
        Log.d("MYDEBUG", "create table " + TABLE_DEBTS + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_SUM + " integer," + KEY_DATE + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_DEBTS);

        onCreate(db);
    }
}
