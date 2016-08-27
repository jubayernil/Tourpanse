package com.compiler.tourpanse.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.adapter.EventAdapter;
import com.compiler.tourpanse.dbhelper.EventDataSource;
import com.compiler.tourpanse.helper.SaveUserCredentialsToSharedPreference;
import com.compiler.tourpanse.models.Event;
import com.compiler.tourpanse.models.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private ListView showEventListView;
    private TextView showUserId;
    private ImageButton addNewEvent;

    private EventAdapter eventAdapter;
    private ArrayList<Event> events;
    private EventDataSource eventDataSource;

    private Intent intent;
    private User user;

    private int userId;
    private String name;
    private String city, state, zip, country;
    private double latitude, longitude;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private SaveUserCredentialsToSharedPreference saveUserCredentialsToSharedPreference;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User();
        saveUserCredentialsToSharedPreference = new SaveUserCredentialsToSharedPreference(MainActivity.this);
        userId = saveUserCredentialsToSharedPreference.getUserCredentials();
        eventDataSource = new EventDataSource(MainActivity.this);
        events = eventDataSource.getAllEvent();
        eventAdapter = new EventAdapter(MainActivity.this, events);

        showUserId = (TextView) findViewById(R.id.showUserId);
        showEventListView = (ListView) findViewById(R.id.showEventListView);
        showEventListView.setAdapter(eventAdapter);

        showUserId.setText("" + userId);
        addNewEvent = (ImageButton) findViewById(R.id.addNewEvent);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();

    }

    public void showToastMsg(String Msg) {
        Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_SHORT).show();
    }

    public void addNewEvent(View view) {
        intent = new Intent(MainActivity.this, AddNewEventActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
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
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        getCurrentAddress(latitude, longitude);
        /*Log.e("Location", "onLocationChanged: " + location.getLatitude() + " " + location.getLongitude());
        Log.e("ca", "Current City: "+getCity() );
        Log.e("ca", "Current Country: "+getCountry() );
        Log.e("ca", "Current State: "+getState() );
        Log.e("ca", "Current Zip: "+getZip() );*/
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

    private void getCurrentAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            setCity(addresses.get(0).getLocality());
            setState(addresses.get(0).getAdminArea());
            setZip(addresses.get(0).getPostalCode());
            setCountry(addresses.get(0).getCountryName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void getWeatherFromMenu(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
        intent.putExtra("city", getCity());
        startActivity(intent);
    }

    public void getLogoutFromMenu(MenuItem item) {
        saveUserCredentialsToSharedPreference.saveUserCredentials(0);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
