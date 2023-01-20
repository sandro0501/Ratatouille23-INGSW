package com.fpsteam.ratatouille2023.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.service.ServiceElemento;

@RestController
@RequestMapping("/elemento")
public class ControllerElemento {
	@Autowired
	private ServiceElemento service;

	@PostMapping("")
	public String aggiungiSezione(@RequestBody Elemento elemento)
	{
		service.save(elemento);
		return "Tutto bene";
	}


}