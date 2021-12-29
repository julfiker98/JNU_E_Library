package com.jnu.jnuelibrary.Librarian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jnu.jnuelibrary.R;

import java.util.HashMap;

public class EditStudentActivity extends AppCompatActivity {
    EditText nameEt,idEt,sessionEt;
    Button updateBtn;
    String uid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        nameEt = findViewById(R.id.nameEt_id);
        idEt = findViewById(R.id.idEt_id);
        sessionEt = findViewById(R.id.sessionEt_id);
        updateBtn = findViewById(R.id.updateBtn_id);

        Intent intent = getIntent();
        if (intent!=null){
            nameEt.setText(intent.getStringExtra("name"));
            idEt.setText(intent.getStringExtra("id"));
            sessionEt.setText(intent.getStringExtra("session"));
            uid = intent.getStringExtra("uid");
        }


        updateBtn.setOnClickListener(v->{
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("studentList");

            String name = nameEt.getText().toString();
            String id = idEt.getText().toString();
            String session = sessionEt.getText().toString();

            if (name.isEmpty() ||id.isEmpty() || session.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter all field", Toast.LENGTH_SHORT).show();
            }else{

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",name);
                hashMap.put("id",id);
                hashMap.put("session",session);


                ref.child(uid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),StudentListActivity.class));
                    }
                });
            }

        });
    }
}