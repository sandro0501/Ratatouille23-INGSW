package com.example.ratatouille23.DAO;

import android.util.Log;

import com.example.ratatouille23.Handlers.EliminaElementiHandler;
import com.example.ratatouille23.Handlers.EliminaPreparazioniHandler;
import com.example.ratatouille23.Handlers.HandleElemento;
import com.example.ratatouille23.Handlers.HandlePreparazione;
import com.example.ratatouille23.InterfacceRetrofit.BaseCallbacks;
import com.example.ratatouille23.InterfacceRetrofit.ElementoService;
import com.example.ratatouille23.InterfacceRetrofit.OpenFootFactsService;
import com.example.ratatouille23.Models.Allergene;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Handlers.HandleAllergeni;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAOElementoImpl implements DAOElemento {

    public interface EliminapreparazioneCallbacks extends BaseCallbacks {
        void onEliminati(boolean esito);
    }

    public interface ImpostaPreparazioneCallbacks extends BaseCallbacks {
        void onImpostata(boolean esito);
    }

    public interface ModificaElementoCallbacks extends BaseCallbacks {
        void onModificato();
    }

    public interface ImpostaAllergeniCallbacks extends BaseCallbacks {
        void onImpostati();
    }

    public interface ElementiFoodFactsCallbacks extends BaseCallbacks {
        void onCaricamentoListaElementiOpenFoodFacts (ArrayList<Elemento> listaElementiOttenuta);
    }

    public interface AggiuntaElementiCallbacks extends BaseCallbacks {
        void onAggiuntaElemento (Elemento elwithid);
    }

    public interface EliminaElementiCallbacks extends BaseCallbacks {
        void onEliminazioneElementi ();
    }

    Retrofit retrofitOpenFoodFacts = new Retrofit.Builder().baseUrl("https://it.openfoodfacts.org/cgi/").addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())).build();
    Retrofit retrofitElemento = new Retrofit.Builder().baseUrl(DAOBaseUrl.getBaseUrl()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())).build();

    OpenFootFactsService openFootFactsService = retrofitOpenFoodFacts.create(OpenFootFactsService.class);
    ElementoService elementoService = retrofitElemento.create(ElementoService.class);

    @Override
    public void modificaElemento(Elemento elemento, ModificaElementoCallbacks callback) {
        HandleElemento handle = new HandleElemento(elemento);
        Call<ResponseBody> callModificaElemento = elementoService.modificaElemento(handle);
        callModificaElemento.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    callback.onModificato();
                }
                else
                    callback.onErroreDiHTTP(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onErroreConnessioneGenerico();
            }
        });
    }

    @Override
    public void impostaAllergeni(Elemento elemento, ArrayList<Allergene> allergeni, ImpostaAllergeniCallbacks callback) {
        ArrayList<HandleAllergeni> lista = new ArrayList<HandleAllergeni>();
        for(Allergene x : allergeni)
        {
            HandleAllergeni curr = new HandleAllergeni();
            curr.idElemento = elemento.getIdElemento();
            curr.idAllergene = x.getIdAllergene();
            lista.add(curr);
        }

        Call<ResponseBody> chiamataImpostaAllergeni = elementoService.impostaAllergeni(lista);
        chiamataImpostaAllergeni.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    callback.onImpostati();
                }
                else {
                    Log.i("ERRORE ALLERGENI", "");
                    callback.onErroreDiHTTP(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onErroreConnessioneGenerico();
            }
        });
    }

    @Override
    public void getElementiOpenFoodFactsDaStringa(String stringaIniziale, ElementiFoodFactsCallbacks callback) {
        Call<ResponseBody> callGetProdottiOpenFoodFacts = openFootFactsService.getProdottiDaTermine(stringaIniziale, true, true, "product_name,generic_name,allergens");
        callGetProdottiOpenFoodFacts.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        ArrayList<Elemento> listaElementi = new ArrayList<>();
                        JSONObject jsonPaginaElementi = new JSONObject(response.body().string());
                        JSONArray jsonArrayElementi = jsonPaginaElementi.getJSONArray("products");
                        for (int i = 0; i < jsonArrayElementi.length(); i++) {
                            JSONObject jsonElemento = jsonArrayElementi.getJSONObject(i);
                            Elemento elemento = new Elemento();
                            if (jsonElemento.has("product_name"))
                                elemento.setDenominazionePrincipale(jsonElemento.getString("product_name"));
                            else
                                continue;
                            elemento.setDescrizionePrincipale((jsonElemento.has("generic_name") ? jsonElemento.getString("generic_name") : ""));
                            ArrayList<Allergene> listaAllergeni = new ArrayList<>();
                            String stringaAllergeni = jsonElemento.getString("allergens");
                            ArrayList<String> arrayStringAllergeni = new ArrayList<String>(Arrays.asList(stringaAllergeni.split(",")));
                            for (String allergeneCurr : arrayStringAllergeni)
                            {
                                switch(allergeneCurr) {
                                    case "en:milk":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Lattosio));
                                        break;
                                    case "en:lupin":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Lupini));
                                        break;
                                    case "en:gluten":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Glutine));
                                        break;
                                    case "en:mustard":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Senape));
                                        break;
                                    case "en:celery":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Sedano));
                                        break;
                                    case "en:fish":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Pesce));
                                        break;
                                    case "en:crustaceans":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Crostacei));
                                        break;
                                    case "en:eggs":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Uova));
                                        break;
                                    case "en:nuts":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Noci));
                                        break;
                                    case "en:peanuts":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Arachidi));
                                        break;
                                    case "en:soybeans":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Soia));
                                        break;
                                    case "en:sulphur-dioxide-and-sulphites":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Solfiti));
                                        break;
                                    case "en:molluscs":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Lattosio));
                                        break;
                                    case "en:sesame-seeds":
                                        listaAllergeni.add(new Allergene(com.example.ratatouille23.Models.listaAllergeni.Sesamo));
                                        break;
                                }
                            }
                            elemento.setPresenta(listaAllergeni);
                            listaElementi.add(elemento);
                        }
                        callback.onCaricamentoListaElementiOpenFoodFacts(listaElementi);
                    }
                    catch (JSONException e) {
                        Log.i("JSON EXCEPTION", e.getMessage());
                    }
                    catch(IOException e) {

                    }
                }
                else {
                    callback.onErroreDiHTTP(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onErroreConnessioneGenerico();
            }
        });
    }

    public void insertElemento(Elemento elementoDaAggiungere, AggiuntaElementiCallbacks callback) {
        Call<ResponseBody> call = elementoService.aggiungiElemento(new HandleElemento(elementoDaAggiungere));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject body = new JSONObject(response.body().string());
                        Elemento el = new Elemento();
                        el.setIdElemento(body.getInt("idElemento"));
                        callback.onAggiuntaElemento(el);
                    }
                    catch (Exception e) {
                        Log.i("Exception", e.getMessage());
                    }
                }
                else {
                    Log.i("ERRORE INSERT", "");
                    callback.onErroreDiHTTP(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onErroreConnessioneGenerico();
            }
        });
    }

    @Override
    public void impostaPreparazione(HandlePreparazione preparazione, DAOElementoImpl.ImpostaPreparazioneCallbacks callback) {
        Call<ResponseBody> call = elementoService.impostaPreparazione(preparazione);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    System.out.println("Success");
                    callback.onImpostata(true);
                }
                else{
                    callback.onImpostata(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callback.onImpostata(false);
                    System.out.println("Failure");
            }
        });
    }

    public void deleteElementi(EliminaElementiHandler handler, EliminaElementiCallbacks callbacks) {
        Call<ResponseBody> call = elementoService.eliminaElementi(handler);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callbacks.onEliminazioneElementi();
                }
                else {
                    callbacks.onErroreDiHTTP(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbacks.onErroreConnessioneGenerico();
            }
        });
    }

    @Override
    public void eliminaPreparazione(EliminaPreparazioniHandler preparazioni, EliminapreparazioneCallbacks callback) {
        Call<ResponseBody> call = elementoService.eliminaPreparazioni(preparazioni);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    callback.onEliminati(true);
                }
                else
                    callback.onEliminati(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callback.onEliminati(false);
            }
        });
    }

}
