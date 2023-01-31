package com.fpsteam.ratatouille2023.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.entity.Prodotto;
import com.fpsteam.ratatouille2023.entity.Ristorante;
import com.fpsteam.ratatouille2023.handlers.EliminaProdottiHandler;
import com.fpsteam.ratatouille2023.service.ServiceProdotto;

@RestController
@RequestMapping("/prodotto")
public class ControllerProdotto {

	@Autowired
	private ServiceProdotto service;
	
	@PostMapping("")
	public void save(@RequestBody Prodotto prodotto)
	{
		service.save(prodotto);
	}
	
	@GetMapping("/{id}")
	public ArrayList<Prodotto> findAll(@PathVariable("id") int idrist)
	{
		return service.findAll(idrist);
	}

	
	@PutMapping("")
	public String update(@RequestBody Prodotto prodotto)
	{
		return service.update(prodotto);
	}
	
	@PatchMapping("")
	public String delete(@RequestBody EliminaProdottiHandler handle)
	{
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
		for(Prodotto x : handle.prodotti)
		{
			Prodotto ne = new Prodotto();
			ne.setIdProdotto(x.getIdProdotto());
			prodotti.add(ne);
		}
		return service.delete(prodotti);
	}
	
}