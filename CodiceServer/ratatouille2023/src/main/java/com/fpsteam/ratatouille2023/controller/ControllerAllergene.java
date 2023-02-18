package com.fpsteam.ratatouille2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.service.ServiceAllergene;

@RestController
@RequestMapping("/allergeni")
public class ControllerAllergene {
	
	@Autowired
	private ServiceAllergene service;
	
	@GetMapping("")
	public void aggiungiAllergeni()
	{
		service.createIfNotThere();
	}
	
}
