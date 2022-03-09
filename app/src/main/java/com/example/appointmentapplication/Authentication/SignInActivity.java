package com.example.appointmentapplication.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointmentapplication.Home.Designation;
import com.example.appointmentapplication.Doctor.DoctorActivity;
import com.example.appointmentapplication.Home.HomeActivity;
import com.example.appointmentapplication.R;
import com.example.appointmentapplication.Home.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    ConstraintLayout constraintLayoutSignIn;

    ProgressBar tvLoading;

    EditText etEmail;
    EditText etPassword;
    Button btnSignIn;
    TextView tvSignUp;
    CardView cvTvDocSignIn;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    String role;
    String s;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);




        constraintLayoutSignIn = findViewById(R.id.signInConstraintLayout);

        tvLoading=findViewById(R.id.sign_in_loading);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
        cvTvDocSignIn= findViewById(R.id.cv_tv_doc_sign_in);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

//
//        mAuth.signOut();
//        finish();
//
//Log.e("av", mUser.getEmail());

        if (mUser != null) {


            databaseReference = FirebaseDatabase.getInstance().getReference("Designation").child(mUser.getUid());


            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                 try {

                     Designation designation = snapshot.getValue(Designation.class);

                     switch (designation.getRole().trim())
                     {
                         case SignUpActivity.ROLE_PATIENT:
                         startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                         finish();
                         break;
                         case DoctorSignUpActivity.ROLE_DOCTOR:
                             startActivity(new Intent(getApplicationContext(),DoctorActivity.class));
                             finish();

                     }

                 }catch (Exception e)
                 {
                     Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                     mAuth.signOut();
                     finish();
                 }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Log.e("av", error.getMessage());

                }
            });


        }

        else
        {
            constraintLayoutSignIn.setVisibility(View.VISIBLE);
            tvLoading.setVisibility(View.INVISIBLE);
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Av","Av4");

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

                Log.e("av","mAuth up");

           //     tvLoading.setVisibility(View.VISIBLE);

                constraintLayoutSignIn.setVisibility(View.INVISIBLE);
                tvLoading.setVisibility(View.VISIBLE);


                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.e("av","mAuth down");


                            Toast.makeText(SignInActivity.this, "user Sign in", Toast.LENGTH_SHORT).show();

                            mUser = mAuth.getCurrentUser();

                            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());


                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);


                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    constraintLayoutSignIn.setVisibility(View.VISIBLE);
                                    tvLoading.setVisibility(View.INVISIBLE);
                         //           tvLoading.setVisibility(View.INVISIBLE);
                                        finish();
                                    Log.e("Av",error.getMessage());

                                }
                            });





                        } else {
                            constraintLayoutSignIn.setVisibility(View.VISIBLE);
                            tvLoading.setVisibility(View.INVISIBLE);
                            Log.e("av",task.getException().getMessage());
                            Toast.makeText(SignInActivity.this, "error occured: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)

                    {
                        constraintLayoutSignIn.setVisibility(View.VISIBLE);
                        tvLoading.setVisibility(View.INVISIBLE);
                        //             tvLoading.setVisibility(View.INVISIBLE);
                        Log.e("Av","Sign in failed");
                    }
                });
            }
        });


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        cvTvDocSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,DoctorSignInActivity.class));
            }
        });

        Log.e("av","mAuth last");



    }




}