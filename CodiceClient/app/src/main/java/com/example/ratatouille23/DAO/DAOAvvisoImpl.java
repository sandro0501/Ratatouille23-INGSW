package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Handlers.AggiornaAvvisoHandler;
import com.example.ratatouille23.Handlers.InserisciAvvisoHandler;
import com.example.ratatouille23.InterfacceRetrofit.AvvisoService;
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
        public void onAggiuntaAvviso(Boolean added);
        public void onVisualizzaAvviso();
        public void onNascondiAvviso();
    }


    Retrofit retrofitBacheca =  new Retrofit.Builder().baseUrl("http://ec2-54-90-54-40.compute-1.amazonaws.com:8080/").addConverterFactory(GsonConverterFactory.create()).build();
    Retrofit retrofitAvviso = new Retrofit.Builder().baseUrl("http://ec2-54-90-54-40.compute-1.amazonaws.com:8080/").addConverterFactory(GsonConverterFactory.create()).build();
    BachecaService bachecaService = retrofitBacheca.create(BachecaService.class);
    AvvisoService avvisoService = retrofitAvviso.create(AvvisoService.class);


    public void getAvvisi(int uid, BachecaCallbacks callback)
    {

        Call<ResponseBody> callGetAvvisi = bachecaService.getAvvisi(uid);
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

    public void insertAvviso(InserisciAvvisoHandler handler, BachecaCallbacks callback)
    {
        Call<ResponseBody> responsePublishing = avvisoService.insertAvviso(handler);
        responsePublishing.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    try
                    {
                        JSONObject body = new JSONObject(response.body().toString());
                        if(body.getString("messaggio").equals("Tutto bene"))
                            callback.onAggiuntaAvviso(true);
                        else
                            callback.onAggiuntaAvviso(false);
                    }
                    catch (Exception e)
                    {
                        callback.onAggiuntaAvviso(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }

    public void visualizzaAvviso(AggiornaAvvisoHandler handler, BachecaCallbacks callback)
    {
        Call<ResponseBody> callGetAvvisi = bachecaService.viewAvviso(handler);
        callGetAvvisi.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    try
                    {
                        JSONObject body = new JSONObject(response.body().toString());
                        if(body.getString("messaggio").equals("Tutto bene"))
                            callback.onVisualizzaAvviso();
                    }
                    catch(Exception e)
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

    @Override
    public void nascondiAvviso(AggiornaAvvisoHandler handler, BachecaCallbacks callback)
    {
        Call<ResponseBody> callNascondiAvviso = bachecaService.nascondiAvviso(handler);
        callNascondiAvviso.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    try
                    {
                        JSONObject body = new JSONObject(response.body().toString());
                        if(body.getString("messaggio").equals("Tutto bene"))
                            callback.onNascondiAvviso();
                    }
                    catch(Exception e)
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

