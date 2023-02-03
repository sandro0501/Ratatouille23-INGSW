package com.example.ratatouille23.Handlers;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class HandlerAvvisi
{
    @Expose
    ArrayList<ArrayList<String>> nonVisualizzati;
    @Expose
    ArrayList<ArrayList<String>> visibili;
    @Expose
    ArrayList<ArrayList<String>> nonVisibili;
}
