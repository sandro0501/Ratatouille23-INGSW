package com.example.ratatouille23.InterfacceRetrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BachecaService {

    @GET("bacheca")
    Call<ResponseBody> getAvvisi(
            @Query("idUtente") int idUtente
    );

}