package com.jnu.jnuelibrary.Student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jnu.jnuelibrary.BookListActivity;
import com.jnu.jnuelibrary.Librarian.TransactionListActivity;
import com.jnu.jnuelibrary.LoginActivity;
import com.jnu.jnuelibrary.R;
import com.jnu.jnuelibrary.databinding.ActivityMainBinding;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ActivityMainBinding binding;
    TextView nameTv,emailTv,idTv,sessionTv;
    ImageView qrIv;
    AlertDialog.Builder builder;
    public static String id="";

    public static String tag="";

    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        builder = new AlertDialog.Builder(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //Navigation Drawer finding
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);  //

        nameTv = findViewById(R.id.nameTv);
        emailTv = findViewById(R.id.emailTv);
        idTv = findViewById(R.id.idTv);
        sessionTv = findViewById(R.id.sessionTv);
        qrIv = findViewById(R.id.qrIv_id);

        SharedPreferences sharedPreferences = getSharedPreferences("jnu", Context.MODE_PRIVATE);
        tag = sharedPreferences.getString("tag", "No tag Found !");

        loadStudentInfo();

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
                        deleteCache(MainActivity.this);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.nav_scan){
            startActivity(new Intent(getApplicationContext(),ScanActivity.class));
        }
        if (id==R.id.nav_borrow){
            startActivity(new Intent(getApplicationContext(),MyBorrowListActivity.class));
        }
        if (id==R.id.nav_books){
            startActivity(new Intent(getApplicationContext(), BookListActivity.class));
        }

        if (id==R.id.nav_return){
            startActivity(new Intent(getApplicationContext(), MyReturnRequestListActivity.class));
        }
        if (id==R.id.nav_transaction){
            startActivity(new Intent(getApplicationContext(), TransactionListActivity.class));
        }
        if (id==R.id.nav_wishlist){
            Toast.makeText(getApplicationContext(), "Coming soon....", Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.nav_review){
            Toast.makeText(getApplicationContext(), "Coming soon....", Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.nav_announcement){
            startActivity(new Intent(getApplicationContext(),NoticeActivity.class));
        }


        return false;
    }




    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }



  private void loadStudentInfo(){
        DatabaseReference ref_st = FirebaseDatabase.getInstance().getReference("studentList");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref_st.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    id = snapshot.child("id").getValue(String.class);
                    String session = snapshot.child("session").getValue(String.class);

                    nameTv.setText(name);
                    emailTv.setText(email);
                    idTv.setText(id);
                    sessionTv.setText(session);

                    String s = name+"\n"+email+"\n"+id+"\n"+session;

                    getSupportActionBar().setTitle(name);

                    generateQr(s);

                    updateNavHeader(name,email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
   }

   private void generateQr(String s){

       MultiFormatWriter writer = new MultiFormatWriter();
       try {
           BitMatrix matrix = writer.encode(s, BarcodeFormat.QR_CODE,300,300);
           BarcodeEncoder encoder = new BarcodeEncoder();
           Bitmap bitmap = encoder.createBitmap(matrix);
           qrIv.setImageBitmap(bitmap);
          // InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


       } catch (WriterException e) {
           e.printStackTrace();
       }

   }

   public void updateNavHeader(String name, String email){

       NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
       View headerView = navigationView.getHeaderView(0);
       TextView nav_userNameTv = headerView.findViewById(R.id.nav_usernameTv_id);
       TextView nav_emailTv = headerView.findViewById(R.id.nav_emailTv_id);
       ImageView nav_imageView = headerView.findViewById(R.id.nav_imageView_id);

       nav_userNameTv.setText(name);
       nav_emailTv.setText(email);

   }



}