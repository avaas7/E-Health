package com.example.appointmentapplication.Appointment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentapplication.Doctor.CheckUpActivity;
import com.example.appointmentapplication.Doctor.DoctorActivity;
import com.example.appointmentapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PastFragment extends Fragment {
    public static final String EXTRA_KEY = "key";
    ArrayList<Appointment> appointmentList = new ArrayList<>();

    RecyclerViewAdapter adapter;
    RecyclerViewAdapter.RecyclerViewClickListener listener;

    RecyclerView recyclerView;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;


    TextView tvNoAppointments;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        View view = inflater.inflate(R.layout.past_fragment,container,false);

        recyclerView = view.findViewById(R.id.rvPast);


        tvNoAppointments = view.findViewById(R.id.tvNoAppointments);

        setOnClickListener();

        adapter = new RecyclerViewAdapter(getContext(),appointmentList,listener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);




        try {



            Query query =  FirebaseDatabase.getInstance().getReference("Appointments").orderByChild("uid_status").equalTo(mUser.getUid()+"_"+CheckUpActivity.EXTRA_COMPLETED);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Appointment appointment = dataSnapshot.getValue(Appointment.class);
                        appointmentList.add(appointment);

                    }

                    if (appointmentList.size()==0)
                    {
                        tvNoAppointments.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                    // display();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        catch (Exception e)
        {

            if (appointmentList.size()==0)
            {
                tvNoAppointments.setVisibility(View.VISIBLE);
            }
            Log.e("av",e.getMessage());
        }

        return view;
    }

    private void setOnClickListener() {
        listener= new RecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getContext(), PrescriptionActivity.class);
                intent.putExtra(EXTRA_KEY,appointmentList.get(position).getKey());
                startActivity(intent);

            }
        };
    }


}
