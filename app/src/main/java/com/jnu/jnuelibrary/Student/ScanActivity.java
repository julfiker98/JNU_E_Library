package com.jnu.jnuelibrary.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jnu.jnuelibrary.R;

import java.util.HashMap;

public class ScanActivity extends AppCompatActivity {
    Button scanbtn,readBtn,borrowBtn;
    public static TextView scantext;

    String mName,mEmail,mId,mSession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scantext=(TextView)findViewById(R.id.scantext);
        scanbtn=(Button) findViewById(R.id.scanbtn);
        readBtn = findViewById(R.id.readBtn_id);
        borrowBtn = findViewById(R.id.borrowBtn_id);

        scan();

        readBtn.setOnClickListener(v->{
            readOnline();
        });
        borrowBtn.setOnClickListener(v->{
            sendBorrowRequest();
        });

        getStudentInfo();



    }

    private void getStudentInfo() {
        String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref_student = FirebaseDatabase.getInstance().getReference("studentList");

        ref_student.child(myUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    mName = snapshot.child("name").getValue(String.class);
                    mEmail = snapshot.child("email").getValue(String.class);
                    mId = snapshot.child("id").getValue(String.class);
                    mSession = snapshot.child("session").getValue(String.class);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendBorrowRequest() {

        String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref_borrow = FirebaseDatabase.getInstance().getReference("borrow_request");
        String timeStamp = String.valueOf(System.currentTimeMillis());

        String book_name = ScannerView.s1;
        String writer_name = ScannerView.s2;
        String book_type = ScannerView.s3;
        String serial_no = ScannerView.s4;
        String isbn_no = ScannerView.s5;

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("st_name",mName);
        hashMap.put("st_email",mEmail);
        hashMap.put("st_id",mId);
        hashMap.put("session",mSession);
        hashMap.put("uid",myUid);
        hashMap.put("timeStamp",timeStamp);

        hashMap.put("book_name",book_name);
        hashMap.put("writer_name",writer_name);
        hashMap.put("book_type",book_type);
        hashMap.put("serial_no",serial_no);
        hashMap.put("isbn_no",isbn_no);

        ref_borrow.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Request Submitted !!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void readOnline() {
        Uri uri1 = Uri.parse(ScannerView.s6.trim()); // missing 'http://' will cause crashed
        Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
        startActivity(intent1);

    }

    private void scan() {
        startActivity(new Intent(getApplicationContext(), ScannerView.class));
        String s = "fdsjsfdhjksdsdf,shfsfksfs";
        String[] splite = s.split("\n");
        s.replace(","," ");

    }


}