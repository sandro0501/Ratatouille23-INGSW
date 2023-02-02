package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Handlers.EliminaProdottiHandler;
import com.example.ratatouille23.Models.Prodotto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProdottoService {

    @GET("prodotto/{id}")
    Call<ResponseBody> getDispensaByIdRistorante(@Path("id") int idRistorante);

    @POST("prodotto")
    Call<ResponseBody> insertProdotto(@Body Prodotto p);

    @PUT("prodotto")
    Call<ResponseBody> updateProdotto(@Body Prodotto p);

    @PATCH("prodotto")
    Call<ResponseBody> deleteProdotto(@Body EliminaProdottiHandler listaProdottiDaEliminare);


}
