package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    private Button btn_select, btn_save, btn_clear, btn_delete;
    private EditText num, name, phone;
    private TextView message;
    private AlertDialog Alert;

    Student s;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num = findViewById(R.id.num);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        btn_select = findViewById(R.id.btn_select);
        btn_save = findViewById(R.id.btn_save);
        btn_clear = findViewById(R.id.btn_clear);
        btn_delete = findViewById(R.id.btn_delete);

        message = findViewById(R.id.message);

        db = new DBHelper(getApplicationContext());

        ViewGroup.LayoutParams params_select = btn_select.getLayoutParams();
        params_select.height = 120;
        btn_select.setLayoutParams(params_select);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(num.getText())){

                    s = db.Select(num.getText().toString().trim());
                    db.CloseDB();

                    if(s != null){
                        OnSetUI(s);
                    } else {
                        OnsetNULL();
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

                    s = db.InsertUpdate(num.getText().toString().trim(),
                                        name.getText().toString().trim(),
                                        phone.getText().toString().trim());
                    db.CloseDB();
                    if(s != null){
                        OnSetUI(s);
                    } else {
                        OnsetNULL();
                    }
                }
            }
        });

        ViewGroup.LayoutParams params_clear = btn_clear.getLayoutParams();
        params_clear.height = 120;
        btn_clear.setLayoutParams(params_clear);

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnsetNULL();
            }
        });

        ViewGroup.LayoutParams params_delete = btn_delete.getLayoutParams();
        params_delete.height = 120;
        btn_delete.setLayoutParams(params_delete);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alert = delete_confirm();
                Alert.show();
            }
        });

    }

    public AlertDialog delete_confirm(){
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final View innerView =
                getLayoutInflater().inflate(R.layout.dialog_delete_confirm, null);
        ab.setView(innerView);

        ab.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean rc = db.delete(num.getText().toString().trim());
                    db.CloseDB();
                if(!rc){
                    message.setText("Error On Delete");
                } else {
                    message.setText("Delete !!");
                }
            }
        });

        ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setDismiss(Alert);
            }
        });
        return ab.create();
    }


    public void setDismiss(Dialog Alert){
        if(Alert != null && Alert.isShowing()){
            Alert.dismiss();
        }
    }

    public void OnSetUI(Student s){
        num.setText(s.getNum());
        name.setText(s.getName());
        phone.setText(s.getPhone());
    }

    public void OnsetNULL(){
        num.setText("");
        name.setText("");
        phone.setText("");
    }


}
