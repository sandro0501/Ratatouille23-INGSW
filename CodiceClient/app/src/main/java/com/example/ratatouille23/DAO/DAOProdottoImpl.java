package com.example.ratatouille23.DAO;

import android.util.Log;

import com.example.ratatouille23.InterfacceRetrofit.OpenFootFactsService;
import com.example.ratatouille23.Models.Prodotto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAOProdottoImpl implements DAOProdotto {

    public interface ProdottoCallbacks {
        void onCaricamentoListaProdottiOpenFoodFacts(ArrayList<Prodotto> listaProdottiOttenuta);
    }

    Retrofit retrofitOpenFoodFacts = new Retrofit.Builder().baseUrl("https://it.openfoodfacts.org/cgi/").addConverterFactory(GsonConverterFactory.create()).build();
    OpenFootFactsService openFootFactsService = retrofitOpenFoodFacts.create(OpenFootFactsService.class);

    @Override
    public void getProdottiOpenFoodFactsDaStringa(String stringaIniziale, ProdottoCallbacks callback) {
        Call<ResponseBody> callGetProdottiOpenFoodFacts = openFootFactsService.getProdottiDaTermine(stringaIniziale, true, true, "product_name,generic_name");
        callGetProdottiOpenFoodFacts.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        ArrayList<Prodotto> listaProdotti = new ArrayList<>();
                        JSONObject jsonPaginaProdotti = new JSONObject(response.body().string());
                        JSONArray jsonArrayProdotti = jsonPaginaProdotti.getJSONArray("products");
                        for (int i = 0; i < jsonArrayProdotti.length(); i++) {
                            JSONObject jsonProdotto = jsonArrayProdotti.getJSONObject(i);
                            Prodotto prodotto = new Prodotto();
                            prodotto.setNome((jsonProdotto.has("product_name") ? jsonProdotto.getString("product_name") : ""));
                            prodotto.setDescrizione((jsonProdotto.has("generic_name") ? jsonProdotto.getString("generic_name") : ""));
                            listaProdotti.add(prodotto);
                        }
                        callback.onCaricamentoListaProdottiOpenFoodFacts(listaProdotti);
                    }
                    catch (JSONException e) {
                        Log.i("JSON EXCEPTION", e.getMessage());
                    }
                    catch(IOException e) {

                    }
                }
                else {
                    Log.i("HTTP EXCEPTION", "");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
