package com.example.appointmentapplication.Service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.appointmentapplication.Doctor.DoctorListActivity;
import com.example.appointmentapplication.Doctor.DoctorListAdapter;
import com.example.appointmentapplication.Doctor.Doctors;
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

public class ServiceActivity extends AppCompatActivity {


    ArrayList<services> serviceList = new ArrayList<>();

    ServiceAdapter adapter;

    RecyclerView recyclerView;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);


        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.rv_services_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(ServiceActivity.this));

        adapter = new ServiceAdapter(ServiceActivity.this,serviceList);


        services service1 = new services("Medical","Medicine ist he science and practice of establishing the diagnosis,prognosis treatment and prevention of disease ",R.drawable.medicineicon);
        services service2 = new services("Surgical","Surgical services are the most spectacularly visible function of the hospital. In a general hospital, all branches of the surgical services diagnostic, therapeutic and operation theatres are combined as one department.",R.drawable.surgicalicon);
        services service3 = new services("Obstetrical","Obstetrical services are also organised like other department with policies and procedures peculiar to its requirement. Qualified staff should be available even off-time with availability of consultants for complicated cases.",R.drawable.obstetricalicon);
        services service4 = new services("Anesthesiology","Anesthesiology service is the least visible service in a hospital, but as important as others. Apart from the high professional knowledge and skill of the anesthesiologists, the service should conform to all the standing policies and procedures.",R.drawable.anesthesiologyicon);
        services service5 = new services("Laboratory","Services appropriate to the need of the hospital should be available, conducted and supervised by competent technicians and pathologists respectively, with appropriate system of maintenance of records of tests and standing orders and procedures.",R.drawable.laboratoryicon);
        services service6 = new services("Radiology","Facilities, including the number of radiographic machines must be adequate to meet the requirement of the diagnostic and therapeutic procedures, with competent medical and technical paramedical staff.",R.drawable.radiologyicon);
        services service7 = new services("OPD","It should be an extension of the total hospital service itself, for those who do not require hospital bed care.",R.drawable.opdicon);
        services service8 = new services("Physiotherapy and Rehabilitation","The service should be operated under the direction of medical staff qualified in physical medicine and should also have appropriately qualified physiotherapy and occupational therapy staff.",R.drawable.physiotherapyicon);
        services service9 = new services("Nursing","Nursing services implement the physicians’ plan of care and provides nursing care for the patients’ assessed needs. The quality of hospital care is to a significant extent associated with the level of nursing care.",R.drawable.nursingicon);



        serviceList.add(service1);
        serviceList.add(service2);
        serviceList.add(service3);
        serviceList.add(service4);
        serviceList.add(service5);
        serviceList.add(service6);
        serviceList.add(service7);
        serviceList.add(service8);
        serviceList.add(service9);




//        adapter.notifyDataSetChanged();


        recyclerView.setAdapter(adapter);


    }



        /*
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Service");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    services service= dataSnapshot.getValue(services.class);
                    serviceList.add(service);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ServiceActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

*/




}