package com.example.administrator.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;
import com.example.administrator.myapplication.adapter.SpinnerAdapter;
public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    Student s;
    Code co;
    DBHelper db;
    //arrayList는 단독으로 사용할 수 없으며 사용시 List 인터페이스를 상속받고 사용해야 한다.
    List<Code> cs = new ArrayList<Code>();


    private EditText num, name, phone;
    private Button btn_select, btn_save, btn_clear, btn_delete;
    private TextView message;
    private AlertDialog alertDialog;
    //spinner에 대한 것.
    private Spinner stu_class_spinner;
    List<String> codes;
    List<String> codes_kor;
    SpinnerAdapter spinnerAdapter;

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
        stu_class_spinner = (Spinner) findViewById(R.id.stu_class);
        makespinner();

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
                    s = db.InsertORUpdate( num.getText().toString().trim(),
                                        name.getText().toString().trim(),
                                        phone.getText().toString().trim());
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
    }



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
    }
    public void onSetNull(){
        //num.setText("");
        name.setText("");
        phone.setText("");
    }

    public void onStart(){
        super.onStart();
    }

    public void makespinner(){
        //spinenr에서 사용할 list 객체생성.
        setListStuClass();

        //spinner에 대한 adapter 생성
        spinnerAdapter = new SpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, codes_kor);

        //spunner에 adapter 설정
        stu_class_spinner.setAdapter(spinnerAdapter);
        //code_kor을 담는 list, codes_kor의 데이터가 1개 이상일 경우
        if (codes_kor.size() > 0){
            //spinner의 선택위치를 선택한 위치로 설정.
            stu_class_spinner.setSelection(stu_class_spinner.getSelectedItemPosition());
        } else {
            //임시로 데이터가 없다는 것을 표시하기 위해 각 list에 해당 내용 추가.
            codes.add("404");
            codes_kor.add("data not found");
            stu_class_spinner.setSelection(0);
        }
        //데이터 변한 값에 대해 다시 설정 (== 새로고침)
        spinnerAdapter.notifyDataSetChanged();
    }
    public void setListStuClass(){
        codes = new ArrayList<String>();
        codes_kor = new ArrayList<String>();
        //db class에서 code를 이용해 데이터를 불러와 cs에 저장.
        cs = db.select_codes("grp"); //오류
        db.closeDB();
        //cs list에 저장된만큼 for문을 실행.
        for(int q = 0; q < cs.size(); q++){
            //cs에 해당하는 q번째의 code값을 가져와 codes에 추가.
            codes.add(cs.get(q).getCode());
            codes_kor.add(cs.get(q).getCode_kor());
        }
    }

}
