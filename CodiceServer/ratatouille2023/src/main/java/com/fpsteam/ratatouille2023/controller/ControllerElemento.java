package com.fpsteam.ratatouille2023.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.Prodotto;
import com.fpsteam.ratatouille2023.handlers.EliminaElementiHandler;
import com.fpsteam.ratatouille2023.handlers.EliminaProdottiHandler;
import com.fpsteam.ratatouille2023.handlers.HandleElemento;
import com.fpsteam.ratatouille2023.service.ServiceElemento;

@RestController
@RequestMapping("/elemento")
public class ControllerElemento {
	@Autowired
	private ServiceElemento service;

	@PostMapping("")
	public Elemento aggiungiElemento(@RequestBody HandleElemento handle)
	{
		Elemento elemento = handle.elemento;
		elemento.setSezioneMenu(handle.sezione);
		return service.save(elemento);
	}
	
	@PutMapping("")
	public String aggiornaElemento(@RequestBody HandleElemento handle)
	{
		try
		{
			Elemento elemento = handle.elemento;
			elemento.setSezioneMenu(handle.sezione);
			service.update(elemento);
			return "Tutto bene";
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
		
	}

	@PatchMapping("")
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