package com.example.ratatouille23.DAO;

import android.util.Log;

import com.example.ratatouille23.Exceptions.LoginFallitoException;
import com.example.ratatouille23.Exceptions.PrimoAccessoException;
import com.example.ratatouille23.Handlers.LoginHandler;
import com.example.ratatouille23.Handlers.RecoverHandler;
import com.example.ratatouille23.InterfacceRetrofit.BaseCallback;
import com.example.ratatouille23.InterfacceRetrofit.DipendentiService;
import com.example.ratatouille23.InterfacceRetrofit.LoginService;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Models.UtenteFactory;

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

public class DAOUtenteImpl implements DAOUtente {

    public interface LoginCallbacks extends BaseCallback {
        void onAccessoCorrettoUtente(Utente utenteControllato);

        void onPrimoAccessoUtente(Utente utente, String session);

        void onAccessoErratoUtente();
    }

    public interface ModificaPasswordPrimoLoginCallbacks {
        void onModificaPasswordUtente(Utente utenteControllato);
    }

    public interface RecuperaPasswordCallbacks
    {
        void onRichiestaCodice();
        void onConfermaCodice();
    }

    public interface RecuperaDipendentiCallbacks {
        void onDipendentiRicevuti(ArrayList<Utente> listaDipendenti);
    }

    Retrofit retrofitLogin = new Retrofit.Builder().baseUrl("http://100.26.153.81:8080/").addConverterFactory(GsonConverterFactory.create()).build();
    LoginService loginService = retrofitLogin.create(LoginService.class);
    DipendentiService dipendentiService = retrofitLogin.create(DipendentiService.class);

    @Override
    public void controllaDatiLogin(Utente utenteCorrente, String password, LoginCallbacks callback) {
        LoginHandler loginHandler = new LoginHandler();
        loginHandler.utente = utenteCorrente;
        loginHandler.password = password;
        Call<ResponseBody> callUtente = loginService.checkLoginUtente(loginHandler);
        try {
            callUtente.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String sessionePrimoLogin = null;
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonLoginUtente = new JSONObject(response.body().string());
                            String successo = jsonLoginUtente.getString("messaggio");
                            if (successo.equals("NEW_PASSWORD_REQUIRED")) {
                                sessionePrimoLogin = jsonLoginUtente.getString("session");
                                throw new PrimoAccessoException();
                            }
                            else if (successo.equals("Tutto bene")) {
                                Utente utenteCorrente = formaUtenteDaJSON(jsonLoginUtente);
                                callback.onAccessoCorrettoUtente(utenteCorrente);

                            } else {
                                throw new LoginFallitoException();
                            }

                        } catch (JSONException e) {
                            Log.i("Prova", e.getMessage());

                        } catch (LoginFallitoException e) {
                            callback.onAccessoErratoUtente();
                        } catch (PrimoAccessoException e) {
                            callback.onPrimoAccessoUtente(utenteCorrente, sessionePrimoLogin);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        callback.onErroreDiConnessione(response);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("Prova", t.getMessage());
                }
            });
        }
        catch (Exception e){

        }
    }

    @Override
    public void modificaPasswordPrimoLogin(Utente utente, String session, String nuovaPassword, ModificaPasswordPrimoLoginCallbacks callback) {
        LoginHandler loginHandler = new LoginHandler();
        loginHandler.utente = utente;
        loginHandler.session = session;
        loginHandler.password = nuovaPassword;
        Call<ResponseBody> callUtente = loginService.modificaPasswordFirstLoginUtente(loginHandler);
        try {
            callUtente.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonLoginUtente = new JSONObject(response.body().string());
                            Utente utenteCorrente = formaUtenteDaJSON(jsonLoginUtente);
                            callback.onModificaPasswordUtente(utenteCorrente);
                        } catch (JSONException e) {
                            Log.i("Prova", e.getMessage());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Log.i("Prova", "noSUCCESS");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("Prova", t.getMessage());
                }
            });
        }
        catch (Exception e){

        }
    }

    @Override
    public void recuperaPassword(Utente utente, RecuperaPasswordCallbacks callback)
    {
        Call<ResponseBody> callRecupero = loginService.recuperaPassword(utente);
        callRecupero.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    callback.onRichiestaCodice();
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }

    @Override
    public void confermaPassword(RecoverHandler handle, RecuperaPasswordCallbacks callback)
    {
        Call<ResponseBody> callConferma = loginService.confermaPassword(handle);
        callConferma.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    callback.onConfermaCodice();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void recuperaDipendentiRistorante(Ristorante ristorante, RecuperaDipendentiCallbacks callback) {
        Call<ResponseBody> callDipendenti = dipendentiService.getDipendentiByIdRistorante(ristorante.getIdRistorante());
        callDipendenti.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject callJSON = new JSONObject(response.body().string());
                        String successo = callJSON.getString("message");
                        if (successo.equals("Tutto bene")) {
                            JSONArray arrayDipedentiJSON = callJSON.getJSONArray("dipendenti");
                            ArrayList<Utente> arrayDipendenti = new ArrayList<>();
                            for (int i = 0; i < arrayDipedentiJSON.length(); i++) {
                                JSONObject dipendenteJSON = arrayDipedentiJSON.getJSONObject(i);
                                Utente dipendente = UtenteFactory.getInstance().getNuovoUtente(
                                        dipendenteJSON.getString("nome"),
                                        dipendenteJSON.getString("cognome"),
                                        dipendenteJSON.getString("email"),
                                        dipendenteJSON.getString("ruolo"),
                                        dipendenteJSON.getBoolean("master")
                                );
                                dipendente.setIdUtente(dipendenteJSON.getInt("idUtente"));
                                dipendente.setIdRistorante(ristorante);
                                arrayDipendenti.add(dipendente);
                            }
                            callback.onDipendentiRicevuti(arrayDipendenti);
                        }
                        else {
                            Log.i("NON TUTTO BENE", "");
                        }

                    }
                    catch (JSONException e) {
                        Log.i("JSON", "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.i("NON successful", "");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("NON failure", "");
            }
        });
    }


    private Utente formaUtenteDaJSON (JSONObject jsonLoginUtente) throws JSONException {
        JSONObject utenteCorrenteJson = jsonLoginUtente.getJSONObject("utente");
        String idToken = jsonLoginUtente.getString("idToken");
        String accessToken = jsonLoginUtente.getString("accessToken");
        Utente utenteCorrente = UtenteFactory.getInstance().getNuovoUtente(
                utenteCorrenteJson.getString("nome"),
                utenteCorrenteJson.getString("cognome"),
                utenteCorrenteJson.getString("email"),
                utenteCorrenteJson.getString("ruolo"),
                utenteCorrenteJson.getBoolean("master")
        );
        utenteCorrente.setAccessToken(accessToken);
        utenteCorrente.setIdToken(idToken);
        utenteCorrente.setIdUtente(utenteCorrenteJson.getInt("idUtente"));
        JSONObject ristoranteUtenteJson = utenteCorrenteJson.getJSONObject("idRistorante");
        Ristorante ristoranteUtente = new Ristorante(
                ristoranteUtenteJson.getInt("idRistorante"),
                ristoranteUtenteJson.getString("denominazione"),
                ristoranteUtenteJson.getString("numeroTelefono"),
                ristoranteUtenteJson.getString("indirizzo"),
                ristoranteUtenteJson.getString("citta"),
                ristoranteUtenteJson.getBoolean("turistico"),
                ristoranteUtenteJson.getString("urlFoto")
        );
        utenteCorrente.setIdRistorante(ristoranteUtente);
        return utenteCorrente;
    }

}
