package com.jnu.jnuelibrary.Librarian;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.jnu.jnuelibrary.R;

public class DashboardActivity extends AppCompatActivity {
  CardView manageCv,borrowReqCv,borrowListCv,returnCv,transactionCv;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);
    this.setTitle("Dashboard");

    manageCv = findViewById(R.id.manageCv_id);
    borrowReqCv = findViewById(R.id.borrowRequestCV_id);
    borrowListCv = findViewById(R.id.borrowListCv_id);
    returnCv = findViewById(R.id.returnCv_id);
    transactionCv = findViewById(R.id.transactionCv_id);

    manageCv.setOnClickListener(v->{
      startActivity(new Intent(getApplicationContext(),GenaratePdfActivity.class));
    });
    borrowReqCv.setOnClickListener(v->{
      startActivity(new Intent(getApplicationContext(), BorrowRequestListActivity.class));
    });
    borrowListCv.setOnClickListener(v->{
      startActivity(new Intent(getApplicationContext(), BorrowListActivity.class));
    });
    returnCv.setOnClickListener(v->{
      startActivity(new Intent(getApplicationContext(), ReturnRequestListActivity.class));
    });
    transactionCv.setOnClickListener(v->{
      startActivity(new Intent(getApplicationContext(), TransactionListActivity.class));
    });


  }
}