package com.example.ratatouille23.DAO;

import com.example.ratatouille23.Handlers.AggiornaAvvisoHandler;
import com.example.ratatouille23.Handlers.InserisciAvvisoHandler;
import com.example.ratatouille23.InterfacceRetrofit.AvvisoService;
import com.example.ratatouille23.InterfacceRetrofit.BachecaService;
import com.example.ratatouille23.InterfacceRetrofit.BaseCallbacks;
import com.example.ratatouille23.Models.Avviso;
import com.example.ratatouille23.Models.Gestore;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Models.UtenteFactory;
import com.example.ratatouille23.Views.BachecaFragment;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAOAvvisoImpl implements DAOAvviso
{

    public interface BachecaCallbacks extends BaseCallbacks
    {
        public void onCaricamentoAvvisi(ArrayList<Avviso> avvisiUtenteNuovi,ArrayList<Avviso> avvisiUtenteLetti,ArrayList<Avviso> avvisiUtenteNascosti);
        public void onAggiuntaAvviso(Boolean added);
        public void onVisualizzaAvviso();
        public void onNascondiAvviso();
    }


    Retrofit retrofitBacheca =  new Retrofit.Builder().baseUrl(DAOBaseUrl.getBaseUrl()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())).build();
    BachecaService bachecaService = retrofitBacheca.create(BachecaService.class);

    AvvisoService avvisoService = retrofitBacheca.create(AvvisoService.class);


    public void getAvvisi(Utente utente, BachecaFragment context, BachecaCallbacks callback)
    {

        Call<ResponseBody> callGetAvvisi = bachecaService.getAvvisi(utente.getIdUtente());
        callGetAvvisi.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    try {
                        ArrayList<Utente> utenti = context.getUtentiCorrenti();
                        ArrayList<Avviso> avvisiNuovi = new ArrayList<Avviso>();
                        ArrayList<Avviso> avvisiLetti = new ArrayList<Avviso>();
                        ArrayList<Avviso> avvisiNascosti = new ArrayList<Avviso>();
                        JSONObject body = new JSONObject(response.body().string());
                        JSONArray nuovi = body.getJSONArray("nonVisualizzati");
                        JSONArray letti = body.getJSONArray("visibili");
                        JSONArray nascosti = body.getJSONArray("nonVisibili");
                        Gestore gestore = new Gestore();

                        for (int x = 0; x < nuovi.length(); x++)
                        {

                            JSONArray avviso = nuovi.getJSONArray(x);
                            LocalDate date =  LocalDate.parse(avviso.getString(3));
                            for(int z = 0; z<utenti.size(); z++)
                            {
                                Utente y = utenti.get(z);
                                if (y.getIdUtente() == avviso.getInt(4))
                                    gestore = (Gestore) UtenteFactory.getInstance().getNuovoUtente(
                                            y.getNome(),
                                            y.getCognome(),
                                            y.getEmail(),
                                            y.getRuoloUtente(),
                                            false
                                            );
                            }
                            Avviso nuovo = new Avviso(avviso.getInt(0),avviso.getString(2),avviso.getString(1),date,gestore);
                            avvisiNuovi.add(nuovo);
                        }

                        for (int x = 0; x < letti.length(); x++)
                        {

                            JSONArray avviso = letti.getJSONArray(x);
                            LocalDate date =  LocalDate.parse(avviso.getString(3));
                            for(int z = 0; z<utenti.size(); z++)
                            {
                                Utente y = utenti.get(z);
                                if (y.getIdUtente() == avviso.getInt(4))
                                    gestore = (Gestore) UtenteFactory.getInstance().getNuovoUtente(
                                            y.getNome(),
                                            y.getCognome(),
                                            y.getEmail(),
                                            y.getRuoloUtente(),
                                            false
                                    );
                            }
                            Avviso nuovo = new Avviso(avviso.getInt(0),avviso.getString(2),avviso.getString(1),date,gestore);
                            avvisiLetti.add(nuovo);
                        }

                        for (int x = 0; x < nascosti.length(); x++)
                        {

                            JSONArray avviso = nascosti.getJSONArray(x);
                            LocalDate date =  LocalDate.parse(avviso.getString(3));
                            for(int z = 0; z<utenti.size(); z++)
                            {
                                Utente y = utenti.get(z);
                                if (y.getIdUtente() == avviso.getInt(4))
                                    gestore = (Gestore) UtenteFactory.getInstance().getNuovoUtente(
                                            y.getNome(),
                                            y.getCognome(),
                                            y.getEmail(),
                                            y.getRuoloUtente(),
                                            false
                                    );
                            }
                            Avviso nuovo = new Avviso(avviso.getInt(0),avviso.getString(2),avviso.getString(1),date,gestore);
                            avvisiNascosti.add(nuovo);
                        }

                        callback.onCaricamentoAvvisi(avvisiNuovi,avvisiLetti,avvisiNascosti);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                else
                    callback.onErroreDiHTTP(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                callback.onErroreConnessioneGenerico();
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

                            callback.onAggiuntaAvviso(true);


                    }
                    catch (Exception e)
                    {
                        callback.onAggiuntaAvviso(false);
                    }
                }
                else
                    callback.onErroreDiHTTP(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                callback.onErroreConnessioneGenerico();
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
 
                         callback.onVisualizzaAvviso();
                    }
                    catch(Exception e)
                    {

                    }
                }
                else
                    callback.onErroreDiHTTP(response);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                callback.onErroreConnessioneGenerico();
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
                        JSONObject body = new JSONObject(response.body().string());
                        if(body.getString("messaggio").equals("Tutto bene"))
                            callback.onNascondiAvviso();
                    }
                    catch(Exception e)
                    {

                    }
                }
                else
                    callback.onErroreDiHTTP(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                callback.onErroreConnessioneGenerico();
            }
        });
    }

}

