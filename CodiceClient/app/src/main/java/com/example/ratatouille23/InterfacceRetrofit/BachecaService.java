package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Handlers.AggiornaAvvisoHandler;
import com.example.ratatouille23.Models.Utente;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BachecaService {

    @GET("bacheca")
    Call<ResponseBody> getAvvisi(
            @Query("uid") int idUtente
    );

    @PUT("bacheca")
    Call<ResponseBody> viewAvviso(
            @Body AggiornaAvvisoHandler handle
    );

    @PUT("/visibile")
    Call<ResponseBody> nascondiAvviso(
            @Body AggiornaAvvisoHandler handle
    );

}