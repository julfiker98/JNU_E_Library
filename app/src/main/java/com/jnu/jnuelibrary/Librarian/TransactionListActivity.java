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
import com.jnu.jnuelibrary.Adapter.BorrowAdapter;
import com.jnu.jnuelibrary.Adapter.TransactionAdapter;
import com.jnu.jnuelibrary.Model.BorrowModel;
import com.jnu.jnuelibrary.Model.TransactionModel;
import com.jnu.jnuelibrary.R;

import java.util.ArrayList;
import java.util.List;

public class TransactionListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<TransactionModel> transactionList;
    TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        recyclerView = findViewById(R.id.recyclerView_id);
        transactionList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getTransaction();

    }

    private void getTransaction() {

        DatabaseReference ref_borrow = FirebaseDatabase.getInstance().getReference("transactions");
        ref_borrow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    transactionList.clear();
                    for (DataSnapshot ds:snapshot.getChildren()){
                        TransactionModel transactionModel = ds.getValue(TransactionModel.class);
                        transactionList.add(transactionModel);
                    }

                    transactionAdapter = new TransactionAdapter(getApplicationContext(),transactionList);
                    recyclerView.setAdapter(transactionAdapter);
                    transactionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}