package com.zespolowe.server.PathFindingClasses;

import com.zespolowe.server.dataFormats.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cirben
 */
public class CommunicationMap {

    Map<Integer, VPoint> stops;
    Map<Integer, VConnection> connections; // for updating graph
    ArrayList<VPoint> all;

    public CommunicationMap(ArrayList<Point> stops, ArrayList<Connection> connections) {
        this.stops = new HashMap<>();
        this.connections = new HashMap<>();
        this.all = new ArrayList<>();

        for (Point p : stops) {
            VPoint tmp = new VPoint(p);
            this.stops.put(p.getId(), tmp);
            this.all.add(tmp);
        }
        for (VPoint p : all) {
            Coords a = p.getCoords();
            ArrayList<TravelTime> tts = new ArrayList<>();
            for (VPoint q : all) {
                Coords b = q.getCoords();
                double j = a.getX() - b.getX();
                double k = a.getY() - b.getY();
                double dist = Math.sqrt(j * j + k * k);
                if (dist < 0.01) { // TO DO: change this loop when working with real data, adjust constants
                    TravelTime tt = new TravelTime((int) ((dist / 0.01) * 10000), p, q);// 600 sec per 0.01 of dist
                    tts.add(tt);
                }
            }
            Collections.sort(tts);
            for (int h = 0; h < Math.min(20, tts.size()); h++) {
                p.addTravelTime(tts.get(h));
            }
        }
        for (Connection c : connections) {
            VConnection vc = new VConnection(c, this.stops.get(c.getIdA()), this.stops.get(c.getIdB()));
            this.stops.get(c.getIdA()).addConnection(vc);
            this.connections.put(vc.getId(), vc);
        }
        /*for (Map.Entry<Integer, VPoint> entry : this.stops.entrySet()) {
			entry.getValue().sort();
		}*/
        for (VPoint a : all) {  // lists in each Vpoint needs to be maintained sorted
            a.sort();
        }
    }

    /**
     *
     * @param updatedConnections list of updated connections; update() replaces
     * connection class in VConnection for updated one
     */
    public void update(ArrayList<Connection> updatedConnections) {
        LocalDateTime a = LocalDateTime.now();
        for (Map.Entry<Integer, VConnection> entry : connections.entrySet()) { //delete all old connections
            VConnection tmp = entry.getValue();
            if (tmp.getDeparture().compareTo(a) < 0) {
                connections.remove(tmp.getId());
                tmp.getSource().removeVConnection(tmp);
            } // now connection is to be handled by garbage collector, or its still in one of answer paths
        }
        ArrayList<VPoint> affected = new ArrayList<>();
        for (Connection c : updatedConnections) { //update connections from the list
            VConnection vc;
            if (connections.containsKey(c.getId())) {
                vc = connections.get(c.getId());
                vc.update(c, stops.get(c.getIdA()), stops.get(c.getIdB())); // update connection with new source and target
            } else {
                vc = new VConnection(c, this.stops.get(c.getIdA()), this.stops.get(c.getIdB()));
                this.stops.get(c.getIdA()).addConnection(vc);
                this.connections.put(vc.getId(), vc);
            }
            affected.add(vc.getSource()); //push affected stops
        }
        for (VPoint vp : affected) {//sort connecion lists in afected stops
            vp.sort();
        }
    }

    public Path findPath(Request r, int threadId) throws MyException {
        //TO:DO check if input values are in "data structure"
        if (r == null) {
            throw new MyException("null request");
        }
        Path path = new Path();
        for (VPoint tmp : all) {
            tmp.initDValues(threadId);
        }

        //################################################################# coords instead of stop id handler section
        VPoint source = null;
        if (!r.isStopIdSpecfied()) {
            source = new VPoint(new Point(-1, "lokacja startowa", r.getStartLocation()));
            source.initDValues(threadId);
            ArrayList<TravelTime> tts = new ArrayList<>();
            for (VPoint q : all) {
                Coords b = q.getCoords();
                Coords a = r.getStartLocation();
                double j = a.getX() - b.getX();
                double k = a.getY() - b.getY();
                double dist = Math.sqrt(j * j + k * k);
                //if (dist < 0.01) { // TO DO: change this loop when working with real data, adjust constants
                TravelTime tt = new TravelTime((int) ((dist / 0.01) * 10000), source, q);// 600 sec per 0.01 of dist
                tts.add(tt);
                //}
            }
            Collections.sort(tts);
            for (int h = 0; h < Math.min(20, tts.size()); h++) {
                source.addTravelTime(tts.get(h));
            }
        } else {
            source = stops.get(r.getStopId());
        }

        VPoint target = null;
        if (!r.isTargetIdSpecfied()) {
            target = new VPoint(new Point(-2, "cel", r.getTargetLocation()));
            target.initDValues(threadId);
            ArrayList<TravelTime> tts = new ArrayList<>();
            for (VPoint q : all) {
                Coords b = q.getCoords();
                Coords a = r.getTargetLocation();
                double j = a.getX() - b.getX();
                double k = a.getY() - b.getY();
                double dist = Math.sqrt(j * j + k * k);
                //if (dist < 0.01) { // TO DO: change this loop when working with real data, adjust constants
                TravelTime tt = new TravelTime((int) ((dist / 0.01) * 10000), q, target);// 600 sec per 0.01 of dist
                tts.add(tt);
                //}
            }
            Collections.sort(tts);
            for (int h = 0; h < Math.min(20, tts.size()); h++) {
                tts.get(h).getSource().getDValues(threadId).addUnTimedConnection(tts.get(h));
            }
        } else {
            target = stops.get(r.getTargetId());
        }
        //#################################################################

        source.getDValues(threadId).setIn(r.getWhen());
        ArrayList<VPoint> unvisited = new ArrayList<>();
        for (VPoint p : all) {
            unvisited.add(p);
            if (unvisited.indexOf(source) == -1) {
                unvisited.add(source);
            }
            if (unvisited.indexOf(target) == -1) {
                unvisited.add(target);
            }
        }
        while (!unvisited.isEmpty()) {
            VPoint u = unvisited.get(0);
            for (VPoint z : unvisited) {
                LocalDateTime utime = u.getDValues(threadId).getIn();
                LocalDateTime ztime = z.getDValues(threadId).getIn();
                if (utime.compareTo(ztime) > 0) {
                    u = z;
                }
            }
            unvisited.remove(u);
            if (u.getId() == target.getId()) {
                VPoint curr = target;
                while (curr.getId() != source.getId()) {
                    VConnection last = curr.getDValues(threadId).getFastestIn();
                    if (last == null) {
                        throw new MyException("Stop unreachable");
                    }
                    path.add(last);
                    curr = last.getSource();
                }
                int a =1+1;
                return path;
            }
            for (VConnection v : u.getKConnections(20, u.getDValues(threadId).getIn(), threadId)) {
                //if in unvisited>??
                DijkstraValues dv;
                if (v.getTarget() == null) {
                    System.out.println("aas");
                }
                dv = v.getTarget().getDValues(threadId);
                if (dv.getIn() == null || v.getArrival().compareTo(dv.getIn()) < 0) {
                    dv.setFastestIn(v);
                    dv.setIn(v.getArrival());
                }
            }
        }
        return null;
    }

}
