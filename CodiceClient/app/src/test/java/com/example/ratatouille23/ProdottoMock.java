package com.example.ratatouille23;

import com.example.ratatouille23.InterfacceMock.IProdotto;

public class ProdottoMock implements IProdotto {

    private double quantita;
    private double soglia;

    public ProdottoMock(double quantita, double soglia) {
        this.quantita = quantita;
        this.soglia = soglia;
    }

    @Override
    public double getQuantita() {
        return quantita;
    }

    @Override
    public double getSoglia() {
        return soglia;
    }
}
