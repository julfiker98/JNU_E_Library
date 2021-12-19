package com.jnu.jnuelibrary.Librarian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jnu.jnuelibrary.databinding.ActivityLibrarianLoginBinding;

import java.util.HashMap;

public class LibrarianLoginActivity extends AppCompatActivity {
    private ActivityLibrarianLoginBinding binding;
    private FirebaseAuth mAuth;

    String email_l="";
    String pass_l="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLibrarianLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setTitle("Librarian Login");

        mAuth = FirebaseAuth.getInstance();

        binding.signinBtnId.setOnClickListener(v->{
            signInLibrarian();
        });
        binding.goSignupBtnId.setOnClickListener(v->{
            binding.loginLayoutId.setVisibility(View.GONE);
            binding.signUpLayoutId.setVisibility(View.VISIBLE);
        });
        binding.signUpBtn.setOnClickListener(v->{
            signUpLibrarian();
        });

        getLibrarianLoginInfo();
    }

    private void signUpLibrarian() {
        // write farther code here...
        String name = binding.nameEtId.getText().toString();
        String email = binding.emailEtId.getText().toString();
        String id = binding.idEtId.getText().toString();
        String password = binding.passEtId.getText().toString();
        if (name.isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    saveLibrarianInfo(name,email,id,password);

                } else {
                    // If sign in fails, display a message to the user.
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "Librarian is already registered !!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void saveLibrarianInfo(String name, String email, String id,String password) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("librarianList");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("email",email);
        hashMap.put("password",password);
        hashMap.put("id",id);
        hashMap.put("uid",uid);

        reference.child(uid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences.Editor editor = getSharedPreferences("jnu", MODE_PRIVATE).edit();
                        editor.putString("name",name);
                        editor.putString("email",email);
                        editor.putString("id",id);
                        editor.putString("tag","lb");
                        editor.apply();

                        Toast.makeText(LibrarianLoginActivity.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LibrarianLoginActivity.this,DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }

    private void signInLibrarian() {
        //write signin code here...
        String email = binding.emailEtLogin.getText().toString();
        String password = binding.passEtLogin.getText().toString();

        if (email_l.equals(email)&&pass_l.equals(password)){
            SharedPreferences.Editor editor = getSharedPreferences("jnu", MODE_PRIVATE).edit();
            editor.putString("email",email);
            editor.putString("tag","lb");
            editor.apply();

            Toast.makeText(LibrarianLoginActivity.this, "Login Success !!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LibrarianLoginActivity.this,DashboardActivity.class));
            finish();
        }

        /*
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    SharedPreferences.Editor editor = getSharedPreferences("jnu", MODE_PRIVATE).edit();
                    editor.putString("email",email);
                    editor.putString("tag","lb");
                    editor.apply();

                    Toast.makeText(LibrarianLoginActivity.this, "Login Success !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LibrarianLoginActivity.this,DashboardActivity.class));
                    finish();

                }else{
                    Toast.makeText(LibrarianLoginActivity.this, "Failed to Login !", Toast.LENGTH_SHORT).show();
                }

            }
        }); */
    }

    public void getLibrarianLoginInfo(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                     email_l =snapshot.child("librarian_email").getValue(String.class);
                     pass_l =snapshot.child("librarian_pass").getValue(String.class);

                     if (!email_l.isEmpty() && !pass_l.isEmpty()){
                         binding.signinBtnId.setEnabled(true);
                     }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}