package com.jnu.jnuelibrary;

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
import com.jnu.jnuelibrary.Adapter.AdapterBookList;
import com.jnu.jnuelibrary.Adapter.AdapterBorrowList;
import com.jnu.jnuelibrary.Model.ModelBookList;
import com.jnu.jnuelibrary.Model.ModelBorrowList;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ModelBookList> bookList;
    AdapterBookList adapterBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        this.setTitle("Book List");

        recyclerView = findViewById(R.id.recyclerView_id);
        bookList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getBookList();

    }

    private void getBookList() {
        DatabaseReference ref_borrow = FirebaseDatabase.getInstance().getReference("bookList");
        ref_borrow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    bookList.clear();
                    for (DataSnapshot ds:snapshot.getChildren()){
                        ModelBookList bookListModel = ds.getValue(ModelBookList.class);
                        bookList.add(bookListModel);
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