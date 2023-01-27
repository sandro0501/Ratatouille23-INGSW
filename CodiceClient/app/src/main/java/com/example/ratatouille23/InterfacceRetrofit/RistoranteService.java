package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Models.Ristorante;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RistoranteService {

    @PUT("ristorante")
    Call<ResponseBody> updateRistorante(
            @Body Ristorante ristorante
    );

    @GET("ristorante/{id}")
    Call<ResponseBody> getRistorante (
            @Path("id") int idRistorante
    );

}
