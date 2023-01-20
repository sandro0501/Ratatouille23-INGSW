package com.fpsteam.ratatouille2023.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.entity.Ristorante;
import com.fpsteam.ratatouille2023.entity.Utente;
import com.fpsteam.ratatouille2023.service.ServiceRistorante;
import com.fpsteam.ratatouille2023.service.ServiceUtente;

@RestController
@RequestMapping("/ristorante")
public class ControllerRistorante {
	
	@Autowired
	private ServiceUtente serviceUtente;
	@Autowired
	private ServiceRistorante service;
	
	//Estraiamo tutti gli utenti che sono impiegati del ristorante nel body
	@GetMapping("")
	public List<Utente> estraiDipendenti(@RequestBody Ristorante ristorante)
	{
		List<Utente> res = serviceUtente.estraiDipendenti(ristorante);	
		return res;
	}	

}