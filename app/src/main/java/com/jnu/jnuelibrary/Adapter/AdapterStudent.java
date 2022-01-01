package com.jnu.jnuelibrary.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.jnu.jnuelibrary.Librarian.EditStudentActivity;
import com.jnu.jnuelibrary.Librarian.UpdateBookActivity;
import com.jnu.jnuelibrary.Model.StudentModel;
import com.jnu.jnuelibrary.R;
import com.jnu.jnuelibrary.Student.MainActivity;

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
                //
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


        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view,uid);
            }
        });

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView nameTv,idTv,sessionTv,deleteTv;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv_view);
            idTv = itemView.findViewById(R.id.stIdTv_view);
            sessionTv = itemView.findViewById(R.id.sessionTv_view);
            deleteTv = itemView.findViewById(R.id.deleteTV_view);

           if (MainActivity.tag.equals("st")){
               deleteTv.setVisibility(View.GONE);
           }

        }
    }

    private void showDialog(View view,String uid) {
        //pass the 'context' here
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getRootView().getContext()); // important for adapter =   view.getRootView().getContext()
        alertDialog.setTitle("Return Book!!");
        alertDialog.setMessage("Are you sure want to delete this student ?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("studentList");
                reference.child(uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context.getApplicationContext(), "Student Removed!", Toast.LENGTH_SHORT).show();
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
