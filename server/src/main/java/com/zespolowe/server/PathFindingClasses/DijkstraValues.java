package com.zespolowe.server.PathFindingClasses;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Cirben
 */
class DijkstraValues {

    VConnection fastestIn;
    boolean visited;
    ArrayList<TravelTime> unTimedConnections = new ArrayList<>(); // when there is no target stop specified this is used as additional on foot connections so the model doesnt need to be changed
    LocalDateTime in;

    DijkstraValues(VConnection a, boolean b, LocalDateTime in) {
        fastestIn = a;
        visited = b;
        this.in = in;
        unTimedConnections=new ArrayList<>();
    }

    DijkstraValues() {
    }

    public VConnection getFastestIn() {
        return fastestIn;
    }

    public void setFastestIn(VConnection fastestIn) {
        this.fastestIn = fastestIn;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public LocalDateTime getIn() {
        return in;
    }

    public void setIn(LocalDateTime in) {
        this.in = in;
    }

    public ArrayList<TravelTime> getUnTimedConnections() {
        return unTimedConnections;
    }

    public void addUnTimedConnection(TravelTime tt){
        unTimedConnections.add(tt);
    }
    
}
