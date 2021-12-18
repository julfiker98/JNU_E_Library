package com.jnu.jnuelibrary.Librarian;

import android.os.Bundle;
import android.widget.Toast;

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
import com.jnu.jnuelibrary.Adapter.BorrowAdapter;
import com.jnu.jnuelibrary.Model.BorrowModel;
import com.jnu.jnuelibrary.R;

import java.util.ArrayList;
import java.util.List;

public class BorrowRequestListActivity extends AppCompatActivity {
    List<BorrowModel> borrowList;
    RecyclerView recyclerView;
    BorrowAdapter borrowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_request_list);

        recyclerView = findViewById(R.id.recyclerView_id);
        borrowList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getBorrowList();
    }

    private void getBorrowList() {

        DatabaseReference ref_borrow = FirebaseDatabase.getInstance().getReference("borrow_request");
        ref_borrow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    borrowList.clear();
                    for (DataSnapshot ds:snapshot.getChildren()){
                        BorrowModel borrowModel = ds.getValue(BorrowModel.class);
                        borrowList.add(borrowModel);
                    }

                    borrowAdapter = new BorrowAdapter(getApplicationContext(),borrowList);
                    recyclerView.setAdapter(borrowAdapter);
                    borrowAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}