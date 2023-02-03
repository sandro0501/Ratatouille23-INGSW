package com.example.ratatouille23.Handlers;

import com.google.gson.annotations.Expose;

public class ModificaPasswordHandler {

    @Expose
    public String old;
    @Expose
    public String newp;
    @Expose
    public String accessToken;
    @Expose
    public boolean passc = true;
}
