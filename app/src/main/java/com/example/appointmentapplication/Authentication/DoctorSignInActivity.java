package com.example.appointmentapplication.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointmentapplication.Doctor.DoctorActivity;
import com.example.appointmentapplication.Doctor.DoctorListActivity;
import com.example.appointmentapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class DoctorSignInActivity extends AppCompatActivity {


    LinearLayout linearLayoutSignIn;
    ProgressBar progressBar;

    EditText etEmail;
    EditText etPassword;
    Button btnSignIn;
    TextView tvSignUp;

    TextView tvPatientSignIn;
    CardView cvPatientSignIn;

    FirebaseAuth mAuthDoctor;
    FirebaseUser mDoctor;


    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_in);



        etEmail = findViewById(R.id.doctor_etEmail);
        etPassword = findViewById(R.id.doctor_etPassword);
        btnSignIn = findViewById(R.id.doctor_btnSignIn);
        tvSignUp = findViewById(R.id.doctor_tvSignUp);
        tvPatientSignIn = findViewById(R.id.tv_patient_sign_in);
        cvPatientSignIn = findViewById(R.id.cv_tv_doc_sign_in);

        linearLayoutSignIn = findViewById(R.id.doctor_sign_in_linear_layout);
        progressBar = findViewById(R.id.doctor_sign_in_loading);

        mAuthDoctor = FirebaseAuth.getInstance();
        mDoctor = mAuthDoctor.getCurrentUser();


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Please enter your Email");
                    return;
                }


                if (TextUtils.isEmpty(pass)) {
                    etPassword.setError("Please enter your Password");
                    return;
                }


                if (pass.length() < 6) {
                    etPassword.setError("pass must be of atleast 6 characters");
                    return;
                }

                linearLayoutSignIn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                mAuthDoctor.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(DoctorSignInActivity.this, "Doctor Sign in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), DoctorActivity.class));
                            finish();

                        } else {
                            linearLayoutSignIn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.e("av",task.getException().getMessage());
                            Toast.makeText(DoctorSignInActivity.this, "error occured: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {    linearLayoutSignIn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.e("Av","Sign in failed");
                    }
                });
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoctorSignUpActivity.class));
            }
        });

        tvPatientSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /*
        cvPatientSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
            }
}