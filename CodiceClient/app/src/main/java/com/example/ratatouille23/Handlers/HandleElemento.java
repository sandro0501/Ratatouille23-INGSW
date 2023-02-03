package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.SezioneMenu;
import com.google.gson.annotations.Expose;

public class HandleElemento {

    @Expose
    public Elemento elemento;
    @Expose
    public SezioneMenu sezione;

    public HandleElemento(Elemento elemento) {
        this.elemento = elemento;
        sezione = elemento.getAppartiene();
    }
}