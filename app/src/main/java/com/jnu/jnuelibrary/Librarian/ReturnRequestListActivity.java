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
import com.jnu.jnuelibrary.Adapter.ReturnAdapter;
import com.jnu.jnuelibrary.Adapter.StudentBorrowAdapter;
import com.jnu.jnuelibrary.Model.BorrowModel;
import com.jnu.jnuelibrary.Model.ModelBorrowList;
import com.jnu.jnuelibrary.R;

import java.util.ArrayList;
import java.util.List;


public class ReturnRequestListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ModelBorrowList> returnList;
    ReturnAdapter returnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_request_list);

        recyclerView = findViewById(R.id.recyclerView_id);
        returnList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getAllReturnRequest();

    }

    private void getAllReturnRequest() {
        DatabaseReference ref_borrow = FirebaseDatabase.getInstance().getReference("return_request");
        ref_borrow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    returnList.clear();
                    for (DataSnapshot ds:snapshot.getChildren()){
                        ModelBorrowList modelBorrowList = ds.getValue(ModelBorrowList.class);
                        returnList.add(modelBorrowList);
                    }
                    returnAdapter = new ReturnAdapter(getApplicationContext(),returnList);
                    recyclerView.setAdapter(returnAdapter);
                    returnAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}