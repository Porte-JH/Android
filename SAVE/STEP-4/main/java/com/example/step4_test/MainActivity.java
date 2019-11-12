package com.example.step4_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.TextView;

import com.example.step4_test.Adapter.SpinnerAdapter;
import com.example.step4_test.Model.Category;
import com.example.step4_test.Model.Student;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    Student s;
    DBHelper db;
    Category c;
    List<Category> cg = new ArrayList<Category>();
    List<String> deptno;
    List<String> deptkor;

    List<String> gradeno;
    List<String> gradekor;

    List<String> classno;
    List<String> classkor;

    List<String> statusno;
    List<String> statuskor;

    List<String> mentono;
    List<String> mentokor;

    SpinnerAdapter spinnerAdapter;

    private EditText num, name, phone;
    private Button btn_select, btn_save, btn_clear, btn_delete;
    private TextView message;
    private AlertDialog alertDialog;
    private Spinner department;
    private Spinner grade;
    private Spinner classes;
    private Spinner status;
    private Spinner mento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num = (EditText) findViewById(R.id.num);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        //전화번호 형식으로 입력.
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        btn_select = (Button) findViewById(R.id.btn_select);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_save = (Button) findViewById(R.id.btn_save);

        message = (TextView) findViewById(R.id.message);

        department = (Spinner) findViewById(R.id.department);
        grade = (Spinner) findViewById(R.id.grade);
        classes = (Spinner) findViewById(R.id.classes);
        status = (Spinner) findViewById(R.id.status);
        mento = (Spinner) findViewById(R.id.mento);

        if(D){
            Log.i(TAG, "JUST START!!");
        }
        //db에서 사용중인 테이블값 가져오기. * getApplicationContext()
        db = new DBHelper(getApplicationContext());

        ////////////////////////////////////////////////////////////////////////////////////////////
        ViewGroup.LayoutParams params_search = btn_select.getLayoutParams();
        params_search.height = 120;
        btn_select.setLayoutParams(params_search);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isEmpty(num.getText())){ //num값이 null이 아닐 때
                    //trim() == 공백 제거
                    s = db.select(num.getText().toString().trim());
                    db.closeDB();

                    if(s != null){ // student테이블 s 의 값이 null이 아닐 경우
                        onSetUI(s);
                    } else {
                        onSetNull();
                    }
                }

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////
        ViewGroup.LayoutParams params_save = btn_save.getLayoutParams();
        params_save.height = 120;
        btn_save.setLayoutParams(params_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(num.getText())){ //num값이 null이 아닐 때
                    //trim() == 공백 제거
                    s = db.InsertORUpdate(num.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            deptno.get(department.getSelectedItemPosition()),
                            gradeno.get(grade.getSelectedItemPosition()),
                            classno.get(classes.getSelectedItemPosition()),
                            statusno.get(status.getSelectedItemPosition()),
                            mentono.get(mento.getSelectedItemPosition()));
                    db.closeDB();

                    if(s != null){ // student테이블 s 의 값이 null이 아닐 경우
                        onSetUI(s);
                    } else {
                        onSetNull();
                    }
                }
                onSetNull();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        ViewGroup.LayoutParams params_clear = btn_clear.getLayoutParams();
        params_clear.height = 120;
        btn_clear.setLayoutParams(params_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onSetNull();
                num.setText("");
                onSetNull();;
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        ViewGroup.LayoutParams params_delete = btn_delete.getLayoutParams();
        params_delete.height = 120;
        btn_delete.setLayoutParams(params_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //메소드 생성
                alertDialog = deleteConfirm();
                alertDialog.show();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        makeDepartmentSpinner();
        makeGradeSpinner();
        makeClassSpinner();
        makeStatusSpinner();
        makeMentoSpinner();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setListDepartment(){
        deptno = new ArrayList<String>();
        deptkor = new ArrayList<String>();

        cg = db.select_category("department");
        db.closeDB();

        for(int q = 0; q < cg.size(); q++){
            deptno.add(cg.get(q).getCode());
            deptkor.add(cg.get(q).getCodekor());
        }
    }
    public void makeDepartmentSpinner(){

        setListDepartment();
        spinnerAdapter= new SpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, deptkor);
        department.setAdapter(spinnerAdapter);

        if(deptkor.size() > 0){
            department.setSelection(department.getSelectedItemPosition());
        } else {
            deptno.add("404");
            deptkor.add("DATA NOT FOUND");
            department.setSelection(0);
        }
        spinnerAdapter.notifyDataSetChanged();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setListGrade(){
        gradeno = new ArrayList<String>();
        gradekor = new ArrayList<String>();

        cg = db.select_category("grade");
        db.closeDB();

        for(int q = 0; q < cg.size(); q++){
            gradeno.add(cg.get(q).getCode());
            gradekor.add(cg.get(q).getCodekor());
        }
    }
    public void makeGradeSpinner(){

        setListGrade();
        spinnerAdapter= new SpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, gradekor);
        grade.setAdapter(spinnerAdapter);

        if(gradekor.size() > 0){
            grade.setSelection(grade.getSelectedItemPosition());
        } else {
            gradeno.add("404");
            gradekor.add("DATA NOT FOUND");
            grade.setSelection(0);
        }
        spinnerAdapter.notifyDataSetChanged();

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setListClass(){
        classno = new ArrayList<String>();
        classkor = new ArrayList<String>();

        cg = db.select_category("classes");
        db.closeDB();

        for(int q = 0; q < cg.size(); q++){
            classno.add(cg.get(q).getCode());
            classkor.add(cg.get(q).getCodekor());
        }
    }
    public void makeClassSpinner(){

        setListClass();
        spinnerAdapter= new SpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, classkor);
        classes.setAdapter(spinnerAdapter);

        if(classkor.size() > 0){
            classes.setSelection(classes.getSelectedItemPosition());
        } else {
            classno.add("404");
            classkor.add("DATA NOT FOUND");
            classes.setSelection(0);
        }
        spinnerAdapter.notifyDataSetChanged();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setListStatus(){
        statusno = new ArrayList<String>();
        statuskor = new ArrayList<String>();

        cg = db.select_category("status");
        db.closeDB();

        for(int q = 0; q < cg.size(); q++){
            statusno.add(cg.get(q).getCode());
            statuskor.add(cg.get(q).getCodekor());
        }
    }
    public void makeStatusSpinner(){

        setListStatus();
        spinnerAdapter= new SpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, statuskor);
        status.setAdapter(spinnerAdapter);

        if(statuskor.size() > 0){
            status.setSelection(status.getSelectedItemPosition());
        } else {
            statusno.add("404");
            statuskor.add("DATA NOT FOUND");
            status.setSelection(0);
        }
        spinnerAdapter.notifyDataSetChanged();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setListMento(){
        mentono = new ArrayList<String>();
        mentokor = new ArrayList<String>();

        cg = db.select_category("mento");
        db.closeDB();

        for(int q = 0; q < cg.size(); q++){
            mentono.add(cg.get(q).getCode());
            mentokor.add(cg.get(q).getCodekor());
        }
    }
    public void makeMentoSpinner(){

        setListMento();
        spinnerAdapter= new SpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, mentokor);
        mento.setAdapter(spinnerAdapter);

        if(mentokor.size() > 0){
            mento.setSelection(mento.getSelectedItemPosition());
        } else {
            mentono.add("404");
            mentokor.add("DATA NOT FOUND");
            mento.setSelection(0);
        }
        spinnerAdapter.notifyDataSetChanged();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public AlertDialog deleteConfirm(){

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final View innerView = //dialog xml 가져오기
                getLayoutInflater().inflate(R.layout.dialog_delete_confirm, null);
        // 알림창 xml 설정
        ab.setView(innerView);
        // yes 버튼을 눌렀을 때
        ab.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // delete문 실행 후 결과 값 저장
                boolean rc = db.delete(num.getText().toString().trim());
                db.closeDB();
                if(!rc){
                    message.setText("Error On Delete");
                } else {
                    onSetNull();
                    message.setText("Delete Data");
                }
            }
        });
        // no 버튼을 눌렀을 때
        ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setDismiss(alertDialog);
            }
        });
        return ab.create();
    }

    public void setDismiss(Dialog Dialog){
        if(Dialog != null && Dialog.isShowing()){
            Dialog.dismiss();
        }
    }

    public void onSetUI(Student s){
        num.setText(s.getNum());
        name.setText(s.getName());
        phone.setText(s.getPhone());
        department.setSelection(deptno.indexOf(s.getDepartment()));
        grade.setSelection(gradeno.indexOf(s.getGrade()));
        classes.setSelection(classno.indexOf(s.getClasses()));
        status.setSelection(statusno.indexOf(s.getStatus()));
        mento.setSelection(statusno.indexOf(s.getMento()));
    }
    public void onSetNull(){
        //num.setText("");
        name.setText("");
        phone.setText("");
    }
}
