package com.jnu.jnuelibrary.Student;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jnu.jnuelibrary.Adapter.AdapterBorrowList;
import com.jnu.jnuelibrary.Adapter.StudentBorrowAdapter;
import com.jnu.jnuelibrary.Model.ModelBorrowList;
import com.jnu.jnuelibrary.R;

import java.util.ArrayList;
import java.util.List;


public class MyBorrowListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ModelBorrowList> myBorrowList;
    StudentBorrowAdapter studentBorrowAdapter;
    String my_student_id = MainActivity.id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrow_list);
        this.setTitle("My Borrow Request");

        recyclerView = findViewById(R.id.recyclerView_id);
        myBorrowList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getMyBorrowList();


    }

    private void getMyBorrowList() {

        DatabaseReference ref_borrow = FirebaseDatabase.getInstance().getReference("borrow_list");
        ref_borrow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    myBorrowList.clear();
                    for (DataSnapshot ds:snapshot.getChildren()){
                        ModelBorrowList modelBorrowList = ds.getValue(ModelBorrowList.class);

                        if (my_student_id.equals(modelBorrowList.getSt_id())){
                            myBorrowList.add(modelBorrowList);
                        }

                    }

                    studentBorrowAdapter = new StudentBorrowAdapter(getApplicationContext(),myBorrowList);
                    recyclerView.setAdapter(studentBorrowAdapter);
                    studentBorrowAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}