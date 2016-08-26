package com.compiler.tourpanse.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.adapter.PlaceListAdapter;
import com.compiler.tourpanse.pojoplace.PlaceResponse;
import com.compiler.tourpanse.pojoplace.Result;
import com.compiler.tourpanse.service.Constant;
import com.compiler.tourpanse.service.PlaceServiceApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceActivity extends AppCompatActivity {

    PlaceServiceApi placeServiceApi;
    PlaceListAdapter placeListAdapter;

    ListView placeListView;

    int placeRadius;
    String placeType;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        placeListView = (ListView) findViewById(R.id.placeListView);

        networkLibraryInitialize();
        getNearbyPlace();
    }

    private void getNearbyPlace() {
        placeRadius = 3000;
        placeType = "restaurant";
        latitude = 23.8204245;
        longitude = 90.4330839;

        String userUrl = "json?location="+latitude + "," + longitude + "&radius=" + placeRadius + "&type=" + placeType + Constant.PLACE_API_KEY;

        Call<PlaceResponse> placeResponseCall = placeServiceApi.getAllPlace(userUrl);
        placeResponseCall.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                PlaceResponse placeResponse = response.body();
                String placeName;
                double lat, lng;
                String openNow = null;
                ArrayList<Result> results = new ArrayList<Result>();
                results = (ArrayList<Result>) placeResponse.getResults();

                placeListAdapter = new PlaceListAdapter(PlaceActivity.this, results);
                placeListView.setAdapter(placeListAdapter);

                for (int i = 0; i < results.size(); i++){
                    Log.e("PlaceApi", i+"----------------------------------------------"+i);
                    Log.e("PlaceApi", "Name: " + results.get(i).getName());
                    Log.e("PlaceApi", "Lat and Lng: "+ results.get(i).getGeometry().getLocation().getLat()+","+results.get(i).getGeometry().getLocation().getLng());
                    Log.e("PlaceApi", "Address: "+ results.get(i).getVicinity() );
                    Log.e("PlaceApi", i+"----------------------------------------------"+i);
                }

            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {

            }
        });

    }

    private void networkLibraryInitialize() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.PLACE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        placeServiceApi = retrofit.create(PlaceServiceApi.class);
    }
}
