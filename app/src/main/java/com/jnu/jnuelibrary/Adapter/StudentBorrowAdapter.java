package com.jnu.jnuelibrary.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jnu.jnuelibrary.Model.ModelBorrowList;
import com.jnu.jnuelibrary.R;

import java.util.HashMap;
import java.util.List;

public class StudentBorrowAdapter extends RecyclerView.Adapter<StudentBorrowAdapter.Myholder> {
    String st_name,book_name,serial_no,st_id,time;
    Context context;
    List<ModelBorrowList> myBorrowList;

    public StudentBorrowAdapter(Context context, List<ModelBorrowList> myBorrowList) {
        this.context = context;
        this.myBorrowList = myBorrowList;
    }


    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(context).inflate(R.layout.borrowlist_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        st_name = myBorrowList.get(position).getSt_name();
        book_name = myBorrowList.get(position).getBook_name();
        serial_no = myBorrowList.get(position).getSerial_no();
        st_id = myBorrowList.get(position).getSt_id();
        time = myBorrowList.get(position).getTime();


        holder.st_nameTv.setText("Student Name: "+st_name);
        holder.book_nameTv.setText("Book Name: "+book_name);
        holder.serialNoTv.setText("Serial No: "+serial_no);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }

        });

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
                returnRequest();
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

    private void returnRequest() {

        String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref_return = FirebaseDatabase.getInstance().getReference("return_request");
        String timeStamp = String.valueOf(System.currentTimeMillis());

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("st_name",st_name);
        hashMap.put("book_name",book_name);
        hashMap.put("serial_no",serial_no);
        hashMap.put("st_id",st_id);
        hashMap.put("time",timeStamp);

        ref_return.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context.getApplicationContext(), "Return Request Submitted!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return myBorrowList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView st_nameTv,book_nameTv,serialNoTv;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            st_nameTv = itemView.findViewById(R.id.st_name_id_view);
            book_nameTv = itemView.findViewById(R.id.b_name_id_view);
            serialNoTv = itemView.findViewById(R.id.sl_no_id_view);

        }
    }

}
