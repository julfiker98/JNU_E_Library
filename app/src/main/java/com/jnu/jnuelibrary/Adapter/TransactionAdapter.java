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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jnu.jnuelibrary.Model.TransactionModel;
import com.jnu.jnuelibrary.R;
import com.jnu.jnuelibrary.Student.MainActivity;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.Myholder> {
    String time="";
    Context context;
    List<TransactionModel> transactionList;

    public TransactionAdapter(Context context, List<TransactionModel> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(context).inflate(R.layout.transaction_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        String st_name = transactionList.get(position).getSt_name();
        String book_name = transactionList.get(position).getBook_name();
        String transType = transactionList.get(position).getTransaction_type();
        time = transactionList.get(position).getTime();

        holder.st_nameTv.setText("Student Name: "+st_name);
        holder.book_nameTv.setText("Book Name: "+book_name);
        holder.transactionTv.setText("Transaction type: "+transType);


        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView st_nameTv,book_nameTv,transactionTv,deleteTv;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            st_nameTv = itemView.findViewById(R.id.st_nameTv_view);
            book_nameTv = itemView.findViewById(R.id.book_nameTv_view);
            transactionTv = itemView.findViewById(R.id.transTypeTv_view);
            deleteTv = itemView.findViewById(R.id.deleteTV_view);

            if (MainActivity.tag.equals("st")){
                deleteTv.setVisibility(View.GONE);
            }

        }
    }

    private void showDialog(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getRootView().getContext()); // important for adapter =   view.getRootView().getContext()
        alertDialog.setTitle("Return Book!!");
        alertDialog.setMessage("Are you sure want to delete this transaction ?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                DatabaseReference ref_trns = FirebaseDatabase.getInstance().getReference("transactions");
                ref_trns.child(time).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context.getApplicationContext(), "Transaction Removed!", Toast.LENGTH_SHORT).show();
                    }
                }); //
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
