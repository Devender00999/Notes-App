package com.aryafacilities.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Notes.db";
    public static final String ID = "ID";
    public static final String TABLE_NAME = "Notes";
    public static final String noteTitle = "noteTitle";
    public static final String noteText = "noteText";
    public static final String noteDate = "noteDate";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NoteText TEXT,NoteTitle TEXT,NoteDate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if Exists "+TABLE_NAME);
        onCreate(db);
    }
}
