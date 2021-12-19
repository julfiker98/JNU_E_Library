package com.jnu.jnuelibrary.Librarian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jnu.jnuelibrary.BookListActivity;
import com.jnu.jnuelibrary.R;
import com.jnu.jnuelibrary.databinding.ActivityGenaratePdfBinding;
import com.jnu.jnuelibrary.databinding.ActivityMainBinding;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class GenaratePdfActivity extends AppCompatActivity {
   private ActivityGenaratePdfBinding binding;

   String book_name,writer_name,book_type,serial_no,isbn_no,book_link;

    //Pdf Section
    Bitmap bmp,scalBitmap;
    int pageWidth =595;
    int pageHeight=842;
    Canvas canvas;
    Paint myPaint;

    PdfDocument myPdfDocument;
    PdfDocument.PageInfo myPageInfo;
    PdfDocument.Page myPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenaratePdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setTitle("Generate Book");

        binding.generateQrBtnId.setOnClickListener(v->{
            generateQr();
        });
        binding.generatePdfBtnId.setOnClickListener(v->{
            generatePdf();
        });

    }

    private void generatePdf() {
        //Pdf Section
        myPdfDocument = new PdfDocument();
        myPaint = new Paint();
        myPageInfo = new PdfDocument.PageInfo.Builder(595,842,1).create();
        myPage = myPdfDocument.startPage(myPageInfo);
        canvas = myPage.getCanvas();
        bmp =((BitmapDrawable)binding.qrIvId.getDrawable()).getBitmap();
        scalBitmap = Bitmap.createScaledBitmap(bmp,300,300,false);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);


        canvas.drawBitmap(scalBitmap,40,20,myPaint); //setting image
        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(12f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Book Name: "+book_name,65,320,myPaint);
        canvas.drawText("Writer Name: "+writer_name,65,335,myPaint);
        canvas.drawText("Book Type: "+book_type,65,350,myPaint);
        canvas.drawText("Serial No: "+serial_no,65,365,myPaint);
        canvas.drawText("ISBN No: "+serial_no,65,380,myPaint);
        canvas.drawText("Book Link: "+book_link,65,395,myPaint);
        myPdfDocument.finishPage(myPage);

        File file =  Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath()+"/QrCodeList/");
        dir.mkdir();
        Date currentTime = Calendar.getInstance().getTime();/////////////////
        File file1= new File(dir,book_name+","+currentTime+".pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file1));
            Toast.makeText(GenaratePdfActivity.this, "Pdf Created", Toast.LENGTH_SHORT).show();
            saveToBookList();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(GenaratePdfActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        myPdfDocument.close();


    }

    private void saveToBookList() {
        DatabaseReference ref_bookList = FirebaseDatabase.getInstance().getReference("bookList");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("book_name",book_name);
        hashMap.put("writer_name",writer_name);
        hashMap.put("book_type",book_type);
        hashMap.put("serial_no",serial_no);
        hashMap.put("isbn_no",isbn_no);
        hashMap.put("book_link",book_link);

        ref_bookList.child(serial_no).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(getApplicationContext(), BookListActivity.class));
            }
        });

    }

    private void generateQr() {
         book_name =binding.bookNameETId.getText().toString();
         writer_name =binding.writerNameETId.getText().toString();
         book_type =binding.bookTypeEtId.getText().toString();
         serial_no =binding.serialNoETId.getText().toString();
         isbn_no =binding.isbnNoETId.getText().toString();
         book_link =binding.linkETId.getText().toString();

        String s= book_name+"\n"+writer_name+"\n"+book_type+"\n"+serial_no+"\n"+isbn_no+"\n"+book_link;

        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(s, BarcodeFormat.QR_CODE,300,300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            binding.qrIvId.setVisibility(View.VISIBLE);
            binding.qrIvId.setImageBitmap(bitmap);
            // InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        } catch (WriterException e) {
            e.printStackTrace();
        }


      }
}