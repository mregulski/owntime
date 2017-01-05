package com.zespolowe.server.dataFormats;

import com.zespolowe.server.PathFindingClasses.VConnection;

import java.util.ArrayList;
import com.zespolowe.server.dataFormats.Transport;
import com.zespolowe.server.dataFormats.TransportType;

/**
 *
 * @author Cirben
 *
 */
public class Path {

    ArrayList<VConnection> p;

    public Path() {
        p = new ArrayList<>();
    }

    public void add(VConnection last) {
        p.add(last);
    }

    /**
     * @return	returns REVERSED array of connections from source to target
     * Collections().reverse(p) to reverse
     */
    public ArrayList<VConnection> getConnectionLists() {
        return p;
    }

    public void print() {
        for (int i = p.size() - 1; i >= 0; --i) {
            System.out.println("  (" + " " + p.get(i).getDeparture() + " [" + p.get(i).getSource().getId() + " --"+tmp(p.get(i).getTransport())+"--> " + p.get(i).getTarget().getId() + "] " + p.get(i).getArrival() + ")  ");
        }
        System.out.println();
    }
    
    private String tmp(Transport t){
        switch(t.type){
            case BUS: return "by bus";
            case FOOT: return "on foot";
            case TRAM: return "by tram";
            case BIKE: return "by bike";
        }
        return "unspecified";
    }
}
