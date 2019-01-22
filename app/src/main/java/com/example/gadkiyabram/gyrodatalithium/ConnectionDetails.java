package com.example.gadkiyabram.gyrodatalithium;

public class ConnectionDetails {
    boolean wConnected;
    String login;

    ConnectionDetails(boolean wConnected, String login){
        this.wConnected = wConnected;
        this.login = login;
    }

    public boolean iswConnected() {
        return wConnected;
    }

    public void setwConnected(boolean wConnected) {
        this.wConnected = wConnected;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
