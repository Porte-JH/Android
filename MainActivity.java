package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText input_num, input_name, input_call;
    TextView output_name, output_call, output_log;
    Button btn_ok;

    String name, call, num;
    Integer temp;


    String[] names = new String[100];
    String[] calls = new String[100];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input_num = (EditText) findViewById(R.id.input_num);
        input_name = (EditText) findViewById(R.id.input_name);
        input_call = (EditText) findViewById(R.id.input_call);

        output_name = (TextView) findViewById(R.id.output_name);
        output_call = (TextView) findViewById(R.id.output_call);
        output_log = (TextView) findViewById(R.id.output_log);

        btn_ok = (Button) findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                num = input_num.getText().toString();

                if(num.isEmpty() == true){
                    output_log.setText("학번을 입력해주세요!");
                } else {
                    temp = Integer.parseInt(num);

                    names[temp - 1] = input_name.getText().toString();
                    calls[temp - 1] = input_call.getText().toString();

                    if(names[temp - 1].isEmpty() == true || calls[temp - 1].isEmpty() == true){

                        output_log.setText("조회가능한 정보가 없습니다!");

                    } else {

                        input_name.setText(names[temp - 1]);
                        input_call.setText(calls[temp - 1]);
                        output_name.setText(names[temp - 1]);
                        output_call.setText(calls[temp - 1]);

                        output_log.setText("요청한 학번(" + temp + ") 을(를) 조회하셨습니다.");

                    }

                }

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
