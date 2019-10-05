package com.example.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DBHelper extends SQLiteOpenHelper {


    //현재의 클래스 이름 불러오기
    private final String TAG = getClass().getSimpleName();
    //디버깅을 위함.
    private final boolean D = true;


    //Static == 클래스 변수
    //DB의 정보.
    private static final String DB_NAME = "APP_DB.db";
    private static final int DB_version = 1;
    private static final String Table_Students = "Students";

    //테이블 생성문
    private static final String CREATE_STUDENTS_TABLE =
            "CREATE TABLE IF NOT EXISTS Students" +
                    "(" +
                    "num TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "PRIMARY KEY(num)" + ")";

    //테이블 삭제문
    String DROP_STUDENTS_TABLE = "DROP TABLE IF EXISTS STUDENTS";

    //저장한 DB정보에 따라 DB요소를 가져온다.
    public DBHelper(Context c) {
        super(c, DB_NAME,null, DB_version);
}   //context, name, factory, version

    @Override //자동 DB실행.
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override // DB버전 체크
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        //현재 버전과 이전 버전이 일치하지 않을 시, 테이블 삭제후 테이블 재생성
        if(old_v != new_v) {
            db.execSQL(DROP_STUDENTS_TABLE);
            onCreate(db);
        }
    }

    //Select문, 기본키 num을 이용. (집게)
    public Student select(String num){
        //Student 테이블 초기화
        Student s = null;
        //기본키가 null이라면 null값 반환.
        if(num == null){
            return null;
        }

        //변경 불가능한 상태로 db를 가져옴
        SQLiteDatabase db = this.getReadableDatabase();

        //null == *   cursor: 데이터를 조회할때 임시로 저장하기 위한 곳
        Cursor c = db.query(Table_Students,null, "num = ?", new String[]{num}
        ,null,null,null);

        //커서를 제일 첫번째 행으로 이동.
        c.moveToFirst();

        //테이블의 행의 수가 0이 넘을 때
        if(c.getCount() > 0){
            s = new Student();
            //커서를 통해 student 테이블에 각 항목의 값을 저장
            s.setNum(c.getString(c.getColumnIndex("num")));
            s.setName(c.getString(c.getColumnIndex("name")));
            s.setPhone(c.getString(c.getColumnIndex("phone")));

            if(D){
                Log.i(TAG,"s.tostring: " + "toString()");
            }
            c.close(); // 커서 종료
        }
        return s; // 테이블 반환
    }


    //update문 (크레인)
    public Student InsertORUpdate(String num, String name, String phone){
        //입력한 기본키에 따라 정보 불러옴.
        select(num);
        //기본키 혹은 이름값이 null일 경우 null값 반환.
        if((num == null) || (name == null)){
            return null;
        }
        // if문 통과 후에 다시 정보를 객체를 통해 불러옴.
        Student s = select(num);
        // db를 변경 불가능한 상태로 데이터를 가져옴.
        SQLiteDatabase db = this.getReadableDatabase();
        // 데이터 운송수단 클래스
        ContentValues values = new ContentValues();
        // 데이터 삽입
        values.put("num", num);
        values.put("name", name);
        values.put("phone", phone);

        if(s == null){ // 데이터가 존재하지 않을 때
            //대체 어따쓰는 변수지 (???????????????????)
            long n = db.insert(Table_Students, null, values);
            if(D){
                Log.i(TAG,"Insert n: " + n);
            }

        } else { // 데이터가 존재할 때
            //대체 어따쓰는 변수지 (???????????????????)
            int n = db.update(Table_Students, values, "num = ?", new String[]{num});
            if(D){
                Log.i(TAG,"Update n: " + n);
            }
        }
        s = select(num);
        return s;
    }

    public void closeDB(){
        //현재 db정보 불러오기
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }
}
