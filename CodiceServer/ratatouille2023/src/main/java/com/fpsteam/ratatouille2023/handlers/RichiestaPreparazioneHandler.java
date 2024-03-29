package com.fpsteam.ratatouille2023.handlers;

public class RichiestaPreparazioneHandler 
{
	public int id_prodotto;
	public String nome;
	public String descrizione;
	public String unita;
	public double quantita;
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getId_prodotto() {
		return id_prodotto;
	}
	public void setId_prodotto(int id_prodotto) {
		this.id_prodotto = id_prodotto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUnita() {
		return unita;
	}
	public void setUnita(String unita) {
		this.unita = unita;
	}
	public double getQuantita() {
		return quantita;
	}
	public void setQuantita(double quantita) {
		this.quantita = quantita;
	}
	public RichiestaPreparazioneHandler(int id_prodotto, String nome, String unita, String descrizione, double quantita) {
		super();
		this.id_prodotto = id_prodotto;
		this.nome = nome;
		this.descrizione = descrizione;
		this.unita = unita;
		this.quantita = quantita;
	}
	
	
	
}
