package com.example.ratatouille23.DAO;

import com.example.ratatouille23.InterfacceRetrofit.BachecaService;
import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Gestore;
import com.example.ratatouille23.Models.Utente;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAOAvvisoImpl implements DAOAvviso
{

    public interface BachecaCallbacks
    {
        public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi,ArrayList<Avviso> avvisiUtenteLetti,ArrayList<Avviso> avvisiUtenteNascosti);
    }
    Retrofit retrofitBacheca =  new Retrofit.Builder().baseUrl("https://100.25.166.43/").addConverterFactory(GsonConverterFactory.create()).build();
    BachecaService bachecaService = retrofitBacheca.create(BachecaService.class);

    public void getAvvisi(Utente utente, BachecaCallbacks callback)
    {
        Call<ResponseBody> callGetAvvisi = bachecaService.getAvvisi(utente.getId());
        callGetAvvisi.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    try {
                        ArrayList<Avviso> avvisiNuovi = new ArrayList<Avviso>();
                        ArrayList<Avviso> avvisiLetti = new ArrayList<Avviso>();
                        ArrayList<Avviso> avvisiNascosti = new ArrayList<Avviso>();
                        JSONObject body = new JSONObject(response.body().toString());
                        JSONArray nuovi = body.getJSONArray("nonVisualizzati");
                        JSONArray letti = body.getJSONArray("visibili");
                        JSONArray nascosti = body.getJSONArray("nonVisibili");

                        for (int x = 0; x < nuovi.length(); x++)
                        {
                            JSONObject avviso = nuovi.getJSONObject(x);
                            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(avviso.getString("data"));
                            JSONObject autore = avviso.getJSONObject("autore");
                            Utente gestore = new Utente();
                            gestore.setNome(autore.getString("nome"));
                            gestore.setCognome(autore.getString("cognome"));
                            gestore.setEmail(autore.getString("email"));
                            Avviso nuovo = new Avviso(avviso.getString("oggetto"),avviso.getString("corpo"),date,(Gestore) gestore);
                            avvisiNuovi.add(nuovo);
                        }

                        for (int x = 0; x < letti.length(); x++)
                        {
                            JSONObject avviso = nuovi.getJSONObject(x);
                            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(avviso.getString("data"));
                            JSONObject autore = avviso.getJSONObject("autore");
                            Utente gestore = new Utente();
                            gestore.setNome(autore.getString("nome"));
                            gestore.setCognome(autore.getString("cognome"));
                            gestore.setEmail(autore.getString("email"));
                            Avviso nuovo = new Avviso(avviso.getString("oggetto"),avviso.getString("corpo"),date,(Gestore) gestore);
                            avvisiLetti.add(nuovo);
                        }

                        for (int x = 0; x < nuovi.length(); x++)
                        {
                            JSONObject avviso = nuovi.getJSONObject(x);
                            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(avviso.getString("data"));
                            JSONObject autore = avviso.getJSONObject("autore");
                            Utente gestore = new Utente();
                            gestore.setNome(autore.getString("nome"));
                            gestore.setCognome(autore.getString("cognome"));
                            gestore.setEmail(autore.getString("email"));
                            Avviso nuovo = new Avviso(avviso.getString("oggetto"),avviso.getString("corpo"),date,(Gestore) gestore);
                            avvisiNascosti.add(nuovo);
                        }
                        callback.onCaricamentoAvvisi(avvisiNuovi,avvisiLetti,avvisiNascosti);
                    }
                    catch (Exception e)
                    {

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }


}
