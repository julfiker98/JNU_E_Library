package com.jnu.jnuelibrary.Student;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jnu.jnuelibrary.databinding.ActivityStudentLoginBinding;


import java.util.HashMap;

public class StudentLoginActivity extends AppCompatActivity {
    private ActivityStudentLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setTitle("Student Login");
         mAuth = FirebaseAuth.getInstance();


        binding.signinBtnId.setOnClickListener(v->{
            signIn();
        });
        binding.goSignupBtnId.setOnClickListener(v->{
            binding.loginLayoutId.setVisibility(View.GONE);
            binding.signUpLayoutId.setVisibility(View.VISIBLE);
        });
        binding.signUpBtn.setOnClickListener(v->{
            signUp();
        });
    }

    private void signUp() {
        // write farther code here...
        String name = binding.nameEtId.getText().toString();
        String email = binding.emailEtId.getText().toString();
        String id = binding.idEtId.getText().toString();
        String session = binding.sessionEtId.getText().toString();
        String password = binding.passEtId.getText().toString();

        if (name.isEmpty()){
            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    saveStudentInfo(name,email,id,session,password);

                } else {
                    // If sign in fails, display a message to the user.
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "User is already registered !!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }

    private void saveStudentInfo(String name, String email, String id, String session,String password) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("studentList");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("email",email);
        hashMap.put("password",password);
        hashMap.put("id",id);
        hashMap.put("session",session);
        hashMap.put("uid",uid);

        reference.child(uid).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences.Editor editor = getSharedPreferences("jnu", MODE_PRIVATE).edit();
                        editor.putString("name",name);
                        editor.putString("email",email);
                        editor.putString("id",id);
                        editor.putString("session",session);
                        editor.putString("password",session);
                        editor.putString("tag","st");
                        editor.apply();

                        Toast.makeText(StudentLoginActivity.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(StudentLoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }

    private void signIn() {
        //write signin code here...
        String email = binding.emailEtLogin.getText().toString();
        String password = binding.passEtLogin.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    SharedPreferences.Editor editor = getSharedPreferences("jnu", MODE_PRIVATE).edit();
                    editor.putString("email",email);
                    editor.putString("tag","st");
                    editor.apply();

                    Toast.makeText(StudentLoginActivity.this, "Login Success !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StudentLoginActivity.this,MainActivity.class));
                    finish();

                }else{
                    Toast.makeText(StudentLoginActivity.this, "Failed to Login !", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}