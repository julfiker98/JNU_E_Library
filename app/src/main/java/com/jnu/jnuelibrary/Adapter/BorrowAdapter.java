package com.jnu.jnuelibrary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.jnuelibrary.Librarian.ScanStudentProfileActivity;
import com.jnu.jnuelibrary.Model.BorrowModel;
import com.jnu.jnuelibrary.R;

import java.util.List;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.Myholder> {
    Context context;
    List<BorrowModel> borrowList;

    public BorrowAdapter(Context context, List<BorrowModel> borrowList) {
        this.context = context;
        this.borrowList = borrowList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(context).inflate(R.layout.borrow_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        String st_name = borrowList.get(position).getSt_name();
        String book_name = borrowList.get(position).getBook_name();
        String st_id = borrowList.get(position).getSt_id();
        String time = borrowList.get(position).getTimeStamp();
        String uid = borrowList.get(position).getUid();

        holder.st_nameTv.setText("Student Name: "+st_name);
        holder.book_nameTv.setText("Book Name: "+book_name);

        holder.itemView.setOnClickListener(v->{

            Intent intent = new Intent(BorrowAdapter.this.context, ScanStudentProfileActivity.class);
            intent.putExtra("tag","borrow");
            intent.putExtra("time",time);
            intent.putExtra("st_id",st_id);
            intent.putExtra("book_name",book_name);
            intent.putExtra("uid",uid);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BorrowAdapter.this.context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return borrowList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
          TextView st_nameTv,book_nameTv;

        public Myholder(@NonNull View itemView) {
            super(itemView);
           st_nameTv = itemView.findViewById(R.id.stNameTv_view);
           book_nameTv = itemView.findViewById(R.id.bookNameTv_view);

        }
    }
}
