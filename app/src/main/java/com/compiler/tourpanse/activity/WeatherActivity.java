package com.compiler.tourpanse.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.adapter.ForecastListAdapter;
import com.compiler.tourpanse.helper.SaveUserCredentialsToSharedPreference;
import com.compiler.tourpanse.pojo.CurrentWeatherResponse;
import com.compiler.tourpanse.pojo.WeatherForecastResponse;
import com.compiler.tourpanse.pojo.WeatherList;
import com.compiler.tourpanse.service.Constant;
import com.compiler.tourpanse.service.WeatherServiceApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    ImageView weatherImageIv;
    TextView tempTv, weatherSummaryTv, weatherDetailTv;
    ListView forecastListView;

    WeatherServiceApi weatherServiceApi;
    ForecastListAdapter adapter;

    String cityName;
    String tempUnit = Constant.CELSIUS_UNIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weatherImageIv = (ImageView) findViewById(R.id.weatherImageIv);
        tempTv = (TextView) findViewById(R.id.tempTv);
        weatherSummaryTv = (TextView) findViewById(R.id.weatherSummaryTv);
        weatherDetailTv = (TextView) findViewById(R.id.weatherDetailTv);
        forecastListView = (ListView) findViewById(R.id.forecastListView);

        networkLibraryInitialize();
        getCurrentWeatherData();
        getWeatherForecastData();
    }

    private void getCurrentWeatherData() {
        cityName = getIntent().getStringExtra("city");
        String userUrl = "weather?q=" + cityName + "&units=" + tempUnit + Constant.ALP_KEY;
        Call<CurrentWeatherResponse> currentWeatherResponseCall = weatherServiceApi.getAllWeather(userUrl);
        currentWeatherResponseCall.enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                CurrentWeatherResponse currentWeatherResponse = response.body();
                //Log.e("Weather", "onResponse: " + currentWeatherResponse.getMain().getTemp() + " City: " + cityName);
                Picasso.with(getApplicationContext()).load(currentWeatherResponse.getWeather().get(0).getIcon()).into(weatherImageIv);
                tempTv.setText(String.valueOf((int) Math.ceil(currentWeatherResponse.getMain().getTemp())) + (char) 0x00B0 + "C");
                weatherSummaryTv.setText(currentWeatherResponse.getWeather().get(0).getMain());
                weatherDetailTv.setText(currentWeatherResponse.getWeather().get(0).getDescription());
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {

            }
        });
    }

    private void getWeatherForecastData() {
        cityName = getIntent().getStringExtra("city");
        String url = "forecast/daily?q=" + cityName + "&mode=json&units=metric&cnt=7&appid=20c5d5dba6ab6ff1a57258e71ca55a0e";
        Call<WeatherForecastResponse> forecastResponseCall = weatherServiceApi.getAllWeatherForecast(url);
        forecastResponseCall.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                WeatherForecastResponse wfr = response.body();
                ArrayList<WeatherList> weatherLists = new ArrayList<WeatherList>();
                weatherLists = (ArrayList<WeatherList>) wfr.getList();

                adapter = new ForecastListAdapter(WeatherActivity.this, weatherLists);
                forecastListView.setAdapter(adapter);
                for (WeatherList weatherList : weatherLists) {
                    Log.d("ListResult", "onResponse: " + weatherList.getDt() + " clouds " + weatherList.getClouds()
                            + " getDeg " + weatherList.getDeg());
                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                Log.e("onFailure", "onFailure: " + t);
            }
        });
    }

    private void networkLibraryInitialize() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherServiceApi = retrofit.create(WeatherServiceApi.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu:
                SaveUserCredentialsToSharedPreference saveUserCredentialsToSharedPreference = null;
                saveUserCredentialsToSharedPreference.saveUserCredentials(0);
                startActivity(new Intent(this, LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent Act2Intent = new Intent(this, MainActivity.class);
            startActivity(Act2Intent);
            finish();
            return true;
        }
        return false;
    }
}
