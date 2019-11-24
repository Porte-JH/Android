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

    SpinnerAdapter spinnerAdapter;

    private EditText num, name, phone;
    private Button btn_select, btn_save, btn_clear, btn_delete;
    private TextView message;
    private AlertDialog alertDialog;
    private Spinner department;

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
                            deptno.get(department.getSelectedItemPosition()));
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

    }
    public void onSetNull(){
        //num.setText("");
        name.setText("");
        phone.setText("");
    }
}
