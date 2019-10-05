package com.example.myapplication1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private final String TAG = getClass().getSimpleName();
    // 현재 클래스에 대한 이름을 가져온다.
    // 복수의 클래스 사이에서 디버깅을 위해 사용.

    private final boolean D = true;
    // 디버깅 용이라는데 왜 이러는 진 ㅁㄹ겠음



    private EditText input_num, input_name, input_call;
    private TextView output_name, output_call, output_log;
    private Button btn_ok;

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



       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
