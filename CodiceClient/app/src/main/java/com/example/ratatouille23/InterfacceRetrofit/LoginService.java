package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Handlers.LoginHandler;
import com.example.ratatouille23.Handlers.ModificaPasswordHandler;
import com.example.ratatouille23.Handlers.RecoverHandler;
import com.example.ratatouille23.Models.Utente;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LoginService {

    @POST("utente")
    Call<ResponseBody> checkLoginUtente (@Body LoginHandler utente);

    @POST("utente/firstlog")
    Call<ResponseBody> modificaPasswordFirstLoginUtente (@Body LoginHandler utente);

    @POST("utente/recover")
    Call<ResponseBody> recuperaPassword(@Body Utente utente);

    @POST("utente/recoverConfirm")
    Call<ResponseBody> confermaPassword(@Body RecoverHandler handler);

    @GET("utente/{rid}")
    Call<ResponseBody> estraiDipendenti(@Path("rid") int rid);

    @PUT("utente")
    Call<ResponseBody> modificaPassword(@Body ModificaPasswordHandler handler);
}
