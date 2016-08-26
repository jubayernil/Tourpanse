package com.compiler.tourpanse.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.compiler.tourpanse.R;

/**
 * Created by User on 8/25/2016.
 */
public class CurrentWeatherFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.current_weather_fragment, container, false);

        /*String getArgumentTemp = getArguments().getString("tempData");
        TextView tempTv = (TextView) view.findViewById(R.id.tempTv);
        tempTv.setText(getArgumentTemp);*/

        return view;
    }
}
