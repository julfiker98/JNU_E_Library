package com.jnu.jnuelibrary.Librarian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jnu.jnuelibrary.Adapter.AdapterBorrowList;
import com.jnu.jnuelibrary.Adapter.AdapterStudent;
import com.jnu.jnuelibrary.Model.ModelBorrowList;
import com.jnu.jnuelibrary.Model.StudentModel;
import com.jnu.jnuelibrary.R;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<StudentModel> studentList;
    AdapterStudent adapterStudent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        studentList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_id);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getStudentList();
    }

    private void getStudentList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("studentList");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    studentList.clear();
                    for (DataSnapshot ds:snapshot.getChildren()){
                        StudentModel studentModel = ds.getValue(StudentModel.class);
                        studentList.add(studentModel);
                    }
                    adapterStudent = new AdapterStudent(getApplicationContext(),studentList);
                    recyclerView.setAdapter(adapterStudent);
                    adapterStudent.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}