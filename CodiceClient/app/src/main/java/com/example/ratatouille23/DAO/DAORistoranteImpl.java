package com.example.ratatouille23.DAO;

import android.util.Log;

import com.example.ratatouille23.InterfacceRetrofit.BachecaService;
import com.example.ratatouille23.InterfacceRetrofit.RistoranteService;
import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Gestore;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAORistoranteImpl implements DAORistorante {

    public interface RistoranteCallbacks
    {
        public void onModificaRistorante(boolean successo);
    }

    Retrofit retrofitRistorante =  new Retrofit.Builder().baseUrl(DAOBaseUrl.baseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
    RistoranteService ristoranteService = retrofitRistorante.create(RistoranteService.class);

    @Override
    public void modificaRistorante(Ristorante ristorante, RistoranteCallbacks callback) {
        Call<String> callUpdateRistorante = ristoranteService.updateRistorante(ristorante);
        callUpdateRistorante.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    boolean successo = (response.body().equals("Tutto bene"));
                    callback.onModificaRistorante(successo);

                }
                else {
                    Log.i("Problema success", ((Integer)response.code()).toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("Problema failure", t.getMessage());
            }
        });
    }
}
