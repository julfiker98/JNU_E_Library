package com.jnu.jnuelibrary;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.jnu.jnuelibrary.Librarian.DashboardActivity;
import com.jnu.jnuelibrary.Librarian.LibrarianLoginActivity;
import com.jnu.jnuelibrary.Student.MainActivity;
import com.jnu.jnuelibrary.Student.StudentLoginActivity;
import com.jnu.jnuelibrary.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
  private ActivityLoginBinding loginBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(loginBinding.getRoot());



    SharedPreferences sharedPreferences = getSharedPreferences("jnu", Context.MODE_PRIVATE);
    String tag = sharedPreferences.getString("tag", "No tag Found !");

    if (tag.equals("st")){
      startActivity(new Intent(getApplicationContext(), MainActivity.class));
      finish();
    }if (tag.equals("lb")){
      startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
      finish();
    }


    loginBinding.loginStBtnId.setOnClickListener(v->{
      startActivity(new Intent(getApplicationContext(), StudentLoginActivity.class));
    });
    loginBinding.loginLbrBtnId.setOnClickListener(v->{
      startActivity(new Intent(getApplicationContext(), LibrarianLoginActivity.class));
    });
  }


}