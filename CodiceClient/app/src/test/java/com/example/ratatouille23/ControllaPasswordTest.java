package com.example.ratatouille23;

import static org.junit.Assert.*;

import com.example.ratatouille23.Exceptions.CampiVuotiException;
import com.example.ratatouille23.Exceptions.ConfermaPasswordErrataException;
import com.example.ratatouille23.Exceptions.PasswordNonAdeguataException;
import com.example.ratatouille23.Exceptions.PasswordUgualeException;

import org.junit.Test;

public class ControllaPasswordTest {

    PresenterAreaPersonaleMock presenterAreaPersonale = PresenterAreaPersonaleMock.getInstance();

    @Test
    public void testVecchiaPasswordVuota() {
        assertThrows(
                CampiVuotiException.class,
                ()-> presenterAreaPersonale.controllaPassword("", "Nuova", "Nuova")
        );
    }

    @Test
    public void testNuovaPasswordVuota() {
        assertThrows(
                CampiVuotiException.class,
                ()-> presenterAreaPersonale.controllaPassword("Vecchia", "", "Nuova")
        );
    }

    @Test
    public void testConfermaPasswordVuota() {
        assertThrows(
                CampiVuotiException.class,
                ()-> presenterAreaPersonale.controllaPassword("Vecchia", "Nuova", "")
        );
    }

    @Test
    public void testConfermaPasswordDiversa() {
        assertThrows(
                ConfermaPasswordErrataException.class,
                ()-> presenterAreaPersonale.controllaPassword("Vecchia", "Nuova", "Diversa")
        );
    }

    @Test
    public void testNuovaPasswordUgualeAllaVecchia() {
        assertThrows(
                PasswordUgualeException.class,
                ()-> presenterAreaPersonale.controllaPassword("Vecchia", "Vecchia", "Vecchia")
        );
    }

    @Test
    public void testPasswordTroppoCorta() {
        assertThrows(
                PasswordNonAdeguataException.class,
                ()-> presenterAreaPersonale.controllaPassword("Vecchia", "Nuova", "Nuova")
        );
    }

    @Test
    public void testPasswordSenzaMinuscola() {
        assertThrows(
                PasswordNonAdeguataException.class,
                ()-> presenterAreaPersonale.controllaPassword("Vecchia", "NUOVA123!", "NUOVA123!")
        );
    }

    @Test
    public void testPasswordSenzaMaiuscola() {
        assertThrows(
                PasswordNonAdeguataException.class,
                ()-> presenterAreaPersonale.controllaPassword("Vecchia", "nuova123!", "nuova123!")
        );
    }

    @Test
    public void testPasswordSenzaNumero() {
        assertThrows(
                PasswordNonAdeguataException.class,
                ()-> presenterAreaPersonale.controllaPassword("Vecchia", "nuovaNUOVA!", "nuovaNUOVA!")
        );
    }

    @Test
    public void testPasswordSenzaSimbolo() {
        assertThrows(
                PasswordNonAdeguataException.class,
                ()-> presenterAreaPersonale.controllaPassword("Vecchia", "nuovaNUOVA123", "nuovaNUOVA123")
        );
    }

    @Test
    public void testCampiRiempitiCorrettamente() {
        try {
            presenterAreaPersonale.controllaPassword("Vecchia", "nuovaNUOVA123!", "nuovaNUOVA123!");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
