package com.example.ratatouille23.Models;

public class Utente {

    private String nome;
    private String cognome;
    private String email;

    public Utente() {};
    public Utente(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public static Utente creaUtenteConRuolo(String nome, String cognome, String email, String ruolo, boolean superAmministratore) {
        Utente utenteCorrente;
        if (ruolo.equals("Amministratore"))
            utenteCorrente = new Amministratore(nome, cognome, email, superAmministratore);
        else if (ruolo.equals("Supervisore"))
            utenteCorrente = new Supervisore(nome, cognome, email);
        else if (ruolo.equals("Addetto alla cucina"))
            utenteCorrente = new Addetto(nome, cognome, email, ruoliPersonale.addettoAllaCucina);
        else
            utenteCorrente = new Addetto(nome, cognome, email, ruoliPersonale.addettoAlServizio);

        return utenteCorrente;
    }

    public String getRuoloUtente() {
        if (this instanceof Amministratore) return "Amministratore";
        else if (this instanceof Supervisore) return "Supervisore";
        else {
            if (((Addetto)this).getRuolo().equals(ruoliPersonale.addettoAllaCucina)) return "Addetto alla cucina";
            else return "Addetto al servizio";
        }
    }

    public String getNomeCompleto() {
        return nome + " " + cognome;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
