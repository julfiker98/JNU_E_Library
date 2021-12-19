package com.jnu.jnuelibrary.Student;

import android.content.Intent;
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
import com.jnu.jnuelibrary.R;
import com.jnu.jnuelibrary.databinding.ActivityMainBinding;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ActivityMainBinding binding;
    TextView nameTv,emailTv,idTv,sessionTv;
    ImageView qrIv;

    public static String id="";

    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);

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
        if (id==R.id.action_settings){
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.nav_scan){
           // Toast.makeText(getApplicationContext(), "Working", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),ScanActivity.class));
        }
        if (id==R.id.nav_borrow){
            startActivity(new Intent(getApplicationContext(),MyBorrowListActivity.class));
        }
        if (id==R.id.nav_books){
            startActivity(new Intent(getApplicationContext(), BookListActivity.class));
        }

        if (id==R.id.nav_return){
            Toast.makeText(getApplicationContext(), "Coming soon....", Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.nav_transaction){
            Toast.makeText(getApplicationContext(), "Coming soon....", Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.nav_wishlist){
            Toast.makeText(getApplicationContext(), "Coming soon....", Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.nav_review){
            Toast.makeText(getApplicationContext(), "Coming soon....", Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.nav_announcement){
            Toast.makeText(getApplicationContext(), "Coming soon....", Toast.LENGTH_SHORT).show();
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