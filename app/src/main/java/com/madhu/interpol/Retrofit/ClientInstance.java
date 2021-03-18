package com.madhu.interpol.Retrofit;

import com.madhu.interpol.Utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientInstance {


    public static Retrofit getClientInstance()
    {
       return new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }
}
