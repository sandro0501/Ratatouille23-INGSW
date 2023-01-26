package com.example.ratatouille23.InterfacceRetrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenFootFactsService {

    @GET("search.pl")
    Call<ResponseBody> getProdottiDaTermine(
            @Query("search_terms") String termineDaCercare,
            @Query("search_simple") boolean ricercaSemplice,
            @Query("json") boolean json,
            @Query("fields") String campiDaCercare
    );

}
