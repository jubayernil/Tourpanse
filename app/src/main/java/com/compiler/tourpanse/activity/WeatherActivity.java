package com.compiler.tourpanse.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.fragment.CurrentWeatherFragment;
import com.compiler.tourpanse.fragment.ForecastWeatherFragment;
import com.compiler.tourpanse.pojo.CurrentWeatherResponse;
import com.compiler.tourpanse.service.Constant;
import com.compiler.tourpanse.service.WeatherServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    WeatherServiceApi weatherServiceApi;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    CurrentWeatherFragment currentWeatherFragment;
    ForecastWeatherFragment forecastWeatherFragment;

    TextView currentTv, forecastTv;
    LinearLayout weatherContainerLinearLayout;

    String cityName;
    String tempUnit = Constant.CELSIUS_UNIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        currentTv = (TextView) findViewById(R.id.currentTv);
        forecastTv = (TextView) findViewById(R.id.forecastTv);

        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        currentWeatherFragment = new CurrentWeatherFragment();

        fragmentTransaction.add(R.id.weatherContainerLinearLayout, currentWeatherFragment);
        fragmentTransaction.commit();

        currentTv.setBackgroundResource(R.color.colorWhite);
        forecastTv.setBackgroundResource(R.color.colorRed);

        networkLibraryInitialize();
        getCurrentWeatherData();
    }

    public void replace(View view) {
        Fragment newFragment = null;
        if(view.getId()==R.id.currentTv){
            newFragment=new CurrentWeatherFragment();
            currentTv.setBackgroundResource(R.color.colorWhite);
            forecastTv.setBackgroundResource(R.color.colorRed);
        }else if(view.getId()==R.id.forecastTv){
            newFragment=new ForecastWeatherFragment();
            forecastTv.setBackgroundResource(R.color.colorWhite);
            currentTv.setBackgroundResource(R.color.colorRed);
        }

        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.weatherContainerLinearLayout,newFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    private void getCurrentWeatherData() {
        cityName = getIntent().getStringExtra("city");
        String userUrl = "weather?q=" + cityName + "&units=" + tempUnit + Constant.ALP_KEY;
        Call<CurrentWeatherResponse> currentWeatherResponseCall = weatherServiceApi.getAllWeather(userUrl);
        currentWeatherResponseCall.enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                CurrentWeatherResponse currentWeatherResponse = response.body();
                Log.e("Weather", "onResponse: "+currentWeatherResponse.getMain().getTemp()+" City: "+cityName);
                /*Bundle tempData = new Bundle();
                tempData.putString("tempData", String.valueOf(currentWeatherResponse.getMain().getTemp()));
                currentWeatherFragment.setArguments(tempData);*/
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {

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
}
