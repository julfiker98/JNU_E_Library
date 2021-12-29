package com.jnu.jnuelibrary.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jnu.jnuelibrary.R;

public class NoticeActivity extends AppCompatActivity {
    TextView noticeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        noticeTv = findViewById(R.id.noticeTv_id);
        getNotice();
    }

    private void getNotice() {
        DatabaseReference ref_notice = FirebaseDatabase.getInstance().getReference("notice");
        ref_notice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String noticeTxt = snapshot.getValue(String.class);
                    noticeTv.setText(noticeTxt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}