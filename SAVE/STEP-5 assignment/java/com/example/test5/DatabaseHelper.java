package com.example.test5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.test5.App.AppDate;
import com.example.test5.Model.Code;
import com.example.test5.Model.Student;

import java.util.ArrayList;
import java.util.List;



public class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    // Database Info
    private static final String DATABASE_NAME = "app2student35.db";
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
                    "codeKor TEXT NOT NULL," +
                    "desc TEXT NULL," +
                    "creation_date TEXT NULL," +
                    "processing_date TEXT NULL," +
                    "PRIMARY KEY(grp,code)" +
                    ")";
    //학생 테이블 생성
    private static final String CREATE_STUDENTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STUDENTS +
                    "(" +
                    "hakbun TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "imageName TEXT ," +
                    "hakgoa TEXT NOT NULL," +
                    "grade TEXT NOT NULL," +
                    "classes TEXT NOT NULL," +
                    "status TEXT NOT NULL," +
                    "mento TEXT NOT NULL," +
                    "PRIMARY KEY(hakbun)" +
                    ")";

    private static final String DROP_CODES_TABLE = "drop table if exists " + TABLE_CODES;
    private static final String DROP_STUDENTS_TABLE = "drop table if exists " + TABLE_CODES;

    private final String INIT_CODE_UNIV_HAKGOA1 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" +
                    ")" +
                    "VALUES" +
                    "(" +
                    "'hakgoa','1001','컴퓨터소프트웨어','builtin','" + appDate.getTodayString() + "'" +
                    ");";

    private final String INIT_CODE_UNIV_HAKGOA2 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" +
                    ")" +
                    "VALUES" +
                    "(" +
                    "'hakgoa','2001','실내디자인','builtin','" + appDate.getTodayString() + "'" +
                    ");";

    private final String INPUT_DATA_GRADE_1 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'grade', '1', '1학년','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_GRADE_2 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'grade', '2', '2학년','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_GRADE_3 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'grade', '3', '3학년','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_GRADE_4 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'grade', '4', '4학년','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_CLASS_1 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'classes', 'a', 'A반','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_CLASS_2 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'classes', 'b', 'B반','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_STATUS_1 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'status', '1', '재학','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_STATUS_2 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'status', '2', '휴학','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_STATUS_3 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'status', '3', '졸업','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_STATUS_4 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'status', '4', '자퇴','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_MENTO_1 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'mento', '1', '이형일','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_MENTO_2 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'mento', '2', '김인범','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    private final String INPUT_DATA_MENTO_3 =
            "INSERT INTO " + TABLE_CODES +
                    "(" +
                    "grp,code,codeKor,desc,creation_date" + ")" +
                    "VALUES" +
                    "(" + "'mento', '3', '김재생','builtin','" + appDate.getTodayString() + "'" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_STUDENTS_TABLE);
        db.execSQL(CREATE_CODES_TABLE);

        initializeCodes(db);
        INPUT_MENTO(db);
        INPUT_STATUS(db);
        INPUT_CLASS(db);
        INPUT_GRADE(db);
    }



    public void INPUT_GRADE(SQLiteDatabase db){
        db.execSQL(INPUT_DATA_GRADE_1);
        db.execSQL(INPUT_DATA_GRADE_2);
        db.execSQL(INPUT_DATA_GRADE_3);
        db.execSQL(INPUT_DATA_GRADE_4);
    }

    public void INPUT_CLASS(SQLiteDatabase db){
        db.execSQL(INPUT_DATA_CLASS_1);
        db.execSQL(INPUT_DATA_CLASS_2);
    }

    public void INPUT_STATUS(SQLiteDatabase db){
        db.execSQL(INPUT_DATA_STATUS_1);
        db.execSQL(INPUT_DATA_STATUS_2);
        db.execSQL(INPUT_DATA_STATUS_3);
        db.execSQL(INPUT_DATA_STATUS_4);
    }

    public void INPUT_MENTO(SQLiteDatabase db){
        db.execSQL(INPUT_DATA_MENTO_1);
        db.execSQL(INPUT_DATA_MENTO_2);
        db.execSQL(INPUT_DATA_MENTO_3);
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
    public Student insertOrUpdateStudent(String hakbun, String name, String phone, String hakgoa,
                                         String grade, String classes, String status, String mento) {

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
        values.put("grade", grade);
        values.put("classes", classes);
        values.put("status", status);
        values.put("mento", mento);

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
                                         String imageName, String hakgoa,
                                         String grade, String classes, String status, String mento) {

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
        values.put("grade", grade);
        values.put("classes", classes);
        values.put("status", status);
        values.put("mento", mento);

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
            s.setGrade(c.getString(c.getColumnIndex("grade")));
            s.setClasses(c.getString(c.getColumnIndex("classes")));
            s.setStatus(c.getString(c.getColumnIndex("status")));
            s.setMento(c.getString(c.getColumnIndex("mento")));
            if (D) Log.i(TAG, "s.toString(): "+s.toString());
        }
        c.close();

        return s;
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
            code.setCreation_date(c.getString(c.getColumnIndex("creation_date")));
            code.setProcessing_date(c.getString(c.getColumnIndex("processing_date")));
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
                    code.setCreation_date(c.getString(c.getColumnIndex("creation_date")));
                    code.setProcessing_date(c.getString(c.getColumnIndex("processing_date")));
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
                        "grp", "code", "codeKor", "desc", "creation_date", "processing_date"
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
                code.setCreation_date(c.getString(c.getColumnIndex("creation_date")));
                code.setProcessing_date(c.getString(c.getColumnIndex("processing_date")));

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