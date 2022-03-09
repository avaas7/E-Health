package com.example.appointmentapplication.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointmentapplication.Appointment.Appointment;
import com.example.appointmentapplication.Authentication.SignUpActivity;
import com.example.appointmentapplication.MainActivity;
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

public class CheckUpActivity extends AppCompatActivity {

    public static final String EXTRA_COMPLETED="Completed";


    TextView tvName;
    TextView tvCheckUpDate;
    TextView tvCheckUpDepartment;
    TextView tvCheckUpStatus;

    EditText etCheckUpPrescription;

    Button btnCheckUpComplete;

    String key;
    String uid;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    Appointment appointment;

    String department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_up);



        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        tvName = findViewById(R.id.check_up_cv_name);
        tvCheckUpDate = findViewById(R.id.check_up_cv_appointment_date);
        tvCheckUpDepartment = findViewById(R.id.check_up_tvDepartment);
        tvCheckUpStatus = findViewById(R.id.check_up_cv_pending);

        etCheckUpPrescription = findViewById(R.id.et_check_up_prescription);

        btnCheckUpComplete = findViewById(R.id.btn_check_up_appointment_completed);



        Intent intent = getIntent();

        key = intent.getStringExtra(PatientListActivity.EXTRA_KEY);
        uid = intent.getStringExtra(PatientListActivity.EXTRA_UID);
        department = intent.getStringExtra(DoctorActivity.DOC_DEPARTMENT);



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments").child(key);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointment = snapshot.getValue(Appointment.class);

                tvName.setText(appointment.getPatientName());
                tvCheckUpDate.setText(appointment.getDate());
                tvCheckUpStatus.setText(appointment.getStatus());
                tvCheckUpDepartment.setText(appointment.getDepartment());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnCheckUpComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appointment.setStatus(EXTRA_COMPLETED);
                appointment.setUid_status(appointment.getUid()+"_"+EXTRA_COMPLETED);
                String presciptionLocal  = etCheckUpPrescription.getText().toString();
                if(presciptionLocal!=null) {
                    appointment.setPrescription(presciptionLocal);
                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments");

                databaseReference.child(key).setValue(appointment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(CheckUpActivity.this, "Appointment completed", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(CheckUpActivity.this,PatientListActivity.class);

                            intent.putExtra(DoctorActivity.DOC_DEPARTMENT,department);
                            startActivity(intent);

                           finish();
                        }else
                        {
                            Toast.makeText(CheckUpActivity.this, "error occured: " + task.getException().getMessage(),   Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(this,PatientListActivity.class);
        intent.putExtra(DoctorActivity.DOC_DEPARTMENT,department);
        startActivity(intent);
        finish();
    }
}