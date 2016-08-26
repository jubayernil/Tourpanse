package com.compiler.tourpanse.service;

import com.compiler.tourpanse.pojoplace.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by User on 8/26/2016.
 */
public interface PlaceServiceApi {
    @GET
    Call<PlaceResponse> getAllPlace(@Url String url);
}
