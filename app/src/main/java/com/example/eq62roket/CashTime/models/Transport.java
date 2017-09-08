package com.example.eq62roket.CashTime.models;

/**
 * Created by eq62roket on 9/5/17.
 */

public class Transport {
    int id;
    int transport;

    public Transport(int id, int transport) {
        this.id = id;
        this.transport = transport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }
}
