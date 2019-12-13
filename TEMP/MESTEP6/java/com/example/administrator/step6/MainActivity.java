package com.example.administrator.step6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;



import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;

import android.content.Intent;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.example.administrator.step6.Adapter.SpinnerAdapter;
import com.example.administrator.step6.App.AppConfig;
import com.example.administrator.step6.Helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true; // debug

    Bundle args;
    AppConfig ac;

    String imageStoragePath;
    public static final int BITMAP_SAMPLE_SIZE = 8;

    //for UI
    private ImageView imgPreview;
    private VideoView videoPreview;
    private EditText hakbun, name, phone;
    private Button btSelect, btSave, btClear, btDelete;
    private TextView message;
    private #

    //for spinner UI
    Spinner hakgoaSpinner;
    List<String> hakgoas;
    List<String> hakgoasKor;

    // database
    DatabaseHelper db;

    // adapters
    SpinnerAdapter spinnerAdapter;
    #r;

    // student table
    Student s;
    List<Student> ss = new ArrayList<Student>();

    // code table
    Code c;
    List<Code> cs = new ArrayList<Code>();

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (D) Log.i(TAG, "just started!!");

        //Activity에서 전달된 데이터를 가져옴니다
        args = getIntent().getExtras() != null ? getIntent().getExtras() : new Bundle();
        ac = args.getSerializable("mVar") != null ?
                (AppConfig) args.getSerializable("mVar") : new AppConfig();

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
                    setCurrentMarkListViewItemNewAddedStudent(s.getHakbun(),stuAdapter);
                    setCurrentMarkListViewItem();
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
                    notifyListItems();
                    setCurrentMarkListViewItemNewAddedStudent(s.getHakbun(),stuAdapter);
                    if (D) Log.i(TAG, "ac.getListViewItemPositionStudentAdapter(): " + ac.getListViewItemPositionStudentAdapter());ac.toString();
                    setCurrentMarkListViewItem();
                    message.setText("요청한 학번("+hakbun.getText().toString().trim()+")을 저장하였습니다.");
                } else {
                    onSetUINulls();
                    message.setText("요청한 학번("+hakbun.getText().toString().trim()+")은 저장되지 않았습니다.");
                }
            }
        });

        message = findViewById(R.id.message);

        listView = findViewById(#);
        stuAdapter = new StudentAdapter(#);
        listView.setAdapter(#);

        if (# > 0) {
            setCurrentMarkListViewItem();
            if (D) Log.i(TAG, "ac.getListViewItemPositionStudentAdapter(): " + ac.getListViewItemPositionStudentAdapter());
            onSetUI(#);
            message.setText(ss.size() + "명이 조회되었습니다.");

        }

        try {

            listView.setOnItemClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (notifyListItems() > 0) {
            setCurrentMarkListViewItem();
        }
    }

    public void setCurrentMarkListViewItem() {

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                stuAdapter.setClickMode(false);
                stuAdapter.setSellectedPosition(
                        ac.getListViewItemPositionStudentAdapter()
                );

                listView.setItemChecked(
                        ac.getListViewItemPositionStudentAdapter(), true);
                listView.smoothScrollToPosition(
                        ac.getListViewItemPositionStudentAdapter());

            }
        };

        handler.postDelayed(runnable, 500);
        stuAdapter.notifyDataSetChanged();
    }

    public int notifyListItems() {

        stuAdapter.#;
        ss = db.#;
        if(ss != null) {

            for (#) {
                stuAdapter.#;
                if (D) Log.i(TAG, s.toString());
            }

            listView.postDelayed(new Runnable() {
                public void run() {
                    AppListViewHeight.setListViewHeightBasedOnChildren(listView);
                }
            }, 400);

            stuAdapter.#;
        }

        return #;
    }

    public void setCurrentMarkListViewItemNewAddedStudent(String hakbun, StudentAdapter studentAdapter){

        int pos = 0;
        for (final Student s : studentAdapter.getStudents()) {
            if(s.getHakbun().equals(hakbun))
                break;
            else
                pos += 1;
        }
        ac.setListViewItemPositionStudentAdapter(pos);
        if (D) Log.i(TAG, "setListViewItemPositionStudentAdapter : " + pos);
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

    public void onSetUI(Student s) {

        hakbun.setText(s.getHakbun());
        name.setText(s.getName());
        phone.setText(s.getPhone());
        hakgoaSpinner.setSelection(hakgoas.indexOf(s.getHakgoa()));

        onSetImage(s.getImageName());

    }

    private void onSetUI(int position) {

        if(!stuAdapter.isEmpty()) {
            s = db.selectStudent(stuAdapter.getItem(position).getHakbun());
            onSetUI(s);
        }
        else
            onSetUINulls();

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
        hakgoaSpinner.setSelection(hakgoas.indexOf(0));

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
                    notifyListItems();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view,
                            int position, long id) {

        ac.setListViewItemPositionStudentAdapter(#);
        args.putSerializable("ac", ac);

        s = stuAdapter.getStudent(#);
        if(D) Log.i(TAG,"onItemClick(): "+s.toString());
        args.putSerializable("student", s);
        stuAdapter.#;

        stuAdapter.setClickMode(false);
        stuAdapter.setSellectedPosition(position);
        view.setBackgroundColor(0x9934B5E4);
        view.invalidate();

        s = db.selectStudent(#);
        db.closeDB();
        onSetUI(s);
        message.setText("리스트에서 학번( "+s.getHakbun()+" )을 선택합니다.");

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
