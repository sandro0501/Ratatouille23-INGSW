package com.example.ratatouille23.Models;

public class UtenteFactory {

    private static UtenteFactory instance;

    private UtenteFactory() {};

    public static UtenteFactory getInstance() {
        if (instance == null) instance = new UtenteFactory();
        return instance;
    }

    public Utente getNuovoUtente(String nome, String cognome, String email, String ruolo, boolean superAmministratore) {
        Utente utenteCorrente;
        if (ruolo.equals("Amministratore"))
            utenteCorrente = new Amministratore(nome, cognome, email, superAmministratore);
        else if (ruolo.equals("Supervisore"))
            utenteCorrente = new Supervisore(nome, cognome, email);
        else if (ruolo.equals("Addetto alla cucina"))
            utenteCorrente = new Addetto(nome, cognome, email, ruoliPersonale.addettoAllaCucina);
        else if (ruolo.equals("Sistema"))
            utenteCorrente = new Sistema(nome);
        else
            utenteCorrente = new Addetto(nome, cognome, email, ruoliPersonale.addettoAlServizio);

        return utenteCorrente;
    }
}
