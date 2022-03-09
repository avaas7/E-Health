package com.example.appointmentapplication.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.appointmentapplication.Appointment.Appointment;
import com.example.appointmentapplication.Appointment.AppointmentActivity;
import com.example.appointmentapplication.Appointment.RecyclerViewAdapter;
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

public class PatientListActivity extends AppCompatActivity {

    TextView tvNoPatients;

    public static final String EXTRA_KEY = "key";
    public static final String EXTRA_UID = "uid";
    ArrayList<Appointment> appointmentList = new ArrayList<>();

    PatientListAdapter adapter;
    PatientListAdapter.RecyclerViewClickListener listener;

    RecyclerView recyclerView;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    String department="department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        recyclerView = findViewById(R.id.rvPatientList);

        tvNoPatients = findViewById(R.id.tvNoPatients);

        setOnClickListener();

        adapter = new PatientListAdapter(PatientListActivity.this,appointmentList,listener);

        recyclerView.setLayoutManager(new LinearLayoutManager(PatientListActivity.this));

        recyclerView.setAdapter(adapter);

        Intent intent=getIntent();
        department = intent.getStringExtra(DoctorActivity.DOC_DEPARTMENT);

        Log.e("avDoc",department);


        try {


            Query query = FirebaseDatabase.getInstance().getReference("Appointments").orderByChild("department").equalTo(department);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Appointment appointment = dataSnapshot.getValue(Appointment.class);
                        appointmentList.add(appointment);

                    }

                    if (appointmentList.size()==0)
                    {
                        tvNoPatients.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                    // display();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } catch (Exception e) {

            if (appointmentList.size()==0)
            {
                tvNoPatients.setVisibility(View.VISIBLE);
            }
            Log.e("av", e.getMessage());
        }

    }
        private void setOnClickListener () {
            listener = new PatientListAdapter.RecyclerViewClickListener() {
                @Override
                public void onClick(View v, int position) {
                    Intent intent = new Intent(PatientListActivity.this, CheckUpActivity.class);
                    intent.putExtra(EXTRA_KEY, appointmentList.get(position).getKey());
                    intent.putExtra(EXTRA_UID,appointmentList.get(position).getUid());
                    intent.putExtra(DoctorActivity.DOC_DEPARTMENT,department);
                    startActivity(intent);

                    finish();
                }
            };
        }

        public void adapterNotify()
        {
            adapter.notifyDataSetChanged();
        }

}