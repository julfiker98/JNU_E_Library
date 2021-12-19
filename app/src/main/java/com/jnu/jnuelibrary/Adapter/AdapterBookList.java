package com.jnu.jnuelibrary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jnu.jnuelibrary.Model.ModelBookList;
import com.jnu.jnuelibrary.R;
import java.util.List;

public class AdapterBookList extends RecyclerView.Adapter<AdapterBookList.Myholder> {
    Context context;
    List<ModelBookList> bookList;

    public AdapterBookList(Context context, List<ModelBookList> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(context).inflate(R.layout.book_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        String book_name = bookList.get(position).getBook_name();
        String writer_name = bookList.get(position).getWriter_name();
        String serial_no = bookList.get(position).getSerial_no();
        String isbn_no = bookList.get(position).getIsbn_no();

        holder.bookNameTv.setText("Book Name: "+book_name);
        holder.writerNameTv.setText("Writer Name: "+writer_name);
        holder.serialNoTv.setText("Serial No: "+serial_no);
        holder.isbnNoTv.setText("ISBN No: "+isbn_no);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView bookNameTv,writerNameTv,serialNoTv,isbnNoTv;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            bookNameTv = itemView.findViewById(R.id.book_nameTv_row);
            writerNameTv = itemView.findViewById(R.id.writer_nameTv_row);
            serialNoTv = itemView.findViewById(R.id.serial_noTv_row);
            isbnNoTv = itemView.findViewById(R.id.isbn_noTv_row);
        }
    }
}
