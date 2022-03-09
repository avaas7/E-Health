package com.example.appointmentapplication.Doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentapplication.R;

import java.util.ArrayList;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.myViewHolder> {


    Context context;
    private ArrayList<Doctors> doctorList;

    public DoctorListAdapter(Context context, ArrayList<Doctors> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }



    @NonNull
    @Override
    public DoctorListAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_layout_item,parent,false);
        return new DoctorListAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView doctorName;
        private TextView doctorPosition;
        private TextView doctorDepartment;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorName = itemView.findViewById(R.id.cv_doctor_name);
            doctorPosition = itemView.findViewById(R.id.doctor_cv_position);
            doctorDepartment = itemView.findViewById(R.id.doctor_cv_department);


        }

    }



    @Override
    public void onBindViewHolder(@NonNull DoctorListAdapter.myViewHolder holder, int position) {

        String name = "Dr. "+ doctorList.get(position).getFullname();
        String pos = doctorList.get(position).getPosition();
        String department = doctorList.get(position).getDepartment();

        holder.doctorName.setText(name);
        holder.doctorPosition.setText(pos);
        holder.doctorDepartment.setText(department);

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }
}
