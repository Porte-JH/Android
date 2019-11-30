package info.example.app1student35;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import java.util.ArrayList;
import java.util.List;

import info.example.app1student35.adapter.SpinnerAdapter;
import info.example.app1student35.app.AppCamera;
import info.example.app1student35.helper.DatabaseHelper;
import info.example.app1student35.model.Code;
import info.example.app1student35.model.Student;


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
    Spinner hakgoaSpinner;
    List<String> hakgoas;
    List<String> hakgoasKor;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (D) Log.i(TAG, "just started!!");

        //Activity에서 전달된 데이터를 가져옴니다
        args = getIntent().getExtras() != null ?  getIntent().getExtras() : new Bundle();

        db = new DatabaseHelper(getApplicationContext());

        imgPreview = findViewById(R.id.imgPreview);
        videoPreview = findViewById(R.id.videoPreview);


        hakbun = (EditText) findViewById(R.id.hakbun);
        name = (EditText) findViewById(R.id.name);

        phone = (EditText) findViewById(R.id.phone);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        hakgoaSpinner = (Spinner) findViewById(R.id.spinner_hakgoa);
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
                            hakgoas.get(hakgoaSpinner.getSelectedItemPosition())
                    );
                }
                else {
                    s = db.insertOrUpdateStudent(
                            hakbun.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            hakgoas.get(hakgoaSpinner.getSelectedItemPosition())
                    );
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
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void makeHakgoaSpinner() {

        setListHakgoas();
        spinnerAdapter = new SpinnerAdapter( , , );
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


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    /**
     * Displaying video in VideoView
     */
    private void previewVideo(String imageName) {

        try {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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
