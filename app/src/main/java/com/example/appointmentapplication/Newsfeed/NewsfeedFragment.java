package com.example.appointmentapplication.Newsfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentapplication.Newsfeed.ApiUtilities;
import com.example.appointmentapplication.Newsfeed.NewsfeedAdapter;
import com.example.appointmentapplication.Newsfeed.mainNews;
import com.example.appointmentapplication.Newsfeed.modelClass;
import com.example.appointmentapplication.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

        public class NewsfeedFragment extends Fragment
        {


            String apiKey = "be39e2e137c342af9305bf21683cf23d";
            ArrayList<modelClass> modelClassArrayList;
            NewsfeedAdapter adapter;
            String country = "in";
            String category = "health";
            private RecyclerView recyclerViewOfHealth;


            @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


                View view = inflater.inflate(R.layout.newsfeed_fragment,null);

                recyclerViewOfHealth = view.findViewById(R.id.vpRecyclerViewNewsfeed);

                modelClassArrayList = new ArrayList<>();
                recyclerViewOfHealth.setLayoutManager(new LinearLayoutManager(getContext()));

                adapter = new NewsfeedAdapter(getContext(),modelClassArrayList);
                recyclerViewOfHealth.setAdapter(adapter);

                findNews();


                return view;
            }

            private void findNews()
            {
                ApiUtilities.getApiInterface().getCategoryNews(country,category,100,apiKey).enqueue(new Callback<mainNews>() {
                    @Override
                    public void onResponse(Call<mainNews> call, Response<mainNews> response) {

                        if(response.isSuccessful())
                        {
                            modelClassArrayList.addAll(response.body().getArticles());
                            adapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<mainNews> call, Throwable t) {

                    }
                });
            }


        }


