package com.jnu.jnuelibrary.Librarian;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.jnu.jnuelibrary.BookListActivity;
import com.jnu.jnuelibrary.LoginActivity;
import com.jnu.jnuelibrary.R;
import com.jnu.jnuelibrary.Student.MainActivity;

import java.io.File;

public class DashboardActivity extends AppCompatActivity {
  CardView manageCv,borrowReqCv,borrowListCv,returnCv,transactionCv,bookListCv,announcementCv,studentManagementCv;

  AlertDialog.Builder builder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);
    this.setTitle("Librarian Dashboard");

    manageCv = findViewById(R.id.manageCv_id);
    borrowReqCv = findViewById(R.id.borrowRequestCV_id);
    borrowListCv = findViewById(R.id.borrowListCv_id);
    returnCv = findViewById(R.id.returnCv_id);
    transactionCv = findViewById(R.id.transactionCv_id);
    bookListCv = findViewById(R.id.bookListCv_id);
    announcementCv = findViewById(R.id.announcementCv_id);
    studentManagementCv = findViewById(R.id.studentManagementCv_id);
    builder = new AlertDialog.Builder(this);

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
    bookListCv.setOnClickListener(v->{
      startActivity(new Intent(getApplicationContext(), BookListActivity.class));
    });
    announcementCv.setOnClickListener(v->{
      startActivity(new Intent(getApplicationContext(), AnouncementActivity.class));
    });

    studentManagementCv.setOnClickListener(v->{
      startActivity(new Intent(getApplicationContext(), StudentListActivity.class));
    });


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    if (id==R.id.logout_menu_id){
      logOut();
    }

    return super.onOptionsItemSelected(item);
  }

  private void logOut() {
    builder.setTitle("Log Out!");
    builder.setMessage("Are you sure want to log Out?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                //remove email reference shared preferences then go to login activity
                SharedPreferences sharedPreferences = getSharedPreferences("jnu", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                deleteCache(DashboardActivity.this);
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
              }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                //  Action for 'NO' Button
                dialog.cancel();
              }
            });
    //Creating dialog box
    AlertDialog alert = builder.create();
    alert.show();
  }

  //Clear Cache Data
  public static void deleteCache(Context context) {
    try {
      File dir = context.getCacheDir();
      deleteDir(dir);
    } catch (Exception e) { e.printStackTrace();}
  }

  public static boolean deleteDir(File dir) {
    if (dir != null && dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
      return dir.delete();
    } else if(dir!= null && dir.isFile()) {
      return dir.delete();
    } else {
      return false;
    }
  }



}