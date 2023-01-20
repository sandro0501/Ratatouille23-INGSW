package com.fpsteam.ratatouille2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.entity.ListaAllergeni;
import com.fpsteam.ratatouille2023.service.ServiceListaAllergeni;

@RestController
@RequestMapping("/listaallergeni")
public class ControllerListaAllergeni {

	@Autowired
	private ServiceListaAllergeni service;
	
	@PostMapping("")
	public void save(@RequestBody ListaAllergeni lista)
	{
		service.save(lista);
	}

}