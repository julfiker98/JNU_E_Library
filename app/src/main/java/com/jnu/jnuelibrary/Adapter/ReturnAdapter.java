package com.jnu.jnuelibrary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.jnuelibrary.Librarian.ScanStudentProfileActivity;
import com.jnu.jnuelibrary.Model.ModelBorrowList;
import com.jnu.jnuelibrary.R;

import java.util.List;

public class ReturnAdapter extends RecyclerView.Adapter<ReturnAdapter.Myholder> {

  String st_name,book_name,serial_no,st_id;
  Context context;
  List<ModelBorrowList> returnList;

  public ReturnAdapter(Context context, List<ModelBorrowList> returnList) {
    this.context = context;
    this.returnList = returnList;
  }

  @NonNull
  @Override
  public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Myholder(LayoutInflater.from(context).inflate(R.layout.borrowlist_view,parent,false));
  }

  @Override
  public void onBindViewHolder(@NonNull Myholder holder, int position) {

    st_name = returnList.get(position).getSt_name();
    book_name = returnList.get(position).getBook_name();
    serial_no = returnList.get(position).getSerial_no();
    st_id = returnList.get(position).getSt_id();
    String time = returnList.get(position).getTime();


    holder.st_nameTv.setText("Student Name: "+st_name);
    holder.book_nameTv.setText("Book Name: "+book_name);
    holder.serialNoTv.setText("Serial No: "+serial_no);

    holder.itemView.setOnClickListener(v->{

      Intent intent = new Intent(ReturnAdapter.this.context, ScanStudentProfileActivity.class);
      intent.putExtra("time",time);
      intent.putExtra("st_name",st_name);
      intent.putExtra("tag","return");
      intent.putExtra("serial",serial_no);
      intent.putExtra("st_id",st_id);
      intent.putExtra("book_name",book_name);
      // intent.putExtra("uid",uid);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      ReturnAdapter.this.context.startActivity(intent);

    });


  }

  @Override
  public int getItemCount() {
    return returnList.size();
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
