package com.example.appointmentapplication.Appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointmentapplication.Appointment.Appointment;
import com.example.appointmentapplication.Doctor.CheckUpActivity;
import com.example.appointmentapplication.Doctor.DoctorActivity;
import com.example.appointmentapplication.Doctor.PatientListActivity;
import com.example.appointmentapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrescriptionActivity extends AppCompatActivity {


    TextView tvName;
    TextView tvDate;
    TextView tvDepartment;
    TextView tvStatus;

    TextView tvPrescription;


    String key;
    String uid;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    Appointment appointment;

    String department;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        tvName = findViewById(R.id.presciption_cv_name);
        tvDate = findViewById(R.id.presciption_cv_appointment_date);
        tvDepartment = findViewById(R.id.presciption_tvDepartment);
        tvStatus = findViewById(R.id.presciption_cv_pending);

        tvPrescription = findViewById(R.id.tv_prescription);


        Intent intent = getIntent();

        key = intent.getStringExtra(PatientListActivity.EXTRA_KEY);
        //            uid = intent.getStringExtra(PatientListActivity.EXTRA_UID);
        //           department = intent.getStringExtra(DoctorActivity.DOC_DEPARTMENT);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments").child(key);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointment = snapshot.getValue(Appointment.class);

                tvName.setText(appointment.getPatientName());
                tvDate.setText(appointment.getDate());
                tvStatus.setText(appointment.getStatus());
                tvDepartment.setText(appointment.getDepartment());

                tvPrescription.setText(appointment.getPrescription());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}