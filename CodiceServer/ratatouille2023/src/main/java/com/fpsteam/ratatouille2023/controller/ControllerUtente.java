package com.fpsteam.ratatouille2023.controller;

import org.json.JSONObject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.awsaccess.ApiGatewayResponse;
import com.fpsteam.ratatouille2023.awsaccess.JsonApiGatewayCaller;
import com.fpsteam.ratatouille2023.entity.Ristorante;
import com.fpsteam.ratatouille2023.entity.Utente;
import com.fpsteam.ratatouille2023.handlers.GetDipendentiHandler;
import com.fpsteam.ratatouille2023.handlers.LoginHandler;
import com.fpsteam.ratatouille2023.handlers.RecoverHandler;
import com.fpsteam.ratatouille2023.handlers.RegisterHandler;
import com.fpsteam.ratatouille2023.handlers.RegisterResponseHandler;
import com.fpsteam.ratatouille2023.handlers.SignoutHandler;
import com.fpsteam.ratatouille2023.handlers.UpdateHandler;
import com.fpsteam.ratatouille2023.handlers.UpdateResponseHandler;
import com.fpsteam.ratatouille2023.service.ServiceRistorante;
import com.fpsteam.ratatouille2023.service.ServiceUtente;

import com.amazonaws.http.HttpMethodName;

import java.io.ByteArrayInputStream;
import java.net.URI;


@RestController
@RequestMapping("/utente")
public class ControllerUtente {
	
	static final String AWS_IAM_ACCESS_KEY = "AKIASNWJXRYODS3QJDR7";
    static final String AWS_IAM_SECRET_ACCESS_KEY = "AKIASNWJXRYODS3QJDR7";
    static final String AWS_REGION = "us-east-1"; //for example "eu-west-1"
    static final String AWS_API_GATEWAY_ENPOINT = "https://9rp33txphk.execute-api.us-east-1.amazonaws.com/test"; //for example https://234n34k5678k.execute-api.eu-west-1.amazonaws.com/TEST

	@Autowired
	private ServiceUtente service;
	@Autowired
	private ServiceRistorante servRist;
	
	@PostMapping("/recoverConfirm")
	public String confirmRecover(@RequestBody RecoverHandler handle)
	{
		try {
			JsonApiGatewayCaller caller = new JsonApiGatewayCaller(
	                AWS_IAM_ACCESS_KEY,
	                AWS_IAM_SECRET_ACCESS_KEY,
	                null,
	                AWS_REGION,
	                new URI(AWS_API_GATEWAY_ENPOINT)
	        );
			String jsonRequest = "{"+
					             "\"email\" : \"" + handle.email + "\","+
					             "\"password\" : \"" + handle.password+ "\","+
					             "\"code\" : \"" + handle.code + "\","+
					             "}";
			ApiGatewayResponse response = caller.execute(HttpMethodName.GET, "/passrecover", new ByteArrayInputStream(jsonRequest.getBytes()));
			return response.getBody();
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
		
	}
	
	@PostMapping("/recover")
	public String recover(@RequestBody Utente utente)
	{
		
		try {
			JsonApiGatewayCaller caller = new JsonApiGatewayCaller(
	                AWS_IAM_ACCESS_KEY,
	                AWS_IAM_SECRET_ACCESS_KEY,
	                null,
	                AWS_REGION,
	                new URI(AWS_API_GATEWAY_ENPOINT)
	        );
			String jsonRequest = "{"+
					             "\"email\" : \"" + utente.getEmail()+"\""+
					             "}";
			ApiGatewayResponse response = caller.execute(HttpMethodName.GET, "/passrecover", new ByteArrayInputStream(jsonRequest.getBytes()));
			return response.getBody();
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}
	
	@PostMapping("/out")
	public String signout(@RequestBody SignoutHandler handle)
	{
		try {
			JsonApiGatewayCaller caller = new JsonApiGatewayCaller(
	                AWS_IAM_ACCESS_KEY,
	                AWS_IAM_SECRET_ACCESS_KEY,
	                null,
	                AWS_REGION,
	                new URI(AWS_API_GATEWAY_ENPOINT)
	        );
			String jsonRequest = "{"+
					             "\"AccessToken\" : \"" + handle.accessToken+"\""+
					             "}";
			ApiGatewayResponse response = caller.execute(HttpMethodName.GET, "/signout", new ByteArrayInputStream(jsonRequest.getBytes()));
			return "Logged out";
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}
	
	@GetMapping("/{idRist}")
	public GetDipendentiHandler estraiDipendenti(@PathVariable("idRist") int idRist)
	{
		GetDipendentiHandler res = new GetDipendentiHandler();
		try 
		{
			Ristorante ristorante = new Ristorante();
			ristorante.setIdRistorante(idRist);
			res.dipendenti = service.estraiDipendenti(ristorante);
			res.message = "Tutto bene";
			return res;
		}
		catch(Exception e)
		{
			res.message = e.getMessage();
			return res;
		}
	}
	
	//routine per la modifica di un utente, incluso di password e entita
	@PutMapping("")
	public UpdateResponseHandler modificaDati(@RequestBody UpdateHandler handle)
	{
		UpdateResponseHandler res = new UpdateResponseHandler();
		
		try 
		{
			if(handle.utente == null)
			{//vuol dire che stiamo aggiornando la password e non l'entita
				JsonApiGatewayCaller caller = new JsonApiGatewayCaller(
	                    AWS_IAM_ACCESS_KEY,
	                    AWS_IAM_SECRET_ACCESS_KEY,
	                    null,
	                    AWS_REGION,
	                    new URI(AWS_API_GATEWAY_ENPOINT)
	            );
				String jsonRequest = "{"+
						             "\"token\" : \"" + handle.accessToken+"\","+
						             "\"old\" : \"" + handle.old+"\","+
						             "\"new\" : \"" + handle.newp+"\""+
						             "}";
						             
						

	            ApiGatewayResponse response = caller.execute(HttpMethodName.GET, "/modifypass", new ByteArrayInputStream(jsonRequest.getBytes()));
	            //Non facciamo controlli siccome assumiamo che questi vengano gia fatti nella schermata dei dati
	            
	            res.messaggio = "Tutto bene";
			}
			else
			{//significa che stiamo aggiornando l'entita e non la password
				Utente mod = handle.utente;
				mod.setidRistorante(handle.ristorante);
				service.updateUtente(mod);
				res.messaggio = "Tutto bene";
				res.utente = handle.utente;
			}
			
			return res;
		}
		catch(Exception e)
		{
			res.messaggio = e.getMessage();
			return res;
		}
	}
	
	
	//Questo e' quello che si usa quando si crea un nuovo account nella finestra degli amministratori.
	@PostMapping("/new")
	public RegisterResponseHandler registrazione(@RequestBody RegisterHandler handle)
	{
		Utente utente = handle.utente;
		utente.setidRistorante(handle.ristorante);
		RegisterResponseHandler res = new RegisterResponseHandler();
		try
		{
			JsonApiGatewayCaller caller = new JsonApiGatewayCaller(
                    AWS_IAM_ACCESS_KEY,
                    AWS_IAM_SECRET_ACCESS_KEY,
                    null,
                    AWS_REGION,
                    new URI(AWS_API_GATEWAY_ENPOINT)
            );
			String jsonRequest = "{"+
					             "\"username\" : \"" + utente.getEmail()+"\","+
					             "\"password\" : \"" + handle.password+"\""+
					             "}";
					             
					

            ApiGatewayResponse response = caller.execute(HttpMethodName.GET, "/register", new ByteArrayInputStream(jsonRequest.getBytes()));
            //Non facciamo controlli siccome assumiamo che questi vengano gia fatti nella schermata dei dati
            
            service.saveUtenteSecondario(utente);
            res.messaggio = "Tutto bene";
			return res;
		}
		catch(Exception e)
		{
			res.messaggio = e.getMessage();
			return res;
		}
	}
	@PostMapping("/firstlog")
	public LoginResponseHandler primoLoginUtente(@RequestBody LoginHandler handle)
	{
		Utente utente = handle.utente;
		LoginResponseHandler res = new LoginResponseHandler();
		try 
		{
			JsonApiGatewayCaller caller = new JsonApiGatewayCaller(
                    AWS_IAM_ACCESS_KEY,
                    AWS_IAM_SECRET_ACCESS_KEY,
                    null,
                    AWS_REGION,
                    new URI(AWS_API_GATEWAY_ENPOINT)
            );
			String jsonRequest = "{"+
								 "\"session\" : \"" + handle.session+"\","+
					             "\"username\" : \"" + utente.getEmail()+"\","+
					             "\"password\" : \"" + handle.password+"\""+
					             "}";
					             
					

            ApiGatewayResponse response = caller.execute(HttpMethodName.GET, "/firstlogin", new ByteArrayInputStream(jsonRequest.getBytes()));
            JSONObject body = new JSONObject(response.getBody());
            
            System.out.println(response.getBody());
            body = body.getJSONObject("AuthenticationResult");
		    res.idToken = body.getString("IdToken");
			res.utente =  service.giveUtente(utente);
			if (res.utente == null)
				throw new Exception("Non sei associato a nessun ristorante!");
			res.messaggio = "Tutto bene";
			
			return res;
		}
		catch(Exception e)
		{
			res.messaggio = e.getMessage();
			return res;
		}
	}
	
	
	
	
	//utente deve avere soltanto il campo email, che siccome nel db e' unique viene usato per restituire
	//l'utente in se.
	@PostMapping("")
	public LoginResponseHandler loginUtente(@RequestBody LoginHandler handle)
	{
		Utente utente = handle.utente;
		LoginResponseHandler res = new LoginResponseHandler();
		try 
		{
			JsonApiGatewayCaller caller = new JsonApiGatewayCaller(
                    AWS_IAM_ACCESS_KEY,
                    AWS_IAM_SECRET_ACCESS_KEY,
                    null,
                    AWS_REGION,
                    new URI(AWS_API_GATEWAY_ENPOINT)
            );
			String jsonRequest = "{"+
					             "\"username\" : \"" + utente.getEmail()+"\","+
					             "\"password\" : \"" + handle.password+"\""+
					             "}";
					             
					

            ApiGatewayResponse response = caller.execute(HttpMethodName.GET, "/login", new ByteArrayInputStream(jsonRequest.getBytes()));
            JSONObject body = new JSONObject(response.getBody());
            //caso in cui la password e' sbagliata
            if(body.has("errorMessage"))
            	throw new Exception(body.getString("errorMessage"));
            //caso in cui si tratta del primo login
            else if(body.has("ChallengeName"))
            {
            	res.session = body.getString("Session");
            	throw new Exception(body.getString("ChallengeName"));
            }
            //caso buono
            body = body.getJSONObject("AuthenticationResult");
		    res.idToken = body.getString("IdToken");
		    res.accessToken = body.getString("AccessToken");
			res.utente =  service.giveUtente(utente);
			res.messaggio = "Tutto bene";
			
			return res;
		}
		catch(Exception e)
		{
			res.messaggio = e.getMessage();
			return res;
		}
	}
	
	//l'utilizzo e' riservato ai dev per creare account ad amministratori primari, per poi andare a 
	//creare l'user nella user pool dell'applicativo.
	@PostMapping("/newAdmin")
	public Utente aggiungiUtente(@RequestBody Utente utente) 
	{
		return service.saveUtentePrimario(utente);
	}
	//Serve a cancellare un utente dal db NON E NON DALLA USER POOL PERCHE NON SERVE
	@DeleteMapping("")
	public String delete(@RequestBody Utente utente)
	{
		return service.delete(utente);
	}
	
	private class LoginResponseHandler{
		public Utente utente;
		public String idToken;
		public String accessToken;
		public String messaggio;
		public String session;
	}
	

}
