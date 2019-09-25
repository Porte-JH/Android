package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myapplication.Helper.DatabaseHelper;
import com.example.administrator.myapplication.Model.Student;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    private final String TAG  = getClass().getSimpleName();
    private final boolean D = true;

    Student s;
    DatabaseHelper db;

    private EditText num, name, phone;
    private Button btn_select, btn_save;
    private TextView message;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //현재 시작상태
        if(D) {
            Log.i(TAG, "Just Started!!");
        }
        // 현재 객체에 대해 db 생성
        db = new DatabaseHelper(getApplicationContext());

        num = findViewById(R.id.num);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        btn_save = findViewById(R.id.btn_save);
        btn_select = findViewById(R.id.btn_select);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        // 000 - 0000 - 0000 형식으로 만들어줌

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(num.getText())){ // null값이 아닐 때
                    //trim == 문자열 중간의 공백을 null로 바꿔줌
                    // ==> 공백 제거;
                    s = db.select(num.getText().toString().trim());
                    db.closeDB(); // 데이터를 불러와서 db를 닫음.
                    if(s != null){
                        onSetUI(s);
                    } else {
                        onSetNull();
                    }
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(num.getText())){ // null값이 아닐 때
                    //trim == 문자열 중간의 공백을 null로 바꿔줌
                    // ==> 공백 제거;
                    s = db.insertOrUpdate(  num.getText().toString().trim(),
                                            name.getText().toString().trim(),
                                            phone.getText().toString().trim());
                    db.closeDB(); // 데이터를 불러와서 db를 닫음.
                    if(s != null){
                        onSetUI(s);
                    } else {
                        onSetNull();
                    }
                }
            }
        });
        message = findViewById(R.id.message);
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
