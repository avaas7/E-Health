package com.example.appointmentapplication.Appointment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AppointmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String status="Pending";


    String sRandom;

    ArrayAdapter<CharSequence> spinnerAdapter;

    CalendarView calendarView;
    Spinner spinner1;

    TextView tvDate;
    Button btnAppointment;
    Button btnCancel;

    String date;
    String department;
    String fullname;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_activity);

        int random = new Random().nextInt(9999999);
         sRandom = "a" + String.valueOf(random);


         Intent intent = getIntent();
         fullname = intent.getStringExtra(MainActivity.USER_FULLNAME);

        calendarView = findViewById(R.id.calendarView);
        tvDate = findViewById(R.id.tvDate);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        btnAppointment = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.appintment_cancel);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        spinner1.setOnItemSelectedListener(this);
        spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.department_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(spinnerAdapter);

        String text = intent.getStringExtra(UpcomingFragment.EXTRA_KEY);
        if(text!=null)
        {

            setEditData(text);
        }
        else
        {
            setAllTheData();

        }



        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment appointment = new Appointment(date, department,mUser.getUid(),sRandom,status,fullname,mUser.getUid()+"_"+status);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments");
                databaseReference.child(sRandom).setValue(appointment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {

                            Toast.makeText(AppointmentActivity.this, "Appointment registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AppointmentViewActivity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(AppointmentActivity.this, "error occured: " + task.getException().getMessage(),   Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
try {


    AlertDialog.Builder builder1 = new AlertDialog.Builder(AppointmentActivity.this);

    builder1.setTitle("Appointment Cancelation");
    builder1.setMessage("Are you sure ?");
    builder1.setCancelable(true);

    builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            FirebaseDatabase.getInstance().getReference("Appointments").child(sRandom).removeValue();
            Toast.makeText(AppointmentActivity.this, "Appointment canceled", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), AppointmentViewActivity.class));
            finish();
        }
    });

    builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    });

    builder1.show();

   // AlertDialog alert1 = builder1.create();
  //e
    // ert1.show();
}
catch (Exception e)
{
    Log.e("avError1",e.getMessage());
}



            }
        });

    }

    private void setEditData(String key) {

        btnCancel.setVisibility(View.VISIBLE);

        sRandom = key;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments").child(key);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
          @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Appointment appointment = snapshot.getValue(Appointment.class);

                date = appointment.getDate();
                department = appointment.getDepartment();
                fullname = appointment.getPatientName();
                tvDate.setText("Date: " + date);

                try {


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Date miti = sdf.parse(date);
                    long millis = miti.getTime();

                    calendarView.setDate(millis);

                    calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        @Override
                        public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                            date = String.valueOf(i) + "/"+ String.valueOf(i1+1)+ "/"+String.valueOf(i2);
                            tvDate.setText("Date: " + date);
                        }
                    });


                    Log.e("avDate",String.valueOf(millis));
                    }
                    catch (Exception e)
                    {
                        Log.e("avDate",e.getMessage());
                    }

              if (department != null) {
                  int spinnerPosition = spinnerAdapter.getPosition(department);
                  spinner1.setSelection(spinnerPosition);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAllTheData() {

        date = todayDate();
        // tvDate.setText("Date: - ");
        tvDate.setText("Date: "+ date);

        Log.e("av",String.valueOf(calendarView.getDate()));


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = String.valueOf(i) + "/"+ String.valueOf(i1+1)+ "/"+String.valueOf(i2);
                tvDate.setText("Date: " + date);
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

    private String todayDate()
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;

    }

}