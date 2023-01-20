package com.fpsteam.ratatouille2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.entity.Prodotto;
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

}