package com.example.appointmentapplication.Appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.appointmentapplication.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class AppointmentViewActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabUpcoming;
    TabItem tabPast;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_activity_view);

        tabLayout = findViewById(R.id.tab_layout);
        tabUpcoming  = findViewById(R.id.tab_upcoming);
        tabPast = findViewById(R.id.tab_past);

        viewPager = findViewById(R.id.view_pager);

        tabLayout.setupWithViewPager(viewPager);

        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        appointmentAdapter.addFragment(new UpcomingFragment(),"Upcoming");
        appointmentAdapter.addFragment(new PastFragment(),"Past");


        viewPager.setAdapter(appointmentAdapter);



    }
}