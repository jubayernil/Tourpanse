package com.compiler.tourpanse.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    PlaceServiceApi placeServiceApi;
    PlaceListAdapter placeListAdapter;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    ListView placeListView;
    Spinner placeTypeSpinner;

    int placeRadius;
    String placeType;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

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

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();

        networkLibraryInitialize();
    }

    private void getNearbyPlace() {
        placeRadius = 3000;

        String userUrl = "json?location="+getLatitude() + "," + getLongitude() + "&radius=" + placeRadius + "&type=" + getPlaceType() + Constant.PLACE_API_KEY;

        Call<PlaceResponse> placeResponseCall = placeServiceApi.getAllPlace(userUrl);
        placeResponseCall.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                PlaceResponse placeResponse = response.body();
                String placeName;
                double lat, lng;
                String openNow = null;
                ArrayList<Result> results = new ArrayList<Result>();
                try {
                    results = (ArrayList<Result>) placeResponse.getResults();

                    placeListAdapter = new PlaceListAdapter(PlaceActivity.this, results);
                    placeListView.setAdapter(placeListAdapter);
                } catch (Exception e){

                }

                /*for (int i = 0; i < results.size(); i++){
                    Log.e("PlaceApi", i+"----------------------------------------------"+i);
                    Log.e("PlaceApi", "Name: " + results.get(i).getName());
                    Log.e("PlaceApi", "Lat and Lng: "+ results.get(i).getGeometry().getLocation().getLat()+","+results.get(i).getGeometry().getLocation().getLng());
                    Log.e("PlaceApi", "Address: "+ results.get(i).getVicinity() );
                    Log.e("PlaceApi", i+"----------------------------------------------"+i);
                }*/

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setFastestInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        setLatitude(location.getLatitude());
        setLongitude(location.getLongitude());
        //Toast.makeText(PlaceActivity.this, ":::Lat:::"+getLatitude()+":::Lng:::"+getLongitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        mGoogleApiClient.disconnect();
        super.onPause();
    }
}
