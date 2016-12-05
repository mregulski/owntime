package com.zespolowe.server.dataFormats;

import com.zespolowe.server.PathFindingClasses.VConnection;

import java.util.ArrayList;
import java.util.Collections;

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
            System.out.println("  (" + " " + p.get(i).getDeparture() + " [" + p.get(i).getSource().getId() + " --"+p.get(i).getType()+"--> " + p.get(i).getTarget().getId() + "] " + p.get(i).getArrival() + ")  ");
        }
        System.out.println();
    }
}
