package com.fpsteam.ratatouille2023.handlers;

import com.fpsteam.ratatouille2023.entity.Prodotto;

public class ProdottoHandler 
{
	public Prodotto prodotto;
	public double quantita;
	
	public ProdottoHandler(Prodotto prodotto, double quantita)
	{
		this.prodotto = prodotto;
		this.quantita = quantita;
	}
}
