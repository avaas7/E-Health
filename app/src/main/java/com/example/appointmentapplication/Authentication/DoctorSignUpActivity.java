package com.example.appointmentapplication.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointmentapplication.Home.Designation;
import com.example.appointmentapplication.Doctor.DoctorActivity;
import com.example.appointmentapplication.Doctor.Doctors;
import com.example.appointmentapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorSignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    LinearLayout linearLayoutSignUp;
    ProgressBar progressBar;

    public static final String ROLE_DOCTOR="Doctor";

    FirebaseAuth mAuthDoctor;
    FirebaseUser mDoctor;

    EditText etFullName;
    EditText etEmail;
    EditText etPhone;
    EditText etPassword;
    EditText etAddress;



    Button btnSignUp;
    TextView tvLogIn;
    Spinner departmentSpinner;

    ArrayAdapter<CharSequence> departmentSpinnerAdapter;

    String department;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);

        linearLayoutSignUp = findViewById(R.id.doctor_sign_up_linear_layout);
        progressBar = findViewById(R.id.doctor_sign_up_loading);


        mAuthDoctor = FirebaseAuth.getInstance();


        etFullName = findViewById(R.id.doctor_etFullName);
        etEmail = findViewById(R.id.doctor_etEmailSignUp);
        etPhone = findViewById(R.id.doctor_etPhoneNo);
        etPassword = findViewById(R.id.doctor_etPasswordSignUp);
        etAddress= findViewById(R.id.doctor_etAddress);

        btnSignUp = findViewById(R.id.doctor_btnSignUp);

        tvLogIn = findViewById(R.id.doctor_tvSignIn);

        departmentSpinner =findViewById(R.id.doc_spinner_sign_up);

        try {


            departmentSpinner.setOnItemSelectedListener(this);
            departmentSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.department_array, android.R.layout.simple_spinner_item);
            departmentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            departmentSpinner.setAdapter(departmentSpinnerAdapter);

        }
        catch (Exception e)
        {
            Log.e("avDoc",e.getMessage());
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String phoneNo = etPhone.getText().toString().trim();
                String fullName= etFullName.getText().toString().trim();


                if(TextUtils.isEmpty(email))
                {
                    etEmail.setError("Please enter your Email");
                    return;
                }


                if(TextUtils.isEmpty(pass))
                {
                    etPassword.setError("Please enter your Password");
                    return;
                }

                if(TextUtils.isEmpty(fullName))
                {
                    etFullName.setError("Please enter your full name");
                    return;
                }


                if(TextUtils.isEmpty(address))
                {
                    etAddress.setError("Please enter your Address");
                    return;
                }


                if(TextUtils.isEmpty(phoneNo))
                {
                    etPhone.setError("Please enter your phone no");
                    return;
                }



                if (pass.length()<6)
                {
                    etPassword.setError("pass must be of atleast 6 characters");
                    return;
                }

                linearLayoutSignUp.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                mAuthDoctor.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            mDoctor = mAuthDoctor.getCurrentUser();

                            long phno2= Long.valueOf(phoneNo);
                            Doctors doctors = new Doctors(email,pass,fullName,address,phno2,department);
                            Designation designation = new Designation(email,mDoctor.getUid(),DoctorSignUpActivity.ROLE_DOCTOR);

                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Doctors");

                            databaseReference1.child(mDoctor.getUid()).setValue(doctors).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                    }else
                                    {
                                        linearLayoutSignUp.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(DoctorSignUpActivity.this, "error occured: " + task.getException().getMessage(),   Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Designation");

                            databaseReference2.child(mDoctor.getUid()).setValue(designation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(DoctorSignUpActivity.this, "Doctor user created", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(getApplicationContext(), DoctorActivity.class));
                                        finish();

                                    }else
                                    {

                                        linearLayoutSignUp.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(DoctorSignUpActivity.this, "error occured: " + task.getException().getMessage(),   Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                        else
                        {

                            linearLayoutSignUp.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(DoctorSignUpActivity.this, "error occured: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });



        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
}