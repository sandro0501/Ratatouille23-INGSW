package com.fpsteam.ratatouille2023.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.handlers.InserisciAvvisoHandler;
import com.fpsteam.ratatouille2023.entity.Avviso;
import com.fpsteam.ratatouille2023.entity.Utente;
import com.fpsteam.ratatouille2023.service.ServiceAvviso;
import com.fpsteam.ratatouille2023.service.ServiceBacheca;
import com.fpsteam.ratatouille2023.service.ServiceUtente;

@RestController
@RequestMapping("/avvisi")
public class ControllerAvviso {
	
	@Autowired
	private ServiceAvviso service;
	@Autowired
	private ServiceUtente servUt;
	@Autowired
	private ServiceBacheca servBa;
	
	//devi cambiare il nome di estraibachehahandler a inserisciavvisohandler e devi cambiare il request body da Avviso a quell'handler
	@PostMapping("")
	public String aggiungiAvviso(@RequestBody InserisciAvvisoHandler handle) 
	{
		Avviso avv = handle.avviso;
		handle.autore.setidRistorante(handle.ristorante);
		avv.setAutore(handle.autore);
		//Si dovrebbe salvare l'avviso nel database
		avv = service.save(avv);
		//Dopo averlo preso, bisogna salvare nella bacheca ogni utente con questo nuovo avviso
		List<Utente> dipendenti = servUt.estraiDipendenti(avv.getAutore().getidRistorante());
		for (Utente x : dipendenti)
		{
			servBa.salvaRelazioneUtenteAvviso(x, avv);
		}
		return "Tutto bene";
	}
	
	
	
}