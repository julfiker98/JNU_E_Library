package com.jnu.jnuelibrary.Adapter;

import android.content.Context;
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

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.Myholder> {
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
        String time = transactionList.get(position).getTime();

        holder.st_nameTv.setText("Student Name: "+st_name);
        holder.book_nameTv.setText("Book Name: "+book_name);
        holder.transactionTv.setText("Transaction type: "+transType);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DatabaseReference ref_trns = FirebaseDatabase.getInstance().getReference("transactions");
                ref_trns.child(time).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context.getApplicationContext(), "Removed!", Toast.LENGTH_SHORT).show();
                    }
                });

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView st_nameTv,book_nameTv,transactionTv;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            st_nameTv = itemView.findViewById(R.id.st_nameTv_view);
            book_nameTv = itemView.findViewById(R.id.book_nameTv_view);
            transactionTv = itemView.findViewById(R.id.transTypeTv_view);

        }
    }
}
