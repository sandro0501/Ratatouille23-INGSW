package com.example.ratatouille23.InterfacceRetrofit;

import okhttp3.ResponseBody;
import retrofit2.Response;

public interface BaseCallbacks {

    void onErroreDiHTTP(Response<ResponseBody> response);

    void onErroreConnessioneGenerico();

}
