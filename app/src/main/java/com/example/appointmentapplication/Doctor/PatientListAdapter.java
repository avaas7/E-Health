package com.example.appointmentapplication.Doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentapplication.Appointment.Appointment;
import com.example.appointmentapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.myViewHolder> {

    Context context;
    private ArrayList<Appointment> appointmentList;
    private PatientListAdapter.RecyclerViewClickListener listener;


    public PatientListAdapter(Context context, ArrayList<Appointment> appointmentList, PatientListAdapter.RecyclerViewClickListener listener) {
        this.context = context;
        this.appointmentList = appointmentList;
        this.listener = listener;
    }


    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView dateText;
        private TextView departmentText;
        private TextView nameText;
        private CardView cardView;
        private TextView tvPending;
    /*    private Button cvEdit;
        private Button cvDelete;
*/

        public myViewHolder(@NonNull View itemView) {
            super(itemView);



            dateText = itemView.findViewById(R.id.cv_appointment_date);
            departmentText = itemView.findViewById(R.id.tvDepartment);
            nameText = itemView.findViewById(R.id.cv_name);
            cardView = itemView.findViewById(R.id.li_card_view);
            tvPending= itemView.findViewById(R.id.cv_pending);

            itemView.setOnClickListener(this);

            /*
            cvEdit = itemView.findViewById(R.id.cv_edit);
            cvDelete = itemView.findViewById(R.id.cv_delete);
  */      }

        @Override
        public void onClick(View view) {

            listener.onClick(view,getAdapterPosition());
        }
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        return new PatientListAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {


        String department = appointmentList.get(position).getDepartment();
        String date = appointmentList.get(position).getDate();
        String key = appointmentList.get(position).getKey();
        String pending = appointmentList.get(position).getStatus();

        holder.departmentText.setText(department);
        holder.dateText.setText(date);
        holder.tvPending.setText(pending);



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments");

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child(appointmentList.get(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Appointment appointment =snapshot.getValue(Appointment.class);

                String name = appointment.getPatientName();
                holder.nameText.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }


    public interface RecyclerViewClickListener{
        void onClick(View v,int position);

    }

}

