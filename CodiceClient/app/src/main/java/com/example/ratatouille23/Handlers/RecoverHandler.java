package com.example.ratatouille23.Handlers;

import com.google.gson.annotations.Expose;

public class RecoverHandler
{
    @Expose
    public String email;
    @Expose
    public String password;
    @Expose
    public String code;
}
