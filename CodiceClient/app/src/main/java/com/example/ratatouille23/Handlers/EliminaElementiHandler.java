package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Elemento;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class EliminaElementiHandler {

    @Expose
    ArrayList<Elemento> elementi = new ArrayList<>();

    public EliminaElementiHandler(ArrayList<Elemento> elementi){
        this.elementi = elementi;
    }

}
