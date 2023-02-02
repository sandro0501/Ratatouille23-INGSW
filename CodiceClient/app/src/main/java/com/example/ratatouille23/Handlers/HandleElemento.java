package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Elemento;
import com.example.ratatouille23.Models.SezioneMenu;

public class HandleElemento {
    public Elemento elemento;
    public SezioneMenu sezione;

    public HandleElemento(Elemento elemento) {
        this.elemento = elemento;
        sezione = elemento.getAppartiene();
    }
}
