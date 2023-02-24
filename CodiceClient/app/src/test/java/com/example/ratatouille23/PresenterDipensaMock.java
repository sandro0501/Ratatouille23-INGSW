package com.example.ratatouille23;

import com.example.ratatouille23.DAO.DAOFactory;
import com.example.ratatouille23.DAO.DAOProdotto;
import com.example.ratatouille23.Exceptions.ProdottoMalFormatoException;
import com.example.ratatouille23.InterfacceMock.IProdotto;
import com.example.ratatouille23.Presenters.PresenterDispensa;

import java.util.ArrayList;

public class PresenterDipensaMock {

    private static PresenterDipensaMock instance;

    private DAOProdotto daoProdotto;

    private PresenterDipensaMock() {
    };

    public static PresenterDipensaMock getInstance(){

        if (instance==null) {
            instance = new PresenterDipensaMock();
        }
        return instance;

    }

    public boolean controllaSogliaProdotto(ArrayList<? extends IProdotto> dispensa, int posizioneProdottoInDispensa) {
        double quantitaProdotto;
        double sogliaProdotto;

        quantitaProdotto = dispensa.get(posizioneProdottoInDispensa).getQuantita();
        sogliaProdotto = dispensa.get(posizioneProdottoInDispensa).getSoglia();

        if (sogliaProdotto < 0 || quantitaProdotto < 0) throw new ProdottoMalFormatoException();

        return quantitaProdotto < sogliaProdotto;
    }

}
