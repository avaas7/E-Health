package com.example.appointmentapplication.Service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentapplication.Doctor.DoctorListAdapter;
import com.example.appointmentapplication.R;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.myViewHolder>{


    Context context;
    private ArrayList<services> servicesList;

    public ServiceAdapter(Context context, ArrayList<services> servicesList) {
        this.context = context;
        this.servicesList = servicesList;
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_layout_item,parent,false);
        return new ServiceAdapter.myViewHolder(view);
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceName;
        private TextView serviceDesc;
        private ImageView serviceImageLink;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceName = itemView.findViewById(R.id.cv_services_name);
            serviceDesc = itemView.findViewById(R.id.services_cv_description);
            serviceImageLink = itemView.findViewById(R.id.cv_services_image);


        }
    }

        @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {


            String name = servicesList.get(position).getName();
            String desc = servicesList.get(position).getDesc();
            int imageLink = servicesList.get(position).getImageLink();

            holder.serviceName.setText(name);
            holder.serviceDesc.setText(desc);

            holder.serviceImageLink.setImageResource(imageLink);
        }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    }






