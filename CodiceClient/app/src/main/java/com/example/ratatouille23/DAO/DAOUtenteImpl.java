package com.example.ratatouille23.DAO;

import android.util.Log;

import com.example.ratatouille23.Exceptions.LoginFallitoException;
import com.example.ratatouille23.Exceptions.PrimoAccessoException;
import com.example.ratatouille23.Handlers.LoginHandler;
import com.example.ratatouille23.InterfacceRetrofit.BaseCallback;
import com.example.ratatouille23.InterfacceRetrofit.LoginService;
import com.example.ratatouille23.Models.Ristorante;
import com.example.ratatouille23.Models.Utente;
import com.example.ratatouille23.Models.UtenteFactory;
import com.example.ratatouille23.Presenters.PresenterLogin;
import com.example.ratatouille23.Presenters.PresenterRistorante;
import com.example.ratatouille23.Views.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

    Retrofit retrofitLogin = new Retrofit.Builder().baseUrl("http://ec2-54-90-54-40.compute-1.amazonaws.com:8080/").addConverterFactory(GsonConverterFactory.create()).build();
    LoginService loginService = retrofitLogin.create(LoginService.class);

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

                        } catch (IOException e) {
                            Log.i("Prova", "io");
                        } catch (LoginFallitoException e) {
                            callback.onAccessoErratoUtente();
                        } catch (PrimoAccessoException e) {
                            callback.onPrimoAccessoUtente(utenteCorrente, sessionePrimoLogin);
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
