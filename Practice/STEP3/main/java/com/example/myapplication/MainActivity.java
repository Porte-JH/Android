package com.example.myapplication;

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
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Student;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    Student s;
    DBHelper db;


    private EditText num, name, phone;
    private Button btn_select, btn_save, btn_clear, btn_delete;
    private TextView message;
    private AlertDialog alertDialog;


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



        if(D){
            Log.i(TAG, "JUST START!!");
        }

        db = new DBHelper(getApplicationContext());


        ViewGroup.LayoutParams params_search = btn_select.getLayoutParams();
        params_search.height = 120;
        btn_select.setLayoutParams(params_search);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isEmpty(num.getText())){
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

        ViewGroup.LayoutParams params_save = btn_save.getLayoutParams();
        params_save.height = 120;
        btn_save.setLayoutParams(params_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(num.getText())){
                    s = db.InsertORUpdate( num.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim());
                    db.closeDB();

                    if(s != null){
                        onSetUI(s);
                    } else {
                        onSetNull();
                    }
                }
                onSetNull();
            }
        });

        ViewGroup.LayoutParams params_clear = btn_clear.getLayoutParams();
        params_clear.height = 120;
        btn_clear.setLayoutParams(params_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                num.setText("");
                onSetNull();;
            }
        });

        ViewGroup.LayoutParams params_delete = btn_delete.getLayoutParams();
        params_delete.height = 120;
        btn_delete.setLayoutParams(params_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog = deleteConfirm();
                alertDialog.show();
            }
        });

    }



    public AlertDialog deleteConfirm(){

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final View innerView = //dialog xml 가져오기
                getLayoutInflater().inflate(R.layout.dialog_delete_confirm, null);

        ab.setView(innerView);

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
}