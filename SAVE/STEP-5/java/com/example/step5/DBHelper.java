package com.example.step5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.step5.Model.Category;
import com.example.step5.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    private final Boolean D = true;

    private static final String DB_NAME = "APP_DB.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_STUDENT = "Students";
    private static final String TABLE_CATEGORY = "Categories";

    private static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE IF NOT EXISTS Students" +
                    "(" +
                    "num TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "imageName TEXT ," +
                    "dept TEXT NOT NULL," +
                    "PRIMARY KEY(num))";

    private static final String CREATE_CATEGORY_TABLE =
            "CREATE TABLE IF NOT EXISTS Categories" +
                    "(" +
                    "grp TEXT NOT NULL," +
                    "code TEXT NOT NULL," +
                    "codeKor TEXT NOT NULL," +
                    "caption TEXT NOT NULL," +
                    "creation_Date TEXT," +
                    "processing_Date TEXT," +
                    "PRIMARY KEY (grp, code))";

    String DROP_TABLE_STUDENT = "DROP TABLE IF EXISTS Students";
    String DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS Categories";

    private final String INPUT_DATA_DEPT1 =
            "INSERT INTO Categories" +
                    "(" +
                    "grp, code, codeKor, caption)" +
                    "VALUES" +
                    "( 'dept', '1001', '컴퓨터소프트웨어과', '선입견충;;')";

    private final String INPUT_DATA_DEPT2 =
            "INSERT INTO Categories" +
                    "(" +
                    "grp, code, codeKor, caption)" +
                    "VALUES" +
                    "( 'dept', '2001', '사이버보안과', '해킹충;;')";

    public void INPUT_DEPT(SQLiteDatabase db){
        db.execSQL(INPUT_DATA_DEPT1);
        db.execSQL(INPUT_DATA_DEPT2);
    }



    public DBHelper(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);

        INPUT_DEPT(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        if(old_v != new_v){
            db.execSQL(DROP_TABLE_STUDENT);
            db.execSQL(DROP_TABLE_CATEGORY);

            onCreate(db);
        }
    }

    public Student select_student(String num){

        Student s = null;
        if(num == null) return s;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_STUDENT, null, "num = ?", new String[]{num},
                            null,null,null);

        c.moveToFirst();

        if(c.getCount() > 0){
            s = new Student();
            s.setNum(c.getString(c.getColumnIndex("num")));
            s.setName(c.getString(c.getColumnIndex("name")));
            s.setPhone(c.getString(c.getColumnIndex("phone")));
            s.setImageName(c.getString(c.getColumnIndex("imageName")));
            s.setDept(c.getString(c.getColumnIndex("dept")));
        }
        c.close();
        return s;
    }

    public Student insert_update(String num, String name, String phone, String dept){

        Student s = select_student(num);

        if(num == null || name == null) return null;

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put("num", num);
        values.put("name", name);
        values.put("phone", phone);
        values.putNull("imageName");
        values.put("dept",dept);

        if(s == null){
            db.insert(TABLE_STUDENT, null, values);
        } else {
            db.update(TABLE_STUDENT, values, "num = ?", new String[]{num});
        }
        db.close();

        s = select_student(num);

        return s;

    }

    public Student insert_update(String num, String name, String phone, String imageName,
                                 String dept){

        Student s = select_student(num);

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put("num", num);
        values.put("name", name);
        values.put("phone", phone);
        values.put("imageName", imageName);
        values.put("dept",dept);

        if(s == null){
            db.insert(TABLE_STUDENT, null, values);
        } else {
            db.update(TABLE_STUDENT, values, "num = ?", new String[]{num});
        }
        db.close();

        s = select_student(num);

        return s;

    }

    public Student saveImageName(String num, String imageName) {

        Student s = select_student(num);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        if (s.getNum() == null) {//not exist
            values.put("num", num);
            values.put("imageName", imageName);
            long n = db.insert(TABLE_STUDENT, null, values);
        } else {//already inserted
            values.put("imageName", imageName);
            int n = db.update(TABLE_STUDENT, values, "num = ?", new String[]{num});
        }
        db.close();

        s = select_student(num);

        return s;
    }

    public boolean delete_student(String num){
        SQLiteDatabase db = this.getWritableDatabase();

        int n = db.delete(TABLE_STUDENT, "num = ?", new String[]{num});

        return (n > 0) ? true : false;
    }


    public List<Category> select_category(){

        List<Category> cgs = new ArrayList<Category>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tableColumns =
                new String[]{
                        "grp", "code", "codeKor", "caption", "creation_Date", "processing_Date"
                };
        Cursor c = db.query(TABLE_CATEGORY, tableColumns,
                null,null,null, null, "grp, code");

        if(c.getCount() > 0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                Category cg = new Category();
                cg.setGrp(c.getString(c.getColumnIndex("grp")));
                cg.setCode(c.getString(c.getColumnIndex("code")));
                cg.setCodeKor(c.getString(c.getColumnIndex("codeKor")));
                cg.setCaption(c.getString(c.getColumnIndex("caption")));
                //cg.setCreation_date(c.getString(c.getColumnIndex("creation_Date")));
                //cg.setProcessing_date(c.getString(c.getColumnIndex("processing_Date")));

                cgs.add(cg);
                c.moveToFirst();
            }
        }
        c.close();
        return cgs;
    }

    public List<Category> select_category(String grp){

        List<Category> cgs = new ArrayList<Category>();

        if(grp == null){
            cgs = select_category();
            return cgs;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_CATEGORY, null, "grp = ?", new String[]{grp},
                            null,null,null);

        if(c.getCount() > 0){
            if(c.moveToFirst()){
                do{
                    Category cg = new Category();
                    cg.setGrp(c.getString(c.getColumnIndex("grp")));
                    cg.setCode(c.getString(c.getColumnIndex("code")));
                    cg.setCodeKor(c.getString(c.getColumnIndex("codeKor")));
                    cg.setCaption(c.getString(c.getColumnIndex("caption")));
                    //cg.setCreation_date(c.getString(c.getColumnIndex("creation_Date")));
                    //cg.setProcessing_date(c.getString(c.getColumnIndex("processing_Date")));
                    cgs.add(cg);
                }while(c.moveToNext());
            }
            c.close();
        } else {
            cgs = select_category();
        }

        return cgs;
    }

    public Category select_category(String grp, String code){

        Category cg = new Category();
        if(grp == null || code == null) return cg;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_CATEGORY, null, "grp = ? and code = ?",
                            new String[]{grp, code}, null,null,null);

        if(c.getCount() > 0){
            c.moveToFirst();
            cg.setGrp(c.getString(c.getColumnIndex("grp")));
            cg.setCode(c.getString(c.getColumnIndex("code")));
            cg.setCodeKor(c.getString(c.getColumnIndex("codeKor")));
            cg.setCaption(c.getString(c.getColumnIndex("caption")));
            //cg.setCreation_date(c.getString(c.getColumnIndex("creation_Date")));
            //cg.setProcessing_date(c.getString(c.getColumnIndex("processing_Date")));
        }
        c.close();
        return cg;
    }

    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }

}
