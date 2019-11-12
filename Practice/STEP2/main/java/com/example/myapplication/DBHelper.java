package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    private static final String DB_NAME = "DBHELPER.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Students";

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS Students" +
                    "(" +
                    "num TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "PRIMARY KEY(num)" + ")";

    String DROP_TABLE = "DROP TABLE IF EXISTS Students";




    public DBHelper(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        if(old_v != new_v){
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }
    }

    public Student select(String num){

        Student s = null;

        if(num == null){
            return null;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_NAME, null , "num = ?", new String[]{num},
                null, null, null);

        c.moveToFirst();

        if(c.getCount() > 0){

            s = new Student();

            s.setNum(c.getString(c.getColumnIndex("num")));
            s.setName(c.getString(c.getColumnIndex("name")));
            s.setPhone(c.getString(c.getColumnIndex("phone")));

            if(D){
                Log.i(TAG, "s.toString(): " + "toString()");
            }
            c.close();
        }
        return s;
    }

    public Student InsertORUpdate(String num, String name, String phone){

        select(num);
        if((num == null) || (name == null)){
            return null;
        }

        Student s = select(num);

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put("num", num);
        values.put("name", name);
        values.put("phone", phone);

        ////////////////////////////////////////////////////////////////////////////////////////////
        if(s == null){
            long n = db.insert(TABLE_NAME,null,values);
            if(D){
                Log.i(TAG, "INSERT: " + n);
            }
        } else {
            int n =db.update(TABLE_NAME,null,"num = ?", new String[]{num});
            if(D){
                Log.i(TAG, "UPDATE: " + n);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        s = select(num);
        return s;


    }

    public void CloseDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }
}
