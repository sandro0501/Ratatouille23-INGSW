package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Handlers.InserisciAvvisoHandler;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AvvisoService {


    @POST("avvisi")
    Call<ResponseBody> insertAvviso(
            @Body InserisciAvvisoHandler handler
    );


}
