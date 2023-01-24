package com.fpsteam.ratatouille2023.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.entity.Allergene;
import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.ListaAllergeni;
import com.fpsteam.ratatouille2023.handlers.HandleListaAllergeni;
import com.fpsteam.ratatouille2023.primaryKeys.ListaAllergeniId;
import com.fpsteam.ratatouille2023.service.ServiceAllergene;
import com.fpsteam.ratatouille2023.service.ServiceListaAllergeni;

@RestController
@RequestMapping("/listaallergeni")
public class ControllerListaAllergeni {

	@Autowired
	private ServiceListaAllergeni service;
	@Autowired
	private ServiceAllergene serviceA;
	@PostMapping("")
	public String save(@RequestBody HandleListaAllergeni handle)
	{
		ListaAllergeni lista = new ListaAllergeni();
		Allergene allergene = new Allergene();
		Elemento elemento = new Elemento();
		allergene.setIdAllergene(handle.idAllergene);
		elemento.setIdElemento(handle.idElemento);
		lista.setIdAllergene(allergene);
		lista.setIdElemento(elemento);
		serviceA.createIfNotThere();
		service.save(lista);
		return "Tutto bene";
	}
	
	@DeleteMapping("")
	public String delete(@RequestBody ArrayList<HandleListaAllergeni> handle)
	{
		
		ListaAllergeniId id = new ListaAllergeniId();
		Allergene all = new Allergene();
		Elemento el = new Elemento();
		try 
		{
			for(int x = 0; x<handle.size(); x++)
			{
				all.setIdAllergene(handle.get(x).idAllergene);
				el.setIdElemento(handle.get(x).idElemento);
				
				id.setIdAllergene(all);
				id.setIdElemento(el);
				
				service.delete(id);
			}
			return "Tutto bene";
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}

}