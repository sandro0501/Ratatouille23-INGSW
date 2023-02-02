package com.example.ratatouille23.InterfacceRetrofit;

import com.example.ratatouille23.Handlers.EliminaSezioniHandler;
import com.example.ratatouille23.Models.SezioneMenu;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SezioneMenuService {
    @GET("/menu/{rid}")
    Call<ResponseBody> estraiMenu(@Path("rid") int rid);

    @POST("menu")
    Call<ResponseBody> insertSezione(@Body SezioneMenu sezione);

    @PATCH("menu")
    Call<ResponseBody> deleteSezione(@Body EliminaSezioniHandler handler);
}
