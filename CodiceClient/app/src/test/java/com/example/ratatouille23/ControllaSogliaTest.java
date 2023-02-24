package com.example.ratatouille23;

import static org.junit.Assert.*;

import com.amplifyframework.core.model.annotations.Index;
import com.example.ratatouille23.Exceptions.ProdottoMalFormatoException;
import com.example.ratatouille23.Presenters.PresenterDispensa;

import org.junit.Test;

import java.util.ArrayList;

public class ControllaSogliaTest {

    PresenterDipensaMock presenterDispensa = PresenterDipensaMock.getInstance();

    @Test
    public void testDispensaPienaPosizioneAccettabile() {
        ArrayList<ProdottoMock> listaProdotti = new ArrayList<>();
        ProdottoMock prodottoMock = new ProdottoMock(1.0, 0.0);
        listaProdotti.add(prodottoMock);
        assertFalse(presenterDispensa.controllaSogliaProdotto(listaProdotti, 0));
    }

    @Test
    public void testDispensaNullPosizioneAccettabile() {
        assertThrows(NullPointerException.class, ()-> {
            presenterDispensa.controllaSogliaProdotto(null, 3);
        });
    }

    @Test
    public void testDispensaVuotaPosizioneAccettabile() {
        assertThrows(IndexOutOfBoundsException.class, ()-> {
            presenterDispensa.controllaSogliaProdotto(new ArrayList<ProdottoMock>(), 1);
        });
    }

    @Test
    public void testDispensaPienaPosizioneNegativa() {
        ArrayList<ProdottoMock> listaProdotti = new ArrayList<>();
        ProdottoMock prodottoMock = new ProdottoMock(1.0, 0.0);
        listaProdotti.add(prodottoMock);
        assertThrows(IndexOutOfBoundsException.class, ()-> {
            presenterDispensa.controllaSogliaProdotto(listaProdotti, -1);
        });
    }

    @Test
    public void testSpecialeProdottoSopraSoglia() {
        ArrayList<ProdottoMock> listaProdotti = new ArrayList<>();
        ProdottoMock prodottoMock = new ProdottoMock(5.0, 0.5);
        listaProdotti.add(prodottoMock);
        assertFalse(presenterDispensa.controllaSogliaProdotto(listaProdotti, 0));
    }

    @Test
    public void testSpecialeProdottoSottoSoglia() {
        ArrayList<ProdottoMock> listaProdotti = new ArrayList<>();
        ProdottoMock prodottoMock = new ProdottoMock(1.0, 2.5);
        listaProdotti.add(prodottoMock);
        assertTrue(presenterDispensa.controllaSogliaProdotto(listaProdotti, 0));
    }

    @Test
    public void testSpecialePosizioneFuoriDispensa() {
        ArrayList<ProdottoMock> listaProdotti = new ArrayList<>();
        ProdottoMock prodottoMock = new ProdottoMock(1.0, 0.0);
        listaProdotti.add(prodottoMock);
        assertThrows(IndexOutOfBoundsException.class, ()-> {
            presenterDispensa.controllaSogliaProdotto(listaProdotti, 2);
        });
    }

    @Test
    public void testSpecialeProdottoQuantitaNegativa() {
        ArrayList<ProdottoMock> listaProdotti = new ArrayList<>();
        ProdottoMock prodottoMock = new ProdottoMock(-1.0, 0.0);
        listaProdotti.add(prodottoMock);
        assertThrows(ProdottoMalFormatoException.class, ()-> {
            presenterDispensa.controllaSogliaProdotto(listaProdotti, 0);
        });
    }

    @Test
    public void testSpecialeProdottoSogliaNegativa() {
        ArrayList<ProdottoMock> listaProdotti = new ArrayList<>();
        ProdottoMock prodottoMock = new ProdottoMock(1.0, -1.0);
        listaProdotti.add(prodottoMock);
        assertThrows(ProdottoMalFormatoException.class, ()-> {
            presenterDispensa.controllaSogliaProdotto(listaProdotti, 0);
        });
    }
}
