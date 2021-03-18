package com.madhu.interpol.Retrofit;

import com.madhu.interpol.DataClasses.ForcesData;
import com.madhu.interpol.DataClasses.SpecificForceData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PoliceGetDataService {

    @GET("forces")
    Call<List<ForcesData>> getForces();

    @GET("forces/{forceId}")
    Call<SpecificForceData> getSpecificForce(@Path("forceId") String forceId);



}
