package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Handlers.AggiornaRuoloHandler;
import com.example.ratatouille23.Handlers.RegistraUtenteHandler;
import com.example.ratatouille23.Handlers.UtenteHandler;
import com.example.ratatouille23.Models.Utente;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DipendentiService {

    @GET("utente/{idRist}")
    Call<ResponseBody> getDipendentiByIdRistorante(@Path("idRist") int idRistorante);

    @PATCH("utente")
    Call<ResponseBody> deleteDipendente(@Body UtenteHandler utenteDaEliminare);

    @POST("utente/new")
    Call<ResponseBody> insertDipendente(@Body RegistraUtenteHandler handler);

    @PUT("utente")
    Call<ResponseBody> updateDipendente(@Body AggiornaRuoloHandler handler);

}
