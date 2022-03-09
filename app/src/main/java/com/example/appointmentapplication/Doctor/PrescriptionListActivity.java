package com.example.appointmentapplication.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.appointmentapplication.Appointment.Appointment;
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

public class PrescriptionListActivity extends AppCompatActivity {

    TextView tvNoPrescriptionList;

    public static final String PRESCRIPTION_LIST_EXTRA_KEY = "key";
    ArrayList<Appointment> appointmentList = new ArrayList<>();

    PrescriptionListAdapter adapter;

    RecyclerView recyclerView;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.rv_prescription_list);

        tvNoPrescriptionList = findViewById(R.id.tvNoPrescriptions);


        adapter = new PrescriptionListAdapter(PrescriptionListActivity.this,appointmentList);

        recyclerView.setLayoutManager(new LinearLayoutManager(PrescriptionListActivity.this));

        recyclerView.setAdapter(adapter);




        try {



            Query query =  FirebaseDatabase.getInstance().getReference("Appointments").orderByChild("uid_status").equalTo(mUser.getUid()+"_"+ CheckUpActivity.EXTRA_COMPLETED);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Appointment appointment = dataSnapshot.getValue(Appointment.class);
                        appointmentList.add(appointment);

                    }

                    if (appointmentList.size()==0)
                    {
                        tvNoPrescriptionList.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                    // display();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        catch (Exception e)
        {

            if (appointmentList.size()==0)
            {
                tvNoPrescriptionList.setVisibility(View.VISIBLE);
            }
            Log.e("av",e.getMessage());
        }



    }


}