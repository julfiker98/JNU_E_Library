package com.jnu.jnuelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jnu.jnuelibrary.Adapter.AdapterBookList;
import com.jnu.jnuelibrary.Adapter.AdapterBorrowList;
import com.jnu.jnuelibrary.Model.ModelBookList;
import com.jnu.jnuelibrary.Model.ModelBorrowList;
import com.jnu.jnuelibrary.Student.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {
    TextView proTv,mathTv,phyTv,engTv,elecTv,netTv;

    int pro=0;
    int math=0;
    int phy=0;
    int eng=0;
    int elec=0;
    int net=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        this.setTitle("Book Category");

        proTv = findViewById(R.id.programmingTv_id);
        mathTv = findViewById(R.id.mathTv_id);
        phyTv = findViewById(R.id.physicsTv_id);
        engTv = findViewById(R.id.englishTv_id);
        elecTv = findViewById(R.id.electronicsTv_id);
        netTv = findViewById(R.id.networkingTv_id);

        getBookList();

       //Toast.makeText(getApplicationContext(), "Tag: "+ MainActivity.tag, Toast.LENGTH_SHORT).show();

        proTv.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),CategoryWiseBookActivity.class);
            intent.putExtra("type","Programming");
            startActivity(intent);

        });

        mathTv.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),CategoryWiseBookActivity.class);
            intent.putExtra("type","Math");
            startActivity(intent);
        });

        phyTv.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),CategoryWiseBookActivity.class);
            intent.putExtra("type","Physics");
            startActivity(intent);
        });

        engTv.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),CategoryWiseBookActivity.class);
            intent.putExtra("type","English");
            startActivity(intent);
        });

        elecTv.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),CategoryWiseBookActivity.class);
            intent.putExtra("type","Electronics");
            startActivity(intent);
        });

        netTv.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),CategoryWiseBookActivity.class);
            intent.putExtra("type","Networking");
            startActivity(intent);
        });

    }


    private void getBookList() {
        DatabaseReference ref_borrow = FirebaseDatabase.getInstance().getReference("bookList");
        ref_borrow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds:snapshot.getChildren()){
                        ModelBookList bookListModel = ds.getValue(ModelBookList.class);

                        if (bookListModel.getBook_type().equals("Programming") && bookListModel.getStatus().equals("1")){
                            pro++;
                        }
                        if (bookListModel.getBook_type().equals("Math") && bookListModel.getStatus().equals("1")){
                            math++;
                        }
                        if (bookListModel.getBook_type().equals("Physics") && bookListModel.getStatus().equals("1")){
                            phy++;
                        }
                        if (bookListModel.getBook_type().equals("English") && bookListModel.getStatus().equals("1")){
                            eng++;
                        }
                        if (bookListModel.getBook_type().equals("Electronics") && bookListModel.getStatus().equals("1")){
                            elec++;
                        }

                        if (bookListModel.getBook_type().equals("Networking") && bookListModel.getStatus().equals("1")){
                            net++;
                        }
                    }

                    //set value to textview
                    proTv.setText("Programming ("+pro+")");
                    mathTv.setText("Mathematics ("+math+")");
                    phyTv.setText("Physics ("+phy+")");
                    engTv.setText("English ("+eng+")");
                    elecTv.setText("Electronics ("+elec+")");
                    netTv.setText("Networking ("+net+")");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}