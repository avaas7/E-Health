package com.example.appointmentapplication.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointmentapplication.Home.Designation;
import com.example.appointmentapplication.Home.HomeActivity;
import com.example.appointmentapplication.R;
import com.example.appointmentapplication.Home.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity{

    public static final String ROLE_PATIENT = "Patient";

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    EditText etFullName;
    EditText etEmail;
    EditText etPhone;
    EditText etAge;
    EditText etPassword;
    EditText etAddress;


    Button btnSignUp;
    TextView tvLogIn;

    ConstraintLayout constraintLayout;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();


        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmailSignUp);
        etPhone = findViewById(R.id.etPhoneNo);
        etAge= findViewById(R.id.etAge);
        etPassword = findViewById(R.id.etPasswordSignUp);
        etAddress= findViewById(R.id.etAddress);

        btnSignUp = findViewById(R.id.btnSignUp);

        tvLogIn = findViewById(R.id.tvSignIn);

        constraintLayout = findViewById(R.id.sign_up_constraint_layout);
        progressBar = findViewById(R.id.sign_up_loading);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                String age = etAge.getText().toString().trim();
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


                if(TextUtils.isEmpty(age))
                {
                    etAge.setError("Please enter your Age");
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

                constraintLayout.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);


                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
           mUser = mAuth.getCurrentUser();

           long phno2= Long.valueOf(phoneNo);
            int age2 = Integer.parseInt(age);
                            User user = new User(email,pass,fullName,age2,address,phno2);
                            Designation designation = new Designation(email,mUser.getUid(),SignUpActivity.ROLE_PATIENT);
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Users");

                            databaseReference1.child(mUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                    }else
                                    {

                                        constraintLayout.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(SignUpActivity.this, "error occured: " + task.getException().getMessage(),   Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Designation");

                            databaseReference2.child(mUser.getUid()).setValue(designation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(SignUpActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();

                                    }else
                                    {

                                        constraintLayout.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);

                                        Toast.makeText(SignUpActivity.this, "error occured: " + task.getException().getMessage(),   Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                        }
                        else
                        {

                            constraintLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUpActivity.this, "error occured: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
            }
        });


    }



}