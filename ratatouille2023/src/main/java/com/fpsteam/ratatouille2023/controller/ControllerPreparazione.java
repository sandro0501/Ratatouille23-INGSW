package com.fpsteam.ratatouille2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.entity.Preparazione;
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

}