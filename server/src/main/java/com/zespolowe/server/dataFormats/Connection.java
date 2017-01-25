package com.zespolowe.server.dataFormats;

import java.time.LocalDateTime;

/**
 *
 * @author Cirben
 */
public class Connection {

    int idA; // id of departure stop
    int idB; // id of arrival stop
    LocalDateTime departure;
    LocalDateTime arrival;
    int id; //connection id for updating
    Transport transport;

    public Connection(int id, int idA, int idB, LocalDateTime departure, LocalDateTime arrival, Transport trns) {
        this.id = id;
        this.idA = idA;
        this.idB = idB;
        this.departure = departure;
        this.arrival = arrival;
        this.transport = trns;
    }

    public Transport getTransport() {
        return transport;
    }

    public Connection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public int getIdA() {
        return idA;
    }

    public int getIdB() {
        return idB;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

}
