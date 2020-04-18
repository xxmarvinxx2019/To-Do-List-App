package com.example.to_do_list_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "to-do-list";
    private static final String TODO_TABLE = "todo_table";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table ="CREATE TABLE "+TODO_TABLE+"(id INTEGER PRIMARY KEY,description TEXT NOT NULL,dateofrecord TEXT NOT NULL,timeofrecord TEXT NOT NULL)";

        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TODO_TABLE);
    }
    public boolean insert(String description,String dateofrecord,String timeofrecord){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("description",description);
        contentValues.put("dateofrecord",dateofrecord);
        contentValues.put("timeofrecord",timeofrecord);

        sqLiteDatabase.insert(TODO_TABLE,null,contentValues);

        return true;
    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM "+TODO_TABLE+" ORDER BY id DESC",new String[]{});
        return data;
    }
    public void deleteTodo(String get_id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TODO_TABLE, "id" + "=" + get_id, null);
    }
}
