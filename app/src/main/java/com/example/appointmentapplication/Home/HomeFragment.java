package com.example.appointmentapplication.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointmentapplication.Authentication.SignInActivity;
import com.example.appointmentapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class HomeFragment extends Fragment {


    ImageView profileImageView;

    TextView tvName;
    TextView tvEmail;
    TextView tvPhone;
    TextView tvAddress;
    TextView tvAge;

    Uri imageUri;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseStorage storage;
    StorageReference storageReference;

    Button btnLogOut;

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_home, container, false);

        btnLogOut = view.findViewById(R.id.btnLogOut);
        profileImageView = view.findViewById(R.id.profile_image);

        tvName = view.findViewById(R.id.tvUserProfileFullName);
        tvEmail = view.findViewById(R.id.tvUserProfileEmail);
        tvAddress = view.findViewById(R.id.tvUserProfileAddress);
        tvAge = view.findViewById(R.id.tvUserProfileAge);
        tvPhone = view.findViewById(R.id.tvUserProfilePhoneNo);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        StorageReference reference = storageReference.child("images/"+mUser.getUid());




        if (storageReference!=null) {

            try {
                final File localFile = File.createTempFile(mUser.getUid(),"jpeg");

                reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        profileImageView.setImageBitmap(bitmap);

//                        Toast.makeText(getActivity(), "Picture retrieved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Please add your profile picture", Toast.LENGTH_SHORT).show();
                        Log.e("TAG",e.getMessage());
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }



        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {

                    User user = snapshot.getValue(User.class);

                    if (user != null) {
                        display(user);
                    }
                } catch (Exception e) {
                    Log.e("av", e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), SignInActivity.class));

            }
        });

        return view;


    }

    private void choosePicture() {

        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    public void display(User user) {
        String fullname = user.getFullname();
        String email = user.getEmail();
        int age = user.getAge();
        String address = user.getAddress();
        long phone = user.getPhoneno();
        String role = user.getRole();


        tvName.setText(fullname);
        tvEmail.setText("Email: " + email);
        tvAddress.setText("Address: " + address);
        tvAge.setText("Age: " + String.valueOf(age));
        tvPhone.setText("Phone: " + String.valueOf(phone));


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==-1 && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
            uploadPicture();
        }

    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Uploading Image...");
        pd.show();

        StorageReference reference = storageReference.child("images/"+mUser.getUid());

        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
//                Snackbar.make(view.findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("percentage: " + (int)progressPercent+ "%");
            }
        });
    }
}