package com.jnu.jnuelibrary.Librarian;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import com.jnu.jnuelibrary.Librarian.ScanStudentProfileActivity;
import com.jnu.jnuelibrary.Student.ScannerView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannViewL extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    ZXingScannerView scannerView;
    public static String s1,s2,s3,s4,s5,s6,s_uid,serial_no;
    String book_name,time,st_name;
    String st_id="";
    String tag="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);

        tag = ScanStudentProfileActivity.tag;
        serial_no = ScanStudentProfileActivity.serial_no;

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


    @Override
    public void handleResult(Result rawResult) {

        String raw = rawResult.getText();
        String[] splite = raw.split("\n");

        if (tag.equals("borrow")){
            Toast.makeText(getApplicationContext(), "borrow", Toast.LENGTH_SHORT).show();
            s1 = splite[0];
            s2 = splite[1];
            s3 = splite[2];
            s4 = splite[3];
            ScanStudentProfileActivity.scantextTv.setText(s1+"\n"+s2+"\n"+s3+"\n"+s4);

            st_name = ScanStudentProfileActivity.st_name;
            st_id = ScanStudentProfileActivity.student_id;
            book_name = ScanStudentProfileActivity.book_name;
            time = ScanStudentProfileActivity.time;
            tag =ScanStudentProfileActivity.tag;
            s_uid = ScanStudentProfileActivity.s_uid;
        }
        if (tag.equals("return")){


            s1 = splite[0];
            s2 = splite[1];
            s3 = splite[2];
            s4 = splite[3];
            s5 = splite[4];
            s6 = splite[5];

            ScanStudentProfileActivity.scantextTv.setText("Book Name: "+s1+"\n"+"Writer Name: "+s2+"\n"+"Book Type: "+s3+"\n"+"Serial No: "+s4+"\n"+"ISBN No: "+s5+"\n"+"Link: "+s6);
            st_id = ScanStudentProfileActivity.student_id;
            st_name = ScanStudentProfileActivity.st_name;
            book_name = ScanStudentProfileActivity.book_name;
            time = ScanStudentProfileActivity.time;
            tag =ScanStudentProfileActivity.tag;
            s_uid = ScanStudentProfileActivity.s_uid;

            ScanStudentProfileActivity.statusTv.setText(s4);
        }

        ScanStudentProfileActivity.statusTv.setText("Status: Approved!");

        if (s3.equals(st_id) && tag.equals("borrow")){
            ScanStudentProfileActivity.statusTv.setText("Borrowed!");
            saveToBorrow();
            addToTransaction("Borrowed");
        }
        if (tag.equals("return") && serial_no.equals(s4.trim())){
            ScanStudentProfileActivity.statusTv.setText("Status: Returned!");
            addToTransaction("Return");

        }else {
            ScanStudentProfileActivity.statusTv.setText("Id not Matched!");
        }

        onBackPressed();

    }

    public void addToTransaction(String key){

        DatabaseReference ref_transactions = FirebaseDatabase.getInstance().getReference("transactions");
        HashMap<String,Object> hashMap = new HashMap<>();
        String timeStamp = String.valueOf(System.currentTimeMillis());

        hashMap.put("st_name",st_name);
        hashMap.put("st_id",st_id);
        hashMap.put("book_name",book_name);
        hashMap.put("serial_no",serial_no);
        hashMap.put("time",timeStamp);
        hashMap.put("transaction_type",key);

        ref_transactions.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Book Returned", Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void saveToBorrow() {
        DatabaseReference ref_bor = FirebaseDatabase.getInstance().getReference("borrow_list");
        HashMap<String,Object> hashMap = new HashMap<>();
        String timeStamp = String.valueOf(System.currentTimeMillis());

        hashMap.put("st_name",s1);
        hashMap.put("book_name",book_name);
        hashMap.put("st_id",st_id);
        hashMap.put("time",timeStamp);

        ref_bor.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Approved!", Toast.LENGTH_SHORT).show();
                deleteRequest();
            }
        });

    }

    private void deleteRequest() {
        DatabaseReference ref_borrow = FirebaseDatabase.getInstance().getReference("borrow_request");
        ref_borrow.child(time).removeValue();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }


}