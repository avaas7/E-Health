package com.example.appointmentapplication.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.appointmentapplication.Newsfeed.NewsfeedFragment;
import com.example.appointmentapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

       getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new AppointmentFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        fragment= new HomeFragment();
                        break;

                    case R.id.nav_appointment:
                        fragment= new AppointmentFragment();
                        break;

                    case R.id.nav_newsfeed:
                        fragment= new NewsfeedFragment();
                        break;

                    case R.id.nav_map:
                        fragment= new MapsFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).commit();

                return true;
            }
        });

    }
}