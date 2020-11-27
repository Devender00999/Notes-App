package com.aryafacilities.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.annotation.Target;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Notes.db";
    public static final String ID = "ID";
    public static final String TABLE_NAME = "Notes";
    public static final String colTitle = "noteTitle";
    public static final String colText = "noteText";
    public static final String colDate = "noteDate";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NoteTitle TEXT,NoteText TEXT,NoteDate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if Exists "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String noteTitle,String noteText,String noteDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colTitle,noteTitle);
        contentValues.put(colText,noteText);
        contentValues.put(colDate,noteDate);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1){
            return false;
        }
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+ TABLE_NAME,null);
        return res;
    }

    public Integer deleteData(String noteTitle){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"NoteTitle = ?",new String[]{noteTitle});
    }

    public boolean updateDate(String check,String noteTitle,String noteText,String noteDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colTitle,noteTitle);
        contentValues.put(colText,noteText);
        contentValues.put(colDate,noteDate);
        int res =  db.update(TABLE_NAME,contentValues," NoteTitle = ?",new String[]{check});
        if (res ==-1)
            return false;
        else
            return true;
    }
}
