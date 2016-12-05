package com.zespolowe.server.PathFindingClasses;

import java.time.LocalDateTime;

import com.zespolowe.server.dataFormats.Connection;
import com.zespolowe.server.dataFormats.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.zespolowe.server.dataFormats.Path;
import com.zespolowe.server.dataFormats.Request;

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
		for (Connection c : connections) {
			VConnection vc = new VConnection(c, this.stops.get(c.getIdA()), this.stops.get(c.getIdB()));
			this.stops.get(c.getIdA()).addConnection(vc);
			this.connections.put(vc.getId(), vc);
		}
		for (Map.Entry<Integer, VPoint> entry : this.stops.entrySet()) {
			entry.getValue().sort();
		}
	}

	public void update(ArrayList<Connection> updatedConnections) {
		//TO:DO
		//delete all old connections
		//update connections from the list
		//push affected stops
		//sort connecion lists in afected stops
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
		VPoint source = stops.get(r.getStopId());
		VPoint target = stops.get(r.getTargetId());
		source.getDValues(threadId).setIn(r.getWhen());
		ArrayList<VPoint> unvisited = new ArrayList<>();
		for (VPoint p : all) {
			unvisited.add(p);
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
				return path;
			}
			for (VConnection v : u.getKConnections(20, u.getDValues(threadId).getIn())) {
				//if in unvisited>??
				DijkstraValues dv = v.getTarget().getDValues(threadId);
				if (dv.getIn() == null || v.getArrival().compareTo(dv.getIn()) < 0) {
					dv.setFastestIn(v);
					dv.setIn(v.getArrival());
				}
			}
		}
		return null;
	}

}
