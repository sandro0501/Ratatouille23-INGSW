package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Handlers.LoginHandler;
import com.example.ratatouille23.Models.Utente;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginService {

    @POST("utente")
    Call<ResponseBody> checkLoginUtente (@Body LoginHandler utente);

    @POST("utente/firstlog")
    Call<ResponseBody> modificaPasswordFirstLoginUtente (@Body LoginHandler utente);

}
