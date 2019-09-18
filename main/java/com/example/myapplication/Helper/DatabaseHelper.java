package com.example.myapplication.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.Model.Student;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG  = getClass().getSimpleName();
    private final boolean D = true;
    // 디버깅을 위함;

    //database

    private static final String DATABASE_NAME = "appstudent32.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENTS ="students";

    private static final String CREATE_STUDENTS_TABLE =
            "CREATE TABLE IF NOT EXISTS students" +
                    "(" +
                    "num TEXT NOT NULL," +
                    "name TEXT NULL," +
                    "phone TEXT NULL," +
                    "PRIMARY KEY(num)" +
                    ")";

    String DROP_STUDENT_TABLE =
            "drop table if exists students";

    public DatabaseHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    } //해당 메소드의 부모 객체인 SQLiteOpenHelper의 요소를 가져옴. (상속 관계)

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL(DROP_STUDENT_TABLE);
            onCreate(db);
        }
    }

    public Student insertOrUpdate(String num, String name, String phone){
        select(num);
        if((num == null) || (name == null)){
            return null;
        }

    }

    private Student select(String num) {
        Student s = null;
        if(num == null){
            return null;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        // 변경 불가능한 상태로 데이터를 가져옴.
        Cursor c = db.query(TABLE_STUDENTS, null,"hakbun = ?", new String[]{num},
                null,null,null);
        // null = *
        // cursor: 데이터를 조회할때 임시로 저장하기 위한 곳.

        c.moveToFirst();
        // ????????????
        if(c.getCount() > 0){
            s = new Student();
            s.setNum(c.getString(c.getColumnIndex("num")));
            //열의 이름이 num인 것을 string형으로 커서 c를 통해 가져와 Student의 num에 값을 넣어준다.
            s.setName(c.getString(c.getColumnIndex("name")));
            s.setPhone(c.getString(c.getColumnIndex("phone")));
            if(D){
                Log.i(TAG, "s.tostring: " + s.toString());
            } // ???????????????????
        }
    }
}
