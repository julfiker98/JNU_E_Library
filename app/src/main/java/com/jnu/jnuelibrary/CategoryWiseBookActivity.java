package com.jnu.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jnu.jnuelibrary.Adapter.AdapterBookList;
import com.jnu.jnuelibrary.Adapter.TransactionAdapter;
import com.jnu.jnuelibrary.Model.ModelBookList;
import com.jnu.jnuelibrary.Model.TransactionModel;
import com.jnu.jnuelibrary.Student.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryWiseBookActivity extends AppCompatActivity {
    List<ModelBookList> bookList;
    AdapterBookList adapterBookList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_book);

        recyclerView = findViewById(R.id.recyclerView_id);

        bookList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Intent intent = getIntent();
        if (intent!=null){
            String type = intent.getStringExtra("type");
            getBooks(type);
        }


    }

    private void getBooks(String type) {

        DatabaseReference ref_borrow = FirebaseDatabase.getInstance().getReference("bookList");
        ref_borrow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    bookList.clear();
                    for (DataSnapshot ds:snapshot.getChildren()){
                        ModelBookList bookListModel = ds.getValue(ModelBookList.class);
                        if (bookListModel.getBook_type().equals(type)){
                            bookList.add(bookListModel);
                        }


                    }

                    adapterBookList = new AdapterBookList(getApplicationContext(),bookList);
                    recyclerView.setAdapter(adapterBookList);
                    adapterBookList.notifyDataSetChanged();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}