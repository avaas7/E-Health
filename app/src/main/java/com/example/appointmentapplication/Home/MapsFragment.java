package com.example.appointmentapplication.Home;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appointmentapplication.Map.MapsActivity;
import com.example.appointmentapplication.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MapsFragment extends Fragment {


    private static final int REQUEST_CHECK_SETTINGS = 3;
    private LocationRequest locationRequest;
    private static int LOCATION_PERMISSION_REQUEST = 1;
    private static final String TAG= "TAG";


    Button btnLocateHospital;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_maps, container, false);

        btnLocateHospital = view.findViewById(R.id.btn_locate_hospital);

        btnLocateHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationpermissionRequest();

            }
        });

        return view;
    }


    private void locationpermissionRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getActivity()).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    mapsPermissionRequest();

                    //     Toast.makeText(MainActivity.this, "Gps is on", Toast.LENGTH_SHORT).show();


                } catch (ApiException e) {
                    e.printStackTrace();

                    switch (e.getStatusCode())
                    {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(getActivity(),REQUEST_CHECK_SETTINGS);  //dialog ask for location
                            } catch (IntentSender.SendIntentException sendIntentException) {
                                sendIntentException.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });


    }

    private void mapsPermissionRequest()
    {

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
   //         Toast.makeText(getActivity(), "Permission already granted", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"already permission granted");
            startActivity(new Intent(getActivity(), MapsActivity.class));

        }
        else
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION))
            {

                new AlertDialog.Builder(getActivity()).setTitle("Permission needed").setMessage("This permission is needed for accessing your location")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST);
                                Log.e(TAG,"permission requested dialog");
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST);
                Log.e(TAG,"permission requested");
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //    Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"permission granted");
                startActivity(new Intent(getActivity(), MapsActivity.class));
            } else {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"permission denied");
               /* Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("Package",getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);*/

            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CHECK_SETTINGS)
        {
            switch (resultCode)
            {
                case Activity.RESULT_OK:
                    //    Toast.makeText(this, "GPS is turned on", Toast.LENGTH_SHORT).show();
                    mapsPermissionRequest();

                    break;
                case Activity.RESULT_CANCELED:
                    //     Toast.makeText(this, "GPS is denied", Toast.LENGTH_SHORT).show();
            }

        }

    }
}