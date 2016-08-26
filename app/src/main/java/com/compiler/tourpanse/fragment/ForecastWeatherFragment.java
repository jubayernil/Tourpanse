package com.compiler.tourpanse.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compiler.tourpanse.R;

/**
 * Created by User on 8/25/2016.
 */
public class ForecastWeatherFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forecast_weather_fragment, container, false);
    }
}
