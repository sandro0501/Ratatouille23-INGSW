package com.example.ratatouille23.Handlers;

import com.example.ratatouille23.Models.Utente;
import com.google.gson.annotations.Expose;

public class LoginHandler {

        @Expose
        public Utente utente;
        @Expose
        public String session;
        @Expose
        public String password;
        public LoginHandler() {}

}
