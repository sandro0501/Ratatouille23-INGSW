package com.fpsteam.ratatouille2023.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.entity.Avviso;
import com.fpsteam.ratatouille2023.entity.Bacheca;
import com.fpsteam.ratatouille2023.entity.Utente;
import com.fpsteam.ratatouille2023.handlers.AggiornaHandler;
import com.fpsteam.ratatouille2023.handlers.AggiornaHandlerResponse;
import com.fpsteam.ratatouille2023.handlers.EstraiBachecaResponseHandler;
import com.fpsteam.ratatouille2023.service.ServiceAvviso;
import com.fpsteam.ratatouille2023.service.ServiceBacheca;

@RestController
@RequestMapping("/bacheca")
public class ControllerBacheca {
	
	@Autowired
	private ServiceBacheca service;
	
	
	//Noi aggiorniamo solo il database. Ovvio e' che poi se si va ad utilizzare sul client, e'
	//molto ambizioso fare una nuova query degli avvisi ogni volta, per cui sarebbe effettivamente meglio
	//far gestire gli array a lui, poi quando si apre di nuovo l'activity della bacheca (DA UN ALTRA ACTIVITY)
	//allora si rifa la query.
	@PutMapping("/visible")
	public AggiornaHandlerResponse nascondiAvviso(@RequestBody AggiornaHandler handle)
	{
		AggiornaHandlerResponse resp = new AggiornaHandlerResponse();
		Utente utente = handle.utente;
		Avviso avviso = handle.avviso;
		try
		{
			service.utenteNascondeORiprendeAvviso(utente, avviso);
			resp.messaggio = "Tutto bene";
		}
		catch(Exception e)
		{
			resp.messaggio = e.getMessage();
			return resp;
		}
		return resp;
	}
	
	
	@PutMapping("")
	public AggiornaHandlerResponse aggiornaStatoavviso(@RequestBody AggiornaHandler handle)
	{
		AggiornaHandlerResponse resp = new AggiornaHandlerResponse();
		Utente utente = handle.utente;
		Avviso avviso = handle.avviso;
		try 
		{
			service.utenteVisualizzaAvviso(utente, avviso);
			resp.messaggio = "Tutto bene";
			return resp;
		}
		catch(Exception e)
		{
			resp.messaggio = e.getMessage();
			return resp;
		}
	}
	
	@GetMapping("/{uid}")
	public EstraiBachecaResponseHandler estraiBacheca(@PathVariable("uid") int uid) 
	{
		return service.estraiAvvisi(uid);
	}
	
	
	
}