package com.example.ratatouille23.Models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Bacheca implements Serializable {

    @Expose
    Avviso avvisoAssociato;
    @Expose
    boolean visibile;
    @Expose
    boolean visualizzato;

    public Bacheca(Avviso avvisoAssociato, boolean visibile, boolean visualizzato) {
        this.avvisoAssociato = avvisoAssociato;
        this.visibile = visibile;
        this.visualizzato = visualizzato;
    }

    public Avviso getAvvisoAssociato() {
        return avvisoAssociato;
    }

    public void setAvvisoAssociato(Avviso avvisoAssociato) {
        this.avvisoAssociato = avvisoAssociato;
    }

    public boolean isVisibile() {
        return visibile;
    }

    public void setVisibile(boolean visibile) {
        this.visibile = visibile;
    }

    public boolean isVisualizzato() {
        return visualizzato;
    }

    public void setVisualizzato(boolean visualizzato) {
        this.visualizzato = visualizzato;
    }
}
