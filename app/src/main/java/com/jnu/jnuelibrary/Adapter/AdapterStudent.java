package com.jnu.jnuelibrary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.jnuelibrary.Librarian.EditStudentActivity;
import com.jnu.jnuelibrary.Librarian.UpdateBookActivity;
import com.jnu.jnuelibrary.Model.StudentModel;
import com.jnu.jnuelibrary.R;

import java.util.List;

public class AdapterStudent extends RecyclerView.Adapter<AdapterStudent.Myholder> {
    Context context;
    List<StudentModel> studentList;

    public AdapterStudent(Context context, List<StudentModel> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(context).inflate(R.layout.student_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        String name = studentList.get(position).getName();
        String id = studentList.get(position).getId();
        String session = studentList.get(position).getSession();
        String uid = studentList.get(position).getUid();

        holder.nameTv.setText(name);
        holder.idTv.setText("ID: "+id);
        holder.sessionTv.setText("Session: "+session);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /////
                Intent intent = new Intent(AdapterStudent.this.context, EditStudentActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("id",id);
                intent.putExtra("session",session);
                intent.putExtra("uid",uid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AdapterStudent.this.context.startActivity(intent);

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView nameTv,idTv,sessionTv;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv_view);
            idTv = itemView.findViewById(R.id.stIdTv_view);
            sessionTv = itemView.findViewById(R.id.sessionTv_view);

        }
    }
}
