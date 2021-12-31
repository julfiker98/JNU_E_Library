package com.jnu.jnuelibrary.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jnu.jnuelibrary.Librarian.ScanStudentProfileActivity;
import com.jnu.jnuelibrary.Librarian.UpdateBookActivity;
import com.jnu.jnuelibrary.LoginActivity;
import com.jnu.jnuelibrary.Model.ModelBookList;
import com.jnu.jnuelibrary.R;
import com.jnu.jnuelibrary.Student.MainActivity;

import java.util.HashMap;
import java.util.List;

public class AdapterBookList extends RecyclerView.Adapter<AdapterBookList.Myholder> {
    Context context;
    List<ModelBookList> bookList;

    String book_name="";
    String writer_name="";
    String serial_no ="";
    String isbn_no="";
    String type="";
    String link="";
    String tag="";



    public AdapterBookList(Context context, List<ModelBookList> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("jnu", Context.MODE_PRIVATE);
        tag = sharedPreferences.getString("tag", "No tag Found !");

        return new Myholder(LayoutInflater.from(context).inflate(R.layout.book_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        book_name = bookList.get(position).getBook_name();
        writer_name = bookList.get(position).getWriter_name();
        serial_no = bookList.get(position).getSerial_no();
        isbn_no = bookList.get(position).getIsbn_no();
        type = bookList.get(position).getBook_type();
        link = bookList.get(position).getBook_link();
        String status = bookList.get(position).getStatus();

        holder.bookNameTv.setText(book_name);
        holder.writerNameTv.setText("Writer Name: "+writer_name);
        holder.serialNoTv.setText("Serial No: "+serial_no);
        holder.isbnNoTv.setText("ISBN No: "+isbn_no);

        if (status.equals("1")){
            holder.statusTv.setText("Status: Available");
        }else{
            holder.statusTv.setText("Status: Unavailable");
        }


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (tag.equals("lb")){
                    Intent intent = new Intent(AdapterBookList.this.context, UpdateBookActivity.class);
                    intent.putExtra("name",book_name);
                    intent.putExtra("writer",writer_name);
                    intent.putExtra("serial",serial_no);
                    intent.putExtra("isbn",isbn_no);
                    intent.putExtra("link",link);
                    intent.putExtra("type",type);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    AdapterBookList.this.context.startActivity(intent);
                }
                return false;
            }
        });


        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView bookNameTv,writerNameTv,serialNoTv,isbnNoTv,statusTv;
        ImageButton deleteBtn;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            bookNameTv = itemView.findViewById(R.id.book_nameTv_row);
            writerNameTv = itemView.findViewById(R.id.writer_nameTv_row);
            serialNoTv = itemView.findViewById(R.id.serial_noTv_row);
            isbnNoTv = itemView.findViewById(R.id.isbn_noTv_row);
            deleteBtn = itemView.findViewById(R.id.deleteBtn_row);
            statusTv = itemView.findViewById(R.id.statusTv_row);

            if (tag.equals("st")) {
                deleteBtn.setVisibility(View.GONE);
            }
        }
    }

    private void showDialog(View view) {
        //pass the 'context' here
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getRootView().getContext()); // important for adapter =   view.getRootView().getContext()
        alertDialog.setTitle("Return Book!!");
        alertDialog.setMessage("Are you sure want to return this book ?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("bookList");
                reference.child(serial_no).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context.getApplicationContext(), "Book Removed!", Toast.LENGTH_SHORT).show();
                    }
                });  //
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }


}
