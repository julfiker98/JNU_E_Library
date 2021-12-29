package com.jnu.jnuelibrary.Librarian;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jnu.jnuelibrary.R;

public class ScanStudentProfileActivity extends AppCompatActivity {

    public static TextView scantextTv;
    public static TextView  statusTv;
    public static String student_id="";
    public static String time="";
    public static String book_name="";
    public static String s_uid="";
    public static String tag="";
    public static String serial_no="";
    public static String st_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_student_profile);

        scantextTv = findViewById(R.id.scantext_id);
        statusTv = findViewById(R.id.statusTv_id);
        Intent intent = getIntent();

        if (intent!=null){
            tag = intent.getStringExtra("tag");
            if (tag.equals("borrow")){
                student_id = intent.getStringExtra("st_id");
                time = intent.getStringExtra("time");
                book_name = intent.getStringExtra("book_name");
                s_uid = intent.getStringExtra("uid");
                serial_no = intent.getStringExtra("serial");
                // String st3 = ScannViewL.s3;

            }if(tag.equals("return")){
                student_id = intent.getStringExtra("st_id");
                st_name = intent.getStringExtra("st_name");
                time = intent.getStringExtra("time");
                book_name = intent.getStringExtra("book_name");
                serial_no = intent.getStringExtra("serial");
                // String st3 = ScannViewL.s3;
            }

        }

        scan();

    }

    private void scan() {
        startActivity(new Intent(getApplicationContext(), ScannViewL.class));
        String s = "fdsjsfdhjksdsdf,shfsfksfs";
        String[] splite = s.split("\n");
        s.replace(","," ");

    }

}