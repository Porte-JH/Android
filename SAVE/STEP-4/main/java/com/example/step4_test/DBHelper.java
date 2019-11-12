package com.example.step4_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.step4_test.Model.Category;
import com.example.step4_test.Model.Student;

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
    private static final String Table_Categories = "Categories";


    //테이블 생성문
    private static final String CREATE_STUDENTS_TABLE =
            "CREATE TABLE IF NOT EXISTS Students" +
                    "(" +
                    "num TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "department TEXT NOT NULL," +
                    "grade TEXT NOT NULL," +
                    "classes TEXT NOT NULL," +
                    "status TEXT NOT NULL," +
                    "mento TEXT NOT NULL," +
                    "PRIMARY KEY(num)" + ")";

    private static final String CREATE_CATEGOTY_TABLE =
            "CREATE TABLE IF NOT EXISTS Categories" +
            "(" +
                    "grp TEXT NOT NULL," +
                    "code TEXT NOT NULL," +
                    "codekor TEXT NOT NULL," +
                    "PRIMARY KEY(grp, code)" + ")";

    //테이블 삭제문
    String DROP_STUDENTS_TABLE = "DROP TABLE IF EXISTS STUDENTS";
    String DROP_CATEGORIES_TABLE = "DROP TABLE IF EXISTS Categories";

    private final String INPUT_DATA_DEPT_1 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'department', '1001', '컴퓨터소프트웨어과'" + ")";

    private final String INPUT_DATA_DEPT_2 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'department', '2001', '실내디자인과'" + ")";



    private final String INPUT_DATA_GRADE_1 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'grade', '1', '1학년'" + ")";

    private final String INPUT_DATA_GRADE_2 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'grade', '2', '2학년'" + ")";

    private final String INPUT_DATA_GRADE_3 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'grade', '3', '3학년'" + ")";

    private final String INPUT_DATA_GRADE_4 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'grade', '4', '4학년'" + ")";

    private final String INPUT_DATA_CLASS_1 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'classes', 'a', 'A반'" + ")";

    private final String INPUT_DATA_CLASS_2 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'classes', 'b', 'B반'" + ")";

    private final String INPUT_DATA_STATUS_1 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'status', '1', '재학'" + ")";

    private final String INPUT_DATA_STATUS_2 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'status', '2', '휴학'" + ")";

    private final String INPUT_DATA_STATUS_3 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'status', '3', '졸업'" + ")";

    private final String INPUT_DATA_STATUS_4 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'status', '4', '자퇴'" + ")";

    private final String INPUT_DATA_MENTO_1 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'mento', '1', '이형일'" + ")";

    private final String INPUT_DATA_MENTO_2 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'mento', '2', '김인범'" + ")";

    private final String INPUT_DATA_MENTO_3 =
            "INSERT INTO " + Table_Categories +
                    "(" +
                    "grp, code, codekor" + ")" +
                    "VALUES" +
                    "(" + "'mento', '3', '김재생'" + ")";

    //저장한 DB정보에 따라 DB요소를 가져온다.
    public DBHelper(Context c) {
        super(c, DB_NAME,null, DB_version);
    }   //context, name, factory, version

    @Override //자동 DB실행.
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENTS_TABLE);
        db.execSQL(CREATE_CATEGOTY_TABLE);


        INPUT_MENTO(db);
        INPUT_STATUS(db);
        INPUT_CLASS(db);
        INPUT_GRADE(db);
        INPUT_DEPT(db);

    }

    public void INPUT_DEPT(SQLiteDatabase db){
        db.execSQL(INPUT_DATA_DEPT_1);
        db.execSQL(INPUT_DATA_DEPT_2);
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

    @Override // DB버전 체크
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        //현재 버전과 이전 버전이 일치하지 않을 시, 테이블 삭제후 테이블 재생성
        if(old_v != new_v) {
            db.execSQL(DROP_STUDENTS_TABLE);
            db.execSQL(DROP_CATEGORIES_TABLE);
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
            s.setDepartment(c.getString(c.getColumnIndex("department")));
            s.setGrade(c.getString(c.getColumnIndex("grade")));
            s.setClasses(c.getString(c.getColumnIndex("classes")));
            s.setStatus(c.getString(c.getColumnIndex("status")));
            s.setMento(c.getString(c.getColumnIndex("mento")));
            if(D){
                Log.i(TAG,"s.tostring: " + s.toString());
            }
            c.close(); // 커서 종료
        }
        return s; // 테이블 반환
    }


    //update문 (크레인)
    public Student InsertORUpdate(String num, String name, String phone,
                                  String department, String grade, String classes, String status, String mento){
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
        values.put("department", department);
        values.put("grade", grade);
        values.put("classes", classes);
        values.put("status", status);
        values.put("mento", mento);
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

    public List<Category> select_category(String grp){
        //Code 테이블 초기화
        List<Category> cgs = new ArrayList<Category>();

        //객체를 포함하는 객체형??
        //기본키가 null이라면 테이블값 반환.
        if(grp == null){
            //cgs = select_category();
            return cgs;
        }

        //변경 불가능한 상태로 db를 가져옴
        SQLiteDatabase db = this.getReadableDatabase();

        //null == *   cursor: 데이터를 조회할때 임시로 저장하기 위한 곳(temp)
        Cursor c = db.query(Table_Categories, null, "grp = ?",
                            new String[]{grp}, null,null,null);
                //그룹의 필드의 값과, 코드의 값이 인수의 값가 같은 경우

        //커서를 제일 첫번째 행으로 이동.
        //c.moveToFirst();

        //테이블의 행의 수가 0이 넘을 때
        // 레코드 안의 레코드를 읽기 위함.
        if(c.getCount() > 0){
            if(c.moveToFirst()){
                do{
                    Category cg = new Category(); // 객체 생성
                    //커서를 통해 student 테이블에 각 항목의 값을 저장
                    cg.setGrp(c.getString(c.getColumnIndex("grp")));
                    cg.setCode(c.getString(c.getColumnIndex("code")));
                    cg.setCodekor(c.getString(c.getColumnIndex("codekor")));

                    // arraylist에 추가
                    // while문 때문에 계속 객체가 생성되어, 저장 -> 생성 과정으로 만들어줘야함
                    cgs.add(cg);
                } while (c.moveToNext()); // 다음 행이 읽어질 때
            }
        }
        c.close(); // 커서 종료
        return cgs; // 테이블 반환
    }

    public Category select_category(String grp, String code){
        //Code 테이블 초기화

        Category cg = new Category();
        //객체를 포함하는 객체형??
        //기본키가 null이라면 테이블값 반환.
        if(grp == null || code == null){
            //cgs = select_category();
            return cg;
        }

        //변경 불가능한 상태로 db를 가져옴
        SQLiteDatabase db = this.getReadableDatabase();

        //null == *   cursor: 데이터를 조회할때 임시로 저장하기 위한 곳(temp)
        Cursor c = db.query(Table_Categories, null, "grp = ?",
                new String[]{grp}, null,null,null);
        //그룹의 필드의 값과, 코드의 값이 인수의 값가 같은 경우

        //커서를 제일 첫번째 행으로 이동.
        //c.moveToFirst();

        //테이블의 행의 수가 0이 넘을 때
        // 레코드 안의 레코드를 읽기 위함.
        if(c.getCount() > 0){

            c.moveToFirst();
            //커서를 통해 student 테이블에 각 항목의 값을 저장
            cg.setGrp(c.getString(c.getColumnIndex("grp")));
            cg.setCode(c.getString(c.getColumnIndex("code")));
            cg.setCodekor(c.getString(c.getColumnIndex("codekor")));

            }

        c.close(); // 커서 종료
        return cg; // 테이블 반환
    }





    public boolean delete(String num){ // String num -> String[]{num} -> num = ?
        Student s = select(num);
        SQLiteDatabase db = this.getWritableDatabase();
        // bool 값도 가능하긴 하지만, 삭제할 데이터가 여러개인 겅우를 생각해 int형으로 줌.
        // delete 실행 값을 n에 저장
        int n = db.delete(Table_Students, "num = ?", new String[]{num});
        return (n > 0 ? true : false);
    }


    public void closeDB(){
        //현재 db정보 불러오기
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }
}