package com.example.test5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.test5.Adapter.SpinnerAdapter;
import com.example.test5.App.AppCamera;
import com.example.test5.Model.Code;
import com.example.test5.Model.Student;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    Bundle args;

    String imageStoragePath;
    public static final int BITMAP_SAMPLE_SIZE = 8;

    //for UI
    private ImageView imgPreview;
    private VideoView videoPreview;
    private EditText hakbun, name, phone;
    Button btSelect, btSave, btClear, btDelete;
    private TextView message;



    //for spinner UI
    private Spinner hakgoaSpinner;
    private Spinner grade;
    private Spinner classes;
    private Spinner status;
    private Spinner mento;

    List<String> hakgoas;
    List<String> hakgoasKor;

    List<String> gradeno;
    List<String> gradekor;

    List<String> classno;
    List<String> classkor;

    List<String> statusno;
    List<String> statuskor;

    List<String> mentono;
    List<String> mentokor;


    // adapters
    SpinnerAdapter spinnerAdapter;

    // student table
    Student s;
    List<Student> ss = new ArrayList<Student>();
    // code table
    Code c;
    List<Code> cs = new ArrayList<Code>();

    // database
    DatabaseHelper db;



    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (D) Log.i(TAG, "just started!!");

        //Activity에서 전달된 데이터를 가져옴니다
        args = getIntent().getExtras() != null ?  getIntent().getExtras() : new Bundle();

        db = new DatabaseHelper(getApplicationContext());

        imgPreview = findViewById(R.id.imgPreview);
        videoPreview = findViewById(R.id.videoPreview);
        imgPreview.setOnClickListener(new PhotographyListener());
        videoPreview.setOnClickListener(new PhotographyListener());

        hakbun = (EditText) findViewById(R.id.hakbun);
        name = (EditText) findViewById(R.id.name);

        phone = (EditText) findViewById(R.id.phone);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());




        hakgoaSpinner = (Spinner) findViewById(R.id.spinner_hakgoa);
        grade = (Spinner) findViewById(R.id.grade);
        classes = (Spinner) findViewById(R.id.classes);
        status = (Spinner) findViewById(R.id.status);
        mento = (Spinner) findViewById(R.id.mento);


        ViewGroup.LayoutParams params_hakgoa = hakgoaSpinner.getLayoutParams();
        params_hakgoa.height = 120;
        hakgoaSpinner.setLayoutParams(params_hakgoa);
        makeHakgoaSpinner();

        btSelect = (Button) findViewById(R.id.btn_select);
        ViewGroup.LayoutParams params_search = btSelect.getLayoutParams();
        params_search.height = 120;
        btSelect.setLayoutParams(params_search);
        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = db.selectStudent(hakbun.getText().toString().trim());
                db.closeDB();
                if (s != null) {
                    onSetUI(s);
                    message.setText("요청한 학번("+hakbun.getText().toString().trim()+")을 조회하였습니다.");
                } else {
                    onSetUINulls();
                    message.setText("요청한 학번("+hakbun.getText().toString().trim()+")은 없습니다!");
                }
            }
        });


        btClear = (Button) findViewById(R.id.btn_clear);
        ViewGroup.LayoutParams params_clear = btClear.getLayoutParams();
        params_clear.height = 120;
        btClear.setLayoutParams(params_clear);
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hakbun.setText("");
                onSetUINulls();
            }
        });

        btDelete = (Button) findViewById(R.id.btn_delete);
        ViewGroup.LayoutParams params_delete = btDelete.getLayoutParams();
        params_delete.height = 120;
        btDelete.setLayoutParams(params_delete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = deleteConfirm();
                alertDialog.show();
            }
        });

        btSave = (Button) findViewById(R.id.btn_save);
        ViewGroup.LayoutParams params_save = btSave.getLayoutParams();
        params_save.height = 120;
        btSave.setLayoutParams(params_save);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (s != null) {
                    s = db.insertOrUpdateStudent(
                            hakbun.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            s.getImageName(),
                            hakgoas.get(hakgoaSpinner.getSelectedItemPosition()),
                            gradeno.get(grade.getSelectedItemPosition()),
                            classno.get(classes.getSelectedItemPosition()),
                            statusno.get(status.getSelectedItemPosition()),
                            mentono.get(mento.getSelectedItemPosition()));
                }
                else {
                    s = db.insertOrUpdateStudent(
                            hakbun.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            hakgoas.get(hakgoaSpinner.getSelectedItemPosition()),
                            gradeno.get(grade.getSelectedItemPosition()),
                            classno.get(classes.getSelectedItemPosition()),
                            statusno.get(status.getSelectedItemPosition()),
                            mentono.get(mento.getSelectedItemPosition()));
                }
                db.closeDB();
                if (s != null) {
                    onSetUI(s);
                    message.setText("요청한 학번("+hakbun.getText().toString().trim()+")을 저장하였습니다.");
                } else {
                    onSetUINulls();
                    message.setText("요청한 학번("+hakbun.getText().toString().trim()+")은 저장되지 않았습니다.");
                }
            }
        });

        message = (TextView) findViewById(R.id.message);

        makeGradeSpinner();
        makeClassSpinner();
        makeStatusSpinner();
        makeMentoSpinner();
    }

    @Override
    public void onStart() {
        super.onStart();

    }



    public void makeHakgoaSpinner() {

        setListHakgoas();
        spinnerAdapter = new SpinnerAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, hakgoasKor);
        hakgoaSpinner.setAdapter(spinnerAdapter);
        if (hakgoasKor.size() > 0) {
            hakgoaSpinner.setSelection(hakgoaSpinner.getSelectedItemPosition());
        } else {
            hakgoas.add("404");
            hakgoasKor.add("개설학과가 생성되지 않았습니다!");
            hakgoaSpinner.setSelection(0);
        }
        spinnerAdapter.notifyDataSetChanged();
    }
    public void setListHakgoas() {

        hakgoas = new ArrayList<String>();
        hakgoasKor = new ArrayList<String>();

        cs = db.selectCode("hakgoa");
        db.closeDB();
        for (int i = 0; i < cs.size(); i++) {
            hakgoas.add(cs.get(i).getCode());
            hakgoasKor.add(cs.get(i).getCodeKor());
        }
    }

    public void setListGrade(){
        gradeno = new ArrayList<String>();
        gradekor = new ArrayList<String>();

        cs = db.selectCode("grade");
        db.closeDB();

        for(int q = 0; q < cs.size(); q++){
            gradeno.add(cs.get(q).getCode());
            gradekor.add(cs.get(q).getCodeKor());
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

        cs = db.selectCode("classes");
        db.closeDB();

        for(int q = 0; q < cs.size(); q++){
            classno.add(cs.get(q).getCode());
            classkor.add(cs.get(q).getCodeKor());
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

        cs = db.selectCode("status");
        db.closeDB();

        for(int q = 0; q < cs.size(); q++){
            statusno.add(cs.get(q).getCode());
            statuskor.add(cs.get(q).getCodeKor());
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

        cs = db.selectCode("mento");
        db.closeDB();

        for(int q = 0; q < cs.size(); q++){
            mentono.add(cs.get(q).getCode());
            mentokor.add(cs.get(q).getCodeKor());
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


    public void onSetUI(Student s) {

        hakbun.setText(s.getHakbun());
        name.setText(s.getName());
        phone.setText(s.getPhone());
        hakgoaSpinner.setSelection(hakgoas.indexOf(s.getHakgoa()));

        onSetImage(s.getImageName());

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
        return TextUtils.isEmpty(fileExtension) ? null : fileExtension;
    }

    public void onSetUINulls() {

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

                boolean rc = db.deleteStudent(hakbun.getText().toString().trim());
                db.closeDB();
                if (!rc) {
                    message.setText("요청한 학생이 없습니다.");
                } else {
                    onSetUINulls();
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



    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent intent) {
        if (requestCode == 1000) {

            if (resultCode == RESULT_OK) {
                imageStoragePath = intent.getStringExtra("imageStoragePath");
                if (D) Log.i(TAG, "imageStoragePath: " + imageStoragePath);

                s = db.saveImageName(hakbun.getText().toString(), imageStoragePath);
                db.closeDB();
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

            Intent i = new Intent(MainActivity.this, PhotographyActivity.class);
            args.putSerializable("student", s);
            i.putExtras(args);

            startActivityForResult(i, 1000);

            if (D) Log.i(TAG + "-> PhotographyActivity", "args: " + args.toString());
        }
    }

}
