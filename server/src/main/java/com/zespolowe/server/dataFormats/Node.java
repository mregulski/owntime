package com.zespolowe.server.dataFormats;

import java.util.ArrayList;

/**
 * Created by lizzard on 23.01.17.
 */
public class Node {
    public ArrayList<Point> stops;
    public Transport transport;
    public AdjustedTime departure;
    public AdjustedTime arrival;
    public ArrayList<Node> alternatives;

    public Node(ArrayList<Point> stops, Transport transport, AdjustedTime departure, AdjustedTime arrival){
        this.stops = stops;
        this.transport = transport;
        this.departure = departure;
        this.arrival = arrival;
        this.alternatives = new ArrayList<>();
    }

    public void addAlternative(Node node){
        this.alternatives.add(node);
    }
}
