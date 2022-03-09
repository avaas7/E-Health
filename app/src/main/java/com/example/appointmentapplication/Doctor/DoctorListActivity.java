package com.example.appointmentapplication.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

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

public class DoctorListActivity extends AppCompatActivity {

    ArrayList<Doctors> doctorsList = new ArrayList<>();

    DoctorListAdapter adapter;

    RecyclerView recyclerView;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.rv_doctor_list);

        adapter = new DoctorListAdapter(DoctorListActivity.this,doctorsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorListActivity.this));

        recyclerView.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctors");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Doctors doctors= dataSnapshot.getValue(Doctors.class);
                    doctorsList.add(doctors);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DoctorListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}