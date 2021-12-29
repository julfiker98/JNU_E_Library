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

public class UpdateBookActivity extends AppCompatActivity {

    String book_name="";
    String writer_name="";
    String serial_no ="";
    String isbn_no="";
    String type="";
    String link="";

    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        updateBtn = findViewById(R.id.updateBtn_id);

        EditText nameEt = findViewById(R.id.nameET_View);
        EditText writerEt = findViewById(R.id.writerET_View);
        EditText typeEt = findViewById(R.id.typeET_View);
        EditText isbnEt = findViewById(R.id.isbnET_View);
        EditText linkEt = findViewById(R.id.linkET_View);


        Intent intent = getIntent();
        if (intent!=null){
            book_name = intent.getStringExtra("name");
            writer_name = intent.getStringExtra("writer");
            serial_no = intent.getStringExtra("serial");
            isbn_no = intent.getStringExtra("isbn");
            type = intent.getStringExtra("type");
            link = intent.getStringExtra("link");

            nameEt.setText(book_name);
            writerEt.setText(writer_name);
            typeEt.setText(type);
            isbnEt.setText(isbn_no);
            linkEt.setText(link);

        }

        updateBtn.setOnClickListener(v->{

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("bookList");
            String name = nameEt.getText().toString();
            String writer = writerEt.getText().toString();
            String type = typeEt.getText().toString();
            String isbn = isbnEt.getText().toString();
            String link = linkEt.getText().toString();

            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("book_name",name);
            hashMap.put("writer_name",writer);
            hashMap.put("book_type",type);
            hashMap.put("isbn_no",isbn);
            hashMap.put("book_link",link);

            reference.child(serial_no).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

}