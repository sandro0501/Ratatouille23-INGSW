package com.fpsteam.ratatouille2023.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.Preparazione;
import com.fpsteam.ratatouille2023.handlers.EliminaElementiHandler;
import com.fpsteam.ratatouille2023.handlers.EliminaPreparazioniHandler;
import com.fpsteam.ratatouille2023.service.ServicePreparazione;

@RestController
@RequestMapping("/preparazione")
public class ControllerPreparazione {

	@Autowired
	private ServicePreparazione service;
	
	@PostMapping("")
	public void save(@RequestBody Preparazione preparazione)
	{
		service.save(preparazione);
	}
	
	@PutMapping("")
	public String aggiornaPreparazione(@RequestBody Preparazione preparazione)
	{
		try
		{
			service.update(preparazione);
			return "Tutto bene";
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
		
	}

	@DeleteMapping("")
	public String delete(@RequestBody EliminaPreparazioniHandler handle)
	{
		ArrayList<Preparazione> preparazioni = new ArrayList<Preparazione>();
		for(Preparazione x : handle.preparazioni)
		{
			Preparazione ne = new Preparazione();
			ne.setIdProdotto(x.getIdProdotto());
			ne.setIdElemento(x.getIdElemento());
			preparazioni.add(ne);
		}
		try
		{
			return service.delete(preparazioni);
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}

}