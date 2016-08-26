package com.compiler.tourpanse.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    Spinner placeTypeSpinner;

    int placeRadius;
    String placeType;
    double latitude, longitude;

    String[] placeTypeArray = {
            "accounting",
            "airport",
            "amusement_park",
            "aquarium",
            "art_gallery",
            "atm",
            "bakery",
            "bank",
            "bar",
            "beauty_salon",
            "bicycle_store",
            "book_store",
            "bowling_alley",
            "bus_station",
            "cafe",
            "casino",
            "clothing_store",
            "department_store",
            "doctor",
            "embassy",
            "fire_station",
            "food",
            "gym",
            "hospital",
            "jewelry_store",
            "local_government_office",
            "mosque",
            "museum",
            "night_club",
            "painter",
            "park",
            "parking",
            "pharmacy",
            "police",
            "post_office",
            "restaurant",
            "school",
            "shoe_store",
            "shopping_mall",
            "spa",
            "stadium",
            "storage",
            "store",
            "taxi_stand",
            "train_station",
            "transit_station",
            "travel_agency",
            "university",
            "veterinary_care",
            "zoo"
    };

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        placeListView = (ListView) findViewById(R.id.placeListView);
        placeTypeSpinner = (Spinner) findViewById(R.id.placeTypeSpinner);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,placeTypeArray);
        placeTypeSpinner.setAdapter(adapter);
        networkLibraryInitialize();
    }

    private void getNearbyPlace() {
        placeRadius = 3000;
        latitude = 23.8204245;
        longitude = 90.4330839;

        String userUrl = "json?location="+latitude + "," + longitude + "&radius=" + placeRadius + "&type=" + getPlaceType() + Constant.PLACE_API_KEY;

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

    public void getPlaceNearby(View view) {
        setPlaceType(placeTypeSpinner.getSelectedItem().toString());
        getNearbyPlace();
    }
}
