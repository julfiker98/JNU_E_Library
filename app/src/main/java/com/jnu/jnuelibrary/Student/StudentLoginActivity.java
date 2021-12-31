package com.jnu.jnuelibrary.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jnu.jnuelibrary.databinding.ActivityStudentLoginBinding;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class StudentLoginActivity extends AppCompatActivity {
    private ActivityStudentLoginBinding binding;
    private FirebaseAuth mAuth;


    private  static  final  int CAMERA_REQUEST_CODE=100;
    private  static  final  int STORAGE_REQUEST_CODE=200;

    private  static  final  int IMAGE_PICK_CAMERA_CODE=300;
    private  static  final  int IMAGE_PICK_GALLERY_CODE=400;

    String cameraPermission[] ;
    String storagePermission[];
    Uri image_rui=null;
    ProgressDialog pd;

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

        //init permission
        cameraPermission = new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        pd = new ProgressDialog(this);

        binding.profileIvId.setOnClickListener(v->{
            showImagePicDialog();
        });


    }

    private void signUp() {
        pd.setMessage("Uploading Data...");
        pd.show();

        final String timeStamp = String.valueOf(System.currentTimeMillis());
        String filePathAndName = "Image/" + "profile_" + timeStamp;

        //get image from imageview
        Bitmap bitmap =((BitmapDrawable)binding.profileIvId.getDrawable()).getBitmap();
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        //image compress
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();

        StorageReference ref = FirebaseStorage.getInstance().getReference().child(filePathAndName);

        ref.putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //image uploaded now get its url
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        String downloadUri =uriTask.getResult().toString();

                        if (uriTask.isSuccessful()){
                            //url is received ..upload it to firebase database
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
                                        saveStudentInfo(name,email,id,session,password,downloadUri);

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
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //image not uploaded
              //  pd.dismiss();
                Toast.makeText(StudentLoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void saveStudentInfo(String name, String email, String id, String session,String password,String url) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("studentList");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("email",email);
        hashMap.put("password",password);
        hashMap.put("id",id);
        hashMap.put("session",session);
        hashMap.put("uid",uid);
        hashMap.put("url",url);

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
                        editor.putString("url",url);
                        editor.apply();

                        pd.dismiss();

                        Toast.makeText(StudentLoginActivity.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(StudentLoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }

    private void signIn() {
        pd.setMessage("Signing...");
        pd.show();
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
                    pd.dismiss();
                    Toast.makeText(StudentLoginActivity.this, "Login Success !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StudentLoginActivity.this,MainActivity.class));
                    finish();

                }else{
                    Toast.makeText(StudentLoginActivity.this, "Failed to Login !", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showImagePicDialog() {

        String option[]= {"Camera","Gallery"};
        //create aletdialouge
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        //set tittle
        builder.setTitle("Choose Image from");
        //set iteems to dialogue
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0)
                {
                    //camera click
                    if (!checkCameraPermission())
                    {
                        requestCameraPermission();
                    }
                    else
                    {
                        pickFromCamera();
                    }


                }
                else if(which==1)
                {
                    //gallery click
                    if (!checkStoragePermission())
                    {
                        requestStoragePermission();
                    }
                    else
                    {
                        pickFromGallery();
                    }


                }


            }
        });
        //create and show dialogue
        builder.create().show();

    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                ==(PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);

        return result && result1;

    }

    private  void requestCameraPermission() {

        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);

    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);

        return result;

    }

    private  void requestStoragePermission() {

        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);

    }

    private void pickFromCamera() {
        ContentValues values= new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp Descr");
        image_rui = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_rui);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE);
    }

    //handle permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "Please enable Camera & Storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please enable Storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode==RESULT_OK)
        {
            if (requestCode==IMAGE_PICK_GALLERY_CODE)
            {
                image_rui=data.getData();
                //set image to imageView
                binding.profileIvId.setImageURI(image_rui);

            }
            if (requestCode==IMAGE_PICK_CAMERA_CODE)
            {
                //set image to imageView
                binding.profileIvId.setImageURI(image_rui);
                // uploadProfileCoverPhoto(image_rui);
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}