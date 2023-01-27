package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Models.Ristorante;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface RistoranteService {

    @PUT("ristorante")
    Call<String> updateRistorante(
            @Body Ristorante ristorante
    );

}
