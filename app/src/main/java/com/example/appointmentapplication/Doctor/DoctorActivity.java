package com.example.appointmentapplication.Doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.appointmentapplication.Authentication.DoctorSignInActivity;
import com.example.appointmentapplication.Authentication.SignInActivity;
import com.example.appointmentapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoctorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String DOC_DEPARTMENT= "department";

    Button btnPatientList;
    Button btnDoctorLogOut;

    Spinner spinnerDoc;
    ArrayAdapter<CharSequence> spinnerAdapter;


    String department;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Log.e("avDoc","eherer");

        btnPatientList =findViewById(R.id.btn_patient_list);
        btnDoctorLogOut = findViewById(R.id.btn_doc_logout);

        spinnerDoc = findViewById(R.id.doc_spinner);
try {


    spinnerDoc.setOnItemSelectedListener(this);
    spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.department_array, android.R.layout.simple_spinner_item);
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerDoc.setAdapter(spinnerAdapter);

}
catch (Exception e)
{
    Log.e("avDoc",e.getMessage());
}
        btnPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(DoctorActivity.this,PatientListActivity.class);

            intent.putExtra(DOC_DEPARTMENT,department);
           startActivity(intent);

            }
        });

    btnDoctorLogOut.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), DoctorSignInActivity.class));
        }
    });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        department = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {

    }
}