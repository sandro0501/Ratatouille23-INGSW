package com.fpsteam.ratatouille2023.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Avviso;
import com.fpsteam.ratatouille2023.entity.Ristorante;
import com.fpsteam.ratatouille2023.entity.Utente;
import com.fpsteam.ratatouille2023.repository.RepositoryAvviso;
import com.fpsteam.ratatouille2023.repository.RepositoryBacheca;
import com.fpsteam.ratatouille2023.repository.RepositoryUtente;

@Service
public class ServiceUtente {
	
	@Autowired
	private RepositoryUtente repository;
	@Autowired
	private ServiceRistorante servRistorante;
	@Autowired
	private RepositoryAvviso repAvv;
	@Autowired
	private RepositoryBacheca repBac;
	
	public List<Utente> estraiDipendenti(Ristorante ristorante)
	{
		return repository.findByRistorante(ristorante.getIdRistorante());
	}
	
	//La gestione di cognito per l'autenticazione viene fatta lato client, qui siamo gia sicuri che 
	//la password sia stata inserita. Utente e' un istanza che contiene solo ed esclusivamente la mail.
	public Utente giveUtente(Utente utente) {
		return repository.findByEmail(utente.getEmail());
	}
	
	public Utente saveUtentePrimario(Utente utente) {
		Ristorante save = servRistorante.saveRistorante(new Ristorante());
		utente.setidRistorante(save);
		utente.setMaster(true);
		return repository.save(utente);
	}
	
	public void updateUtente(Utente utente) {
		Utente mod = repository.findById(utente.getIdUtente()).get();
		if ((mod.getRuolo().equals("Amministratore") || mod.getRuolo().equals("Supervisore")) && (!utente.getRuolo().equals("Amministratore") && !utente.getRuolo().equals("Supervisore")))
			{
				ArrayList<Avviso> avvisi = repAvv.findByUid(mod.getIdUtente());
				for(Avviso x : avvisi)
				{
					repBac.deleteAll(repBac.findByAid(x.getIdAvviso()));
				}
				repAvv.deleteAll(avvisi);
			}
		mod = utente;
		repository.save(mod);
	}
	
	public Utente saveUtenteSecondario(Utente utente) {
		utente.setMaster(false);
		return repository.save(utente);
	}

	public String delete(Utente utente) {
		//Prima eliminiamo tutti gli avvisi e POI le bachece associate
		repBac.deleteAll(repBac.findByUid(utente.getIdUtente()));
		ArrayList<Avviso> avvisi = repAvv.findByUid(utente.getIdUtente());
		for(Avviso x : avvisi)
		{
			repBac.deleteAll(repBac.findByAid(x.getIdAvviso()));
		}
		repAvv.deleteAll(avvisi);
		//Infine l'utente
		repository.delete(repository.findById(utente.getIdUtente()).get());
		return "Tutto bene";
	}

}
