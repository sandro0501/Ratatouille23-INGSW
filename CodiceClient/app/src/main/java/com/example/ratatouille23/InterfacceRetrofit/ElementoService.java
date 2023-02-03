package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Handlers.HandleAllergeni;
import com.example.ratatouille23.Handlers.HandleElemento;
import com.example.ratatouille23.Handlers.HandlePreparazione;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Handlers.HandleAllergeni;
import com.example.ratatouille23.Models.Preparazione;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ElementoService {

    @POST("elemento")
    Call<ResponseBody> aggiungiElemento(@Body HandleElemento elemento);


    @PUT("elemento")
    Call<ResponseBody> modificaElemento(@Body HandleElemento handle);

    @POST("listaallergeni")
    Call<ResponseBody> impostaAllergeni(@Body ArrayList<HandleAllergeni> lista);

    @POST("preparazione")
    Call<ResponseBody> impostaPreparazione(@Body HandlePreparazione preparazione);

}
