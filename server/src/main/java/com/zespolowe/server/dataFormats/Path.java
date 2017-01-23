package com.zespolowe.server.dataFormats;

import com.zespolowe.server.PathFindingClasses.VConnection;

import java.util.ArrayList;
import com.zespolowe.server.dataFormats.Transport;
import com.zespolowe.server.dataFormats.TransportType;
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
			System.out.println("  (" + " " + p.get(i).getDeparture() + " [" + p.get(i).getSource().getId() + " --" + tmp(p.get(i).getTransport()) + "--> " + p.get(i).getTarget().getId() + "] " + p.get(i).getArrival() + ")  ");
		}
		System.out.println();
	}

	private String tmp(Transport t) {
		switch (t.type) {
			case BUS:
				return "by bus";
			case FOOT:
				return "on foot";
			case TRAM:
				return "by tram";
			case BIKE:
				return "by bike";
		}
		return "unspecified";
	}

	static Result toResult(Path path) {

		// Start of conversion -Cirben
		ArrayList<VConnection> list = path.getConnectionLists();
		Collections.reverse(list); // so it will be in correct order
		ArrayList<Node> route = new ArrayList<>();
		int currentTripId = list.get(0).getTripId();
		ArrayList<Point> currentStops = new ArrayList<>();
		AdjustedTime departure = new AdjustedTime(list.get(0).getDeparture(), list.get(0).getDeparture()); // for now: planned = predicted
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i).getTripId() == currentTripId) {
				currentStops.add(list.get(i).getSourcePoint());
			} else {
				currentStops.add(list.get(i).getSourcePoint()); // last point in the node
				AdjustedTime arrival = new AdjustedTime(list.get(i - 1).getArrival(), list.get(i - 1).getArrival());
				Transport transport = list.get(i - 1).getTransport(); // all connections with the same tripId are of the same transport
				Node node = new Node(currentStops, transport, departure, arrival);
				route.add(node);

				currentStops = new ArrayList<>(); // reset for new node
				currentStops.add(list.get(i).getSourcePoint());
				departure = new AdjustedTime(list.get(i).getDeparture(), list.get(i).getDeparture());
			}
		}
		int last = list.size() - 1;
		currentStops.add(list.get(last).getTargetPoint());
		AdjustedTime arrival = new AdjustedTime(list.get(last).getArrival(), list.get(last).getArrival());
		Transport transport = list.get(last).getTransport();
		Node node = new Node(currentStops, transport, departure, arrival);
		route.add(node);

		Point start = list.get(0).getSourcePoint();
		Point stop = list.get(list.size() - 1).getTargetPoint();

		Result result = new Result(start, stop, route);
		return result;
	}
}
