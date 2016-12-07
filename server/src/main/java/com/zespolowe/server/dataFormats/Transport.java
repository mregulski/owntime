package com.zespolowe.server.dataFormats;

/**
 *
 * @author Michał Gadowicz
 */
public class Transport {
    String line;
    TransportType type;

    Transport(TransportType type, String line){
        this.type = type;
        this.line = line;
    }

}
