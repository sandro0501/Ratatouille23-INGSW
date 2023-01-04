package com.fpsteam.ratatouille2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.entity.Utente;
import com.fpsteam.ratatouille2023.service.ServiceUtente;

@RestController
public class ControllerUtente {
	
	@Autowired
	private ServiceUtente service;
	
	@PostMapping("/aggiungiUtente")
	public Utente aggiungiUtente(@RequestBody Utente utente) 
	{
		//Utente utente = new Utente(nome,cognome,email,password,ristorante,ruolo,superr);
		return service.saveUtente(utente);
	
	}
	
	
	

}
