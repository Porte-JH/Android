package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Student;
import com.example.myapplication.DBHelper;

import org.w3c.dom.Text;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;


    Student s;
    DBHelper db;

    private EditText num, name, phone;
    private TextView message;
    private Button btn_save, btn_select;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num = (EditText) findViewById(R.id.num);
        name = (EditText) findViewById(R.id.name);

        phone = (EditText) findViewById(R.id.phone);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_select = (Button) findViewById(R.id.btn_select);

        message = (TextView) findViewById(R.id.message);

        if(D){
            Log.i(TAG, "Just Start!");
        }

        db = new DBHelper(getApplicationContext());

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(num.getText())){
                    s = db.select(num.getText().toString().trim());
                    db.CloseDB();

                    if(s != null){
                        setOnUI(s);
                    } else {
                        setOnNull();
                    }
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(!isEmpty(num.getText())){
                        s = db.InsertORUpdate(num.getText().toString().trim(),
                                                name.getText().toString().trim(),
                                                phone.getText().toString().trim());

                        db.CloseDB();

                        if(s != null){
                            setOnUI(s);
                        } else {
                            setOnNull();
                        }
                    }
            }
        });



    }

    public void setOnUI(Student s){
        num.setText(s.getNum());
        name.setText(s.getName());
        phone.setText(s.getPhone());
    }

    public void setOnNull(){
        //num.setText(null);
        name.setText(null);
        phone.setText(null);
    }
}
