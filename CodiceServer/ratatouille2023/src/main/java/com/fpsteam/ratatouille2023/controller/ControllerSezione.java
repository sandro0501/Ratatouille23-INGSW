package com.fpsteam.ratatouille2023.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpsteam.ratatouille2023.entity.Ristorante;
import com.fpsteam.ratatouille2023.entity.SezioneMenu;
import com.fpsteam.ratatouille2023.handlers.ElementoHandler;
import com.fpsteam.ratatouille2023.handlers.EliminaProdottiHandler;
import com.fpsteam.ratatouille2023.handlers.EliminaSezioniHandler;
import com.fpsteam.ratatouille2023.handlers.MenuResponseHandler;
import com.fpsteam.ratatouille2023.handlers.ProdottoHandler;
import com.fpsteam.ratatouille2023.handlers.RichiestaPreparazioneHandler;
import com.fpsteam.ratatouille2023.entity.Allergene;
import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.Prodotto;
import com.fpsteam.ratatouille2023.service.ServiceRistorante;
import com.fpsteam.ratatouille2023.service.ServiceSezioneMenu;
import com.fpsteam.ratatouille2023.service.ServiceAllergene;
import com.fpsteam.ratatouille2023.service.ServiceElemento;
import com.fpsteam.ratatouille2023.service.ServiceProdotto;

@RestController
@RequestMapping("/menu")
public class ControllerSezione {
	@Autowired
	private ServiceAllergene serviceAllergene;
	@Autowired
	private ServiceElemento serviceElemento;
	@Autowired
	private ServiceProdotto serviceProdotto;
	@Autowired
	private ServiceSezioneMenu service;
	
	@PostMapping("")
	public String aggiungiSezione(@RequestBody SezioneMenu sezione)
	{
		service.save(sezione);
		return "Tutto bene";
	}
	
	//Estraiamo tutte le sezioni e gli elementi delle sezioni, come anche tutte le preparazioni e tutti
	//gli allergeni
	@GetMapping("/{rid}")
	public ArrayList<MenuResponseHandler> estraiMenu(@PathVariable("rid") int ristorante)
	{
		ArrayList<MenuResponseHandler> res = new ArrayList<MenuResponseHandler>();
		for(SezioneMenu x : service.findByIdRistorante(ristorante))
			res.add(new MenuResponseHandler(x));
		for(MenuResponseHandler x : res  )
			for(Elemento y : serviceElemento.findBySezione(x.sezione))
				x.elementi.add(new ElementoHandler(y));
		for(MenuResponseHandler x : res)
			for(ElementoHandler y : x.elementi)
			{
				for(Allergene z : serviceAllergene.findByElemento(y.elemento))
					y.allergeni.add(z);
				for(RichiestaPreparazioneHandler z : serviceProdotto.findByElemento(y.elemento))
					y.preparazione.add(new ProdottoHandler( new Prodotto(z.id_prodotto, z.nome, z.unita),z.quantita));
			}
		
		return res;
	}
	
	//Questa viene chiamata dal client ogni volta che una sezione diventa vuota all'uscita dall'activity
	//del menu. 
	@PatchMapping("")
	public String delete(@RequestBody EliminaSezioniHandler handle)
	{
		ArrayList<SezioneMenu> sezioni = new ArrayList<SezioneMenu>();
		for(SezioneMenu x : handle.sezioni)
		{
			SezioneMenu ne = new SezioneMenu();
			ne.setIdAvviso(x.getIdAvviso());
			sezioni.add(ne);
		}
		return service.delete(sezioni);
	}
	
	

}