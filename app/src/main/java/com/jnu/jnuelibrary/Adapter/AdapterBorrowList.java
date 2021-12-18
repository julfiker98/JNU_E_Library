package com.jnu.jnuelibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jnu.jnuelibrary.Model.ModelBorrowList;
import com.jnu.jnuelibrary.R;

import java.util.List;

public class AdapterBorrowList extends RecyclerView.Adapter<AdapterBorrowList.MyHolder> {
    Context context;
    List<ModelBorrowList> borrowList;

    public AdapterBorrowList(Context context, List<ModelBorrowList> borrowList) {
        this.context = context;
        this.borrowList = borrowList;
    }

    @NonNull
    @Override
    public AdapterBorrowList.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.borrowlist_view,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBorrowList.MyHolder holder, int position) {

        String st_name = borrowList.get(position).getSt_name();
        String book_name = borrowList.get(position).getBook_name();
        String serial_no = borrowList.get(position).getSerial_no();

        holder.st_nameTv.setText("Student Name: "+st_name);
        holder.book_nameTv.setText("Book Name: "+book_name);
        holder.serialNoTv.setText("Serial No: "+serial_no);



    }

    @Override
    public int getItemCount() {
        return borrowList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView st_nameTv,book_nameTv,serialNoTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            st_nameTv = itemView.findViewById(R.id.st_name_id_view);
            book_nameTv = itemView.findViewById(R.id.b_name_id_view);
            serialNoTv = itemView.findViewById(R.id.sl_no_id_view);

        }
    }
}
