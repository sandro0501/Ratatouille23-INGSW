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
import com.fpsteam.ratatouille2023.entity.Prodotto;
import com.fpsteam.ratatouille2023.handlers.EliminaElementiHandler;
import com.fpsteam.ratatouille2023.handlers.EliminaProdottiHandler;
import com.fpsteam.ratatouille2023.service.ServiceElemento;

@RestController
@RequestMapping("/elemento")
public class ControllerElemento {
	@Autowired
	private ServiceElemento service;

	@PostMapping("")
	public String aggiungiElemento(@RequestBody Elemento elemento)
	{
		service.save(elemento);
		return "Tutto bene";
	}
	
	@PutMapping("")
	public String aggiornaElemento(@RequestBody Elemento elemento)
	{
		try
		{
			service.update(elemento);
			return "Tutto bene";
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
		
	}

	@DeleteMapping("")
	public String delete(@RequestBody EliminaElementiHandler handle)
	{
		ArrayList<Elemento> elementi = new ArrayList<Elemento>();
		for(Elemento x : handle.elementi)
		{
			Elemento ne = new Elemento();
			ne.setIdElemento(x.getIdElemento());
			elementi.add(ne);
		}
		try
		{
			return service.delete(elementi);
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}

}