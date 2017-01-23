package com.zespolowe.server.dataFormats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cirben
 */
public class Point {
	String displayName;
	Coords coords;
	int id;
	ArrayList<Transport> transports;	
    Map<String, Boolean> added;
	
	public Point(int id){
		this.id = id;
	}

	public void addTransport(Transport t){
		String line = t.line;
		if(!added.containsKey(line)){
			transports.add(t);
		}
	}
	
	public Point(int id, String displayName, Coords coords){
		this.id = id;
		this.displayName = displayName;
		this.coords = coords;
		this.transports = new ArrayList<>();
		this.added = new HashMap();
	} 
	
	public Point(int id, String displayName, double x, double y){
		this.id = id;
		this.displayName = displayName;
		coords = new Coords(x,y);
		this.transports = new ArrayList<>();
		this.added = new HashMap();
	}

	public String getDisplayName() {
		return displayName;
	}

	public Coords getCoords() {
		return coords;
	}

	public int getId() {
		return id;
	}

}
