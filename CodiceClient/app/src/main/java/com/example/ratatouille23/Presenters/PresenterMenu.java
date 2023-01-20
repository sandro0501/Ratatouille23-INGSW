package com.example.ratatouille23.Presenters;

public class PresenterMenu extends PresenterBase {

    private static PresenterMenu instance;

    private PresenterMenu() { };

    public static PresenterMenu getInstance(){

        if (instance==null) {
            instance = new PresenterMenu();
        }
        return instance;

    }
}
