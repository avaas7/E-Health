package com.example.appointmentapplication.Doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentapplication.Appointment.Appointment;
import com.example.appointmentapplication.R;

import java.util.ArrayList;

public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.myViewHolder>{

    Context context;
    private ArrayList<Appointment> appointmentList;

    public PrescriptionListAdapter(Context context, ArrayList<Appointment> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public PrescriptionListAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_layout_item,parent,false);
        return new PrescriptionListAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        String department = appointmentList.get(position).getDepartment();
        String date = appointmentList.get(position).getDate();
        String key = appointmentList.get(position).getKey();
        String pending = appointmentList.get(position).getStatus();
        String patientName = appointmentList.get(position).getPatientName();
        String prescription = appointmentList.get(position).getPrescription();

        holder.departmentText.setText(department);
        holder.dateText.setText(date);
        holder.tvPending.setText(pending);
        holder.nameText.setText(patientName);
        holder.tvPrescription.setText(prescription);

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView dateText;
        private TextView departmentText;
        private TextView nameText;
        private TextView tvPrescription;
        private TextView tvPending;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);



            dateText = itemView.findViewById(R.id.presciption_cv_appointment_date);
            departmentText = itemView.findViewById(R.id.presciption_tvDepartment);
            nameText = itemView.findViewById(R.id.presciption_cv_name);
            tvPending= itemView.findViewById(R.id.presciption_cv_pending);
            tvPrescription = itemView.findViewById(R.id.tv_prescription);

      }

    }


    }



