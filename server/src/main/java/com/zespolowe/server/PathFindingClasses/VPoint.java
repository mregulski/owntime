package com.zespolowe.server.PathFindingClasses;

import com.zespolowe.server.dataFormats.Connection;
import com.zespolowe.server.dataFormats.Coords;
import com.zespolowe.server.dataFormats.Point;
import com.zespolowe.server.dataFormats.Transport;
import com.zespolowe.server.dataFormats.TransportType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cirben
 */
public class VPoint {

    Point point;
    ArrayList<VConnection> connections;
    ArrayList<TravelTime> unTimedConnections;
    Map<Integer, DijkstraValues> dValues;

    public VPoint(Point point) {
        this.point = point;
        connections = new ArrayList<>();
        unTimedConnections = new ArrayList<>();
        dValues = new HashMap<>();
    }

    public void addConnection(VConnection c) {
        connections.add(c);
    }

    /**
     *
     * @return k planned connections (bus tram etc.) + all connections "on foot"
     */
    public ArrayList<VConnection> getKConnections(int K, LocalDateTime from, int threadId) {
        ArrayList<VConnection> ret = new ArrayList<>();
        for (TravelTime tt : unTimedConnections) {
            ret.add(new VConnection(new Connection(-1, tt.getSource().getId(), tt.getTarget().getId(), from, from.plusSeconds(tt.getSecs()), new Transport(TransportType.FOOT,"")), tt.getSource(), tt.getTarget()));
        }

        for (TravelTime tt : getDValues(threadId).getUnTimedConnections()) { //those are connections unique for each request, they are form closest stops to target loacation (target stop id is not specified, only coords)
            ret.add(new VConnection(new Connection(-1, tt.getSource().getId(), tt.getTarget().getId(), from, from.plusSeconds(tt.getSecs()), new Transport(TransportType.FOOT,"")), tt.getSource(), tt.getTarget()));
        }
        int count = 0;
        for (VConnection c : connections) {
            if (count == K) {
                break;
            }
            if (c.getDeparture().compareTo(from) >= 0) {
                ret.add(c);
                count++;
            }
        }
        return ret;
    }

    void sort() {
        Collections.sort(connections);
    }

    public void initDValues(int threadId) {

        DijkstraValues dv = new DijkstraValues();

        dv.setIn(LocalDateTime.of(3000, 1, 1, 1, 1));
        dv.setFastestIn(null);
        dv.setVisited(false);
        dValues.put(threadId, dv);
    }

    public DijkstraValues getDValues(int threadId) {
        return dValues.get(threadId);
    }

    public int getId() {
        return point.getId();
    }

    public void removeVConnection(VConnection rm) {
        connections.remove(rm);
    }

    public Coords getCoords() {
        return point.getCoords();
    }

    void addTravelTime(TravelTime tt) {
        unTimedConnections.add(tt);
    }
}
