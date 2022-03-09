package com.example.appointmentapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointmentapplication.Appointment.AppointmentActivity;
import com.example.appointmentapplication.Appointment.AppointmentViewActivity;
import com.example.appointmentapplication.Authentication.SignInActivity;
import com.example.appointmentapplication.Home.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    public static final String ROLE_DOCTOR = "Doctor";
    public static final String USER_FULLNAME = "fullname";


    FirebaseAuth mAuth;
    FirebaseUser mUser;


    TextView tvFullname;
    TextView tvAge;
    TextView tvEmail;
    TextView tvPhone;
    TextView tvAddress;
    TextView tvAppointemnt;



    Button btnLogOut;
    Button btnAppointment;
    Button btnAppointmentView;
    Button btnPatientList;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    btnLogOut = findViewById(R.id.btnLogOut);
    btnAppointment = findViewById(R.id.btnAppointment);
    btnAppointmentView = findViewById(R.id.btnViewAppointment);
    btnPatientList = findViewById(R.id.btn_patient_list);

    tvAddress = findViewById(R.id.tvAddress);
    tvFullname =findViewById(R.id.tvFullName);
    tvPhone= findViewById(R.id.tvPhone);
    tvAge = findViewById(R.id.tvAge);
    tvEmail = findViewById(R.id.tvEmail);

    tvAppointemnt = findViewById(R.id.tv_appointment);

    mAuth = FirebaseAuth.getInstance();
    mUser= mAuth.getCurrentUser();



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {

                    user = snapshot.getValue(User.class);

                    if(user!=null)
                    {
                        display(user);
                    }
                }
                catch (Exception e)
                {
                    Log.e("av",e.getMessage());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    btnLogOut.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }
    });

    btnAppointment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AppointmentActivity.class);
                intent.putExtra("fullname",user.getFullname());
        startActivity(intent);

        }
    });


    btnAppointmentView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), AppointmentViewActivity.class));
        }
    });


  /*  btnPatientList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            try {

                startActivity(new Intent(MainActivity.this, DoctorActivity.class));

            }
            catch (Exception e)
            {
                Log.e("avMain",e.getMessage());
            }
        }
    });
*/
    }



    public void display(User user)
    {
        String fullname = user.getFullname();
        String email = user.getEmail();
        int age = user.getAge();
        String address= user.getAddress();
        long phone = user.getPhoneno();
        String role = user.getRole();


    tvFullname.setText(fullname);
    tvEmail.setText("Email: "+email);
    tvAddress.setText("Address: " + address);
    tvAge.setText("Age: "+ String.valueOf(age));
    tvPhone.setText("Phone: "+ String.valueOf(phone));


    if(role.equals(ROLE_DOCTOR))
    {
        btnPatientList.setVisibility(View.VISIBLE);

    }
    else {
        btnAppointmentView.setVisibility(View.VISIBLE);
        btnAppointment.setVisibility(View.VISIBLE);
        tvAppointemnt.setVisibility(View.VISIBLE);
    }

    }

    @Override
    public void onBackPressed() {

    }
}