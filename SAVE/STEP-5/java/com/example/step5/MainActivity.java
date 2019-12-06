package com.example.step5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.step5.Adapter.SpinnerAdapter;
import com.example.step5.App.AppCamera;
import com.example.step5.Model.Category;
import com.example.step5.Model.Student;

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
    private EditText num, name, phone;
    Button btn_select, btn_save, btn_clear, btn_delete;
    private TextView message;

    //for spinner UI
    Spinner dept_spinner;
    List<String> dept;
    List<String> deptKor;

    // adapters
    SpinnerAdapter spinnerAdapter;

    // student table
    Student s;
    List<Student> ss = new ArrayList<Student>();
    // Category table
    Category cg;
    List<Category> cgs = new ArrayList<Category>();

    // database
     DBHelper db;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (D) Log.i(TAG, "just started!!");

        //Activity에서 전달된 데이터를 가져옴니다
        args = getIntent().getExtras() != null ?  getIntent().getExtras() : new Bundle();

        db = new DBHelper(getApplicationContext());

        imgPreview = findViewById(R.id.imgPreview);
        videoPreview = findViewById(R.id.videoPreview);

        imgPreview.setOnClickListener(new PhotographyListener());
        videoPreview.setOnClickListener(new PhotographyListener());

        num = (EditText) findViewById(R.id.num);
        name = (EditText) findViewById(R.id.name);

        phone = (EditText) findViewById(R.id.phone);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        dept_spinner = (Spinner) findViewById(R.id.dept);
        ViewGroup.LayoutParams params_hakgoa = dept_spinner.getLayoutParams();
        params_hakgoa.height = 120;
        dept_spinner.setLayoutParams(params_hakgoa);
        makeHakgoaSpinner();

        btn_select = (Button) findViewById(R.id.btn_select);
        ViewGroup.LayoutParams params_search = btn_select.getLayoutParams();
        params_search.height = 120;
        btn_select.setLayoutParams(params_search);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = db.select_student(num.getText().toString().trim());
                db.closeDB();
                if (s != null) {
                    onSetUI(s);
                    message.setText("요청한 학번("+num.getText().toString().trim()+")을 조회하였습니다.");
                } else {
                    onSetUINulls();
                    message.setText("요청한 학번("+num.getText().toString().trim()+")은 없습니다!");
                }
            }
        });


        btn_clear = (Button) findViewById(R.id.btn_clear);
        ViewGroup.LayoutParams params_clear = btn_clear.getLayoutParams();
        params_clear.height = 120;
        btn_clear.setLayoutParams(params_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText("");
                onSetUINulls();
            }
        });

        btn_delete = (Button) findViewById(R.id.btn_delete);
        ViewGroup.LayoutParams params_delete = btn_delete.getLayoutParams();
        params_delete.height = 120;
        btn_delete.setLayoutParams(params_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = deleteConfirm();
                alertDialog.show();
            }
        });

        btn_save = (Button) findViewById(R.id.btn_save);
        ViewGroup.LayoutParams params_save = btn_save.getLayoutParams();
        params_save.height = 120;
        btn_save.setLayoutParams(params_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (s != null) {
                    s = db.insert_update(
                            num.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            s.getImageName(),
                            dept.get(dept_spinner.getSelectedItemPosition())
                    );
                }
                else {
                    s = db.insert_update(
                            num.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            dept.get(dept_spinner.getSelectedItemPosition())
                    );
                }
                db.closeDB();
                if (s != null) {
                    onSetUI(s);
                    message.setText("요청한 학번("+num.getText().toString().trim()+")을 저장하였습니다.");
                } else {
                    onSetUINulls();
                    message.setText("요청한 학번("+num.getText().toString().trim()+")은 저장되지 않았습니다.");
                }
            }
        });

        message = (TextView) findViewById(R.id.message);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void makeHakgoaSpinner() {

        setListHakgoas();
        // 메서드 인자 3개 문제
        spinnerAdapter = new SpinnerAdapter(getApplicationContext(),android.R.layout.simple_spinner_item, deptKor);
        dept_spinner.setAdapter(spinnerAdapter);
        if (deptKor.size() > 0) {
            dept_spinner.setSelection(dept_spinner.getSelectedItemPosition());
        } else {
            dept.add("404");
            deptKor.add("개설학과가 생성되지 않았습니다!");
            dept_spinner.setSelection(0);
        }
        spinnerAdapter.notifyDataSetChanged();
    }
    public void setListHakgoas() {

        dept = new ArrayList<String>();
        deptKor = new ArrayList<String>();

        cgs = db.select_category("dept");
        db.closeDB();
        for (int i = 0; i < cgs.size(); i++) {
            dept.add(cgs.get(i).getCode());
            deptKor.add(cgs.get(i).getCodeKor());
        }
    }

    public void onSetUI(Student s) {

        num.setText(s.getNum());
        name.setText(s.getName());
        phone.setText(s.getPhone());
        dept_spinner.setSelection(dept.indexOf(s.getDept()));

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

        try { //빈칸

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

        try { //빈칸

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

                boolean rc = db.delete_student(num.getText().toString().trim());
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

            if (resultCode == RESULT_OK) {//빈칸

                imageStoragePath = intent.getStringExtra("imageStoragePath");
                if (D) Log.i(TAG, "imageStoragePath: " + imageStoragePath);

                s = db.saveImageName(num.getText().toString(), imageStoragePath);
                db.closeDB();
                


                if (s != null) {
                    onSetImage(s.getImageName());
                    if (D) Log.i(TAG, "s.getImageName(): " + s.getImageName());
                    if (D) Log.i(TAG, "s.getHakbun(): " + s.getNum());
                    message.setText("요청한 학번("+num.getText().toString().trim()+")의 이미지를 저장하였습니다!");
                } else {
                    //setUINull();
                    message.setText("요청한 학번("+num.getText().toString().trim()+")의 이미지가 저장되지 않았습니다!");
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
        public void onClick(View v) { //빈칸

            Intent i = new Intent(MainActivity.this, PhotographyActivity.class);
            args.putSerializable("student", s);
            i.putExtras(args);

            startActivityForResult(i, 1000);

            if (D) Log.i(TAG + "-> PhotographyActivity", "args: " + args.toString());
        }
    }

}
