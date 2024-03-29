package com.fpsteam.ratatouille2023.controller;

import java.sql.Date;
import java.util.ArrayList;
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
import com.fpsteam.ratatouille2023.entity.Prodotto;
import com.fpsteam.ratatouille2023.entity.Utente;
import com.fpsteam.ratatouille2023.handlers.AggiornaHandler;
import com.fpsteam.ratatouille2023.handlers.AggiornaHandlerResponse;
import com.fpsteam.ratatouille2023.handlers.EstraiBachecaResponseHandler;
import com.fpsteam.ratatouille2023.repository.RepositoryAvviso;
import com.fpsteam.ratatouille2023.repository.RepositoryProdotto;
import com.fpsteam.ratatouille2023.repository.RepositoryUtente;
import com.fpsteam.ratatouille2023.service.ServiceAvviso;
import com.fpsteam.ratatouille2023.service.ServiceBacheca;

@RestController
@RequestMapping("/bacheca")
public class ControllerBacheca {
	
	@Autowired
	private ServiceBacheca service;
	@Autowired
	private RepositoryProdotto repProd;
	@Autowired
	private RepositoryUtente repUt;
	@Autowired
	private RepositoryAvviso repAvv;
	@Autowired
	private ServiceAvviso servAvv;
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
		Utente utente = repUt.findById(uid).get();
		if(utente.getRuolo().equals("Amministratore") || utente.getRuolo().equals("Supervisore"))
		{
			ArrayList<Prodotto> prodottiMancanti = repProd.findInScadenza(utente.getidRistorante().getIdRistorante());
			String corpoAvviso;
			if(prodottiMancanti.size()>0)
			{
				corpoAvviso = "Alcuni prodotti sono sotto soglia:\n";
				for(Prodotto x : prodottiMancanti)
				{
					corpoAvviso += x.getNome()+";\n";
				}
				corpoAvviso = corpoAvviso.substring(0, corpoAvviso.length()-2) + ".";	
			}
			else
			{
				corpoAvviso = "Tutti i prodotti sono al di sopra della soglia limite.";
			}
			ArrayList<Utente> utenteSistema = repUt.findByRank("Sistema", utente.getidRistorante().getIdRistorante());
			Utente utenteFormato;
			if(utenteSistema.size()== 0)
			{
				utenteFormato = new Utente("RataCounter", "", "", utente.getidRistorante(), "Sistema");
				utenteFormato = repUt.save(utenteFormato);
				
			}
			else
			{
				utenteFormato = utenteSistema.get(0);
			}
			ArrayList<Avviso> avvisiSistema = repAvv.findByUid(utenteFormato.getIdUtente());
			Avviso avvisoSistema;
			if(avvisiSistema.size()>0)
			{
				avvisoSistema = avvisiSistema.get(0);
				avvisoSistema.setCorpo(corpoAvviso);
				avvisoSistema.setOggetto("Monitoraggio dispensa");
				avvisoSistema.setData(new Date(System.currentTimeMillis()));
			}
			else
			{
				avvisoSistema = new Avviso("Monitoraggio dispensa",corpoAvviso,new Date(System.currentTimeMillis()));
			} 
			avvisoSistema.setAutore(utenteFormato);
			if(corpoAvviso.equals("Tutti i prodotti sono al di sopra della soglia limite.")) servAvv.saveAvvisoSistemaTransiente(avvisoSistema);
			else servAvv.saveAvvisoSistemaUrgente(avvisoSistema);
		}
			
			
		return service.estraiAvvisi(uid);
	}
	
}