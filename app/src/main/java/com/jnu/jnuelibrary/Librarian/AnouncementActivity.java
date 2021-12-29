package com.jnu.jnuelibrary.Librarian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jnu.jnuelibrary.R;

public class AnouncementActivity extends AppCompatActivity {
    EditText noticeEt;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anouncement);

        noticeEt = findViewById(R.id.noticeEt_id);
        submitBtn = findViewById(R.id.submitBtn_id);

        getNotice();

        submitBtn.setOnClickListener(v->{
            String txt = noticeEt.getText().toString();

            if (txt.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please Write Something...", Toast.LENGTH_SHORT).show();
            }else{
                updateNotice(txt);
            }


        });
    }

    private void updateNotice(String text) {
        DatabaseReference ref_notice = FirebaseDatabase.getInstance().getReference("notice");
        ref_notice.setValue(text).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Notice Updated!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNotice() {
        DatabaseReference ref_notice = FirebaseDatabase.getInstance().getReference("notice");
        ref_notice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String noticeTxt = snapshot.getValue(String.class);
                    noticeEt.setText(noticeTxt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}