package com.example.ratatouille23.DAO;

import android.util.Log;

import com.example.ratatouille23.InterfacceRetrofit.RistoranteService;
import com.example.ratatouille23.Models.Ristorante;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAORistoranteImpl implements DAORistorante {

    public interface RistoranteModificaCallbacks
    {
        public void onModificaRistorante();
    }

    public interface RistoranteRiceviCallbacks
    {
        public void onRicezioneRistorante(Ristorante ristorante);
    }

    Retrofit retrofitRistorante =  new Retrofit.Builder().baseUrl("http://100.26.153.81:8080/").addConverterFactory(GsonConverterFactory.create()).build();
    RistoranteService ristoranteService = retrofitRistorante.create(RistoranteService.class);

    @Override
    public void modificaRistorante(Ristorante ristorante, RistoranteModificaCallbacks callback) {
        Call<ResponseBody> callUpdateRistorante = ristoranteService.updateRistorante(ristorante);

        callUpdateRistorante.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onModificaRistorante();
                }
                else {
                    Log.i("Problema success", ((Integer)response.code()).toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Problema failure", t.getMessage());
            }
        });
    }

    @Override
    public void getRistorante(int idRistorante, RistoranteRiceviCallbacks callback) {
        Call<ResponseBody> callUpdateRistorante = ristoranteService.getRistorante(idRistorante);

        callUpdateRistorante.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Ristorante ristorante;
                        JSONObject ristoranteJSON = new JSONObject(response.body().string());
                        ristorante = new Ristorante(
                                ristoranteJSON.getInt("idRistorante"),
                                ristoranteJSON.getString("denominazione"),
                                ristoranteJSON.getString("numeroTelefono"),
                                ristoranteJSON.getString("indirizzo"),
                                ristoranteJSON.getString("citta"),
                                ristoranteJSON.getBoolean("turistico"),
                                ristoranteJSON.getString("urlFoto")
                        );
                        callback.onRicezioneRistorante(ristorante);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    Log.i("Problema success", ((Integer)response.code()).toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Problema failure", t.getMessage());
            }
        });
    }
}
