package com.jnu.jnuelibrary.Librarian;

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
import com.jnu.jnuelibrary.Model.BorrowModel;
import com.jnu.jnuelibrary.Model.ModelBorrowList;
import com.jnu.jnuelibrary.R;

import java.util.ArrayList;
import java.util.List;


public class BorrowListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ModelBorrowList> borrowList;
    AdapterBorrowList adapterBorrowList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_list);
        this.setTitle("Borrow List");
        recyclerView = findViewById(R.id.recyclerView_id);
        borrowList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getBorrowList();

    }

    private void getBorrowList() {

        DatabaseReference ref_borrow = FirebaseDatabase.getInstance().getReference("borrow_list");
        ref_borrow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    borrowList.clear();
                    for (DataSnapshot ds:snapshot.getChildren()){
                        ModelBorrowList modelBorrowList = ds.getValue(ModelBorrowList.class);
                        borrowList.add(modelBorrowList);
                    }

                    adapterBorrowList = new AdapterBorrowList(getApplicationContext(),borrowList);
                    recyclerView.setAdapter(adapterBorrowList);
                    adapterBorrowList.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}