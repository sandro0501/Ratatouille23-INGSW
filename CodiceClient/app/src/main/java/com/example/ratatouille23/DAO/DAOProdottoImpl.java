package com.example.ratatouille23.DAO;

import android.util.Log;

import com.example.ratatouille23.Handlers.EliminaProdottiHandler;
import com.example.ratatouille23.InterfacceRetrofit.OpenFootFactsService;
import com.example.ratatouille23.InterfacceRetrofit.ProdottoService;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.Ristorante;
import com.google.gson.GsonBuilder;

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

    public interface AggiuntaProdottoCallbacks {
        void onAggiuntaProdotto(Boolean isAggiunto);
    }

    public interface EliminazioneProdottoCallbacks {
        void onEliminazioneProdotto();
    }

    public interface ModificaProdottoCallbacks {
        void onModificaProdotto();
    }

    public interface  OttenimentoDispensaCallbacks {
        void onRichiestaDispensa(ArrayList<Prodotto> dispensa);
    }

    Retrofit retrofitOpenFoodFacts = new Retrofit.Builder().baseUrl("https://it.openfoodfacts.org/cgi/").addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())).build();
    OpenFootFactsService openFootFactsService = retrofitOpenFoodFacts.create(OpenFootFactsService.class);

    Retrofit retrofitProdotto = new Retrofit.Builder().baseUrl(DAOBaseUrl.getBaseUrl()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())).build();
    ProdottoService prodottoService = retrofitProdotto.create(ProdottoService.class);

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
                            if (jsonProdotto.has("product_name"))
                                prodotto.setNome(jsonProdotto.getString("product_name"));
                            else
                                continue;
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

    @Override
    public void aggiungiProdotto(Prodotto prodotto, AggiuntaProdottoCallbacks callback) {
        Call<ResponseBody> callAggiungiProdotto = prodottoService.insertProdotto(prodotto);

        callAggiungiProdotto.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        callback.onAggiuntaProdotto(true);
                    } catch (Exception e){
                        Log.println(Log.VERBOSE,"Errore catch", e.getMessage());
                        callback.onAggiuntaProdotto(false);

                    }
                }
                else {
                    Log.println(Log.VERBOSE,"Errore response", "Errore");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.println(Log.VERBOSE,"Errore failure", t.getMessage());
            }
        });

    }

    @Override
    public void modificaProdotto(Prodotto prodotto, ModificaProdottoCallbacks callback) {
        Call<ResponseBody> callModifica = prodottoService.updateProdotto(prodotto);

        callModifica.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        //JSONObject bodyJSON = new JSONObject();
                        String messaggio = response.body().string();
                        if(messaggio.equals("Tutto bene")){
                            callback.onModificaProdotto();
                        } else {
                            Log.println(Log.VERBOSE, "ErroreMessaggio", response.message());
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                        Log.println(Log.VERBOSE, "ErroreCatch", e.getMessage());
                    }

                }else {
                    Log.println(Log.VERBOSE, "ErroreResponse", response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.println(Log.VERBOSE, "ErroreFailure",  t.getMessage());
            }
        });

    }

    @Override
    public void eliminaProdotto(EliminaProdottiHandler listaProdottiDaEliminare, EliminazioneProdottoCallbacks callback) {
        Call<ResponseBody> callEliminazione = prodottoService.deleteProdotto(listaProdottiDaEliminare);

        callEliminazione.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    callback.onEliminazioneProdotto();
                }
                else{
                    Log.println(Log.VERBOSE, "ErroreMessaggio", response.message()+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.println(Log.VERBOSE, "ErroreFailure",  t.getMessage());
            }
        });

    }

    @Override
    public void getDispensa(Ristorante ristorante, OttenimentoDispensaCallbacks callback) {
        Call<ResponseBody> callDispensa = prodottoService.getDispensaByIdRistorante(ristorante.getIdRistorante());

        callDispensa.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONArray dispensaJSON = new JSONArray(response.body().string());
                        ArrayList<Prodotto> dispensa = new ArrayList<Prodotto>();
                        if(dispensaJSON.length()>0) {
                            JSONObject prodott0JSON = dispensaJSON.getJSONObject(0);
                            JSONObject ristoranteJSON = prodott0JSON.getJSONObject("ristorante");
                            Ristorante ristoranteProdotto = new Ristorante(
                                    ristoranteJSON.getInt("idRistorante"),
                                    ristoranteJSON.getString("denominazione"),
                                    ristoranteJSON.getString("numeroTelefono"),
                                    ristoranteJSON.getString("indirizzo"),
                                    ristoranteJSON.getString("citta"),
                                    ristoranteJSON.getBoolean("turistico"),
                                    ristoranteJSON.getString("urlFoto")
                            );
                            for (int i = 0; i < dispensaJSON.length(); i++) {

                                JSONObject prodottoJSON = dispensaJSON.getJSONObject(i);


                                Prodotto prodotto = new Prodotto(
                                        prodottoJSON.getInt("idProdotto"),
                                        prodottoJSON.getString("nome"),
                                        prodottoJSON.getString("descrizione"),
                                        prodottoJSON.getString("unita"),
                                        prodottoJSON.getString("costo"),
                                        prodottoJSON.getDouble("quantita"),
                                        prodottoJSON.getDouble("soglia"),
                                        ristoranteProdotto
                                );

                                dispensa.add(prodotto);
                            }
                        }
                        callback.onRichiestaDispensa(dispensa);
                    }
                    catch (Exception e){
                        Log.println(Log.VERBOSE,"Errore creazione oggetto", e.getMessage());

                    }
                }
                else{
                    Log.println(Log.VERBOSE,"Errore response", "Errore");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.println(Log.VERBOSE,"Errore failure", t.getMessage());
            }
        });



    }
}
