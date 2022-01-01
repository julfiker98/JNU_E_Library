package com.jnu.jnuelibrary.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jnu.jnuelibrary.R;
import com.jnu.jnuelibrary.databinding.ActivityStudentProfileBinding;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

public class StudentProfileActivity extends AppCompatActivity {
    private ActivityStudentProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStudentProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setTitle("My Profile");


        loadStudentInfo();
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
                    String  id = snapshot.child("id").getValue(String.class);
                    String session = snapshot.child("session").getValue(String.class);
                    String profileUrl = snapshot.child("url").getValue(String.class);

                    binding.nameTv.setText(name);
                    binding.emailTv.setText(email);
                    binding.idTv.setText(id);
                    binding.sessionTv.setText(session);

                    try {
                        Picasso.get().load(profileUrl).into(binding.profileIvId);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    String s = name+"\n"+email+"\n"+id+"\n"+session;

                    getSupportActionBar().setTitle(name);

                    generateQr(s);

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
            binding.qrIvId.setImageBitmap(bitmap);
            // InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}