package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    private static final String DB_NAME = "DB_NAME.db";
    private static final int DB_VER = 1;
    private static final String TABLE_NAME = "Students";
    private static final String TABLE_CODE = "Codes";



    private static final String CREATE_TABLE_STUDENTS =
            "CREATE TABLE IF NOT EXISTS Students" +
                    " ( " +
                    "num TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "PRIMARY KEY(num))";

    private static final String CREATE_TABLE_CODES =
            "CREATE TABLE IF NOT EXISTS Codes" +
                    " ( " +
                    "grp TEXT NOT NULL," +
                    "code TEXT NOT NULL," +
                    "code_kor TEXT NOT NULL," +
                    "desc TEXT NOT NULL," +
                    "creation_date TEXT NOT NULL," +
                    "processing_date TEXT NOT NULL," +
                    "PRIMARY KEY(grp, code))";

    String DROP_TABLE_STUDENTS = "DROP TABLE IF EXISTS Students";
    String DROP_TABLE_CODES = "DROP TABLE IF EXISTS Codes";

    private final String CREATE_STUCLASS_1 =
            "INSERT INTO Codes" +
                    "(" +
                    "grp, code, code_kor, desc, creatron_date" + " ) " +
                    "VALUE" +
                    " ( " + "'stu_class', '1001, '컴퓨터소프트웨어과', 'initial','" + appDate.getTodayString() + "'" + ")";

    private final String CREATE_STUCLASS_2 =
            "INSERT INTO Codes" +
                    "(" +
                    "grp, code, code_kor, desc, creatron_date" + " ) " +
                    "VALUE" +
                    " ( " + "'stu_class', '2001, '멀티미디어과', 'initial','" + appDate.getTodayString() + "'" + ")";


    public DBHelper(Context c) {
        super(c, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_CODES);

        init_codes(db);
    }

    public void init_codes(SQLiteDatabase db){
        db.execSQL(CREATE_STUCLASS_1);
        db.execSQL(CREATE_STUCLASS_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        if(old_v != new_v){
            db.execSQL(DROP_TABLE_STUDENTS);
            db.execSQL(DROP_TABLE_CODES);
            onCreate(db);
        }
    }

    public Student Select(String num){
        Student s = null;
        if(num == null){
            return null;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_NAME, null, "num = ?", new String[]{num},
                null,null,null);

        c.moveToFirst();

        if(c.getCount() > 0){
            s = new Student();
            s.setNum(c.getString(c.getColumnIndex("num")));
            s.setName(c.getString(c.getColumnIndex("name")));
            s.setPhone(c.getString(c.getColumnIndex("phone")));

            c.close();
        }
        return s;
    }

    public Student InsertUpdate(String num, String name, String phone){
        Select(num);
        if(num == null || name == null){
            return null;
        }
        Student s = Select(num);

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put("num", num);
        values.put("name", name);
        values.put("phone", phone);

        if(s == null){
            db.insert(TABLE_NAME,null,values);

        } else {
            db.update(TABLE_NAME, values, "num = ?", new String[]{num});
        }
        s = Select(num);
        return s;
    }

    public boolean delete(String num){
        Student s = Select(num);

        SQLiteDatabase db = this.getWritableDatabase();
        int n = db.delete(TABLE_NAME, "num = ?", new String[]{num});

        return (n > 0 ? true : false);
    }

    public Code select_codes(String grp, String code){
        Code co = null;
        if(grp == null || code == null){
            return null;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_CODE,null,"grp = ? and code = ?", new String[]{grp,code},
                            null,null,null);

        c.moveToFirst();

        if(c.getCount() > 0){
            co = new Code();
            co.setGrp(c.getString(c.getColumnIndex("grp")));
            co.setCode(c.getString(c.getColumnIndex("code")));
            co.setCode_kor(c.getString(c.getColumnIndex("code_kor")));
            co.setDesc(c.getString(c.getColumnIndex("desc")));
            co.setCreation_date(c.getString(c.getColumnIndex("creation_date")));
            co.setProcessing_date(c.getString(c.getColumnIndex("processing_date")));

            c.close();
        }
        return co;
    }

    public List<Code> select_codes(String grp){

        List<Code> codes = new ArrayList<Code>();
        Code co = null;
        if(grp == null){
            return null;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_CODE,null,"grp = ?", new String[]{grp},
                null,null,null);

        c.moveToFirst();

        if(c.getCount() > 0){
            if(c.moveToFirst()){
                do{
                    co = new Code();
                    co.setGrp(c.getString(c.getColumnIndex("grp")));
                    co.setCode(c.getString(c.getColumnIndex("code")));
                    co.setCode_kor(c.getString(c.getColumnIndex("code_kor")));
                    co.setDesc(c.getString(c.getColumnIndex("desc")));
                    co.setCreation_date(c.getString(c.getColumnIndex("creation_date")));
                    co.setProcessing_date(c.getString(c.getColumnIndex("processing_date")));

                    codes.add(co);
                }while(c.moveToFirst());
            }
        }

        c.close();
        return codes;

    }


    public void CloseDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }
}
