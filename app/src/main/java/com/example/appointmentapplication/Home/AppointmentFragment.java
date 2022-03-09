package com.example.appointmentapplication.Home;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appointmentapplication.Appointment.AppointmentActivity;
import com.example.appointmentapplication.Appointment.AppointmentViewActivity;
import com.example.appointmentapplication.Doctor.DoctorListActivity;
import com.example.appointmentapplication.Doctor.PrescriptionListActivity;
import com.example.appointmentapplication.R;
import com.example.appointmentapplication.Service.ServiceActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppointmentFragment extends Fragment {

    CardView cvAppointmentBook;
    CardView cvAppointmentView;
    CardView cvPrescription;
    CardView cvDoctorList;
    CardView cvEmergencyCall;
    CardView cvServicesList;


    public static final String ROLE_DOCTOR_FRAGMENT = "Doctor";
    public static final String USER_FULLNAME_FRAGMENT = "fullname";


    FirebaseAuth mAuth;
    FirebaseUser mUser;


    Button btnAppointment;
    Button btnAppointmentView;

    User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment,container,false);


        btnAppointment = view.findViewById(R.id.btnAppointment);
        btnAppointmentView = view.findViewById(R.id.btnViewAppointment);

        cvAppointmentBook = view.findViewById(R.id.cv_book_appointment);
        cvAppointmentView = view.findViewById(R.id.cv_view_appointment);
        cvDoctorList = view.findViewById(R.id.cv_Doctors);
        cvServicesList = view.findViewById(R.id.cv_services);
        cvPrescription = view.findViewById(R.id.cv_prescription);
        cvEmergencyCall = view.findViewById(R.id.cv_emergency_call);


        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {

                    user = snapshot.getValue(User.class);

                }
                catch (Exception e)
                {
                    Log.e("av",e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cvAppointmentBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AppointmentActivity.class);
                intent.putExtra("fullname",user.getFullname());
                startActivity(intent);

            }
        });

        cvAppointmentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AppointmentViewActivity.class));
            }
        });

        cvPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PrescriptionListActivity.class));
            }
        });

        cvDoctorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DoctorListActivity.class));
            }
        });

        cvServicesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ServiceActivity.class));
            }
        });

        cvEmergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

                builder1.setTitle("Emergency call alert");
                builder1.setMessage("Are you sure to call an ambulance ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+"9803397768"));
                        startActivity(intent);
                    }
                });

                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder1.show();

                }
        });

        return view;
    }
}