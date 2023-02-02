package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Handlers.HandleElemento;
import com.example.ratatouille23.Models.Elemento;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ElementoService {

    @POST("elemento")
    Call<ResponseBody> aggiungiElemento(@Body HandleElemento elemento);

}
