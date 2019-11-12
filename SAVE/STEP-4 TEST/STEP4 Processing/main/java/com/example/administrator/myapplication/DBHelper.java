package com.example.administrator.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
    private static final String Table_Codes = "Codes";


    //테이블 생성문
    private static final String CREATE_STUDENTS_TABLE =
            "CREATE TABLE IF NOT EXISTS Students" +
                    "(" +
                    "num TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "PRIMARY KEY(num)" + ")";

    private static final String CREATE_CODES_TABLE =
            "CREATE TABLE IF NOT EXISTS Codes" +
                    "(" +
                    "grp TEXT NOT NULL," + //1 = 학과코드, 2 = 재학코드
                    "code TEXT NOT NULL," + // 학과코드
                    "code_kor TEXT NOT NULL," + //
                    "desc TEXT NOT NULL," +
                    "creation_date TEXT NOT NULL," +
                    "processing_date TEXT NULL," +
                    "PRIMARY KEY(grp,code)" + ")";

    //테이블 삭제문
    String DROP_STUDENTS_TABLE = "DROP TABLE IF EXISTS STUDENTS";
    String DROP_CODES_TABLE = "DROP TABLE IF EXISTS Codes";

    //초기값 설정
    private final String INIT_CODE_UNIV_STUCLASS_1 =
            "INSERT INTO " + Table_Codes +
                    "("  +
                    "grp, code, code_kor, desc, creation_date" + ")" +
                    "VALUE" +
                    "(" + "'stu_class', '1001', '컴퓨터소프트웨어괴','initial','" +  appDate.getTodayString() + "'" +")";
                                                                                //String 형이기 떄문이 " " 포함;

    private final String INIT_CODE_UNIV_STUCLASS_2 =
            "INSERT INTO " + Table_Codes +
                    "("  +
                    "grp, code, code_kor, desc, creation_date" + ")" +
                    "VALUE" +
                    "(" + "'stu_class', '2001', '실내디자인과','initial','" +  appDate.getTodayString() + "'" +")";
                                                                            //String 형이기 떄문이 " " 포함;

    //저장한 DB정보에 따라 DB요소를 가져온다.
    public DBHelper(Context c) {
        super(c, DB_NAME,null, DB_version);
    }   //context, name, factory, version

    @Override //자동 DB실행.
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENTS_TABLE);
        db.execSQL(CREATE_CODES_TABLE);

        init_codes(db);

    }
    //테이블 초기값 삽입
    public void init_codes(SQLiteDatabase db){
        db.execSQL(INIT_CODE_UNIV_STUCLASS_1);
        db.execSQL(INIT_CODE_UNIV_STUCLASS_2);
    }

    @Override // DB버전 체크
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        //현재 버전과 이전 버전이 일치하지 않을 시, 테이블 삭제후 테이블 재생성
        if(old_v != new_v) {
            db.execSQL(DROP_STUDENTS_TABLE);
            db.execSQL(DROP_CODES_TABLE);
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

        //null == *   cursor: 데이터를 조회할때 임시로 저장하기 위한 곳(temp)
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
                Log.i(TAG,"s.tostring: " + s.toString());
            }
            c.close(); // 커서 종료
        }
        return s; // 테이블 반환
    }


    //update문 (크레인)
    public Student InsertORUpdate(String num, String name, String phone){
        //입력한 기본키에 따라 정보 불러옴.
        //select(num);
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


    public boolean delete(String num){ // String num -> String[]{num} -> num = ?
        Student s = select(num);
        SQLiteDatabase db = this.getWritableDatabase();
        // bool 값도 가능하긴 하지만, 삭제할 데이터가 여러개인 겅우를 생각해 int형으로 줌.
        // delete 실행 값을 n에 저장
        int n = db.delete(Table_Students, "num = ?", new String[]{num});
        return (n > 0 ? true : false);
    }


    public Code select_codes(String grp, String code){
        //Code 테이블 초기화
        Code co = null;
        //기본키가 null이라면 테이블값 반환.
        if(grp == null || code == null){
            return co;
        }

        //변경 불가능한 상태로 db를 가져옴
        SQLiteDatabase db = this.getReadableDatabase();

        //null == *   cursor: 데이터를 조회할때 임시로 저장하기 위한 곳(temp)
        Cursor c = db.query(Table_Codes,null, "grp = ? and code = ?", new String[]{grp, code}
                ,null,null,null);   //그룹의 필드의 값과, 코드의 값이 인수의 값가 같은 경우

        //커서를 제일 첫번째 행으로 이동.
        c.moveToFirst();

        //테이블의 행의 수가 0이 넘을 때
        if(c.getCount() > 0){
            co = new Code(); // 객체 생성
            //커서를 통해 student 테이블에 각 항목의 값을 저장
            co.setGrp(c.getString(c.getColumnIndex("grp")));
            co.setCode(c.getString(c.getColumnIndex("code")));
            co.setCode_kor(c.getString(c.getColumnIndex("code_kor")));
            co.setDesc(c.getString(c.getColumnIndex("desc")));
            co.setCreation_date(c.getString(c.getColumnIndex("creation_date")));
            co.setProcessing_date(c.getString(c.getColumnIndex("processing_date")));

            if(D){
                Log.i(TAG,"s.tostring: " + co.toString());
            }
            c.close(); // 커서 종료
        }
        return co; // 테이블 반환
    }

    //grp의 값으로 모든 값 리스트 형태로 불러옴.
    public List<Code> select_codes(String grp){
        //Code 테이블 초기화
        List<Code> codes = new ArrayList<Code>();
        Code co = null;
        //객체를 포함하는 객체형??
        //기본키가 null이라면 테이블값 반환.
        if(grp == null){
            return codes;
        }

        //변경 불가능한 상태로 db를 가져옴
        SQLiteDatabase db = this.getReadableDatabase();

        //null == *   cursor: 데이터를 조회할때 임시로 저장하기 위한 곳(temp)
        Cursor c = db.query(Table_Codes,null, "grp = ?", new String[]{grp}
                ,null,null,null);   //그룹의 필드의 값과, 코드의 값이 인수의 값가 같은 경우

        //커서를 제일 첫번째 행으로 이동.
        c.moveToFirst();

        //테이블의 행의 수가 0이 넘을 때
        // 레코드 안의 레코드를 읽기 위함.
        if(c.getCount() > 0){
            if(c.moveToFirst()){
                do{
                    co = new Code(); // 객체 생성
                    //커서를 통해 student 테이블에 각 항목의 값을 저장
                    co.setGrp(c.getString(c.getColumnIndex("grp")));
                    co.setCode(c.getString(c.getColumnIndex("code")));
                    co.setCode_kor(c.getString(c.getColumnIndex("code_kor")));
                    co.setDesc(c.getString(c.getColumnIndex("desc")));
                    co.setCreation_date(c.getString(c.getColumnIndex("creation_date")));
                    co.setProcessing_date(c.getString(c.getColumnIndex("processing_date")));
                    // arraylist에 추가
                    // while문 때문에 계속 객체가 생성되어, 저장 -> 생성 과정으로 만들어줘야함
                    codes.add(co);
                } while (c.moveToNext()); // 다음 행이 읽어질 때
            }
        }
        c.close(); // 커서 종료
        return codes; // 테이블 반환
    }



    public void closeDB(){
        //현재 db정보 불러오기
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }

}

/*
    public Code select_codes(String grp, String code){
        //Code 테이블 초기화
        Code co = null;
        //기본키가 null이라면 테이블값 반환.
        if(grp == null || code == null){
            return co;
        }

        //변경 불가능한 상태로 db를 가져옴
        SQLiteDatabase db = this.getReadableDatabase();

        //null == *   cursor: 데이터를 조회할때 임시로 저장하기 위한 곳(temp)
        Cursor c = db.query(Table_Codes,null, "grp = ? and code = ?", new String[]{grp, code}
                ,null,null,null);   //그룹의 필드의 값과, 코드의 값이 인수의 값가 같은 경우

        //커서를 제일 첫번째 행으로 이동.
        c.moveToFirst();

        //테이블의 행의 수가 0이 넘을 때
        // 레코드 안의 레코드를 읽기 위함.
        if(c.getCount() > 0){
            if(c.moveToFirst()){ // == true
                co = new Code();
            }
            co = new Code(); // 객체 생성
            //커서를 통해 student 테이블에 각 항목의 값을 저장
            co.setGrp(c.getString(c.getColumnIndex("grp")));
            co.setCode(c.getString(c.getColumnIndex("code")));
            co.setCode_kor(c.getString(c.getColumnIndex("code_kor")));
            co.setDesc(c.getString(c.getColumnIndex("desc")));
            co.setCreation_date(c.getString(c.getColumnIndex("creation_date")));
            co.setProcessing_date(c.getString(c.getColumnIndex("processing_date")));

            if(D){
                Log.i(TAG,"s.tostring: " + co.toString());
            }
            c.close(); // 커서 종료
        }
        return co; // 테이블 반환
    } */
