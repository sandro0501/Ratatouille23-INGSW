package com.example.ratatouille23.InterfacceRetrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DipendentiService {

    @GET("utente/{idRist}")
    Call<ResponseBody> getDipendentiByIdRistorante(@Path("idRist") int idRistorante);

}
