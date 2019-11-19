package com.example.administrator.ps5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;

import android.content.Intent;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.administrator.ps5.Adapter.SpinnerAdapter;
import com.example.administrator.ps5.App.AppCamera;
import com.example.administrator.ps5.Model.Category;
import com.example.administrator.ps5.Model.Student;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;


public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    Bundle args;

    String imageStoragePath;
    public static final int BITMAP_SAMPLE_SIZE = 8;

    //for UI
    private ImageView imgPreview;
    private VideoView videoPreview;

    private EditText num, name, phone;
    private Button btn_select, btn_save, btn_clear, btn_delete;
    private TextView message;
    private AlertDialog alertDialog;
    private Spinner department;


    //for spinner UI


    // adapters
    SpinnerAdapter spinnerAdapter;

    Student s;
    DBHelper db;
    Category c;
    List<Category> cg = new ArrayList<Category>();
    List<String> deptno;
    List<String> deptkor;






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

        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        videoPreview = (VideoView) findViewById(R.id.videoPreview);

        department = (Spinner) findViewById(R.id.department);


        if(D){
            Log.i(TAG, "JUST START!!");
        }
        //db에서 사용중인 테이블값 가져오기. * getApplicationContext()
        db = new DBHelper(getApplicationContext());
        //activity에서 전달된 데이터를 가져옴.
        args = getIntent().getExtras() != null ? getIntent().getExtras() : new Bundle();

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

                // 제공 파일에 맞춰 수정 필요
                if(!isEmpty(num.getText())){ //num값이 null이 아닐 때
                    //trim() == 공백 제거
                    s = db.InsertORUpdate(num.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            s.getImagename(),
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


    @Override
    public void onStart() {
        super.onStart();

    }


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


    public void onSetUI(Student s) {

        num.setText(s.getNum());
        name.setText(s.getName());
        phone.setText(s.getPhone());
        department.setSelection(deptno.indexOf(s.getDepartment()));

        //onSetImage(s.getImageName());

    }

    private void onSetImage(String imageName) {

        if (imageName != null) {

            if (getExtension(imageName).
                    equalsIgnoreCase("jpg")) {
                previewCapturedImage(imageName);
            } else if (getExtension(imageName).
                    equalsIgnoreCase("mp4")) {
                previewVideo(imageName);
            }
        }
        else {

            imgPreview.setVisibility(View.VISIBLE);
            videoPreview.setVisibility(View.GONE);
            imgPreview.setImageResource(R.drawable.ic_android);
        }
    }

    public static String getExtension(String fileStr){
        String fileExtension =
                fileStr.substring(fileStr.lastIndexOf(".")+1,fileStr.length());
        return isEmpty(fileExtension) ? null : fileExtension;
    }

    public void onSetNull() {

        name.setText("");
        phone.setText("");

        onSetImageNameNull();
    }

    private void onSetImageNameNull() {
        imgPreview.setVisibility(View.VISIBLE);
        videoPreview.setVisibility(View.GONE);
        imgPreview.setImageResource(R.drawable.ic_android);
    }

    /**
     * Displaying Image in ImageView
     */
    private void previewCapturedImage(String imageName) {

        try {
            imgPreview.setVisibility(View.VISIBLE);
            videoPreview.setVisibility(View.GONE);
            Bitmap bitmap = AppCamera.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageName);
            imgPreview.setImageBitmap(bitmap);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    /**
     * Displaying video in VideoView
     */
    private void previewVideo(String imageName) {

        try {
            imgPreview.setVisibility(View.GONE);
            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoPath(imageName);
            videoPreview.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    AlertDialog deleteConfirm() {

        AlertDialog.Builder
                ab = new AlertDialog.Builder(this);
        //ab.setTitle("Title");
        final View innerView =
                getLayoutInflater().inflate(R.layout.dialog_delete_confirm, null);
        ab.setView(innerView);

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                boolean rc = db.delete(num.getText().toString().trim());
                db.closeDB();
                if (!rc) {
                    message.setText("요청한 학생이 없습니다.");
                } else {
                    onSetNull();
                    message.setText("요청한 학생을 삭제하였습니다.");
                }

            }
        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(alertDialog);
            }
        });

        return ab.create();
    }

    private void setDismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    /* 수정 필요
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent intent) {
        if (requestCode == 1000) {

            if (resultCode == RESULT_OK) {




                if (s != null) {
                    onSetImage(s.getImageName());
                    if (D) Log.i(TAG, "s.getImageName(): " + s.getImageName());
                    if (D) Log.i(TAG, "s.getHakbun(): " + s.getHakbun());
                    message.setText("요청한 학번("+hakbun.getText().toString().trim()+")의 이미지를 저장하였습니다!");
                } else {
                    //setUINull();
                    message.setText("요청한 학번("+hakbun.getText().toString().trim()+")의 이미지가 저장되지 않았습니다!");
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
*/
    @Override
    public void onBackPressed() {

        Intent i = this.getIntent();
        args = new Bundle();
        if (s != null) {
            args.putSerializable("student", s);
        }
        i.putExtras(args);

        this.setResult(RESULT_OK, i);
        if (D) Log.i(TAG, "args: " + args.toString());

        finish();
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        super.onDestroy();

    }

    private class PhotographyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {



            if (D) Log.i(TAG + "-> PhotographyActivity", "args: " + args.toString());
        }
    }

}
