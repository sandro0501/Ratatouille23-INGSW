package com.example.ratatouille23.DAO;

import android.util.Log;

import com.example.ratatouille23.Handlers.EliminaSezioniHandler;
import com.example.ratatouille23.InterfacceRetrofit.LoginService;
import com.example.ratatouille23.InterfacceRetrofit.SezioneMenuService;
import com.example.ratatouille23.Models.Allergene;
import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.Preparazione;
import com.example.ratatouille23.Models.Prodotto;
import com.example.ratatouille23.Models.SezioneMenu;
import com.example.ratatouille23.Models.listaAllergeni;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAOSezioneMenuImpl implements DAOSezioneMenu {

    public interface ModificaSezioneCallbacks{
        void onModificato();
    }

    public interface EstraiMenuCallbacks {
        void onEstratto(ArrayList<SezioneMenu> listaSezioni);
    }

    public interface AggiungiSezioneCallbacks {
        void onAggiuntaSezione();
    }

    public interface RimuoviSezioneCallbacks {
        void onRimozioneSezione();
    }

    Retrofit retrofitSezione = new Retrofit.Builder().baseUrl(DAOBaseUrl.baseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
    SezioneMenuService sezioneMenuService = retrofitSezione.create(SezioneMenuService.class);

    public void estraiMenu(int idrist, EstraiMenuCallbacks callback) {
        Call<ResponseBody> callEstraiMenu = sezioneMenuService.estraiMenu(idrist);

        callEstraiMenu.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    try
                    {
                        //Estraggo tutto il corpo della response
                        JSONArray body = new JSONArray((response.body().string()));
                        ArrayList<SezioneMenu> res = new ArrayList<SezioneMenu>();
                        //Per ogni elemento dell'array
                        for(int x = 0; x<body.length(); x++)
                        {
                            JSONObject currSecInd = body.getJSONObject(x);
                            //Estraggo il singolo oggetto
                            JSONObject currsect = currSecInd.getJSONObject("sezione");
                            //Creo la sezione
                            SezioneMenu sezione = new SezioneMenu(currsect.getString("titolo"), currsect.getInt("posizione"));
                            sezione.setIdAvviso(currsect.getInt("idAvviso"));
                            //Prendo l'array degli elementi
                            JSONArray elementi = currSecInd.getJSONArray("elementi");
                            ArrayList<Elemento> listaElementi = new ArrayList<Elemento>();
                            //Per ognuno degli elementi nell'array
                            for(int y = 0; y<elementi.length(); y++)
                            {
                                //Estraggo l'elemento
                                JSONObject currEleInd = elementi.getJSONObject(y);
                                JSONObject currEle = currEleInd.getJSONObject("elemento");
                                Elemento elemento;
                                //Creo l'oggetto associato
                                try
                                {
                                    Elemento elementos = new Elemento(currEle.getString("denominazioneP"), currEle.getString("denominazioneS"), currEle.getString("descrizioneP"), currEle.getString("descrizioneS"), currEle.getDouble("costo"), currEle.getInt("posizione"));
                                    elemento = elementos;
                                }
                                catch(Exception e)
                                {
                                    Elemento elementos = new Elemento(currEle.getString("denominazioneP"), currEle.getString("descrizioneP"), currEle.getDouble("costo"), currEle.getInt("posizione"));
                                    elemento = elementos;
                                }
                                elemento.setIdElemento(currEle.getInt("idElemento"));
                                //Prendo il jsonarray degli allergeni
                                JSONArray allergeni = currEleInd.getJSONArray("allergeni");
                                ArrayList<Allergene> listaAllergenis = new ArrayList<Allergene>();
                                //Per ogni prodotto nella preparazione
                                for(int z = 0; z<allergeni.length();z++)
                                {
                                    //Estraggo l'allergene json
                                    JSONObject currAllInd = allergeni.getJSONObject(z);
                                    Allergene allergene = new Allergene(listaAllergeni.valueOf(currAllInd.getString("nome")));
                                    //Lo inserisco all'interno dell'array allergeni dell'elemento
                                    listaAllergenis.add(allergene);
                                }
                                //aggiungo gli allergeni all'elemento
                                elemento.setPresenta(listaAllergenis);
                                ArrayList<Preparazione> listaPreparazioni = new ArrayList<Preparazione>();
                                //estraggo il jsonarray delle preparazioni
                                JSONArray nonHoPiuNomiAiutoComunqueSonoLePreparazioni = currEleInd.getJSONArray("preparazione");
                                for(int z = 0; z< nonHoPiuNomiAiutoComunqueSonoLePreparazioni.length(); z++)
                                {
                                    //Estraggo la preparazione corrente
                                    JSONObject currPrepInd = nonHoPiuNomiAiutoComunqueSonoLePreparazioni.getJSONObject(z);
                                    //Creo il prodotto
                                    JSONObject currProdJS = currPrepInd.getJSONObject("prodotto");
                                    Prodotto prodotto = new Prodotto(currProdJS.getString("nome"), currProdJS.getString("descrizione"), currProdJS.getString("unita"), currProdJS.getString("costo"), currProdJS.getDouble("quantita"), currProdJS.getDouble("soglia"));
                                    //Creo la preparazione
                                    Preparazione preparazione = new Preparazione(prodotto, currPrepInd.getDouble("quantita"));
                                    listaPreparazioni.add(preparazione);
                                }
                                //aggiungo la preparazione all'elemento
                                elemento.setPreparazione(listaPreparazioni);
                                //aggiungo l'elemento alla lista
                                listaElementi.add(elemento);
                            }
                            //aggiungo la lista degli elementi alla sezione
                            sezione.setAppartenente(listaElementi);
                            //aggiungo la sezione alla lista delle sezioni
                            res.add(sezione);
                        }
                        //TODO metti i callback
                        callback.onEstratto(res);
                    }
                    catch(Exception e )
                    {
                        System.out.println(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void aggiungiSezioneMenu(SezioneMenu sezione, AggiungiSezioneCallbacks callback) {
        Call<ResponseBody> callAggiunta = sezioneMenuService.insertSezione(sezione);
        callAggiunta.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String messaggio = response.body().string();
                        Log.i("messaggio", messaggio);
                        if (messaggio.equals("Tutto bene"))
                            callback.onAggiuntaSezione();
                    }
                    catch (Exception e) {
                        Log.i("Exception", e.getMessage());
                    }
                }
                else {
                    Log.i("response", ((Integer)response.code()).toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void modificaSezioneMenu(SezioneMenu sezione, ModificaSezioneCallbacks callback) {
        Call<ResponseBody> callModifica = sezioneMenuService.modificaSezione(sezione);
        callModifica.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {

                    System.out.println("Success");
                    callback.onModificato();
                }
                else
                {
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Failure");
            }
        });
    }

    public void rimuoviSezioneMenu(EliminaSezioniHandler handler, RimuoviSezioneCallbacks callback) {
        Call<ResponseBody> callAggiunta = sezioneMenuService.deleteSezione(handler);
        callAggiunta.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String messaggio = response.body().string();
                        if (messaggio.equals("Tutto bene"))
                            callback.onRimozioneSezione();
                    }
                    catch (Exception e) {
                        Log.i("Exception", e.getMessage());
                    }
                }
                else {
                    Log.i("response", response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
