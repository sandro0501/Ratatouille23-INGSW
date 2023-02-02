package com.example.ratatouille23.DAO;

import android.util.Log;

import com.example.ratatouille23.Exceptions.LoginFallitoException;
import com.example.ratatouille23.Exceptions.PrimoAccessoException;
import com.example.ratatouille23.Handlers.AggiornaRuoloHandler;
import com.example.ratatouille23.Handlers.LoginHandler;
import com.example.ratatouille23.Handlers.ModificaPasswordHandler;
import com.example.ratatouille23.Handlers.RecoverHandler;
import com.example.ratatouille23.Handlers.RegistraUtenteHandler;
import com.example.ratatouille23.Handlers.UtenteHandler;
import com.example.ratatouille23.InterfacceRetrofit.BaseCallback;
import com.example.ratatouille23.InterfacceRetrofit.DipendentiService;
import com.example.ratatouille23.InterfacceRetrofit.LoginService;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Models.UtenteFactory;
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

public class DAOUtenteImpl implements DAOUtente {

    public interface LoginCallbacks extends BaseCallback {
        void onAccessoCorrettoUtente(Utente utenteControllato);

        void onPrimoAccessoUtente(Utente utente, String session);

        void onAccessoErratoUtente();

        void onAccessoUtenteLicenziato();
    }

    public interface ModificaPasswordPrimoLoginCallbacks {
        void onModificaPasswordUtente(Utente utenteControllato);
        void onModificaPasswordUtenteLicenziato();
    }

    public interface RecuperaPasswordCallbacks
    {
        void onRichiestaCodice();
        void onConfermaCodice();
        void onCodiceErrato();
    }


    public interface DipendentiCallbacks
    {
        void onRichiestaDipendenti(ArrayList<Utente> utenti);
    }

    public interface RimuoviDipendenteCallbacks{
        void onEliminazioneDipendente();
    }

    public interface AggiungiDipendenteCallbacks {
        void onAggiuntaDipendente();
        void onUtenteGiaPresente();
        void onUtenteLicenziatoPrecedentemente();
    }

    public interface ModificaDipendenteCallbacks {
        void onModificaDipendente();
    }

    public interface ModificaPasswordCallbacks {
        void onModificaPassword();
        void onVecchiaPasswordErrata();
    }

    Retrofit retrofitLogin = new Retrofit.Builder().baseUrl(DAOBaseUrl.baseUrl()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())).build();
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
                            Log.i("successo", successo);
                            if (successo.equals("NEW_PASSWORD_REQUIRED")) {
                                sessionePrimoLogin = jsonLoginUtente.getString("session");
                                throw new PrimoAccessoException();
                            }
                            else if (successo.equals("Tutto bene")) {
                                Utente utenteCorrente = formaUtenteDaJSON(jsonLoginUtente);
                                callback.onAccessoCorrettoUtente(utenteCorrente);

                            } else if (successo.equals("Non sei associato a nessun ristorante!")) {
                                callback.onAccessoUtenteLicenziato();
                            }
                            else {
                                throw new LoginFallitoException();
                            }

                        } catch (JSONException e) {
                            Log.i("Prova", e.getMessage());

                        } catch (IOException e) {
                            Log.i("Prova", "io");
                        } catch (LoginFallitoException e) {
                            callback.onAccessoErratoUtente();
                        } catch (PrimoAccessoException e) {
                            callback.onPrimoAccessoUtente(utenteCorrente, sessionePrimoLogin);
                        }
                    }
                    else {
                        callback.onErroreDiHTTP(response);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callback.erroreConnessioneGenerico();
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
                            if (jsonLoginUtente.getString("messaggio").equals("Tutto bene")) {
                                Utente utenteCorrente = formaUtenteDaJSON(jsonLoginUtente);
                                callback.onModificaPasswordUtente(utenteCorrente);
                            }
                            else if (jsonLoginUtente.getString("messaggio").equals("Non sei associato a nessun ristorante!")) {
                                callback.onModificaPasswordUtenteLicenziato();
                            }
                        } catch (JSONException e) {
                            Log.i("Prova", e.getMessage());

                        } catch (IOException e) {
                            Log.i("Prova", "io");
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
                    try {
                        String messaggio = (response.body().string());
                        Log.i("messaggio", messaggio);
                         if (messaggio.equals("CodeMismatchException")){
                            callback.onCodiceErrato();
                        }
                        else
                            callback.onConfermaCodice();
                    }
                    catch (Exception e) {
                        Log.i("Exception", e.getMessage());
                    }
                }
                else {
                    Log.i("Exception", response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Failure", t.getMessage());

            }
        });
    }

    public void ottieniDipendenti(Ristorante ristorante, DipendentiCallbacks callback)
    {
        Call<ResponseBody> callConferma = loginService.estraiDipendenti(ristorante.getIdRistorante());
        callConferma.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    try
                    {
                        JSONObject body = new JSONObject(response.body().string());
                        JSONArray utenti = body.getJSONArray("dipendenti");
                        ArrayList<Utente> dipendenti = new ArrayList<Utente>();
                        for(int x = 0; x<utenti.length(); x++)
                        {
                            JSONObject y = utenti.getJSONObject(x);
                            Utente utenteCorrente = UtenteFactory.getInstance().getNuovoUtente(
                                    y.getString("nome"),
                                    y.getString("cognome"),
                                    y.getString("email"),
                                    y.getString("ruolo"),
                                    y.getBoolean("master")
                            );
                            utenteCorrente.setIdUtente(y.getInt("idUtente"));
                            utenteCorrente.setIdRistorante(ristorante);
                            dipendenti.add(utenteCorrente);
                        }
                        callback.onRichiestaDipendenti(dipendenti);
                    }
                    catch(Exception e)
                    {
                    }
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void rimuoviDipendente(UtenteHandler utente, RimuoviDipendenteCallbacks callback) {
        Call<ResponseBody> callConferma = dipendentiService.deleteDipendente(utente);
        callConferma.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    callback.onEliminazioneDipendente();
                }
                else {
                    Log.i("HTTP", ((Integer)response.code()).toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("FAILURE", t.getMessage());
            }
        });
    }

    @Override
    public void aggiungiDipendente(RegistraUtenteHandler handler, AggiungiDipendenteCallbacks callback) {
        Call<ResponseBody> callConferma = dipendentiService.insertDipendente(handler);
        callConferma.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject bodyJSON = new JSONObject(response.body().string());
                        String messaggio = bodyJSON.getString("messaggio");
                        if (messaggio.equals("Tutto bene"))
                            callback.onAggiuntaDipendente();
                        else if (messaggio.equals("could not execute statement; SQL [n/a]; constraint [utente.UK_gxvq4mjswnupehxnp35vawmo2]")){
                            callback.onUtenteGiaPresente();
                        }
                        else if (messaggio.equals("UsernameExistsException")) {
                            callback.onUtenteLicenziatoPrecedentemente();
                        }
                    }
                    catch (Exception e) {
                    }
                }
                else {
                    Log.i("ERRORE", response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("ERRORE", t.getMessage());
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

    public void modificaDipendente(AggiornaRuoloHandler handler, ModificaDipendenteCallbacks callback) {
        Call<ResponseBody> callConferma = dipendentiService.updateDipendente(handler);
        callConferma.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject bodyJSON = new JSONObject(response.body().string());
                        String messaggio = bodyJSON.getString("messaggio");
                        if (messaggio.equals("Tutto bene"))
                            callback.onModificaDipendente();
                        else {

                        }
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    Log.i("ERRORE", response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("ERRORE", t.getMessage());
            }
        });
    }

    public void modificaPassword(ModificaPasswordHandler handler, ModificaPasswordCallbacks callback) {
        Log.i("HANDLER", handler.accessToken +"\n" + handler.old + "\n" + handler.newp + "\n" + handler.passc);
        Call<ResponseBody> callConferma = loginService.modificaPassword(handler);
        callConferma.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject bodyJSON = new JSONObject(response.body().string());
                        String messaggio = bodyJSON.getString("messaggio");
                        if (messaggio.equals("Tutto bene"))
                            callback.onModificaPassword();
                        else if (messaggio.contains("Incorrect username or password.")){
                            callback.onVecchiaPasswordErrata();
                        }
                        else {
                            Log.i("MESSAGGIO", messaggio);
                        }
                    }
                    catch (Exception e) {
                        Log.i("Exception", e.getMessage());
                    }
                }
                else {
                    Log.i("ERRORE", response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("ERRORE", t.getMessage());
            }
        });
    }

}
