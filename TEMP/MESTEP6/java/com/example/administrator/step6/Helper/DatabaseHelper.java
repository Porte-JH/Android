package com.example.administrator.step6.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.example.app2student36.app.AppDate;
import info.example.app2student36.model.Code;
import info.example.app2student36.model.Student;

public class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    // Database Info
    private static final String DATABASE_NAME = "app2student36.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_CODES = "codes";

    private AppDate appDate = new AppDate();

    //코드 테이블 생성
    private static final String CREATE_CODES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CODES +
                    "(" +
                    "grp TEXT NOT NULL," +
                    "code TEXT NOT NULL," +
                    "codeKor TEXT NULL," +
                    "desc TEXT NULL," +
                    "creationDate TEXT NULL," +
                    "processingDate TEXT NULL," +
                    "PRIMARY KEY(grp,code)" +
                    ")";
    //학생 테이블 생성
    private static final String CREATE_STUDENTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STUDENTS +
                    "(" +
                    "hakbun TEXT NOT NULL," +
                    "name TEXT NULL," +
                    "phone TEXT NULL," +
                    "imageName TEXT NULL," +
                    "hakgoa TEXT NULL," +
                    "PRIMARY KEY(hakbun)" +
                    ")";

    private static final String DROP_CODES_TABLE = "drop table if exists " + TABLE_CODES;
    private static final String DROP_STUDENTS_TABLE = "drop table if exists " + TABLE_CODES;

    private final String INIT_CODE_UNIV_HAKGOA1 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creationDate" +
                    ")" +
                    "VALUES" +
                    "(" +
                    "'hakgoa','1001','컴퓨터소프트웨어','builtin','" + appDate.getTodayString() + "'" +
                    ");";

    private final String INIT_CODE_UNIV_HAKGOA2 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creationDate" +
                    ")" +
                    "VALUES" +
                    "(" +
                    "'hakgoa','2001','실내디자인','builtin','" + appDate.getTodayString() + "'" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_STUDENTS_TABLE);
        db.execSQL(CREATE_CODES_TABLE);

        initializeCodes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {

            db.execSQL(DROP_STUDENTS_TABLE);
            db.execSQL(DROP_CODES_TABLE);
            onCreate(db);
        }
    }

    // ------------------- "codes" table initializes ----------------//

    public void initializeCodes(SQLiteDatabase db) {

        db.execSQL(INIT_CODE_UNIV_HAKGOA1);
        db.execSQL(INIT_CODE_UNIV_HAKGOA2);
    }

    // ------------------- "students" table methods ----------------//

    //데이터 저장
    public Student insertOrUpdateStudent(String hakbun, String name, String phone, String hakgoa) {

        if ((hakbun == null) || (name == null)) return null;

        Student s = selectStudent(hakbun);
        SQLiteDatabase db = this.getWritableDatabase();
        AppDate appDate = new AppDate();
        ContentValues values = new ContentValues();
        values.put("hakbun", hakbun);
        values.put("name", name);
        values.put("phone", phone);
        values.putNull("imageName");
        values.put("hakgoa", hakgoa);

        if (s == null) {//not exist

            long n = db.insert(TABLE_STUDENTS, null, values);
            if (D) Log.i(TAG, "insert n: " + n);
        }
        else {//already inserted

            int n = db.update(
                    TABLE_STUDENTS, values, "hakbun = ?",
                    new String[]{hakbun}
            );
            if (D) Log.i(TAG, "update n: " + n);
        }
        db.close();

        s = selectStudent(hakbun);

        return s;
    }
    //데이터 저장
    public Student insertOrUpdateStudent(String hakbun, String name, String phone,
                                         String imageName, String hakgoa) {

        if ((hakbun == null) || (name == null)) return null;

        Student s = selectStudent(hakbun);
        SQLiteDatabase db = this.getWritableDatabase();
        AppDate appDate = new AppDate();
        ContentValues values = new ContentValues();
        values.put("hakbun", hakbun);
        values.put("name", name);
        values.put("phone", phone);
        values.put("imageName", imageName);
        values.put("hakgoa", hakgoa);

        if (s == null) {//not exist

            long n = db.insert(TABLE_STUDENTS, null, values);
            if (D) Log.i(TAG, "insert n: " + n);
        }
        else {//already inserted

            int n = db.update(
                    TABLE_STUDENTS, values, "hakbun = ?",
                    new String[]{hakbun}
            );
            if (D) Log.i(TAG, "update n: " + n);
        }
        db.close();

        s = selectStudent(hakbun);

        return s;
    }

    //데이터 저장
    public Student saveImageName(String hakbun, String imageName) {

        Student p = selectStudent(hakbun);

        SQLiteDatabase db = this.getWritableDatabase();
        AppDate appDate = new AppDate();
        ContentValues values = null;

        if (p.getHakbun() == null) {//not exist
            values = new ContentValues();
            values.put("hakbun", hakbun);
            values.put("imageName", imageName);

            long n = db.insert(TABLE_STUDENTS, null, values);
        } else {//already inserted
            values = new ContentValues();
            values.put("imageName", imageName);

            int n = db.update(
                    TABLE_STUDENTS, values, "hakbun = ?",
                    new String[]{hakbun}
            );
        }
        db.close();

        p = selectStudent(hakbun);

        return p;
    }

    public Student selectStudent(String hakbun) {

        if (D) Log.i(TAG, "select("+hakbun+")");

        Student s = null;
        if (hakbun == null) return s;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =
                db.query(
                        TABLE_STUDENTS, null, "hakbun = ?", new String[]{hakbun},
                        null, null, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            s = new Student();
            s.setHakbun(c.getString(c.getColumnIndex("hakbun")));
            s.setName(c.getString(c.getColumnIndex("name")));
            s.setPhone(c.getString(c.getColumnIndex("phone")));
            s.setImageName(c.getString(c.getColumnIndex("imageName")));
            s.setHakgoa(c.getString(c.getColumnIndex("hakgoa")));
            if (D) Log.i(TAG, "s.toString(): "+s.toString());
        }
        c.close();

        return s;
    }

    public List<Student> selectStudent() {

        List<Student> students = new ArrayList<Student>();

        String orderBy = "hakbun";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_STUDENTS, null, null, null, null, null, orderBy);
        c.moveToFirst();
        if (c.getCount() > 0) {
            while (!c.isAfterLast()) {
                Student s = new Student();
                s.setHakbun(c.getString(c.getColumnIndex("hakbun")));
                s.setName(c.getString(c.getColumnIndex("name")));
                s.setPhone(c.getString(c.getColumnIndex("phone")));
                s.setImageName(c.getString(c.getColumnIndex("imageName")));
                s.setHakgoa(c.getString(c.getColumnIndex("hakgoa")));

                students.add(s);
                c.moveToNext();
                if (D) Log.i(TAG, s.toString());
            }
        }
        c.close();

        //if (D) Log.i(TAG, students.toString());
        return students;
    }

    //데이터 삭제
    public boolean deleteStudent(String hakbun) {

        SQLiteDatabase db = this.getWritableDatabase();
        //1 for success
        int n = db.delete(TABLE_STUDENTS, "hakbun = ?", new String[]{hakbun});

        return (n > 0) ? true : false;
    }

    public boolean deleteStudent() {

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        int n = db.delete(TABLE_STUDENTS, null, null);

        return (n > 0) ? true : false;
    }

    // ------------------- "codes" table methods ----------------//

    public Code selectCode(String in_grp, String in_code) {

        Code code = new Code();
        if (in_grp == null || in_code == null) return code;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =
                db.query(TABLE_CODES, null, "grp = ? and code = ?",
                        new String[]{in_grp, in_code}, null, null, null
                );
        if (c.getCount() > 0) {
            c.moveToFirst();
            code.setGrp(c.getString(c.getColumnIndex("grp")));
            code.setCode(c.getString(c.getColumnIndex("code")));
            code.setCodeKor(c.getString(c.getColumnIndex("codeKor")));
            code.setDesc(c.getString(c.getColumnIndex("desc")));
            code.setCreationDate(c.getString(c.getColumnIndex("creationDate")));
            code.setProcessingDate(c.getString(c.getColumnIndex("processingDate")));
        }
        c.close();

        return code;
    }

    public List<Code> selectCode(String grp) {

        List<Code> codes = new ArrayList<Code>();

        if (grp == null) {
            codes = selectCode();
            return codes;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =
                db.query(TABLE_CODES, null, "grp = ?",
                        new String[]{grp}, null, null, null);
        if (D) Log.i(TAG, "c.getCount(): " + c.getCount());
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Code code = new Code();
                    code.setGrp(c.getString(c.getColumnIndex("grp")));
                    code.setCode(c.getString(c.getColumnIndex("code")));
                    code.setCodeKor(c.getString(c.getColumnIndex("codeKor")));
                    code.setDesc(c.getString(c.getColumnIndex("desc")));
                    code.setCreationDate(c.getString(c.getColumnIndex("creationDate")));
                    code.setProcessingDate(c.getString(c.getColumnIndex("processingDate")));
                    codes.add(code);
                } while (c.moveToNext());
            }
            c.close();
        } else {
            codes = selectCode();
        }

        return codes;
    }

    public List<Code> selectCode() {

        List<Code> codes = new ArrayList<Code>();

        //http://stackoverflow.com/questions/10600670/sqlitedatabase-query-method
        SQLiteDatabase db = this.getReadableDatabase();
        String orderBy = "grp,code";
        String[] tableColumns =
                new String[]{
                        "grp", "code", "codeKor", "desc", "creationDate", "processingDate"
                };
        Cursor c = db.query(TABLE_CODES, tableColumns, null, null, null, null, orderBy);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Code code = new Code();
                code.setGrp(c.getString(c.getColumnIndex("grp")));
                code.setCode(c.getString(c.getColumnIndex("code")));
                code.setCodeKor(c.getString(c.getColumnIndex("codeKor")));
                code.setDesc(c.getString(c.getColumnIndex("desc")));
                code.setCreationDate(c.getString(c.getColumnIndex("creationDate")));
                code.setProcessingDate(c.getString(c.getColumnIndex("processingDate")));

                codes.add(code);
                c.moveToNext();
                if (D) Log.i(TAG, "Code: " + code.toString());
            }
        }
        c.close();

        return codes;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}